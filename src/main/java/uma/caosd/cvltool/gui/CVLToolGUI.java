package uma.caosd.cvltool.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import uma.caosd.cvltool.CVLTool;
import uma.caosd.cvltool.CVLTool_Controller;

public class CVLToolGUI {
	public static final String CVL_EXECUTION = "CVL_EXECUTION";
	
	
	private JFrame frmCvlTool;

	
	private DefaultListModel<String> listModelVSpecs;
	private DefaultListModel<String> listModelResolutions;
	private JPanel panelNorth;
	private PanelFileGUI panelFileBaseModel;
	private PanelFileGUI panelFileCVLModel;
	private PanelFileGUI panelFileResolutionModel;
	private JPanel panelCVLModel;
	private JPanel panelResolutionModel;
	private JPanel panelBaseModels;
	private JPanel panelMetamodel;
	private PanelFileGUI panelFileMetamodel;
	private PanelConsoleGUI panelConsole;
	private JPanel panelSouth;
	private JPanel panelCenter;
	private JButton btnCVLExecution;
	private JPanel panel;
	private JPanel panelResolvedModel;
	private PanelFileGUI panelFileResolvedModel;
	private JPanel panelExecution;
	private JPanel panelPredefinedMetamodels;
	private JCheckBox chckbxUML;
	private JPanel panelM2MParameters;
	private JTextField textFieldBaseModelName;
	private JPanel panelBaseModelName;
	private JPanel panelMetamodelName;
	private JTextField textFieldMetamodelName;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CVLToolGUI window = new CVLToolGUI();
					window.frmCvlTool.setVisible(true);
					CVLTool model = new CVLTool();
					CVLTool_Controller controller = new CVLTool_Controller(model, window);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CVLToolGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCvlTool = new JFrame();
		frmCvlTool.setTitle("vEXgine - UMA");
		frmCvlTool.setIconImage(new ImageIcon("src/main/resources/vEXgine.png").getImage());
		frmCvlTool.setBounds(100, 100, 765, 412);
		frmCvlTool.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panelNorth = new JPanel();
		frmCvlTool.getContentPane().add(panelNorth, BorderLayout.NORTH);
		panelNorth.setLayout(new BorderLayout(0, 0));
		
		listModelVSpecs = new DefaultListModel<String>();
		
		listModelResolutions = new DefaultListModel<String>();
		
		panelConsole = PanelConsoleGUI.getPanelConsoleGUI();
		
		panelSouth = new JPanel();
		frmCvlTool.getContentPane().add(panelSouth, BorderLayout.SOUTH);
		panelSouth.add(panelConsole);
		panelSouth.setLayout(new GridLayout(0, 1, 0, 0));
		
		panelCenter = new JPanel();
		frmCvlTool.getContentPane().add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new GridLayout(0, 1, 0, 0));
		
		panel = new JPanel();
		panelCenter.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		panelExecution = new JPanel();
		panel.add(panelExecution);
		panelExecution.setLayout(new GridLayout(0, 1, 0, 0));
		
		panelM2MParameters = new JPanel();
		panelM2MParameters.setBorder(new TitledBorder(null, "M2M parameters", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelExecution.add(panelM2MParameters);
		panelM2MParameters.setLayout(new GridLayout(0, 1, 0, 0));
		
		panelBaseModelName = new JPanel();
		FlowLayout fl_panelBaseModelName = (FlowLayout) panelBaseModelName.getLayout();
		fl_panelBaseModelName.setAlignment(FlowLayout.LEFT);
		panelM2MParameters.add(panelBaseModelName);
		
		JLabel lblBaseModelName = new JLabel("Base model name:");
		panelBaseModelName.add(lblBaseModelName);
		
		textFieldBaseModelName = new JTextField();
		panelBaseModelName.add(textFieldBaseModelName);
		textFieldBaseModelName.setColumns(15);
		
		panelMetamodelName = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelMetamodelName.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panelM2MParameters.add(panelMetamodelName);
		
		JLabel labelMetamodelName = new JLabel("Metamodel name:");
		panelMetamodelName.add(labelMetamodelName);
		
		textFieldMetamodelName = new JTextField();
		textFieldMetamodelName.setColumns(15);
		panelMetamodelName.add(textFieldMetamodelName);
		
		btnCVLExecution = new JButton();
		btnCVLExecution.setIcon(new ImageIcon("src/main/resources/vEXgine.png"));
		panelExecution.add(btnCVLExecution);
		
		panelResolvedModel = new JPanel();
		panelResolvedModel.setBorder(new TitledBorder(null, "Resolved model", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panelResolvedModel);
		panelResolvedModel.setLayout(new GridLayout(0, 1, 0, 0));
		
		panelFileResolvedModel = new PanelFileGUI("Save resolved model...", true);
		panelResolvedModel.add(panelFileResolvedModel);
		panelFileResolvedModel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panelLeft = new JPanel();
		frmCvlTool.getContentPane().add(panelLeft, BorderLayout.WEST);
		panelLeft.setLayout(new GridLayout(0, 1, 0, 0));
		
		panelCVLModel = new JPanel();
		panelCVLModel.setBorder(new TitledBorder(null, "CVL model", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelLeft.add(panelCVLModel);
		panelCVLModel.setLayout(new GridLayout(1, 0, 0, 0));
		
		panelFileCVLModel = new PanelFileGUI("Add CVL model...", false);
		panelCVLModel.add(panelFileCVLModel);
		
		panelResolutionModel = new JPanel();
		panelResolutionModel.setBorder(new TitledBorder(null, "Resolution model", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelLeft.add(panelResolutionModel);
		panelResolutionModel.setLayout(new GridLayout(0, 1, 0, 0));
		
		panelFileResolutionModel = new PanelFileGUI("Add resolution model...", false);
		panelResolutionModel.add(panelFileResolutionModel);
		
		JPanel panelRight = new JPanel();
		frmCvlTool.getContentPane().add(panelRight, BorderLayout.EAST);
		panelRight.setLayout(new GridLayout(0, 1, 0, 0));
		
		panelMetamodel = new JPanel();
		panelMetamodel.setBorder(new TitledBorder(null, "Metamodel", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelRight.add(panelMetamodel);
		panelMetamodel.setLayout(new GridLayout(0, 1, 0, 0));
		
		panelFileMetamodel = new PanelFileGUI("Add metamodel...", false);
		panelMetamodel.add(panelFileMetamodel);
		
		panelPredefinedMetamodels = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelPredefinedMetamodels.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panelPredefinedMetamodels.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Predefined:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelMetamodel.add(panelPredefinedMetamodels);
		
		chckbxUML = new JCheckBox("UML");
		panelPredefinedMetamodels.add(chckbxUML);
		
		panelBaseModels = new JPanel();
		panelBaseModels.setBorder(new TitledBorder(null, "Base models", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelRight.add(panelBaseModels);
		panelBaseModels.setLayout(new GridLayout(1, 0, 0, 0));
		
		panelFileBaseModel = new PanelFileGUI("Add base model...", false);
		panelBaseModels.add(panelFileBaseModel);
	}
	
	public File getCVLModelFile() {
		return panelFileCVLModel.getFile();
	}
	
	public File getResolutionModelFile() {
		return panelFileResolutionModel.getFile();
	}
	
	public File getResolvedModelFile() {
		return panelFileResolvedModel.getFile();
	}
	
	public File getMetamodelFile() {
		return panelFileResolvedModel.getFile();
	}
	
	public String getMetamodelURL() {
		String res = null;
		if (chckbxUML.isSelected()) {
			res = chckbxUML.getText();
		}
		return res;
	}

	public File getBaseModelFile() {
		return panelFileBaseModel.getFile();
	}
	
	public void writeToConsole(String text) {
		panelConsole.write(text);
	}
	
	public void registerController(ActionListener l) {
		btnCVLExecution.addActionListener(l);
		btnCVLExecution.setActionCommand(CVL_EXECUTION);
	}
	
	public String getBaseModelName() {
		return textFieldBaseModelName.getText();
	}
	
	public String getMetamodelName() {
		return textFieldMetamodelName.getText();
	}
}
