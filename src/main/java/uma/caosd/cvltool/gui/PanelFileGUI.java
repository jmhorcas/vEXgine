package uma.caosd.cvltool.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

public class PanelFileGUI extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private static final String ADD = "ADD";
	private static final String DELETE = "DELETE";
	
	private JButton btnAddFile;
	private JButton btnDeleteFile;

	private String btnCaption;
	private File file;
	private boolean save;	/* it is a save file dialog */
	
	
	public PanelFileGUI(String btnCaption, boolean save) {
		this.btnCaption = btnCaption;
		this.file = null;
		this.save = save;
	
		btnAddFile = new JButton(btnCaption);
		add(btnAddFile);
		
		btnDeleteFile = new JButton("");
		btnDeleteFile.setIcon(new ImageIcon("src/main/resources/delete_16x16.png"));
		add(btnDeleteFile);
		btnDeleteFile.setVisible(false);
		
		registerController(this);
	}

	private void addFile() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle(btnCaption);
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
			if (file != null) {
				btnAddFile.setText(file.getName());
				btnDeleteFile.setVisible(true);	
			}
		}
	}
	
	private void addFileSave() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle(btnCaption);
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
			if (file != null) {
				btnAddFile.setText(file.getName());
				btnDeleteFile.setVisible(true);	
			}
		}
	}
	
	private void deleteFile() {
		btnDeleteFile.setVisible(false);
		btnAddFile.setText(btnCaption);
		file = null;
	}
	
	private void registerController(ActionListener l) {
		btnAddFile.addActionListener(l);
		btnAddFile.setActionCommand(ADD);
		btnDeleteFile.addActionListener(l);
		btnDeleteFile.setActionCommand(DELETE);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals(PanelFileGUI.ADD)) {
			if (save) {
				addFileSave();
			} else {
				addFile();	
			}
		} else if (action.equals(PanelFileGUI.DELETE)) {
			deleteFile();
		}
	}
	
	public File getFile() {
		return file;
	}
	
}
