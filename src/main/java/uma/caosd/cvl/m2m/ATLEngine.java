package uma.caosd.cvl.m2m;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.m2m.atl.common.ATLResourceProvider;
import org.eclipse.m2m.atl.core.ATLCoreException;
import org.eclipse.m2m.atl.core.IExtractor;
import org.eclipse.m2m.atl.core.IInjector;
import org.eclipse.m2m.atl.core.IModel;
import org.eclipse.m2m.atl.core.IReferenceModel;
import org.eclipse.m2m.atl.core.ModelFactory;
import org.eclipse.m2m.atl.core.emf.EMFExtractor;
import org.eclipse.m2m.atl.core.emf.EMFInjector;
import org.eclipse.m2m.atl.core.emf.EMFModelFactory;
import org.eclipse.m2m.atl.core.launch.ILauncher;
import org.eclipse.m2m.atl.engine.compiler.AtlCompiler;
import org.eclipse.m2m.atl.engine.emfvm.launch.EMFVMLauncher;

/**
 * Model to Model (M2M) transformation engine implementation for the ATL transformation language.
 * It provides support to compile and run ATL model transformations at runtime.
 * 
 * @author José Miguel Horcas
 *
 */
public class ATLEngine implements M2MEngine {	
	public static final String ATL_COMPILER_2010 = "atl2010";	/* ATL compiler version */
	
	private static final String MODEL_TRANSFORMATION_COMPILED = "modelTransformation";	/* Name of the compiled model transformation */
	private static final String MODEL_TRANSFORMATION_COMPILED_EXTENSION = ".ams";		/* Extension of the compiled model transformation */
	
	private static final String REFINING_TRACE_METAMODEL = "RefiningTrace.ecore";
	private static final String REFINING_TRACE_MODEL = "refiningTrace.xmi";
	private static final String REFINING_TRACE_METAMODEL_NAME = "RefiningTrace";
	private static final String REFINING_TRACE_MODEL_NAME = "refiningTrace";
	
	private ModelFactory mF;
	private IInjector injector;
	private IExtractor extractor;
	private ILauncher transformationLauncher;
	
	private IModel resultModel;
	private URL resultModelURL;
	
	public ATLEngine() throws ATLCoreException {
		initialize();
	}
	
	public void initialize() {
		mF = new EMFModelFactory();
		injector = new EMFInjector();
		extractor = new EMFExtractor();
		
		transformationLauncher = new EMFVMLauncher();
		transformationLauncher.initialize(new HashMap<String, Object>());
	}

	public void loadModel(String inOutType, URL modelURL, String modelName, URL metamodelURL, String metamodelName) throws M2MEngineException {
		System.out.println("modelName: " + modelName + "->" + metamodelName);
		System.out.println("modelURL: " + modelURL + "->" + metamodelURL);
		try {
			IReferenceModel metamodel = mF.newReferenceModel();
			IModel model = mF.newModel(metamodel);
			
			injectMetamodel(metamodel, metamodelURL.openStream());
			loadModel(inOutType, model, modelURL, modelName, metamodelName);	
		} catch (ATLCoreException e) {
			String errorMessage = "Error loading model/metamodel.\n";
			throw new M2MEngineException(errorMessage + e.getMessage());
		} catch (IOException e) {
			String errorMessage = "Error loading metamodel content.\n";
			throw new M2MEngineException(errorMessage + e.getMessage());
		}
		System.out.println("Model loaded!");
	}
	
	public void loadEMFModel(String inOutType, URL modelURL, String modelName, String metamodelURL, String metamodelName) throws M2MEngineException {
		System.out.println("modelName: " + modelName + "->" + metamodelName);
		System.out.println("modelURL: " + modelURL + "->" + metamodelURL);
		try {
			IReferenceModel metamodel = mF.newReferenceModel();
			IModel model = mF.newModel(metamodel);
			
			injectEMFMetamodel(metamodel, metamodelURL);
			loadModel(inOutType, model, modelURL, modelName, metamodelName);	
		} catch (ATLCoreException e) {
			String errorMessage = "Error loading model/metamodel.\n";
			throw new M2MEngineException(errorMessage + e.getMessage());
		}
		System.out.println("Model loaded!");
	}
	
	private void loadModel(String inOutType, IModel model, URL modelURL, String modelName, String metamodelName) throws ATLCoreException {
		String modelPath = modelURL.getPath();
		
		if (inOutType.equals(IN) || inOutType.equals(IN_OUT)) {
			injectModel(model, modelPath);
		} 
		addModel(inOutType, model, modelName, metamodelName);
		
		if (inOutType.equals(OUT) || inOutType.equals(IN_OUT)) {
			resultModel = model;
			resultModelURL = modelURL;
		}
	}
	
	private void addModel(String inOutType, IModel model, String modelName, String metamodelName) {
		if (inOutType.equals(IN)) {
			transformationLauncher.addInModel(model, modelName, metamodelName);
		} else if (inOutType.equals(OUT)) {
			transformationLauncher.addOutModel(model, modelName, metamodelName);
		} else if (inOutType.equals(IN_OUT)) {
			transformationLauncher.addInOutModel(model, modelName, metamodelName);
		}
	}

	private void injectModel(IModel model, String modelPath) throws ATLCoreException {
		injector.inject(model, modelPath);	
	}
	
	private void injectEMFMetamodel(IReferenceModel metamodel, String metamodelURL) throws ATLCoreException {
		injector.inject(metamodel, metamodelURL);
	}
	
	private void injectMetamodel(IReferenceModel metamodel, InputStream metamodelContent) throws ATLCoreException {
		injector.inject(metamodel, metamodelContent, new HashMap<String, Object>());
	}

	public URL runTransformation(URL modelTransformationCompiled) throws M2MEngineException  {
		try {
			Map<String, Object> options = new HashMap<String, Object>();
			transformationLauncher.launch(ILauncher.RUN_MODE, new NullProgressMonitor(), options, modelTransformationCompiled.openStream());
			extractor.extract(resultModel, resultModelURL.getPath());	
		} catch (IOException e) {
			String errorMessage = "Error reading model transformation file compiled.\n";
			throw new M2MEngineException(errorMessage + e.getMessage()); 
		} catch (ATLCoreException e) {
			String errorMessage = "Error running model transformation.\n";
			throw new M2MEngineException(errorMessage + e.getMessage());
		}
		System.out.println("Transformation performed!");
		return resultModelURL;
	}

	public URL runTransformationRefineMode(URL modelTransformationCompiled) throws M2MEngineException {
		activateRefineMode();
		return runTransformation(modelTransformationCompiled);
	}

	/**
	 * It loads the model and metamodel needed for the refine mode.
	 * @throws M2MEngineException 
	 */
	private void activateRefineMode() throws M2MEngineException {
		try {
			URL refiningMetamodel = ATLResourceProvider.getURL(REFINING_TRACE_METAMODEL);
			File refiningModelFile = new File(REFINING_TRACE_MODEL);
			URL refiningModel = refiningModelFile.toURI().toURL();
			IModel auxResultModel = resultModel; URL auxResultModelURL = resultModelURL; /* preserve current output model */
			loadModel(ATLEngine.OUT, refiningModel, REFINING_TRACE_MODEL_NAME, refiningMetamodel, REFINING_TRACE_METAMODEL_NAME);
			resultModel = auxResultModel; resultModelURL = auxResultModelURL;
		} catch (IOException e) {
			String errorMessage = "Error creating refining trace model.\n";
			throw new M2MEngineException(errorMessage + e.getMessage());
		}
	}
	
	public URL compileTransformation(URL modelTransformation) throws M2MEngineException {
		URL modelTransformationCompiledURL = null;
		try {
			File modelTransformationCompiled = File.createTempFile(MODEL_TRANSFORMATION_COMPILED, MODEL_TRANSFORMATION_COMPILED_EXTENSION);
			modelTransformationCompiledURL = modelTransformationCompiled.toURI().toURL();
			
			//AtlCompiler.getCompiler(AtlCompiler.DEFAULT_COMPILER_NAME).compile(modelTransformation.openStream(), modelTransformationCompiledURL.getPath());
			AtlCompiler.getCompiler(ATL_COMPILER_2010).compile(modelTransformation.openStream(), modelTransformationCompiledURL.getPath());
		} catch (IOException e) {
			String errorMessage = "Error creating model transformation file compiled.\n";
			throw new M2MEngineException(errorMessage + e.getMessage()); 
		}
		System.out.println("Transformation compiled!");
		return modelTransformationCompiledURL;
	}
}
