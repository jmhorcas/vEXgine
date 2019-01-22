package uma.caosd.cvl.m2m;

import java.net.URL;

/**
 * Generic Model to Model (M2M) transformation engine.
 * 
 * @author José Miguel Horcas
 *
 */
public interface M2MEngine {
	public static final String IN = "IN";						/* It indicates an input model */
	public static final String OUT = "OUT";						/* It indicates an output model */
	public static final String IN_OUT = "IN_OUT";				/* It indicates an input/output model */

	/**
	 * Initialize the M2M transformation engine.
	 */
	public void initialize();
	
	/**
	 * Load a model conforms to a metamodel.
	 * @param inOutType				IN, OUT, or IN/OUT model.
	 * @param modelURL				URL of the model.
	 * @param modelName				Name of the model in the transformation rule.
	 * @param metamodelURL			URL of the metamodel.
	 * @param metamodelName			Name of the metamodel in the transformation rule.
	 * @throws M2MEngineException 
	 */
	public void loadModel(String inOutType, URL modelURL, String modelName, URL metamodelURL, String metamodelName) throws M2MEngineException;
	
	/**
	 * Load a model conforms to a metamodel.
	 * @param inOutType				IN, OUT, or IN/OUT model.
	 * @param modelURL				URL of the model.
	 * @param modelName				Name of the model in the transformation rule.
	 * @param metamodelURL			URL of the metamodel in the EMF registry.
	 * @param metamodelName			Name of the metamodel in the transformation rule.
	 * @throws M2MEngineException 
	 */
	public void loadEMFModel(String inOutType, URL modelURL, String modelName, String metamodelURL, String metamodelName) throws M2MEngineException;
	
	/**
	 * Run a model transformation at runtime.
	 * @param modelTransformationCompiled	Model transformation compiled file.
	 * @return								Result model of the transformation.
	 * @throws M2MEngineException
	 */
	public URL runTransformation(URL modelTransformationCompiled) throws M2MEngineException;
	
	/**
	 * Run a model transformation in refine mode (i.e., modifying the original input model) at runtime.
	 * @param modelTransformationCompiled	Model transformation compiled file.
	 * @return								Result model of the transformation.
	 * @throws M2MEngineException
	 */
	public URL runTransformationRefineMode(URL modelTransformationCompiled) throws M2MEngineException;
	
	/**
	 * Compile a model transformation at runtime.
	 * @param modelTransformation	Model transformation source file.
	 * @return						Model transformation compiled file.
	 * @throws M2MEngineException
	 */
	public URL compileTransformation(URL modelTransformation) throws M2MEngineException;
}
