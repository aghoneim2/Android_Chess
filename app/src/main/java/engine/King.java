package engine;

import java.util.ArrayList;
import java.util.List;

public class King {

	/* Calculates all possible legal moves by the king piece  */
	//calculate all possible moves for king
	public static int calculatePossKing(int destX, int destY, Pieces p){
		List<Integer> possMoves = new ArrayList<Integer>();

		if (p.getType() == Types.KING){
			int initX = p.getX();
			int initY = p.getY();
			
			//downward, downRight and downLeft
			if (initX+1 <= 7){
				possMoves.add(initX+1);
				possMoves.add(initY);
				
				if (initY-1 >= 0){
					possMoves.add(initX+1);
					possMoves.add(initY-1);
				}
				
				if (initY+1 <= 7){
					possMoves.add(initX+1);
					possMoves.add(initY+1);
				}
			}
			
			//upward, upRight and upLeft
			if (initX-1 >= 0){
				possMoves.add(initX-1);
				possMoves.add(initY);
				
				if (initY-1 >= 0){
					possMoves.add(initX-1);
					possMoves.add(initY-1);
				}
				
				if (initY+1 <= 7){
					possMoves.add(initX+-1);
					possMoves.add(initY+1);
				}
			}
			
			//left and right
			if (initY-1 >= 0){
				possMoves.add(initX);
				possMoves.add(initY-1);
			}
			
			if (initY+1 <= 7){
				possMoves.add(initX);
				possMoves.add(initY+1);
			}

			//added all possible moves, now check if destination X and Y are within
			for (int i = 0; i < possMoves.size(); i+=2){
				//check if the destination X and Y match any of the possible moves
				if (possMoves.get(i) == destX && possMoves.get(i+1) == destY){
					return 1;
				}
			}
		}
		return -1;
	}

	/* Returns all moves made by the king piece */
	public List<Integer> listOfMoves(Pieces p){
		List<Integer> possMoves = new ArrayList<Integer>();

		if (p.getType() == Types.KING) {
			int initX = p.getX();
			int initY = p.getY();

			//downward, downRight and downLeft
			if (initX + 1 <= 7) {
				possMoves.add(initX + 1);
				possMoves.add(initY);

				if (initY - 1 >= 0) {
					possMoves.add(initX + 1);
					possMoves.add(initY - 1);
				}

				if (initY + 1 <= 7) {
					possMoves.add(initX + 1);
					possMoves.add(initY + 1);
				}
			}

			//upward, upRight and upLeft
			if (initX - 1 >= 0) {
				possMoves.add(initX - 1);
				possMoves.add(initY);

				if (initY - 1 >= 0) {
					possMoves.add(initX - 1);
					possMoves.add(initY - 1);
				}

				if (initY + 1 <= 7) {
					possMoves.add(initX + -1);
					possMoves.add(initY + 1);
				}
			}

			//left and right
			if (initY - 1 >= 0) {
				possMoves.add(initX);
				possMoves.add(initY - 1);
			}

			if (initY + 1 <= 7) {
				possMoves.add(initX);
				possMoves.add(initY + 1);
			}
		}
		return possMoves;
	}
}
