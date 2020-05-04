package engine;

import java.util.ArrayList;
import java.util.List;

public class Pieces {
	
	private Types type;
	private int color;
	private int x;
	private int y;
	private boolean isPawnFirst = true;

	/* Constructor - sets type,color and coordinates for a piece  */
	public Pieces(Types type, int color){
		this.type = type;
		this.color = color;
		this.x = 0;
		this.y = 0;
	}

	/* Returns type of piece */
	public Types getType(){
		return type;
	}

	/* Returns color of the piece */
	public int getColor(){
		return color;
	}

	/* Returns X coordinate */
	public int getX(){
		return x;
	}

	/* Returns Y coordinate */
	public int getY(){
		return y;
	}

	/* Sets X coordinate for movement conversion */
	public void setX(int x){
		this.x = x;
	}

	/* Sets Y coordinate for movement conversion */
	public void setY(int y){
		this.y = y;
	}

	/* Sets Pawn's first movement */
	public void setPawnFirst(){
		isPawnFirst = false;
	}

	/* Gets Pawn's first movement */
	public boolean getPawnFirst(){
		return isPawnFirst;
	}

	/* Estimates and determines a piece's movement */
	public int possMoves(int destX, int destY, Pieces p){
		int x = 0;
		
		if (p.getType() == Types.PAWN)
			x = calculatePossPawn(destX, destY, p);
		else if (p.getType() == Types.ROOK)
			x = calculatePossRook(destX, destY, p);
		else if (p.getType() == Types.BISHOP)
			x = calculatePossBishop(destX, destY, p);
		else if (p.getType() == Types.KNIGHT)
			x = calculatePossKnight(destX, destY, p);
		else if (p.getType() == Types.QUEEN)
			x = calculatePossQueen(destX, destY, p);
		else if (p.getType() == Types.KING)
			x = calculatePossKing(destX, destY, p);
		
		return x;
	}

	/* Returns Pawn's movement coordinate */
	public int calculatePossPawn(int destX, int destY, Pieces p){
		return Pawn.calculatePossPawn(destX, destY, p);
	}

	/* Returns Rook's movement coordinate */
	public int calculatePossRook(int destX, int destY, Pieces p){
		return Rook.calculatePossRook(destX, destY, p);
	}

	/* Returns Bishop's movement coordinate */
	public int calculatePossBishop(int destX, int destY, Pieces p){
		return Bishop.calculatePossBishop(destX, destY, p);
	}

	/* Returns Knight's movement coordinate */
	public int calculatePossKnight(int destX, int destY, Pieces p){
		return Knight.calculatePossKnight(destX, destY, p);
	}

	/* Returns Queen's movement coordinate */
	public int calculatePossQueen(int destX, int destY, Pieces p){
		return Queen.calculatePossQueen(destX, destY, p);
	}

	/* Returns King's movement coordinate */
	public int calculatePossKing(int destX, int destY, Pieces p){
		return King.calculatePossKing(destX, destY, p);
	}
	
	public String toString(){
		String toReturn = "XX";
		if(this.type == Types.ROOK){
			if (this.color == 0)
				toReturn = "wR";
			if (this.color == 1)
				toReturn = "bR";
		}
		if (this.type == Types.KNIGHT){
			if (this.color == 0)
				toReturn = "wN";
			if (this.color == 1)
				toReturn = "bN";
		}
		if (this.type == Types.BISHOP){
			if (this.color == 0)
				toReturn = "wB";
			if (this.color == 1)
				toReturn = "bB";
		}
		if (this.type == Types.QUEEN){
			if (this.color == 0)
				toReturn = "wQ";
			if (this.color == 1)
				toReturn = "bQ";
		}
		if (this.type == Types.KING){
			if (this.color == 0)
				toReturn = "wK";
			if (this.color == 1)
				toReturn = "bK";
		}
		if (this.type == Types.PAWN){
			if (this.color == 0)
				toReturn = "wP";
			if (this.color == 1)
				toReturn = "bP";
		}
		
		return toReturn;
	}
}
