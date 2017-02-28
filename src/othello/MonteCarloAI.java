package othello;

import java.util.ArrayList;
import java.util.Arrays;

public class MonteCarloAI extends Player {
	int PLAYOUT;
	
	public MonteCarloAI(byte player_info, Board gameboard) {
		super(player_info, gameboard);
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	public MonteCarloAI(byte player_info, Board gameboard, int playout) {
		super(player_info, gameboard);
		this.PLAYOUT = playout;
	}

	@Override
	ArrayList<Integer> decideSetPosition() {
		// TODO 自動生成されたメソッド・スタブ
		ArrayList<Integer> position = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> places = this.board.canPutPlace(this);
		/*int size = places.size();
		if (size == 0) {
			position.add(Const.CANNOT_PUT);
			position.add(Const.CANNOT_PUT);
			return position;
		} else if (size == 1) {
			position.add((places.get(0)).get(0));
			position.add((places.get(0)).get(1));
			return position;
		} else {
//			for(int try = 0; try < Const.PLAYOUT; try++) {
			int n_trial[] = new int[size];
			int n_victory[] = new int[size];
			Arrays.fill(n_trial, 0);
			Arrays.fill(n_victory, 0);
			int maxWinCountIndex = 0;
			int tmpmax = 0;
			for (int i = 0; i < size; i++) {
				WinCount[i] = 0;
			}

			for (int i = 0; i < size; i++) {
				for (int j = 0; j < tries; j++) {
					Board boardclone = new Board(this.board.countBlack(), this.board.countWhite(), this.board.getTurn(),
							this.board.getBoardStateAll());
					Player myselfclone = new RandomAI(this.player, boardclone);
					Player enemyclone;
					if (this.player == Player.FIRST) {
						enemyclone = new RandomAI(Player.SECOND, boardclone);
					} else {
						enemyclone = new RandomAI(Player.FIRST, boardclone);
					}
					Master masterclone = new Master(boardclone, myselfclone, enemyclone);
					myselfclone.setStone(places.get(i).get(0), places.get(i).get(1));

					int PlayOutResult = this.PlayOut(boardclone, masterclone, myselfclone, enemyclone);
					// if(PlayOutResult == 1) System.out.println("WIN");
					WinCount[i] += PlayOutResult;
				}
				if (WinCount[i] > tmpmax) {
					tmpmax = WinCount[i];
					maxWinCountIndex = i;
				}
				// System.out.print(" wincount[" + i + "]=" + WinCount[i]);
			}
			// System.out.println("");

			position.add(places.get(maxWinCountIndex).get(0));
			position.add(places.get(maxWinCountIndex).get(1));
			System.out.println("maxindes:" + maxWinCountIndex + "\twincount:" + Arrays.toString(WinCount));*/
			return position;
		}
	

	/* win:return 1, lose or draw:return 0 */
	private int PlayOut(Board board, Master master, Player myself, Player enemy) {
		master.playGameWithoutPrint();
		int result = master.getResult();
		if (myself.getPlayerInfo() == Player.FIRST && result == 0) {
			return 1;
		} else if (myself.getPlayerInfo() == Player.SECOND && result == 1) {
			return 1;
		} else
			return 0;
	}

}
