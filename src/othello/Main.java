package othello;

import java.util.Scanner;

public class Main {
	public static Scanner scan = new Scanner(System.in);

	public static void main(String args[]) {
		String sep = System.getProperty("line.separator");
		scan.useDelimiter(sep);
		Master master = new Master();
		master.beforeGame();
		master.playGame();
		master.showResult();
		scan.close();
	}
}
