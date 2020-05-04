package engine;

import java.util.ArrayList;
import java.util.List;

public class Rook {

	/* Calculates all possible legal moves by the Rook piece  */
	//calculate all possible moves for rook
	public static int calculatePossRook(int destX, int destY, Pieces p){
		List<Integer> possMoves = new ArrayList<Integer>(); 
		if (p.getType() == Types.ROOK || p.getType() == Types.QUEEN){			
			int initX = p.getX();
			int initY = p.getY();
			
			//no matter what color, whole row and whole column are valid
			//moving up/down
			while (initX >= 0){
				possMoves.add(initX);
				possMoves.add(initY);
				initX--;
			}
			initX = p.getX();
			while (initX <= 7){
				possMoves.add(initX);
				possMoves.add(initY);
				initX++;
			}
			initX = p.getX();
			
			//moving left/right
			while (initY >= 0){
					possMoves.add(initX);
					possMoves.add(initY);
				initY--;
			}
			initY = p.getY();
			//moving left/right
			while (initY <= 7){
				possMoves.add(initX);
				possMoves.add(initY);
				initY++;
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

	/* Returns all moves made by the Rook piece */
	public List<Integer> listOfMoves(Pieces p){
		List<Integer> possMoves = new ArrayList<Integer>();
		if (p.getType() == Types.ROOK || p.getType() == Types.QUEEN) {
			int initX = p.getX();
			int initY = p.getY();

			//no matter what color, whole row and whole column are valid
			//moving up/down
			while (initX >= 0) {
				possMoves.add(initX);
				possMoves.add(initY);
				initX--;
			}
			initX = p.getX();
			while (initX <= 7) {
				possMoves.add(initX);
				possMoves.add(initY);
				initX++;
			}
			initX = p.getX();

			//moving left/right
			while (initY >= 0) {
				possMoves.add(initX);
				possMoves.add(initY);
				initY--;
			}
			initY = p.getY();
			//moving left/right
			while (initY <= 7) {
				possMoves.add(initX);
				possMoves.add(initY);
				initY++;
			}
		}
		return possMoves;
	}
}
