package othello;

import java.util.ArrayList;
import java.util.Collections;

public class RandomAI extends Player {

	/* constructor */
	RandomAI(byte player_info, Board gameboard) {
		super(player_info, gameboard);
	}
	
	@Override
	ArrayList<Integer> decideSetPosition() {
		int x, y;
		ArrayList<Integer> position = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> places = this.board.canPutPlace(this);
		
		if(places.isEmpty()) {
		 position.add(Const.CANNOT_PUT);
		 position.add(Const.CANNOT_PUT);
		 return position;
		}
		
		Collections.shuffle(places);
		
		x = places.get(0).get(0);
		y = places.get(0).get(1);
		
		position.add(x); position.add(y);
		return position;
	}

}
