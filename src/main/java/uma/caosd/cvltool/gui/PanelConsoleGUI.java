package uma.caosd.cvltool.gui;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class PanelConsoleGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private static volatile PanelConsoleGUI console = new PanelConsoleGUI();
	private JTextArea textAreaConsole;

	/**
	 * Create the panel.
	 */
	private PanelConsoleGUI() {
		setBorder(new TitledBorder(null, "Console", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new GridLayout(0, 1, 0, 0));
		
		textAreaConsole = new JTextArea();
		textAreaConsole.setLineWrap(true);
		textAreaConsole.setWrapStyleWord(true);
		textAreaConsole.setColumns(50);
		textAreaConsole.setTabSize(4);
		textAreaConsole.setRows(4);
		
		JScrollPane listScroller = new JScrollPane(textAreaConsole);
		add(listScroller);
	}

	public static PanelConsoleGUI getPanelConsoleGUI() {
		return console;
	}
	
	public void cleanAndWrite(String text) {		
		textAreaConsole.setText(text);
	}
	
	public void write(String text) {
		String pre = textAreaConsole.getText();
		textAreaConsole.setText(pre + "\n" + text);
	}
	
	public void clean() {
		textAreaConsole.setText("");
	}
}
