package engine;

import java.util.ArrayList;
import java.util.List;

public class Pawn{

	/* Calculates all possible legal moves by the Pawn piece  */
	//need to calculate all possible moves for any pawn
	public static int calculatePossPawn(int destX, int destY, Pieces p){
		List<Integer> possMoves = new ArrayList<Integer>(); 
		if (p.getType() == Types.PAWN){			
			int initX = p.getX();
			int initY = p.getY();
			
			if (initX > 0 && initX < 7){
				//the move will stay on the board
				//regular move
				if (p.getColor() == 0){
					//white move
					initX-=1;
				}else{
					initX+=1;
				}
			}else{
				//the move will be off the board
			}
			
			//adding moving without attacking
			possMoves.add(initX);
			possMoves.add(initY);
			
			if (initY > 0 && initY < 7){
				//move will stay on board
				//atacking move
				
				possMoves.add(initX);
				possMoves.add(initY-1);//attack left for white, right for black
				
				possMoves.add(initX);
				possMoves.add(initY+1);//attack right for white, left for black
			}else{
				//move will be off the board
				if (initY == 0){
					//white pawn on far left
					possMoves.add(initX);
					possMoves.add(initY+1);
				}else if (initY == 7){
					//white pawn on far right
					possMoves.add(initX);
					possMoves.add(initY-1);
				}
			}
			
			//double first move
			if (p.getPawnFirst()){
				if (p.getColor() == 0){
					//white move
					initX-=1;
				}else{
					initX+=1;
				}
				p.setPawnFirst();
				possMoves.add(initX);
				possMoves.add(initY);
			}
			for (int i = 0; i < possMoves.size(); i+=2){
				//check if the destination X and Y match any of the possible moves
				if (possMoves.get(i) == destX && possMoves.get(i+1) == destY){
					return 1;
				}
			}
		}
		//if reached here, did not return
		return -1;
	}

	/* Returns all moves made by the Pawn piece */
	public List<Integer> listOfMoves(Pieces p){
		List<Integer> possMoves = new ArrayList<Integer>();
		if (p.getType() == Types.PAWN) {
			int initX = p.getX();
			int initY = p.getY();

			if (initX > 0 && initX < 7) {
				//the move will stay on the board
				//regular move
				if (p.getColor() == 0) {
					//white move
					initX -= 1;
				} else {
					initX += 1;
				}
			} else {
				//the move will be off the board
			}

			//adding moving without attacking
			possMoves.add(initX);
			possMoves.add(initY);

			if (initY > 0 && initY < 7) {
				//move will stay on board
				//atacking move

				possMoves.add(initX);
				possMoves.add(initY - 1);//attack left for white, right for black

				possMoves.add(initX);
				possMoves.add(initY + 1);//attack right for white, left for black
			} else {
				//move will be off the board
				if (initY == 0) {
					//white pawn on far left
					possMoves.add(initX);
					possMoves.add(initY + 1);
				} else if (initY == 7) {
					//white pawn on far right
					possMoves.add(initX);
					possMoves.add(initY - 1);
				}
			}

			//double first move
			if (p.getPawnFirst()) {
				if (p.getColor() == 0) {
					//white move
					initX -= 1;
				} else {
					initX += 1;
				}
				p.setPawnFirst();
				possMoves.add(initX);
				possMoves.add(initY);
			}
		}
		return possMoves;
	}

}
