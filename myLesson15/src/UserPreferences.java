
public class UserPreferences implements java.io.Serializable {
	public static final long serialVersionUID = 1L;
	String colourVal;
	String fontVal;
	int sizeVal;

	public void setColour(String colour) {
		colourVal = colour;
	}

	public String getColour() {
		return colourVal;
	}

	public String getFont() {
		return fontVal;
	}

	public void setFont(String font) {
		fontVal = font;
	}

	public void setSize(int size) {
		sizeVal = size;
	}

	public int getSize() {
		return sizeVal;
	}

}
