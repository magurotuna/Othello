package othello;

import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Scanner;
import java.util.Map;

public class Master {
	int GameMode; // 0:AI on AI, 1:Human on AI
	Board board;
	Player black, white;
	String BlackPlayerType, WhitePlayerType;
	/*Map<String, Integer> WinCount = new HashMap<String, Integer>() {
		{
			put("Human", 0);
			put("RandomAI", 0);
			put("MaxMinAI", 0);
			put("AlphaBetaAI", 0);
			put("PrimitiveMonteCarloAI", 0);
			put("MonteCarloAI", 0);
		}
	};*/
	Map<String, Integer> WinCount = new HashMap<String, Integer>() {
		{
			put("Black", 0);
			put("White", 0);
		}
	};
	
	Map<String, String> ColorToPlayerType = new HashMap<String, String>() {
		{
			put("Black", null);
			put("White", null);
		}
	};

	/* constructor */
	Master(Board board, Player A, Player B) {
		this.board = board;
		if (A.getPlayerInfo() == Player.FIRST) {
			this.black = A;
			this.white = B;
		} else {
			this.black = B;
			this.white = A;
		}
	}

	Master(Board board) {
		this.board = board;
	}

	Master() {
	}

	public void registerNextBoard(Board NextBoard) {
		this.board = NextBoard;
	}

	public void registerNextPlayer(Player A, Player B) {
		if (A.getPlayerInfo() == Player.FIRST) {
			this.black = A;
			this.white = B;
		} else {
			this.black = B;
			this.white = A;
		}
	}

	/* prepare for starting game */
	/* decide the mode of enemy and the order of playing */
	public void beforeGame() {
		String sep = System.getProperty("line.separator");
		Map<Integer, String> IntToPlayerType = new HashMap<Integer, String>() {
			{
				put(0, "RandomAI");
				put(1, "MaxMinAI");
				put(2, "AlphaBetaAI");
				put(3, "PrimitiveMonteCarloAI");
				put(4, "MonteCarloAI");
				put(5, "Human");
			}
		};

		// String[] AIs = {"Random AI", "MAX/MIN AI", "alpha/beta AI",
		// "Primitive Monte Carlo AI", "Monte Carlo AI"};
		System.out.println("Welcome to Othello Game!!");
		System.out.println("AI同士で対局させたい場合は0，あなたがAIと対局したい場合は1を入力してください..........");
		String gamemode = Main.scan.next();
		while (!gamemode.matches("[0-1]")) {
			System.out.println("0か1を入力してください..........");
			gamemode = Main.scan.next();
		}

		/* AI on AI */
		if (Integer.parseInt(gamemode) == 0) {
			System.out.println("AI同士で対局させます．");
			System.out.println("Random AI : 0" + sep + "MAX/MIN AI : 1" + sep + "alpha/beta AI : 2" + sep
					+ "Primitive Monte Carlo AI : 3" + sep + "Monte Carlo AI : 4" + sep + "先攻にするAIを選んでください..........");
			String first = Main.scan.next();
			while (!first.matches("[0-4]")) {
				System.out.println("そのAIはありません．0〜4のいずれかを入力してください..........");
				first = Main.scan.next();
			}
			if(first.matches("[3-4]")) {
				System.out.println("プレイアウト数を入力してください..........");
				String playout = Main.scan.next();
				while(!OrigInt.isInt(playout)) {
					System.out.println("自然数を入力してください..........");
					playout = Main.scan.next();
				}
				Const.PLAYOUT1 = Integer.parseInt(playout);
			}
			System.out.println("後攻にするAIを選んでください..........");
			String second = Main.scan.next();
			while (!second.matches("[0-4]")) {
				System.out.println("そのAIはありません．0〜4のいずれかを入力してください..........");
				second = Main.scan.next();
			}
			if(second.matches("[3-4]")) {
				System.out.println("プレイアウト数を入力してください..........");
				String playout = Main.scan.next();
				while(!OrigInt.isInt(playout)) {
					System.out.println("自然数を入力してください..........");
					playout = Main.scan.next();
				}
				Const.PLAYOUT2 = Integer.parseInt(playout);
			}

			this.BlackPlayerType = IntToPlayerType.get(Integer.parseInt(first));
			this.WhitePlayerType = IntToPlayerType.get(Integer.parseInt(second));
			this.ColorToPlayerType.put("Black", this.BlackPlayerType);
			this.ColorToPlayerType.put("White", this.WhitePlayerType);
			this.GameMode = 0;
			/*
			 * switch(Integer.parseInt(first)) { case 0: this.black = new
			 * RandomAI(Player.FIRST, this.board); this.BlackPlayerType =
			 * "RandomAI"; break; case 1: this.black = new
			 * MaxMinAI(Player.FIRST, this.board); break; case 2: this.black =
			 * new AlphaBetaAI(Player.FIRST, this.board); break; case 3:
			 * this.black = new PrimitiveMonteCarloAI(Player.FIRST, this.board);
			 * break; case 4: this.black = new MonteCarloAI(Player.FIRST,
			 * this.board); break; }
			 * 
			 * switch(Integer.parseInt(second)) { case 0: this.white = new
			 * RandomAI(Player.SECOND, this.board); break; case 1: this.white =
			 * new MaxMinAI(Player.SECOND, this.board); break; case 2:
			 * this.white = new AlphaBetaAI(Player.SECOND, this.board); break;
			 * case 3: this.white = new PrimitiveMonteCarloAI(Player.SECOND,
			 * this.board); break; case 4: this.white = new
			 * MonteCarloAI(Player.SECOND, this.board); break; }
			 */

			System.out.println("先攻【" + this.BlackPlayerType + "】 VS 後攻【" + this.WhitePlayerType + "】の対局を " + Const.nGAME
					+ "回 行います．");
			/*
			 * try { Thread.sleep(1500); } catch (InterruptedException e) { }
			 */
		}

		/* AI on Human */
		else if (Integer.parseInt(gamemode) == 1) {
			System.out.println("Random AI : 0" + sep + "MAX/MIN AI : 1" + sep + "alpha/beta AI : 2" + sep
					+ "Primitive Monte Carlo AI : 3" + sep + "Monte Carlo AI : 4" + sep + "どのAIと遊びたいですか？..........");

			/* receive enemy mode */
			String enemy = Main.scan.next();
			while (!enemy.matches("[0-4]")) {
				System.out.println("そのAIはありません．0〜4のいずれかを入力してください..........");
				enemy = Main.scan.next();
			}
			if(enemy.matches("[3-4]")) {
				System.out.println("プレイアウト数を入力してください..........");
				String playout = Main.scan.next();
				while(!OrigInt.isInt(playout)) {
					System.out.println("自然数を入力してください..........");
					playout = Main.scan.next();
				}
				Const.PLAYOUT1 = Integer.parseInt(playout);
			}

			/* receive information that tells which player plays first */
			System.out.println("先攻と後攻どちらがいいですか？先攻は0，後攻は1を入力してください..........");
			String order = Main.scan.next();
			while (!order.matches("[0-1]")) {
				System.out.println("0か1を入力してください..........");
				order = Main.scan.next();
			}

			switch (Integer.parseInt(order)) {
			case 0:
				this.BlackPlayerType = IntToPlayerType.get(5);
				this.WhitePlayerType = IntToPlayerType.get(Integer.parseInt(enemy));
				this.ColorToPlayerType.put("Black", this.BlackPlayerType);
				this.ColorToPlayerType.put("White", this.WhitePlayerType);
				this.GameMode = 1;
				/*
				 * switch (Integer.parseInt(enemy)) { case 0: this.white = new
				 * RandomAI(Player.SECOND, this.board); break; case 1:
				 * this.white = new MaxMinAI(Player.SECOND, this.board); break;
				 * case 2: this.white = new AlphaBetaAI(Player.SECOND,
				 * this.board); break; case 3: this.white = new
				 * PrimitiveMonteCarloAI(Player.SECOND, this.board); break; case
				 * 4: this.white = new MonteCarloAI(Player.SECOND, this.board);
				 * break; }
				 */
				break;
			case 1:
				this.BlackPlayerType = IntToPlayerType.get(Integer.parseInt(enemy));
				this.WhitePlayerType = IntToPlayerType.get(5);
				this.ColorToPlayerType.put("Black", this.BlackPlayerType);
				this.ColorToPlayerType.put("White", this.WhitePlayerType);
				this.GameMode = 1;
				/*
				 * switch (Integer.parseInt(enemy)) { case 0: this.black = new
				 * RandomAI(Player.FIRST, this.board); break; case 1: this.black
				 * = new MaxMinAI(Player.FIRST, this.board); break; case 2:
				 * this.black = new AlphaBetaAI(Player.FIRST, this.board);
				 * break; case 3: this.black = new
				 * PrimitiveMonteCarloAI(Player.FIRST, this.board); break; case
				 * 4: this.black = new MonteCarloAI(Player.FIRST, this.board);
				 * break; }
				 */
				break;
			}
		}
	}

	public void playGame() {
		switch (this.GameMode) {
		case 0:
			this.AIonAI();
			break;
		case 1:
			this.HumanonAI();
			break;
		}
	}

	public void playGameWithoutPrint() {
		int unavoidable_pass_count = 0; // if this count is two, this means that
										// the game has been finished
		int set_x, set_y;
		ArrayList<Integer> tmp_array = new ArrayList<Integer>();

		while (unavoidable_pass_count < 2) {
			// this.board.printBoard();
			if (this.board.countBlack() + this.board.countWhite() == 64)
				break;

			if (this.board.getTurn() % 2 != 0) { // black turn
				tmp_array = this.black.decideSetPosition();
				set_x = tmp_array.get(0);
				set_y = tmp_array.get(1);
				if (set_x == Const.CALL_PASS) {
					unavoidable_pass_count = 0;
					this.black.setStone(set_x, set_y);
				} else if (set_x == Const.CANNOT_PUT) {
					unavoidable_pass_count++;
					this.black.setStone(set_x, set_y);
				} else {
					unavoidable_pass_count = 0;
					this.black.setStone(set_x, set_y);
				}
			} else { // white turn
				tmp_array = this.white.decideSetPosition();
				set_x = tmp_array.get(0);
				set_y = tmp_array.get(1);
				if (set_x == Const.CALL_PASS) {
					unavoidable_pass_count = 0;
					this.white.setStone(set_x, set_y);
				} else if (set_x == Const.CANNOT_PUT) {
					unavoidable_pass_count++;
					this.white.setStone(set_x, set_y);
				} else {
					unavoidable_pass_count = 0;
					this.white.setStone(set_x, set_y);
				}
			}
		}
	}

	private void AIonAI() {
		for (int i = 0; i < Const.nGAME; i++) {
			Board board = new Board();
			Player black = this.newPlayer(this.BlackPlayerType, Player.FIRST, board, Const.PLAYOUT1);
			Player white = this.newPlayer(this.WhitePlayerType, Player.SECOND, board, Const.PLAYOUT2);
			this.registerNextBoard(board);
			this.registerNextPlayer(black, white);

			int unavoidable_pass_count = 0; // if this count is two, this means
											// that
			// the game has been finished
			int set_x, set_y;
			ArrayList<Integer> tmp_array = new ArrayList<Integer>();

			while (unavoidable_pass_count < 2) {
				// this.board.printBoard();
				if (this.board.countBlack() + this.board.countWhite() == 64)
					break;

				if (this.board.getTurn() % 2 != 0) { // black turn
					tmp_array = this.black.decideSetPosition();
					set_x = tmp_array.get(0);
					set_y = tmp_array.get(1);
					if (set_x == Const.CALL_PASS) {
						unavoidable_pass_count = 0;
						this.black.setStone(set_x, set_y);
					} else if (set_x == Const.CANNOT_PUT) {
						unavoidable_pass_count++;
						this.black.setStone(set_x, set_y);
					} else {
						unavoidable_pass_count = 0;
						this.black.setStone(set_x, set_y);
					}
				} else { // white turn
					tmp_array = this.white.decideSetPosition();
					set_x = tmp_array.get(0);
					set_y = tmp_array.get(1);
					if (set_x == Const.CALL_PASS) {
						unavoidable_pass_count = 0;
						this.white.setStone(set_x, set_y);
					} else if (set_x == Const.CANNOT_PUT) {
						unavoidable_pass_count++;
						this.white.setStone(set_x, set_y);
					} else {
						unavoidable_pass_count = 0;
						this.white.setStone(set_x, set_y);
					}
				}
			}
			
			System.out.println("count:" + i);
			if (this.getResult() == 0) { // black win
				int count = this.WinCount.get("Black") + 1;
				this.WinCount.put("Black", count);
			} else if (this.getResult() == 1) { // white win
				int count = this.WinCount.get("White") + 1;
				this.WinCount.put("White", count);
			}
		}
	}

	private void HumanonAI() {
		Board board = new Board();
		Player black = this.newPlayer(this.BlackPlayerType, Player.FIRST, board, Const.PLAYOUT1);
		Player white = this.newPlayer(this.WhitePlayerType, Player.SECOND, board, Const.PLAYOUT1);
		this.registerNextBoard(board);
		this.registerNextPlayer(black, white);

		int unavoidable_pass_count = 0; // if this count is two, this means that
		// the game has been finished
		int set_x, set_y;
		ArrayList<Integer> tmp_array = new ArrayList<Integer>();

		while (unavoidable_pass_count < 2) {
			this.board.printBoard();
			if (this.board.countBlack() + this.board.countWhite() == 64)
				break;

			if (this.board.getTurn() % 2 != 0) { // black turn
				tmp_array = this.black.decideSetPosition();
				set_x = tmp_array.get(0);
				set_y = tmp_array.get(1);
				if (set_x == Const.CALL_PASS) {
					unavoidable_pass_count = 0;
					this.black.setStone(set_x, set_y);
				} else if (set_x == Const.CANNOT_PUT) {
					unavoidable_pass_count++;
					this.black.setStone(set_x, set_y);
				} else {
					unavoidable_pass_count = 0;
					this.black.setStone(set_x, set_y);
				}
			} else { // white turn
				tmp_array = this.white.decideSetPosition();
				set_x = tmp_array.get(0);
				set_y = tmp_array.get(1);
				if (set_x == Const.CALL_PASS) {
					unavoidable_pass_count = 0;
					this.white.setStone(set_x, set_y);
				} else if (set_x == Const.CANNOT_PUT) {
					unavoidable_pass_count++;
					this.white.setStone(set_x, set_y);
				} else {
					unavoidable_pass_count = 0;
					this.white.setStone(set_x, set_y);
				}
			}
		}
	}

	private Player newPlayer(String PlayerType, byte player_info, Board board, int... playout) {
		if (PlayerType.equals("RandomAI"))
			return new RandomAI(player_info, board);
		else if (PlayerType.equals("MaxMinAI"))
			return new MaxMinAI(player_info, board);
		else if (PlayerType.equals("AlphaBetaAI"))
			return new AlphaBetaAI(player_info, board);
		else if (PlayerType.equals("PrimitiveMonteCarloAI"))
			return new PrimitiveMonteCarloAI(player_info, board, playout[0]);
		else if (PlayerType.equals("MonteCarloAI"))
			return new MonteCarloAI(player_info, board, playout[0]);
		else if (PlayerType.equals("Human"))
			return new Human(player_info, board);
		else
			return null;
	}

	/*
	 * black win -> 0 white win -> 1 draw game -> 2
	 */
	public int getResult() {
		if (this.board.countBlack() > this.board.countWhite()) {
			return 0;
		} else if (this.board.countBlack() < this.board.countWhite()) {
			return 1;
		} else {
			return 2;
		}
	}

	public void showResult() {
		if (this.GameMode == 0) {
			System.out.println(Const.nGAME + "回ゲームを行った結果");
			System.out.println("先攻 " + this.BlackPlayerType + " : " + this.WinCount.get("Black") + "勝\t後攻 "
					+ this.WhitePlayerType + " : " + this.WinCount.get("White") + "勝");
			System.out.println("でした．");
		} else {
			System.out.println("------------------------------------[RESULT]------------------------------------");
			System.out.println("(black, white) = (" + this.board.countBlack() + ", " + this.board.countWhite() + ")");
			if (this.board.countBlack() > this.board.countWhite()) {
				System.out.println("BLACK WINS!!");
			} else if (this.board.countBlack() < this.board.countWhite()) {
				System.out.println("WHITE WINS!!");
			} else if (this.board.countBlack() == this.board.countWhite()) {
				System.out.println("DRAWN GAME");
			}
		}
	}

	public void printUsage() {
		System.out.println("Welcome to Othello Game!!");
		System.out.println("AIと遊びたい場合は 1 を，自分vs自分をしたい場合は 2 : ");
		System.out.println("まず先攻（1）か後攻（2）か選んでください．1 か 2 を入力してEnter : ");
	}

	/* print places where the player can put his/her stone */
	/* when there is no such place, return false */
	public boolean printWherePut(Player player) {
		int count;
		boolean flag = false;
		String convert[] = { "A", "B", "C", "D", "E", "F", "G", "H" };
		String sep = System.getProperty("line.separator");

		for (int i = 1; i < 9; i++) {
			for (int j = 1; j < 9; j++) {
				if ((count = board.countReverseStone(i, j, player)) > 0) {
					System.out.print(convert[j - 1] + i + ":" + count + "個\t");
					flag = true;
				}
			}
		}
		if (flag) {
			System.out.println(sep + "の相手の石が取れます．");
			return flag;
		} else {
			System.out.println("相手の石が1つも取れません．");
			return flag;
		}
	}

	public void guideEachTurn(int turn) {

	}
}
