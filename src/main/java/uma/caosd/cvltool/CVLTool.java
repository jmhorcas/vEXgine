package uma.caosd.cvltool;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.m2m.atl.core.ATLCoreException;
import org.eclipse.uml2.uml.UMLPackage;

import bvr.BvrPackage;
import bvr.VPackageable;
import bvr.VSpec;
import bvr.VariationPoint;
import uma.caosd.cvl.BaseModel;
import uma.caosd.cvl.m2m.ATLEngine;
import uma.caosd.cvl.m2m.CVLExecution;
import uma.caosd.cvl.m2m.M2MEngine;

public class CVLTool {
	private static final Map<String, String> PREDEFINED_METAMODELS = new HashMap<String, String>();   
	private static final String UML_METAMODEL = "http://www.eclipse.org/uml2/5.0.0/UML";
	
	private VPackageable cvlModel;
	private VPackageable resolutionModel;
	private String metamodelURL;
	private URL baseModel;
	private URL metamodel;
	
	private String metamodelName;
	private String baseModelName;
	
	
	public CVLTool() {
		// load the required meta class packages
		BvrPackage.eINSTANCE.eClass();
		
		// register the factory to be able to read xmi files
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
		
		PREDEFINED_METAMODELS.put("UML", UML_METAMODEL);
	}
	
	public static Map<String, String> getPredefinedMetamodels() {
		return PREDEFINED_METAMODELS;
	}

	public void setCVLModel(File file) throws IOException {
		cvlModel = (VPackageable) getModel(file, BvrPackage.eNS_URI, BvrPackage.eINSTANCE);
	}
	
	public void setResolutionModel(File file) throws IOException {
		resolutionModel = (VPackageable) getModel(file, BvrPackage.eNS_URI, BvrPackage.eINSTANCE);
	}
	
	public void setBaseModel(File file, String name) throws IOException {
		baseModel = file.toURI().toURL();
		baseModelName = name;
	}
	
	public void setMetamodel(File file, String name) throws MalformedURLException {
		metamodel = file.toURI().toURL();
		metamodelURL = null;
		metamodelName = name;
	}
	
	public void setMetamodel(String metamodelURLName, String name) {
		if (metamodelURLName.equals("UML")) {
			/* Load UML metamodel in registry */
			Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(UMLPackage.eNS_URI, UMLPackage.eINSTANCE);
		}
		metamodelURL = PREDEFINED_METAMODELS.get(metamodelURLName);
		metamodel = null;
		metamodelName = name;
	}
	
	
	/**
	 * Get the main object model from a EMF (.xmi) file.
	 * 
	 * @param file
	 * @param eNS_URI
	 * @param eINSTANCE
	 * @return
	 * @throws IOException 
	 */
	private EObject getModel(File file, String eNS_URI, Object eINSTANCE) throws IOException {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(eNS_URI, eINSTANCE);
		
		// load the resource and resolve the proxies
		ResourceSet rs = new ResourceSetImpl();
		Resource r = rs.createResource(URI.createFileURI(file.getAbsolutePath()));
		r.load(null);
		EcoreUtil.resolveAll(rs);
		
		// convert the model to a java model
		EObject model = r.getContents().get(0);
		return model;
	}

	public void cvlExecution() throws ATLCoreException {
		BaseModel bm = null;
		if (metamodel != null) {
			System.out.println("metamodel != null");
			bm = new BaseModel(M2MEngine.IN_OUT, baseModel, baseModelName, metamodel, metamodelName);	
		} else if (metamodelURL != null) {
			System.out.println("metamodelURL != null");
			bm = new BaseModel(M2MEngine.IN_OUT, baseModel, baseModelName, metamodelURL, metamodelName);
		}
		
		M2MEngine m2mEngine = new ATLEngine();
		CVLExecution cvlExecution = new CVLExecution(m2mEngine);
		URL output = cvlExecution.materialize(cvlModel, resolutionModel, bm);
	}
	
	/*
	public void testNewCVLMetamodel() {
		VPackageable m = cvlModel;
		VPackageable rm = resolutionModel;
		
		// comprobar que hay raíz.
		VSpec root = (VSpec) m.eContents().get(0);
		
		TreeIterator<EObject> it = m.eAllContents();
		while (it.hasNext()) {
			EObject o = it.next();
			if (o instanceof VariationPoint) {
				System.out.println(o);
			}
			
		}
		System.out.println("-----------------------------------------------------------");
		
		TreeIterator<EObject> it2 = rm.eAllContents();
		while (it2.hasNext()) {
			EObject o = it2.next();
			System.out.println(o);
		}
		
	}
	*/
}
