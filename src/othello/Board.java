package othello;

import java.util.ArrayList;

public class Board {
	/* declare STATE CONSTANT */
	public static final byte BLANK = 0; 
	public static final byte BLACK = 1; //player 1 uses BLACK
	public static final byte WHITE = 2; //player 2 uses WHITE
	public static final byte EDGE = 3;
	
	private byte board[][] = new byte[10][10]; // the surface of board
	private int n_black; // the number of black stones
	private int n_white; // the number of white stones
	private int n_turn; // the number of turns
	
	/* constructor */
	Board() {
		for(int i = 0; i < 10; i++) { 
			for(int j = 0; j < 10; j++) {
				if(i == 0 || i == 9 || j == 0 || j == 9) { // set EDGE
					this.board[i][j] = EDGE;
				}
				else {
					this.board[i][j] = BLANK; //initialize the board
				}
			}	
		}
		this.board[4][4] = this.board[5][5] = WHITE;
		this.board[4][5] = this.board[5][4] = BLACK;
		this.n_black = 2;
		this.n_white = 2;
		this.n_turn = 1;
	}
	
	Board(int TheNumberOfBlack, int TheNumberOfWhite, int turn, byte BoardState[][]) {
		this.n_black = TheNumberOfBlack;
		this.n_white = TheNumberOfWhite;
		this.n_turn = turn;
		
		for(int x = 0; x < 10; x++) {
			for(int y = 0; y < 10; y++) {
				this.board[x][y] = BoardState[x][y];
			}
		}
	}
	
	/* get the state of (x, y) */
	public byte getBoardState(int x, int y) {
		return this.board[x][y];
	}
	
	public byte[][] getBoardStateAll() {
		return this.board;
	}
	
	public int getTurn() {
		return this.n_turn;
	}
	
	/* get the number of BLACK stones */
	public int countBlack() {
		return this.n_black;
	}
	
	/* get the number of WHITE stones */
	public int countWhite() {
		return this.n_white;
	}
	
	/* set the state to (x, y) */
	public void setBoardState(int x, int y, byte state) {
		this.board[x][y] = state;
	}
	
	/* print the board */
	public void printBoard() {
		System.out.println("  ABCDEFGH");
		for(int i = 1; i < 9; i++) {
			System.out.print(i);
			System.out.print(" ");
			for(int j = 1; j < 9; j++) {
				switch(this.board[i][j]) {
				case BLANK:
					System.out.print(".");
					break;
				case BLACK:
					System.out.print("@");
					break;
				case WHITE:
					System.out.print("O");
				}
			}
			System.out.print(" ");
			System.out.println(i);
		}
		System.out.println("  ABCDEFGH");
		System.out.println("[ turn : " + this.n_turn + " ]\t(black, white) = (" + this.n_black + ", " + this.n_white + ")");
	}
	
	/* whether (x, y) is BLANK or not */
	public boolean isEmpty(int x, int y) {
		if(this.board[x][y] == BLANK) return true;
		else return false;
	}

	/* whether (x, y) is BLACK or not */
	public boolean isBlack(int x, int y) {
		if(this.board[x][y] == BLACK) return true;
		else return false;
	}

	/* whether (x, y) is WHITE or not */
	public boolean isWhite(int x, int y) {
		if(this.board[x][y] == WHITE) return true;
		else return false;
	}
	
	/* whether (x, y) is EDGE or not */
	public boolean isEdge(int x, int y) {
		if(this.board[x][y] == EDGE) return true;
		else return false;
	}
	
	/* if a stone is placed at (x, y), how many opponent stones will be reversed? */
	public int countReverseStone(int x, int y, Player player) {
		int count = 0;
		int count_tmp;
		int x_tmp, y_tmp;
		byte color = player.getPlayerInfo();
		
		if(color != BLACK && color != WHITE) return -1; // error
		if(this.board[x][y] != BLANK) return 0; // there is already a stone at (x, y)
		
		switch(color) {
			case BLACK: 
					/* examine in each direction */
					/* 0:upper, 1:upper right, 2:right, 3: lower right, 4:lower, 5:lower left, 6:left, 7:upper left */
					for(int i = 0; i < 8; i++) { 
							switch(i) {
							case 0:
								count_tmp = 0;
								x_tmp = x; y_tmp = y;
								while(this.isWhite(x_tmp-1, y_tmp)) {
									count_tmp++;
									x_tmp--;
								}
								if(this.isBlack(x_tmp-1, y_tmp)) count += count_tmp;
								break;
							case 1:
								count_tmp = 0;
								x_tmp = x; y_tmp = y;
								while(this.isWhite(x_tmp-1, y_tmp+1)) {
									count_tmp++;
									x_tmp--; y_tmp++;
								}
								if(this.isBlack(x_tmp-1, y_tmp+1)) count += count_tmp;
								break;
							case 2:
								count_tmp = 0;
								x_tmp = x; y_tmp = y;
								while(this.isWhite(x_tmp, y_tmp+1)) {
									count_tmp++;
									y_tmp++;
								}
								if(this.isBlack(x_tmp, y_tmp+1)) count += count_tmp;
								break;
							case 3:
								count_tmp = 0;
								x_tmp = x; y_tmp = y;
								while(this.isWhite(x_tmp+1, y_tmp+1)) {
									count_tmp++;
									x_tmp++; y_tmp++;
								}
								if(this.isBlack(x_tmp+1, y_tmp+1)) count += count_tmp;
								break;
							case 4:
								count_tmp = 0;
								x_tmp = x; y_tmp = y;
								while(this.isWhite(x_tmp+1, y_tmp)) {
									count_tmp++;
									x_tmp++;
								}
								if(this.isBlack(x_tmp+1, y_tmp)) count += count_tmp;
								break;
							case 5:
								count_tmp = 0;
								x_tmp = x; y_tmp = y;
								while(this.isWhite(x_tmp+1, y_tmp-1)) {
									count_tmp++;
									x_tmp++; y_tmp--;
								}
								if(this.isBlack(x_tmp+1, y_tmp-1)) count += count_tmp;
								break;
							case 6:
								count_tmp = 0;
								x_tmp = x; y_tmp = y;
								while(this.isWhite(x_tmp, y_tmp-1)) {
									count_tmp++;
									y_tmp--;
								}
								if(this.isBlack(x_tmp, y_tmp-1)) count += count_tmp;
								break;
							case 7:
								count_tmp = 0;
								x_tmp = x; y_tmp = y;
								while(this.isWhite(x_tmp-1, y_tmp-1)) {
									count_tmp++;
									x_tmp--; y_tmp--;
								}
								if(this.isBlack(x_tmp-1, y_tmp-1)) count += count_tmp;
								break;
							}
					}
					break;
					
			case WHITE:
				/* examine in each direction */
				/* 0:upper, 1:upper right, 2:right, 3: lower right, 4:lower, 5:lower left, 6:left, 7:upper left */
				for(int i = 0; i < 8; i++) { 
						switch(i) {
						case 0:
							count_tmp = 0;
							x_tmp = x; y_tmp = y;
							while(this.isBlack(x_tmp-1, y_tmp)) {
								count_tmp++;
								x_tmp--;
							}
							if(this.isWhite(x_tmp-1, y_tmp)) count += count_tmp;
							break;
						case 1:
							count_tmp = 0;
							x_tmp = x; y_tmp = y;
							while(this.isBlack(x_tmp-1, y_tmp+1)) {
								count_tmp++;
								x_tmp--; y_tmp++;
							}
							if(this.isWhite(x_tmp-1, y_tmp+1)) count += count_tmp;
							break;
						case 2:
							count_tmp = 0;
							x_tmp = x; y_tmp = y;
							while(this.isBlack(x_tmp, y_tmp+1)) {
								count_tmp++;
								y_tmp++;
							}
							if(this.isWhite(x_tmp, y_tmp+1)) count += count_tmp;
							break;
						case 3:
							count_tmp = 0;
							x_tmp = x; y_tmp = y;
							while(this.isBlack(x_tmp+1, y_tmp+1)) {
								count_tmp++;
								x_tmp++; y_tmp++;
							}
							if(this.isWhite(x_tmp+1, y_tmp+1)) count += count_tmp;
							break;
						case 4:
							count_tmp = 0;
							x_tmp = x; y_tmp = y;
							while(this.isBlack(x_tmp+1, y_tmp)) {
								count_tmp++;
								x_tmp++;
							}
							if(this.isWhite(x_tmp+1, y_tmp)) count += count_tmp;
							break;
						case 5:
							count_tmp = 0;
							x_tmp = x; y_tmp = y;
							while(this.isBlack(x_tmp+1, y_tmp-1)) {
								count_tmp++;
								x_tmp++; y_tmp--;
							}
							if(this.isWhite(x_tmp+1, y_tmp-1)) count += count_tmp;
							break;
						case 6:
							count_tmp = 0;
							x_tmp = x; y_tmp = y;
							while(this.isBlack(x_tmp, y_tmp-1)) {
								count_tmp++;
								y_tmp--;
							}
							if(this.isWhite(x_tmp, y_tmp-1)) count += count_tmp;
							break;
						case 7:
							count_tmp = 0;
							x_tmp = x; y_tmp = y;
							while(this.isBlack(x_tmp-1, y_tmp-1)) {
								count_tmp++;
								x_tmp--; y_tmp--;
							}
							if(this.isWhite(x_tmp-1, y_tmp-1)) count += count_tmp;
							break;
						}
				}
				break;
		}
		
		return count;
	}
	
	/* look for places on which a stone can be set */
	/* if there is no place, then canPutPlace.isEmpty() is true */
	public ArrayList<ArrayList<Integer>> canPutPlace(Player player) {
		int x, y;
		ArrayList<ArrayList<Integer>> position_list = new ArrayList<ArrayList<Integer>>();
		
		for(x = 1; x < 9; x++) {
			for(y = 1; y < 9; y++) {
				if(this.countReverseStone(x, y, player) > 0) {
					ArrayList<Integer> position = new ArrayList<Integer>();
					position.add(x);
					position.add(y);
					position_list.add(position);
				}
			}
		}
		return position_list;		
	}
	
	/* when a stone is placed, renew the condition of the board */
	/* if there is no reversible stone, return false, if else return true */
	public boolean renewBoard(int x, int y, Player player) {
		if(x == Const.CALL_PASS) {
			this.n_turn++;
			return true;
		}
		else if(x == Const.CANNOT_PUT) {
			this.n_turn++;
			return false;
		}
		else {
		int x_tmp, y_tmp;
		int count = this.countReverseStone(x, y, player);
		byte color = player.getPlayerInfo();
		
		if(count <= 0) return false; // error
		else {
			switch(color) {
				case BLACK:
					this.board[x][y] = BLACK;
					for(int i = 0; i < 8; i++) {
						switch(i) {
							case 0:
								x_tmp = x; y_tmp = y;
								while(this.isWhite(x_tmp-1, y_tmp)) {
									x_tmp--;
								}
								if(this.isBlack(x_tmp-1, y_tmp)) {
									for(int j = 0; j < x - x_tmp; j++) {
										this.board[x_tmp + j][y] = BLACK;
									}
								}
								break;
							case 1:
								x_tmp = x; y_tmp = y;
								while(this.isWhite(x_tmp-1, y_tmp+1)) {
									x_tmp--; y_tmp++;
								}
								if(this.isBlack(x_tmp-1, y_tmp+1)) {
									for(int j = 0; j < x - x_tmp; j++) {
										this.board[x_tmp + j][y_tmp - j] = BLACK;
									}
								}
								break;
							case 2:
								x_tmp = x; y_tmp = y;
								while(this.isWhite(x_tmp, y_tmp+1)) {
									y_tmp++;
								}
								if(this.isBlack(x_tmp, y_tmp+1)) {
									for(int j = 0; j < y_tmp - y; j++) {
										this.board[x_tmp][y_tmp - j] = BLACK;
									}
								}
								break;
							case 3:
								x_tmp = x; y_tmp = y;
								while(this.isWhite(x_tmp+1, y_tmp+1)) {
									x_tmp++; y_tmp++;
								}
								if(this.isBlack(x_tmp+1, y_tmp+1)) {
									for(int j = 0; j < y_tmp - y; j++) {
										this.board[x_tmp - j][y_tmp - j] = BLACK;
									}
								}
								break;
							case 4:
								x_tmp = x; y_tmp = y;
								while(this.isWhite(x_tmp+1, y_tmp)) {
									x_tmp++;
								}
								if(this.isBlack(x_tmp+1, y_tmp)) {
									for(int j = 0; j < x_tmp - x; j++) {
										this.board[x_tmp - j][y] = BLACK;
									}
								}
								break;
							case 5:
								x_tmp = x; y_tmp = y;
								while(this.isWhite(x_tmp+1, y_tmp-1)) {
									x_tmp++; y_tmp--;
								}
								if(this.isBlack(x_tmp+1, y_tmp-1)) {
									for(int j = 0; j < y - y_tmp; j++) {
										this.board[x_tmp - j][y_tmp + j] = BLACK;
									}
								}
								break;
							case 6:
								x_tmp = x; y_tmp = y;
								while(this.isWhite(x_tmp, y_tmp-1)) {
									y_tmp--;
								}
								if(this.isBlack(x_tmp, y_tmp-1)) {
									for(int j = 0; j < y - y_tmp; j++) {
										this.board[x_tmp][y_tmp + j] = BLACK;
									}
								}
								break;
							case 7:
								x_tmp = x; y_tmp = y;
								while(this.isWhite(x_tmp-1, y_tmp-1)) {
									x_tmp--; y_tmp--;
								}
								if(this.isBlack(x_tmp-1, y_tmp-1)) {
									for(int j = 0; j < y - y_tmp; j++) {
										this.board[x_tmp + j][y_tmp + j] = BLACK;
									}
								}
								break;
						}
					}
					this.n_black = this.n_black + count + 1;
					this.n_white = this.n_white - count;
					break;
					
				case WHITE:
					this.board[x][y] = WHITE;
					for(int i = 0; i < 8; i++) {
						switch(i) {
							case 0:
								x_tmp = x; y_tmp = y;
								while(this.isBlack(x_tmp-1, y_tmp)) {
									x_tmp--;
								}
								if(this.isWhite(x_tmp-1, y_tmp)) {
									for(int j = 0; j < x - x_tmp; j++) {
										this.board[x_tmp + j][y] = WHITE;
									}
								}
								break;
							case 1:
								x_tmp = x; y_tmp = y;
								while(this.isBlack(x_tmp-1, y_tmp+1)) {
									x_tmp--; y_tmp++;
								}
								if(this.isWhite(x_tmp-1, y_tmp+1)) {
									for(int j = 0; j < x - x_tmp; j++) {
										this.board[x_tmp + j][y_tmp - j] = WHITE;
									}
								}
								break;
							case 2:
								x_tmp = x; y_tmp = y;
								while(this.isBlack(x_tmp, y_tmp+1)) {
									y_tmp++;
								}
								if(this.isWhite(x_tmp, y_tmp+1)) {
									for(int j = 0; j < y_tmp - y; j++) {
										this.board[x_tmp][y_tmp - j] = WHITE;
									}
								}
								break;
							case 3:
								x_tmp = x; y_tmp = y;
								while(this.isBlack(x_tmp+1, y_tmp+1)) {
									x_tmp++; y_tmp++;
								}
								if(this.isWhite(x_tmp+1, y_tmp+1)) {
									for(int j = 0; j < y_tmp - y; j++) {
										this.board[x_tmp - j][y_tmp - j] = WHITE;
									}
								}
								break;
							case 4:
								x_tmp = x; y_tmp = y;
								while(this.isBlack(x_tmp+1, y_tmp)) {
									x_tmp++;
								}
								if(this.isWhite(x_tmp+1, y_tmp)) {
									for(int j = 0; j < x_tmp - x; j++) {
										this.board[x_tmp - j][y] = WHITE;
									}
								}
								break;
							case 5:
								x_tmp = x; y_tmp = y;
								while(this.isBlack(x_tmp+1, y_tmp-1)) {
									x_tmp++; y_tmp--;
								}
								if(this.isWhite(x_tmp+1, y_tmp-1)) {
									for(int j = 0; j < y - y_tmp; j++) {
										this.board[x_tmp - j][y_tmp + j] = WHITE;
									}
								}
								break;
							case 6:
								x_tmp = x; y_tmp = y;
								while(this.isBlack(x_tmp, y_tmp-1)) {
									y_tmp--;
								}
								if(this.isWhite(x_tmp, y_tmp-1)) {
									for(int j = 0; j < y - y_tmp; j++) {
										this.board[x_tmp][y_tmp + j] = WHITE;
									}
								}
								break;
							case 7:
								x_tmp = x; y_tmp = y;
								while(this.isBlack(x_tmp-1, y_tmp-1)) {
									x_tmp--; y_tmp--;
								}
								if(this.isWhite(x_tmp-1, y_tmp-1)) {
									for(int j = 0; j < y - y_tmp; j++) {
										this.board[x_tmp + j][y_tmp + j] = WHITE;
									}
								}
								break;
						}
					}
					this.n_white = this.n_white + count + 1;
					this.n_black = this.n_black - count;
					break;
				}
		}
		this.n_turn++;
		return true;
		}
	}
	
	/* judge whether the game is finished or not */
	public boolean isGameFinish() {
		return true;
	}
}