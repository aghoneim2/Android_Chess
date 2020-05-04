package engine;

public class Space {
	
	private int x;
	private int y;
	private Pieces p;
	private boolean filled;

	/* Constructor - sets piece type,coordinates of the field for space estimation */
	public Space(int x, int y, Pieces p, boolean isFilled){
		this.x = x;
		this.y = y;
		this.p = p;
		this.filled = isFilled;
		p.setX(this.x);
		p.setX(this.y);
	}

	/* Returns X coordinate  */
	public int getX(){
		return this.x;
	}

	/* Returns Y coordinate  */
	public int getY(){
		return this.y;
	}

	/* Returns Piece  */
	public Pieces getPiece(){
		return p;
	}

	/* Returns boolean for space check  */
	public boolean isFilled(){
		return this.filled;
	}

	/* Sets X coordinate  */
	public void setX(int x){
		this.x = x;
	}

	/* Sets Y coordinate  */
	public void setY(int y){
		this.y = y;
	}

	/* Sets Piece  */
	public void setPiece(Pieces p){
		this.p = p;
	}

	/* Sets space filled option  */
	public void setFilled(boolean filled){
		this.filled = filled;
	}

	/* Returns color of the chosen piece - black or white */
	public String toString(){
		String toReturn = "XX";
		if(this.p.getType() == Types.ROOK){
			if (this.p.getColor() == 0)
				toReturn = "wR";
			if (this.p.getColor() == 1)
				toReturn = "bR";
		}
		if (this.p.getType() == Types.KNIGHT){
			if (this.p.getColor() == 0)
				toReturn = "wN";
			if (this.p.getColor() == 1)
				toReturn = "bN";
		}
		if (this.p.getType() == Types.BISHOP){
			if (this.p.getColor() == 0)
				toReturn = "wB";
			if (this.p.getColor() == 1)
				toReturn = "bB";
		}
		if (this.p.getType() == Types.QUEEN){
			if (this.p.getColor() == 0)
				toReturn = "wQ";
			if (this.p.getColor() == 1)
				toReturn = "bQ";
		}
		if (this.p.getType() == Types.KING){
			if (this.p.getColor() == 0)
				toReturn = "wK";
			if (this.p.getColor() == 1)
				toReturn = "bK";
		}
		if (this.p.getType() == Types.PAWN){
			if (this.p.getColor() == 0)
				toReturn = "wP";
			if (this.p.getColor() == 1)
				toReturn = "bP";
		}
		
		return toReturn;
	}
	
}
