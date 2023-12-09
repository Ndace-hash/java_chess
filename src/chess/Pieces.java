package chess;

public class Pieces {
	public static int none = 0;
	public static int king = 1;
	public static int pawn = 2;
	public static int bishop= 3;
	public static int knignt = 4;
	public static int rook = 5;
	public static int queen = 6;
	
	public static int white = 8;
	public static int black = 16;
	
	static boolean isColour(int piece, int colourToMove) {
		return (piece & colourToMove) == colourToMove ? true:false;		
	}
	
	static boolean isType(int piece, int pieceType) {
		return (piece & pieceType) == pieceType ? true:false;		
	}
	
	static boolean isSlidingPiece(int piece) {
		boolean isQueen = isType(piece, queen);
		boolean isRook = isType(piece, rook);
		boolean isBishop = isType(piece, bishop);
		
		return isQueen || isRook || isBishop;
		
	}
	
}
