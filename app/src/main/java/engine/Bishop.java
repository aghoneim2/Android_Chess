package engine;

import java.util.ArrayList;
import java.util.List;

public class Bishop {

	/* Given coordinates for potential move - all possible and blocked moves are
	first calculated for Bishop piece*/
	//calculates all the possible moves for a bishop
	public static int calculatePossBishop(int destX, int destY, Pieces p){
		List<Integer> possMoves = new ArrayList<Integer>(); 
		if (p.getType() == Types.BISHOP || p.getType() == Types.QUEEN){
			int initX = p.getX();
			int initY = p.getY();
			
			//up right, while i am in boundary
			while (initX >= 0 && initY <= 7){
				if (initX != p.getX() && initY != p.getY()){
					possMoves.add(initX);
					possMoves.add(initY);
				}
				initX--;
				initY++;
			}
			//reset x and y
			initX = p.getX();
			initY = p.getY();
			
			//up left, while i am in boundary
			while (initX >= 0 && initY >= 0){
				if (initX != p.getX() && initY != p.getY()){
					possMoves.add(initX);
					possMoves.add(initY);
				}
				initX--;
				initY--;
			}
			
			//reset x and y
			initX = p.getX();
			initY = p.getY();
			
			//down right, while i am in boundary
			while (initX <= 7 && initY <= 7){
				if (initX != p.getX() && initY != p.getY()){
					possMoves.add(initX);
					possMoves.add(initY);
				}
				initX++;
				initY++;
			}
			
			//reset x and y
			initX = p.getX();
			initY = p.getY();
			
			//down right, while i am in boundary
			while (initX <= 7 && initY >= 0){
				if (initX != p.getX() && initY != p.getY()){
					possMoves.add(initX);
					possMoves.add(initY);
				}
				initX++;
				initY--;
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

	/* Determines list of all possible legal moves by the Bishop piece for AI*/
	public List<Integer> listOfMoves(Pieces p){
		List<Integer> possMoves = new ArrayList<Integer>();
		if (p.getType() == Types.BISHOP || p.getType() == Types.QUEEN) {
			int initX = p.getX();
			int initY = p.getY();

			//up right, while i am in boundary
			while (initX >= 0 && initY <= 7) {
				if (initX != p.getX() && initY != p.getY()) {
					possMoves.add(initX);
					possMoves.add(initY);
				}
				initX--;
				initY++;
			}
			//reset x and y
			initX = p.getX();
			initY = p.getY();

			//up left, while i am in boundary
			while (initX >= 0 && initY >= 0) {
				if (initX != p.getX() && initY != p.getY()) {
					possMoves.add(initX);
					possMoves.add(initY);
				}
				initX--;
				initY--;
			}

			//reset x and y
			initX = p.getX();
			initY = p.getY();

			//down right, while i am in boundary
			while (initX <= 7 && initY <= 7) {
				if (initX != p.getX() && initY != p.getY()) {
					possMoves.add(initX);
					possMoves.add(initY);
				}
				initX++;
				initY++;
			}

			//reset x and y
			initX = p.getX();
			initY = p.getY();

			//down right, while i am in boundary
			while (initX <= 7 && initY >= 0) {
				if (initX != p.getX() && initY != p.getY()) {
					possMoves.add(initX);
					possMoves.add(initY);
				}
				initX++;
				initY--;
			}
		}
		return possMoves;
	}
	
}
