package engine;

import java.util.ArrayList;
import java.util.List;

public class Queen {

	/* Calculates all possible legal moves by the Queen piece  */
	//calculate all possible moves for queen
	public static int calculatePossQueen(int destX, int destY, Pieces p){
		if (p.getType() == Types.QUEEN){
			//reuse the code of king and bishop because queen moves in the same directions
			//first, diagonal movements
			if (Bishop.calculatePossBishop(destX, destY, p) == 1){
				//check if possible moves are diagonal, if true, return right away
				return 1;
			}

			//now vertical and horizontal movements
			if (Rook.calculatePossRook(destX, destY, p) == 1){
				//check if possible moves are horizontal or vertical, if true, return right away
				return 1;
			}
		}
			
		//failed both diagonal and horizontal/vertical tests
		return -1;
	}

}
