package uma.caosd.cvltool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.eclipse.m2m.atl.core.ATLCoreException;

import uma.caosd.cvltool.gui.CVLToolGUI;

public class CVLTool_Controller implements ActionListener {
	private CVLToolGUI view;
	private CVLTool model;
	
	public CVLTool_Controller(CVLTool model, CVLToolGUI view) {
		this.model = model;
		this.view = view;
		
		view.registerController(this);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals(CVLToolGUI.CVL_EXECUTION)) {
			//testNewCVLMetamodel();
			loadCVLModel();
			loadResolutionModel();
			loadBaseModel();
			loadMetamodel();
			try {
				model.cvlExecution();
			} catch (ATLCoreException e1) {
				view.writeToConsole("Error executing model transformations. " + e1.getMessage());
			} catch (Exception e2) {
				view.writeToConsole("Error. " + e2.getMessage());
			}
			view.writeToConsole("CVL executed successfully!");
			
		} 
	}
	
	/*
	private void testNewCVLMetamodel() {
		loadCVLModel();
		loadResolutionModel();
		model.testNewCVLMetamodel();
	}*/
	
	private void loadCVLModel() {
		File file = view.getCVLModelFile();
		if (file != null) {
			try {
				model.setCVLModel(file);
			} catch (IOException e) {
				view.writeToConsole("Error loading CVL model. " + e.getMessage());
			}	
		} else {
			view.writeToConsole("Error: CVL model not found.");
		}
	}
	
	private void loadResolutionModel() {
		File file = view.getResolutionModelFile();
		if (file != null) {
			try {
				model.setResolutionModel(file);
			} catch (IOException e) {
				view.writeToConsole("Error loading resolution model. " + e.getMessage());
			}	
		} else {
			view.writeToConsole("Error: Resolution model not found.");
		}
	}
	
	private void loadMetamodel() {
		String name = view.getMetamodelName();
		if (name == null || name.trim().equalsIgnoreCase("")) {
			view.writeToConsole("Error: metamodel name not provided.");
		} else {
			File file = view.getMetamodelFile();
			if (file != null) {
				try {
					model.setMetamodel(file, name);
				} catch (MalformedURLException e) {
					view.writeToConsole("Error loading metamodel. " + e.getMessage());
				}
			} else {
				String metamodelName = view.getMetamodelURL();
				if (metamodelName != null) {
					model.setMetamodel(metamodelName, name);	
				} else {
					view.writeToConsole("Error: Metamodel not found.");
				}
			}
		}
	}
	
	private void loadBaseModel() {
		String name = view.getBaseModelName();
		if (name == null || name.trim().equalsIgnoreCase("")) {
			view.writeToConsole("Error: base model name not provided.");
		} else {
			File file = view.getBaseModelFile();
			if (file != null) {
				try {
					model.setBaseModel(file, name);
				} catch (IOException e) {
					view.writeToConsole("Error loading base model. " + e.getMessage());
				}	
			} else {
				view.writeToConsole("Error: Base model not found.");
			}
		}
	}
	
}
