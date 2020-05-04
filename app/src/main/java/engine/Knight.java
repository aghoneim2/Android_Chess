package engine;

import java.util.ArrayList;
import java.util.List;

public class Knight {

	/* Calculates all possible legal moves by the Knight piece  */
	//calculate all possible moves for knight
	public static int calculatePossKnight(int destX, int destY, Pieces p){
		List<Integer> possMoves = new ArrayList<Integer>();
		if (p.getType() == Types.KNIGHT){
			int initX = p.getX();
			int initY = p.getY();
			
			//up 2 and right 1
			if ((initX-2) >= 0 && (initY+1) <= 7){
				if ((initX-2) != p.getX() && (initY+1) != p.getY()){
					possMoves.add(initX-2);
					possMoves.add((initY+1));
				}
			}
			
			//up 1 and right 2
			if ((initX-1) >= 0 && (initY+2) <= 7){
				if ((initX-1) != p.getX() && (initY+2) != p.getY()){
					possMoves.add(initX-1);
					possMoves.add((initY+2));
				}
			}
			
			//up 2 and left 1
			if ((initX-2) >= 0 && (initY-1) >= 0){
				if ((initX-2) != p.getX() && (initY-1) != p.getY()){
					possMoves.add(initX-2);
					possMoves.add((initY-1));
				}
			}
			
			//up 1 and left 2
			if ((initX-1) >= 0 && (initY-2) >= 0){
				if ((initX-1) != p.getX() && (initY-2) != p.getY()){
					possMoves.add(initX-1);
					possMoves.add((initY-2));
				}
			}
			
			//down 2 and right 1
			if ((initX+2) <= 7 && (initY+1) <= 7){
				if ((initX+2) != p.getX() && (initY+1) != p.getY()){
					possMoves.add(initX+2);
					possMoves.add((initY+1));
				}
			}
			
			//down 1 and right 2
			if ((initX+1) <= 7 && (initY+2) <= 7){
				if ((initX+1) != p.getX() && (initY+2) != p.getY()){
					possMoves.add(initX+1);
					possMoves.add((initY+2));
				}
			}
			
			//down 2 and left 1
			if ((initX+2) <= 7 && (initY-1) >= 0){
				if ((initX+2) != p.getX() && (initY-1) != p.getY()){
					possMoves.add(initX+2);
					possMoves.add((initY-1));
				}
			}
			
			//down 1 and left 2
			if ((initX+1) <= 7 && (initY-2) >= 0){
				if ((initX+1) != p.getX() && (initY-2) != p.getY()){
					possMoves.add(initX+1);
					possMoves.add((initY-2));
				}
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

	/* Returns all moves made by the Knights piece */
	public List<Integer> listOfMoves(Pieces p){
		List<Integer> possMoves = new ArrayList<Integer>();
		if (p.getType() == Types.KNIGHT) {
			int initX = p.getX();
			int initY = p.getY();

			//up 2 and right 1
			if ((initX - 2) >= 0 && (initY + 1) <= 7) {
				if ((initX - 2) != p.getX() && (initY + 1) != p.getY()) {
					possMoves.add(initX - 2);
					possMoves.add((initY + 1));
				}
			}

			//up 1 and right 2
			if ((initX - 1) >= 0 && (initY + 2) <= 7) {
				if ((initX - 1) != p.getX() && (initY + 2) != p.getY()) {
					possMoves.add(initX - 1);
					possMoves.add((initY + 2));
				}
			}

			//up 2 and left 1
			if ((initX - 2) >= 0 && (initY - 1) >= 0) {
				if ((initX - 2) != p.getX() && (initY - 1) != p.getY()) {
					possMoves.add(initX - 2);
					possMoves.add((initY - 1));
				}
			}

			//up 1 and left 2
			if ((initX - 1) >= 0 && (initY - 2) >= 0) {
				if ((initX - 1) != p.getX() && (initY - 2) != p.getY()) {
					possMoves.add(initX - 1);
					possMoves.add((initY - 2));
				}
			}

			//down 2 and right 1
			if ((initX + 2) <= 7 && (initY + 1) <= 7) {
				if ((initX + 2) != p.getX() && (initY + 1) != p.getY()) {
					possMoves.add(initX + 2);
					possMoves.add((initY + 1));
				}
			}

			//down 1 and right 2
			if ((initX + 1) <= 7 && (initY + 2) <= 7) {
				if ((initX + 1) != p.getX() && (initY + 2) != p.getY()) {
					possMoves.add(initX + 1);
					possMoves.add((initY + 2));
				}
			}

			//down 2 and left 1
			if ((initX + 2) <= 7 && (initY - 1) >= 0) {
				if ((initX + 2) != p.getX() && (initY - 1) != p.getY()) {
					possMoves.add(initX + 2);
					possMoves.add((initY - 1));
				}
			}

			//down 1 and left 2
			if ((initX + 1) <= 7 && (initY - 2) >= 0) {
				if ((initX + 1) != p.getX() && (initY - 2) != p.getY()) {
					possMoves.add(initX + 1);
					possMoves.add((initY - 2));
				}
			}
		}
		return possMoves;
	}
	
}
