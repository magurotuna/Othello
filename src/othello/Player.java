package othello;

import java.util.ArrayList;

public abstract class Player {
	public static final byte FIRST = 1; //player who is using black stones
	public static final byte SECOND = 2; //player who is using white stones
	
	protected byte player;
	protected Board board;
	
	/* constructor */
	Player(byte player_info, Board gameboard) {
		if(player_info == FIRST) {
			this.player = FIRST;
			this.board = gameboard;
		}
		else if(player_info == SECOND) {
			this.player = SECOND;
			this.board = gameboard;
		}
	}
	
	Player() {
	}
	
	Player(Board gameboard) {
		this.board = gameboard;
	}

	public byte getPlayerInfo() {
		return this.player;
	}
	
	/* set a stone on (x, y) */
	public void setStone(int x, int y) {
		board.renewBoard(x, y, this);
	}
	
	/* determine where a stone is set */
	abstract ArrayList<Integer> decideSetPosition();
	
	
}
