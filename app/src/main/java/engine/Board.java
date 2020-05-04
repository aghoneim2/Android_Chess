package engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board {
	
	private static Pieces[][] board;
	public static Space[][] board2;
	public boolean isWhite;
	private List<Pieces> deadWhite = new ArrayList<Pieces>();
	private List<Pieces> deadBlack = new ArrayList<Pieces>();
	private List<Pieces> whiteInPlay = new ArrayList<>();
	private List<Pieces> blackInPlay = new ArrayList<>();
	private Space blackKing;
	private Space whiteKing;
	private boolean chechStatus = false;
	private boolean deadKing = false;
	private int kingKiller = 0;

	/* Initializes a 2D array for the board for moves and pieces */
	public Board(){
		board = new Pieces[8][8];
		board2 = new Space[8][8];
	}

	/* Fills the 2D array sketched board with all pieces(Black and white) with filled spaced colors */
	public void fillBoard2(){
		for (int i = 0; i < board2.length; i++){
			for (int j = 0; j < board2[0].length; j++){
				if (i == 7){
					switch (j){
						case 0: board2[i][j] = new Space(i, j, new Pieces (Types.ROOK, 0), true);
								whiteInPlay.add(board2[i][j].getPiece());
								break;
						case 7: board2[i][j] = new Space(i, j, new Pieces (Types.ROOK, 0), true);
								whiteInPlay.add(board2[i][j].getPiece());
								break;
						case 1: board2[i][j] = new Space(i, j, new Pieces (Types.KNIGHT, 0), true);
								whiteInPlay.add(board2[i][j].getPiece());
								break;
						case 6: board2[i][j] = new Space(i, j, new Pieces (Types.KNIGHT, 0), true);
								whiteInPlay.add(board2[i][j].getPiece());
								break;
						case 2: board2[i][j] = new Space(i, j, new Pieces (Types.BISHOP, 0), true);
								whiteInPlay.add(board2[i][j].getPiece());
								break;
						case 5: board2[i][j] = new Space(i, j, new Pieces (Types.BISHOP, 0), true);
								whiteInPlay.add(board2[i][j].getPiece());
								break;
						case 3: board2[i][j] = new Space(i, j, new Pieces (Types.QUEEN, 0), true);
								whiteInPlay.add(board2[i][j].getPiece());
								break;
						case 4: board2[i][j] = new Space(i, j, new Pieces (Types.KING, 0), true);
								whiteInPlay.add(board2[i][j].getPiece());
								break;
					}
				}else if (i == 6){
					board2[i][j] = new Space(i, j, new Pieces (Types.PAWN, 0), true);
					whiteInPlay.add(board2[i][j].getPiece());
				}else if (i > 1 && i < 6){
					board2[i][j] = new Space(i, j, new Pieces (Types.EMPTY, -1), false);
				}else if(i == 0){
					switch (j){
					case 0: board2[i][j] = new Space(i, j, new Pieces (Types.ROOK, 1), true);
							blackInPlay.add(board2[i][j].getPiece());
							break;
					case 7: board2[i][j] = new Space(i, j, new Pieces (Types.ROOK, 1), true);
							blackInPlay.add(board2[i][j].getPiece());
							break;
					case 1: board2[i][j] = new Space(i, j, new Pieces (Types.KNIGHT, 1), true);
							blackInPlay.add(board2[i][j].getPiece());
							break;
					case 6: board2[i][j] = new Space(i, j, new Pieces (Types.KNIGHT, 1), true);
							blackInPlay.add(board2[i][j].getPiece());
							break;
					case 2: board2[i][j] = new Space(i, j, new Pieces (Types.BISHOP, 1), true);
							blackInPlay.add(board2[i][j].getPiece());
							break;
					case 5: board2[i][j] = new Space(i, j, new Pieces (Types.BISHOP, 1), true);
							blackInPlay.add(board2[i][j].getPiece());
							break;
					case 3: board2[i][j] = new Space(i, j, new Pieces (Types.QUEEN, 1), true);
							blackInPlay.add(board2[i][j].getPiece());
							break;
					case 4: board2[i][j] = new Space(i, j, new Pieces (Types.KING, 1), true);
							blackInPlay.add(board2[i][j].getPiece());
							break;
				}
				}else if (i == 1){
					board2[i][j] = new Space(i, j, new Pieces (Types.PAWN, 1), true);
					blackInPlay.add(board2[i][j].getPiece());
				}
			}
		}
		
		
		for (int i = 0; i < board2.length; i++){
			for (int j = 0; j < board2[0].length; j++){
				if (board2[i][j].isFilled()){
					board2[i][j].getPiece().setX(i);
					board2[i][j].getPiece().setY(j);
				}
			}
		}
		
		blackKing = board2[0][4];
		whiteKing = board2[7][4];
	}

	/* Returns board to keep track of the board for spaces after the moves */
	public Space[][] getBoard(){
		return board2;
	}

	/* Checks and converts coordinates into moves for pieces e.g movements,promotion etc */
	public int translateInput(int inX, int inY, int outX, int outY){
		//convert based on ascii
		int inI = inX;
		int inJ = inY;
		int outI = outX;
		int outJ = outY;
		
		if (inI < board2.length && inJ < board2[0].length){
			//coordinates in board boundaries
			if (board2[inI][inJ].isFilled()){
				//if there is a piece to move
				if (board2[inI][inJ].getPiece().getType() == Types.PAWN){
					//if moving a pawn
					//first check if move is possible
					if (board2[inI][inJ].getPiece().possMoves(outI, outJ, board2[inI][inJ].getPiece()) == 1){
						//move is possible
						if (board2[outI][outJ].isFilled() == false){
							//empty space, switch the space allocation on board
							if (inJ == outJ)
								moveEmptySpace(inI, inJ, outI, outJ);//regular move is only ahead, not to the sides
							else
								return -1;
						}else{
							//space is filled, must check if enemy or friendly
							if (board2[outI][outJ].getPiece().getColor() == board2[inI][inJ].getPiece().getColor()){
								//friendly, can't move there
								return -1;
							}else{
								//kill move, add piece to dead pieces, remove it from board
								if (inJ == outJ)
									return -1;//cant kill forward

								killMove(inI, inJ, outI, outJ);
							}
						}
					}else if (board2[inI][inJ].getPiece().possMoves(outI, outJ, board2[inI][inJ].getPiece()) == 3){
						//pawn promotion for white
						if (deadWhite.size() == 0){
							//"No dead pieces to promote");
							return -1;
						}
						
						if (board2[outI][outJ].isFilled() == false){
							Scanner scan = new Scanner(System.in);
							boolean check = false;
							while (true){
								//choose piece to promote
								String input = scan.nextLine();
								
								for (int i = 0; i < deadWhite.size(); i++){
									if (input.equals(deadWhite.get(i).toString())){
										board2[outI][outJ] = new Space(outI, outJ, deadWhite.get(i), true);
										board2[inI][inJ] = new Space(inI, inJ, (new Pieces (Types.EMPTY, -1)), false);
										board2[outI][outJ].setX(outI);
										board2[outI][outJ].setY(outJ);
										board2[outI][outJ].getPiece().setX(outI);
										board2[outI][outJ].getPiece().setY(outJ);
										deadWhite.remove(i);
										check = true;
										break;
									}
								}
								
								if (!check){
									//System.out.println("Please try again");
								}else{
									//System.out.println();
									break;
								}
							}
							scan.close();
						}else{
							Scanner scan = new Scanner(System.in);
							boolean check = false;
							while (true){

								//System.out.println("Choose Piece to promote (e.g: \"wQ\"): ");
								String input = scan.nextLine();
								
								for (int i = 0; i < deadWhite.size(); i++){
									if (input.equals(deadWhite.get(i).toString())){
										killMove(inI, inJ, outI, outJ);
										board2[outI][outJ] = new Space(outI, outJ, deadWhite.get(i), true);
										board2[outI][outJ].setX(outI);
										board2[outI][outJ].setY(outJ);
										board2[outI][outJ].getPiece().setX(outI);
										board2[outI][outJ].getPiece().setY(outJ);
										deadWhite.remove(i);
										check = true;
										break;
									}
								}
								
								if (!check){
									//System.out.println("Please try again");
								}else{
									//System.out.println();
									break;
								}
							}
							scan.close();
						}
						
						
						
					}else if(board2[inI][inJ].getPiece().possMoves(outI, outJ, board2[inI][inJ].getPiece()) == 4){
						//pawn promotion for black
						
						if (deadBlack.size() == 0){
							System.out.println("No dead pieces to promote");
							return -1;
						}
						
						if (board2[outI][outJ].isFilled() == false){
							Scanner scan = new Scanner(System.in);
							boolean check = false;
							while (true){
								System.out.println("\n");
								System.out.println(deadBlack);
								System.out.println("Choose Piece to promote (e.g: \"wQ\"): ");
								String input = scan.nextLine();
								
								for (int i = 0; i < deadBlack.size(); i++){
									if (input.equals(deadBlack.get(i).toString())){
										board2[outI][outJ] = new Space(outI, outJ, deadBlack.get(i), true);
										board2[inI][inJ] = new Space(inI, inJ, (new Pieces (Types.EMPTY, -1)), false);
										board2[outI][outJ].setX(outI);
										board2[outI][outJ].setY(outJ);
										board2[outI][outJ].getPiece().setX(outI);
										board2[outI][outJ].getPiece().setY(outJ);
										deadBlack.remove(i);
										check = true;
										break;
									}
								}
								
								if (!check){
									System.out.println("Please try again");
								}else{
									System.out.println();
									break;
								}
							}
							scan.close();
						}else{
							Scanner scan = new Scanner(System.in);
							boolean check = false;
							while (true){
								System.out.println("\n");
								System.out.println(deadBlack);
								System.out.println("Choose Piece to promote (e.g: \"wQ\"): ");
								String input = scan.nextLine();
								
								for (int i = 0; i < deadBlack.size(); i++){
									if (input.equals(deadBlack.get(i).toString())){
										killMove(inI, inJ, outI, outJ);
										board2[outI][outJ] = new Space(outI, outJ, deadBlack.get(i), true);
										board2[outI][outJ].setX(outI);
										board2[outI][outJ].setY(outJ);
										board2[outI][outJ].getPiece().setX(outI);
										board2[outI][outJ].getPiece().setY(outJ);
										deadBlack.remove(i);
										check = true;
										break;
									}
								}
								
								if (!check){
									System.out.println("Please try again");
								}else{
									System.out.println();
									break;
								}
							}
							scan.close();
						}
						
					}
				}else if (board2[inI][inJ].getPiece().getType() == Types.ROOK){
					if (board2[inI][inJ].getPiece().possMoves(outI, outJ, board2[inI][inJ].getPiece()) == 1){
						if (pathCheckerRook(outI, outJ, board2[inI][inJ].getPiece()) == false){
							//path clear
							if (board2[outI][outJ].isFilled() == false){
								moveEmptySpace(inI, inJ, outI, outJ);
							}else{
								//place is filled, check friendly first
								if (board2[outI][outJ].getPiece().getColor() == board2[inI][inJ].getPiece().getColor()){
									//friendly, can't move there
									return -1;
								}
								//kill move, add piece to dead pieces, remove it from board
								killMove(inI, inJ, outI, outJ);
							}
						}else{
							//path is not clear
							//System.out.println("Illegal Move");
							return -1;
						}
					}
				}else if (board2[inI][inJ].getPiece().getType() == Types.BISHOP){					
					if (board2[inI][inJ].getPiece().possMoves(outI, outJ, board2[inI][inJ].getPiece()) == 1){
						//move is possible, now need to check paths
						if (pathCheckerBishop(outI, outJ, board2[inI][inJ].getPiece()) == false){
							//the path is clear
							if (board2[outI][outJ].isFilled() == false){
								//path is clear, move there
								moveEmptySpace(inI, inJ, outI, outJ);
							}else{
								//check friendly first
								if (board2[outI][outJ].getPiece().getColor() == board2[inI][inJ].getPiece().getColor()){
									//friendly, can't move there
									return -1;
								}
								//kill move, add piece to dead pieces, remove it from board
								killMove(inI, inJ, outI, outJ);
							}
						}else{
							//pathCheckerBishop failed
							return -1;
						}
					}
				}else if(board2[inI][inJ].getPiece().getType() == Types.KNIGHT){
					if (board2[inI][inJ].getPiece().possMoves(outI, outJ, board2[inI][inJ].getPiece()) == 1){
						//knight jumps over pieces, it does not require a path checker
						//just need to check the destination
						if (board2[outI][outJ].isFilled() == false){
							//the place to go to is empty
							moveEmptySpace(inI, inJ, outI, outJ);
						}else{
							//check friendly first
							if (board2[outI][outJ].getPiece().getColor() == board2[inI][inJ].getPiece().getColor()){
								//friendly, can't move there
								return -1;
							}
							//kill move, add piece to dead pieces, remove it from board
							killMove(inI, inJ, outI, outJ);
						}
					}
					
				}else if(board2[inI][inJ].getPiece().getType() == Types.QUEEN){
					if (board2[inI][inJ].getPiece().possMoves(outI, outJ, board2[inI][inJ].getPiece()) == 1){
						if (pathCheckerQueen(outI, outJ, board2[inI][inJ].getPiece()) == false){
							//just need to check the destination
							if (board2[outI][outJ].isFilled() == false){
								//path is clear, move there
								moveEmptySpace(inI, inJ, outI, outJ);
							}else{
								//place is filled, check friendly first
								if (board2[outI][outJ].getPiece().getColor() == board2[inI][inJ].getPiece().getColor()){
									//friendly, can't move there
									return -1;
								}
								//kill move, add piece to dead pieces, remove it from board
								killMove(inI, inJ, outI, outJ);
							}
						}
					}
				}else if (board2[inI][inJ].getPiece().getType() == Types.KING){
					if (board2[inI][inJ].getPiece().possMoves(outI, outJ, board2[inI][inJ].getPiece()) == 1){
						if (pathCheckerKing(outI, outJ, board2[inI][inJ].getPiece()) == false){
							//just need to check the destination
							if (board2[outI][outJ].isFilled() == false){
								//the place to go to is empty
								moveEmptySpace(inI, inJ, outI, outJ);
								if (board2[outI][outJ].getPiece().getColor() == 0){
									whiteKing = board2[outI][outJ];
								}else{
									blackKing = board2[outI][outJ];
								}
							}else{
								//check friendly first
								if (board2[outI][outJ].getPiece().getColor() == board2[inI][inJ].getPiece().getColor()){
									//friendly, can't move there
									return -1;
								}
								//kill move, add piece to dead pieces, remove it from board
								killMove(inI, inJ, outI, outJ);
								if (board2[outI][outJ].getPiece().getColor() == 0){
									whiteKing = board2[outI][outJ];
								}else{
									blackKing = board2[outI][outJ];
								}
							}
						}else{
							//king path checker failed
							return -1;
						}
					}
				}
			}else{
				//System.out.println("Illegal Move");
				return -1;
			}
		}else{
			//System.out.println("Illegal Move");
			return -1;
			
		}
		return 1;
	}

	/* Sets empty space on the board after movement from a piece  */
	public void moveEmptySpace(int inI, int inJ, int outI, int outJ){
		//path is clear, move there
		Space temp = board2[inI][inJ];
		Space temp2 = board2[outI][outJ];
		board2[outI][outJ] = temp;
		board2[inI][inJ] = temp2;
		board2[outI][outJ].setX(outI);
		board2[outI][outJ].setY(outJ);
		board2[outI][outJ].getPiece().setX(outI);
		board2[outI][outJ].getPiece().setY(outJ);
		chechStatus = isCheckLocal(board2[outI][outJ].getPiece());

	}

	/* Updates the board and pieces whenever a checkmove or kill move is made by a piece */
	public void killMove(int inI, int inJ, int outI, int outJ){
		Space temp = board2[inI][inJ];
		if (temp.getPiece().getType() != Types.PAWN){
			if (temp.getPiece().getColor() == 0){
				//dead is black, killer is white
				deadBlack.add(board2[outI][outJ].getPiece());
				blackInPlay.remove(board2[outI][outJ].getPiece());
				if (board2[outI][outJ].getPiece().getType() == Types.KING){
					setKingDead();
					setKingKiller(0);
					return;
				}
			}else{
				//dead is white, killer is black
				deadWhite.add(board2[outI][outJ].getPiece());
				whiteInPlay.remove(board2[outI][outJ].getPiece());
				if (board2[outI][outJ].getPiece().getType() == Types.KING){
					setKingDead();
					setKingKiller(1);
					return;
				}
			}
		}
		board2[outI][outJ] = new Space(outI, outJ, temp.getPiece(), true);
		
		board2[inI][inJ] = new Space(inI, inJ, (new Pieces (Types.EMPTY, -1)), false);
		board2[outI][outJ].setX(outI);
		board2[outI][outJ].setY(outJ);
		board2[outI][outJ].getPiece().setX(outI);
		board2[outI][outJ].getPiece().setY(outJ);
		chechStatus = isCheckLocal(board2[outI][outJ].getPiece());
	}

	/* Sets true if King is dead */
	private void setKingDead(){
		deadKing = true;
	}

	/* Returns boolean for the king's piece status */
	public boolean isKingDead(){
		return deadKing;
	}

	/* Sets X for kill move for King */
	private void setKingKiller(int x){
		kingKiller = x;
	}

	/* Returns king's killer move */
	public int getKingKiller(){
		return kingKiller;
	}

	/* Checks for filled or empty path for possible movements for the Queen piece
	 -  return true is path is filled, false if not */
	//return true is path is filled, false if not
	public boolean pathCheckerQueen(int destX, int destY, Pieces p){
		//will attempt to reuse the code of Bishop and Rook, since queen has full 
		//range of motion
		
		//the only way i can reuse the path Checker is to know whether queen will 
		//move like a bishop or will move like a rook
		
		if (p.getType() == Types.QUEEN){
			
			if (board2[p.getX()][p.getY()].getPiece().calculatePossBishop(destX, destY, p) == 1){
				//queen will move like bishop, so use pathCheckerBishop()
				
				if (pathCheckerBishop(destX, destY, p) == false){
					//the path is empty
					return false;
				}
			}
			
			//getting here means the queen will not move like a bishop, so must move like rook
			if (board2[p.getX()][p.getY()].getPiece().calculatePossRook(destX, destY, p) == 1){
				//double checking it will move like a rook
				if (pathCheckerRook(destX, destY, p) == false){
					//the path is empty
					return false;
				}
			}	
		}
		
		//if reached here, then the paths were filled, whether rook or bishop
		return true;
	}

	/* Checks for filled or empty path for possible movements for the Bishop piece
	-  return true is path is filled, false if not */
	//return true is path is filled, false if not
	public boolean pathCheckerBishop(int destX, int destY, Pieces p){
		boolean check = false;
		
		if (p.getType() == Types.BISHOP){
			//System.out.println("IN BISHOP");
			
			int initX = p.getX();
			int initY = p.getY();
			
			//moving up
			if (destX < initX){
				//moving right
				if (destY > initY){
					while (initX > destX && initY < destY){
						initX--;
						initY++;
						if (board2[initX][initY].isFilled() && (initX != destX && initY != destY)){
							return true;
						}
					}
					
				}else if(destY < initY){
					//moving left
					while(initX > destX && initY > destY){
						initX--;
						initY--;
						if (board2[initX][initY].isFilled() && (initX != destX && initY != destY)){
							return true;
						}
					}
				}
			}else if (destX > initX){
				//moving down
				if (destY > initY){
					//moving right
					while(initX < destX && initY < destY){
						initX++;
						initY++;
						if (board2[initX][initY].isFilled() && (initX != destX && initY != destY)){
							return true;
						}
					}
					
				}else if(destY < initY){
					//moving left
					while (initX < destX && initY > destY){
						initX++;
						initY--;
						if (board2[initX][initY].isFilled() && (initX != destX && initY != destY)){
							return true;
						}
					}
				}	
			}
			
			
		}
		
		return check;
	}

	/* Checks for filled or empty path for possible movements for the King piece
	 -  return true is path is filled, false if not */
	//return true is path is filled, false if not
	public boolean pathCheckerKing(int destX, int destY, Pieces p){
		if (p.getType() == Types.KING){
			int initX = p.getX();
			int initY = p.getY();
						
			//if king is moving downard
			if ((destX - initX) == 1){
				//if moving right
				if ((destY - initY) == 1){
					if (((initX-1) == destX && (initY+1) == destY) && board2[initX-1][initY+1].isFilled()){
						return true;
					}
				}
				
				if ((initY - destX) == 1){
					//if moving left
					if (((initX-1) == destX && (initY-1) == destY) && board2[initX-1][initY-1].isFilled()){
						return true;
					}
				}
				
				if (initY == destY){
					//if not left or right
					if (((initX-1) == destX && initY == destY) && board2[initX-1][initY].isFilled()){
						return true;
					}
				}
			}
			
			//if king is moving upward
			if ((initX - destX) == 1){
				
				//if moving right
				if ((destY - initY) == 1){
					if (((initX+1) == destX && (initY+1) == destY) && board2[initX+1][initY+1].isFilled()){
						return true;
					}
				}
				
				if ((initY - destX) == 1){
					//if moving left
					if (((initX+1) == destX && (initY-1) == destY) && board2[initX+1][initY-1].isFilled()){
						return true;
					}
				}
				
				if (initY == destY){
					//if not left or right
					if (((initX-1) == destX && initY == destY) && board2[initX-1][initY].isFilled()){
						return true;
					}
				}
			}
			
			//if king is either moving left or right
			if (initX == destX){
				
				//if moving right
				if ((destY - initY) == 1){
					if (((initX) == destX && (initY+1) == destY) && board2[initX][initY+1].isFilled()){
						return true;
					}
				}
				
				if ((initY - destX) == 1){
					//if moving left
					if (((initX) == destX && (initY-1) == destY) && board2[initX][initY-1].isFilled()){
						return true;
					}
				}
			}
			
		}
		
		return false;
	}

	/* Checks for filled or empty path for possible movements for the Rook piece
	-  return true is path is filled, false if not */
	//return true is path is filled, false if not
	public boolean pathCheckerRook(int destX, int destY, Pieces p){
		//checks if path is not blocked between piece and destination
		boolean check = false;
		if (p.getType() == Types.ROOK){
			int initX = p.getX();
			int initY = p.getY();
			
			if (initX == destX){
				//moving horizontally
				if (destY > initY){//moving right
					for (int i = initY+1; i < destY; i++){
						if (board2[initX][i].isFilled()){
							check = true;
							break;
						}
					}
				}else if (initY > destY){//moving left
					for (int i = initY-1; i > destY; i--){
						if (board2[initX][i].isFilled()){
							check = true;
							break;
						}
					}
				}
			}else if(initY == destY){
				//moving vertically
				if (destX > initX){//moving down
					for (int i = initX+1; i < destX; i++){
						if (board2[i][initY].isFilled()){
							check = true;
							break;
						}
					}
				}else if (initX > destX){//moving up
					for (int i = initX-1; i > destX; i--){
						if (board2[i][initY].isFilled()){
							check = true;
							break;
						}
					}
				}
			}else{
				//not moving horzontal or vertical
				check = true;
			}
		}
		
		
		return check;
	}

	/* Checks if the move made places King in check */
	//return true if king is in check
	private boolean isCheckLocal(Pieces p){
		if (chechStatus == true){
			chechStatus = false;
		}
		int destX;
		int destY;
		if (p.getColor() == 0){
			destX = blackKing.getX();
			destY = blackKing.getY();
		}else{
			destX = whiteKing.getX();
			destY = whiteKing.getY();
		}
		
		boolean result = true;
		
		if (p.getType() == Types.ROOK){
			result = pathCheckerRook(destX, destY, p);
		}else if (p.getType() == Types.BISHOP){
			result = pathCheckerBishop(destX, destY, p);
		}else if (p.getType() == Types.KNIGHT){
			if (p.calculatePossKnight(destX, destY, p) == 1){
				result = false;
			}
		}else if (p.getType() == Types.QUEEN){
			result = pathCheckerQueen(destX, destY, p);
		}else if (p.getType() == Types.KING){
			if (p.calculatePossKing(destX, destY, p) == 1){
				result = false;
			}
		}else if (p.getType() == Types.PAWN){
			if (p.calculatePossPawn(destX, destY, p) == 1 || p.calculatePossPawn(destX, destY, p) == 3
					|| p.calculatePossPawn(destX, destY, p) == 4){
				result = false;
			}
		}
		
		return result;
	}

	//used by chess activity
	public boolean isCheckPublic(){

		return chechStatus;
	}

	/* Returns white pieces from play move */
	public List<Pieces> getWhiteInPlay(){
		return whiteInPlay;
	}

	/* Returns black pieces from play move */
	public List<Pieces> getBlackInPlay(){
		return blackInPlay;
	}

	/* Returns AI moves */
	public List<Integer> getAIMoves(Pieces p){
		List<Integer> possMoves = new ArrayList<>();

		if (p.getType() == Types.PAWN){
			//pawn moves
			Pawn a = new Pawn();
			possMoves = a.listOfMoves(p);
		}else if (p.getType() == Types.KING){
			//king moves
			King a = new King();
			possMoves = a.listOfMoves(p);
		}else if (p.getType() == Types.BISHOP){
			//bishop moves
			Bishop a = new Bishop();
			possMoves = a.listOfMoves(p);
		}else if (p.getType() == Types.KNIGHT){
			//knight moves
			Knight a = new Knight();
			possMoves = a.listOfMoves(p);
		}else if (p.getType() == Types.QUEEN){
			//queen moves
			Bishop a = new Bishop();
			possMoves = a.listOfMoves(p);

			Rook b = new Rook();
			possMoves.addAll(b.listOfMoves(p));
		}else if (p.getType() == Types.ROOK){
			//rook moves
			Rook a = new Rook();
			possMoves = a.listOfMoves(p);
		}

		return possMoves;
	}
	
}
