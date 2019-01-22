package uma.caosd.cvl.m2m;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import bvr.BaseModelHandle;
import bvr.ObjectExistence;
import bvr.VPackageable;
import bvr.VSpec;
import bvr.VSpecResolution;
import bvr.VariationPoint;
import uma.caosd.cvl.BaseModel;
import uma.caosd.cvltool.CVLUtils;

public class CVLExecution {
	public static final String PARAMETERS_MODEL_NAME = "PARAMS";
	public static final String PARAMETERS_METAMODEL_NAME = "Params";
	//public static final String PARAMETERS_METAMODEL_FILE = "CVLParams.ecore";
	public static final String PARAMETERS_METAMODEL_FILE = "src/main/resources/CVLParams.ecore";
	
	//private static final URL PARAMETERS_METAMODEL = ObjectExistence.class.getClassLoader().getResource(PARAMETERS_METAMODEL_FILE);
	//private static final URL PARAMETERS_METAMODEL = new File(PARAMETERS_METAMODEL_FILE).toURI().toURL();
	private static final String PARAMETERS_MODEL_FILE = "CVLParams";
	private static final String PARAMETERS_MODEL_FILE_EXTENSION = ".xmi";
	
	private M2MEngine m2mEngine;
	
	public CVLExecution(M2MEngine m2mEngine) {
		this.m2mEngine = m2mEngine;
	}
	
	public URL materialize(VPackageable variabilityModel, VPackageable resolutionModel, BaseModel... baseModels) {		
		// get output model
		URL outputModel = getOutputModel(baseModels);
		List<VariationPoint> vps = CVLUtils.getAllVariationPoints(variabilityModel);
		System.out.println("vps: " + vps);
		for (VariationPoint vp : vps) {
			VSpec binding = vp.getBindingVSpec();
			System.out.println("binding: " + binding);
			List<VSpecResolution> resolvedBindings = CVLUtils.getResolutionForVSpec(binding, resolutionModel);
			System.out.println("resolvedBindings: " + resolvedBindings);
			for (VSpecResolution res : resolvedBindings) {
				Boolean decision = CVLUtils.getVspecResolutionDecision(res);
				if (decision && !vp.isNegativeVariability() ||
					!decision && vp.isNegativeVariability()) {
					System.out.println("executing variation point: " + vp.getBindingVSpec());
					outputModel = execute(vp, baseModels);
				}
			}
		}
		return outputModel;
	}

	private URL getOutputModel(BaseModel... baseModels) {
		for (BaseModel bm : baseModels) {
			String inOutType = bm.getInOutType(); 
			if (inOutType.equals(M2MEngine.OUT) || inOutType.equals(M2MEngine.IN_OUT)) {
				return bm.getModelURL();
			}
		}
		return null;
	}

	private URL execute(VariationPoint vp, BaseModel... baseModels) {
		URL outputModel = null;
		try {	
			m2mEngine.initialize();
			loadBaseModels(baseModels);
			loadParameters(CVLUtils.getBaseModelHandleReferences(vp));
			File transformation = new File(vp.getSpec().getModelTransformation()); // URL to the model transformation.
			outputModel = m2mEngine.runTransformationRefineMode(transformation.toURI().toURL());
		} catch (M2MEngineException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outputModel;
	}

	private void loadBaseModels(BaseModel... baseModels) throws M2MEngineException {
		for (BaseModel bm : baseModels) {
			if (bm.getMetamodelURL() != null) {
				System.out.println("loading model.");
				m2mEngine.loadModel(bm.getInOutType(), bm.getModelURL(), bm.getModelName(), bm.getMetamodelURL(), bm.getMetamodelName());		
			} else {
				System.out.println("loading EMF model.");
				m2mEngine.loadEMFModel(bm.getInOutType(), bm.getModelURL(), bm.getModelName(), bm.getMetamodelStringURL(), bm.getMetamodelName());
			}
		}
	}
	
	/**
	 * 
	 * @param refs
	 * @throws M2MEngineException
	 * @throws IOException
	 */
	private void loadParameters(List<BaseModelHandle> refs) throws M2MEngineException, IOException {
		File paramsFile = File.createTempFile(PARAMETERS_MODEL_FILE, PARAMETERS_MODEL_FILE_EXTENSION);
		XMLParamsModel.generateConfigXML(refs, paramsFile.getPath());
		System.out.println("paramsFile: " + paramsFile);
		m2mEngine.loadModel(M2MEngine.IN, paramsFile.toURI().toURL(), PARAMETERS_MODEL_NAME, new File(PARAMETERS_METAMODEL_FILE).toURI().toURL(), PARAMETERS_METAMODEL_NAME);
	}
}
