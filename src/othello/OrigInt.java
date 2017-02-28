package othello;

public class OrigInt {
	
	/* input number is integer or not? */
	public static boolean isInt(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException nfex) {
			return false;
		}
	}
	
	public static boolean includes(int lower, int upper, int value) {
		return lower <= value && value <= upper;
	}
	
}
