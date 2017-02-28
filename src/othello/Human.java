package othello;

import java.util.ArrayList;
//import java.util.Scanner;

public class Human extends Player {
	
	/* constructor */
	Human(byte player_info, Board gameboard) {
		super(player_info, gameboard);
	}
	
	@Override
	ArrayList<Integer> decideSetPosition() {
		// TODO 自動生成されたメソッド・スタブ
		ArrayList<Integer> position = new ArrayList<Integer>();
		String sep = System.getProperty("line.separator");
		
		/* get positions where a stone can be put */
		ArrayList<ArrayList<Integer>> can_put_position = this.board.canPutPlace(this);
		
		/* if there is no place to set */
		if(can_put_position.isEmpty()) {
			System.out.println("石を置ける場所がないのでパスします．");
			position.add(Const.CANNOT_PUT);
			position.add(Const.CANNOT_PUT);
			return position;
		}
		System.out.println("置ける場所");
		for(int i = 0; i < can_put_position.size(); i++) {
			switch((can_put_position.get(i)).get(1)) {
			case 1:
				System.out.print(i + ":A" + (can_put_position.get(i)).get(0) + "\t");
				break;
			case 2:
				System.out.print(i + ":B" + (can_put_position.get(i)).get(0) + "\t");
				break;
			case 3:
				System.out.print(i + ":C" + (can_put_position.get(i)).get(0) + "\t");
				break;
			case 4:
				System.out.print(i + ":D" + (can_put_position.get(i)).get(0) + "\t");
				break;
			case 5:
				System.out.print(i + ":E" + (can_put_position.get(i)).get(0) + "\t");
				break;
			case 6:
				System.out.print(i + ":F" + (can_put_position.get(i)).get(0) + "\t");
				break;
			case 7:
				System.out.print(i + ":G" + (can_put_position.get(i)).get(0) + "\t");
				break;
			case 8:
				System.out.print(i + ":H" + (can_put_position.get(i)).get(0) + "\t");
				break;
			}
		}
		System.out.print(sep);
		
		System.out.println("どこに石を置きますか？一覧から置きたい場所の数字を選び入力してください．パスしたい場合は" + can_put_position.size() + "を入力してください..........");
		String input = Main.scan.next();
		int input_number;
		
		while(!OrigInt.isInt(input) || !OrigInt.includes(0, can_put_position.size(), input_number = Integer.parseInt(input))) {
			System.out.println("正しい値を入力してください..........");
			input = Main.scan.next();
		}
		
		if(input_number == can_put_position.size()) {
//			Main.scan.close();
			position.add(Const.CALL_PASS);
			position.add(Const.CALL_PASS);
			return position;
		}
		
		position.add((can_put_position.get(input_number)).get(0));
		position.add((can_put_position.get(input_number)).get(1));
		
		/*String[] xy = input.split("");
		
		while( (!xy[0].matches("[A-H]|[a-h]")) || (!xy[1].matches("[1-8]")) ) {
			System.out.println("入力形式が誤っています．もう一度入力してください..........");
			input = scan.next();
			xy = input.split("");
		}
		
		
		if(xy[0].equals("A") || xy[0].equals("a")) position.add(1);
		else if(xy[0].equals("B") || xy[0].equals("b")) position.add(2);
		else if(xy[0].equals("C") || xy[0].equals("c")) position.add(3);
		else if(xy[0].equals("D") || xy[0].equals("d")) position.add(4);
		else if(xy[0].equals("E") || xy[0].equals("e")) position.add(5);
		else if(xy[0].equals("F") || xy[0].equals("f")) position.add(6);
		else if(xy[0].equals("G") || xy[0].equals("g")) position.add(7);
		else if(xy[0].equals("H") || xy[0].equals("h")) position.add(8);
		
		position.add(Integer.parseInt(xy[1]));*/
//		scan.close();
		return position;
		}

}
