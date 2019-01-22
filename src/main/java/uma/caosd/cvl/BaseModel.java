package uma.caosd.cvl;

import java.net.URL;

public class BaseModel {
	private String inOutType;
	private URL modelURL;
	private String modelName;
	private URL metamodelURL;
	private String metamodelStringURL;
	private String metamodelName;
	
	public BaseModel(String inOutType, URL modelURL, String modelName, URL metamodelURL, String metamodelName) {
		this.inOutType = inOutType;
		this.modelURL = modelURL;
		this.modelName = modelName;
		this.metamodelURL = metamodelURL;
		this.metamodelName = metamodelName;
		this.metamodelStringURL = null;
	}
	
	public BaseModel(String inOutType, URL modelURL, String modelName, String metamodelURL, String metamodelName) {
		this.inOutType = inOutType;
		this.modelURL = modelURL;
		this.modelName = modelName;
		this.metamodelStringURL = metamodelURL;
		this.metamodelName = metamodelName;
		this.metamodelURL = null;
	}

	public String getInOutType() {
		return inOutType;
	}

	public URL getModelURL() {
		return modelURL;
	}

	public String getModelName() {
		return modelName;
	}

	public URL getMetamodelURL() {
		return metamodelURL;
	}

	public String getMetamodelStringURL() {
		return metamodelStringURL;
	}

	public String getMetamodelName() {
		return metamodelName;
	}
}
