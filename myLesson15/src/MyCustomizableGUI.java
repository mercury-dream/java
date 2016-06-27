
/* Create a Java Swing program called MyCustomizableGUI that enables the 
 * user to specify her preferences, such as background color, font family, and size. 
 * Pick any GUI components that have these attributes. Selected values 
 * should be assigned to fields of the serializable class UserPreferences and be 
 * serialized into the file preferences.ser. Each time MyCustomizableGUI is started 
 * it should determine if the file preferences.ser exists. If the file does exist, 
 * MyCustomizableGUI should deserialize it and apply previously saved preferences to the GUI.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.html.StyleSheet;

public class MyCustomizableGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	UserPreferences userPref;
	UserPreferences newUserPref;
	Vector<String> colourVec, fontVec;
	Vector<Integer> sizeVec;
	JFrame frame;
	JPanel panel;
	JComboBox colour, font, sizeB;
	JLabel colourLabel, textLabel, fontLabel;
	// JTextField textField;

	MyCustomizableGUI() {

		userPref = new UserPreferences();
		initVecs();
		panel = new JPanel(new GridLayout(4, 2));

		/* create panel elements START */
		// combobox color
		colour = new JComboBox(colourVec);
		colourLabel = new JLabel("Set colour:");

		// textfield size
		// textField = new JTextField(10);
		sizeB = new JComboBox(sizeVec);
		textLabel = new JLabel("Enter size in int:");

		// combobox font
		font = new JComboBox(fontVec);
		fontLabel = new JLabel("Select font:");

		// fill if preferences already exist
		deserializePref();

		JButton saveBut = new JButton("save pref");
		JButton cancelBut = new JButton("cancel");
		/* create panel elements END */

		/* add action listeners START */
		colour.addActionListener(event -> {
			userPref.setColour(colour.getSelectedItem().toString());
			checkPrefs();
		});

		sizeB.addActionListener(event -> {
			userPref.setSize((Integer) sizeB.getSelectedItem());
			checkPrefs();
		});

		// textField.addKeyListener(new KeyAdapter() {
		// public void keyReleased(KeyEvent e) {
		// JTextField textField = (JTextField) e.getSource();
		// try {
		// // userPref.setSize(Integer.parseInt(textField.getText()));
		// if (Integer.parseInt(textField.getText()) > 7 &&
		// Integer.parseInt(textField.getText()) < 20) {
		// userPref.setSize(Integer.parseInt(textField.getText()));
		// checkPrefs();
		// } else {
		// System.out.println("7 < SIZE < 20");
		// }
		// } catch (NumberFormatException e2) {
		// System.out.println("Enter a number " + e2.toString());
		// }
		// }
		//
		// public void keyTyped(KeyEvent e) {
		// }
		//
		// public void keyPressed(KeyEvent e) {
		// }
		//
		// });

		font.addActionListener(event -> {
			userPref.setFont(font.getSelectedItem().toString());
			checkPrefs();
		});

		saveBut.addActionListener(event -> {
			checkVals();
			checkPrefs();
			serializePref();
		});

		cancelBut.addActionListener(event -> {
			frame.setVisible(false);
		});

		/* add action listeners END */

		/* visualize panel */
		panel.add(colourLabel);
		panel.add(colour);
		panel.add(textLabel);
		panel.add(sizeB);
		panel.add(fontLabel);
		panel.add(font);
		panel.add(saveBut);
		panel.add(cancelBut);

		frame = new JFrame("Set your preferences");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(panel);
		Dimension dim = new Dimension(500, 200);
		frame.setSize(dim);
		frame.setVisible(true);
	}

	public void initVecs() {
		colourVec = new Vector<String>(4);
		colourVec.addElement("green");
		colourVec.addElement("blue");
		colourVec.addElement("yellow");
		colourVec.addElement("red");

		fontVec = new Vector<String>(3);
		fontVec.addElement("Times New Roman");
		fontVec.addElement("Courier");
		fontVec.addElement("Serif");

		sizeVec = new Vector<Integer>(5);
		sizeVec.addElement(5);
		sizeVec.addElement(10);
		sizeVec.addElement(15);
		sizeVec.addElement(20);
		sizeVec.addElement(25);
	}

	public void serializePref() {
		try (FileOutputStream outFile = new FileOutputStream("preferences.ser");
				ObjectOutputStream objOut = new ObjectOutputStream(outFile)) {
			objOut.writeObject(userPref);

		} catch (IOException e) {
			System.out.println("couldnt write file " + e.toString());
		}
	}

	public void deserializePref() {
		File fserFile = new File("preferences.ser");
		if (fserFile.exists() && !fserFile.isDirectory()) {
			try (FileInputStream inFile = new FileInputStream("preferences.ser");
					ObjectInputStream objIn = new ObjectInputStream(inFile)) {
				userPref = (UserPreferences) objIn.readObject();
			} catch (final IOException | ClassNotFoundException e) {
				System.out.println("couldnt read file " + e.toString());
			}
			checkPrefs();
		}
	}

	public void checkPrefs() {
		StyleSheet color = new StyleSheet();
		colour.setSelectedItem(userPref.getColour());
		colourLabel.setForeground(color.stringToColor(userPref.getColour()));
		textLabel.setForeground(color.stringToColor(userPref.getColour()));
		fontLabel.setForeground(color.stringToColor(userPref.getColour()));

		sizeB.setSelectedItem(userPref.getSize());
		// textField.setText(String.valueOf(userPref.getSize()));

		font.setSelectedItem(userPref.getFont());
		colourLabel.setFont(new Font(userPref.getFont(), Font.BOLD, userPref.getSize()));
		fontLabel.setFont(new Font(userPref.getFont(), Font.BOLD, userPref.getSize()));
		textLabel.setFont(new Font(userPref.getFont(), Font.BOLD, userPref.getSize()));
	}

	public void checkVals() {
		if (userPref.getColour() == null) {
			userPref.setColour(colourVec.elementAt(0));
		}
		if (userPref.getFont() == null) {
			userPref.setFont(fontVec.elementAt(0));
		}
		if (userPref.getSize() == 0) {
			userPref.setSize(10);
		}
	}

	public static void main(String[] args) {
		MyCustomizableGUI testGui = new MyCustomizableGUI();
	}
}