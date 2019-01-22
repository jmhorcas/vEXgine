import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import uma.caosd.cvltool.CVLTool;


public class TestReadingFromXMI {
	
	public static void main(String[] args) throws IOException {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("prueba");
		int returnVal = fc.showOpenDialog(new JFrame());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			CVLTool cvlTool = new CVLTool();
			cvlTool.setCVLModel(file);
		}
	}
}
