package chess;

import java.util.HashMap;

public class Board {
	static int[] squares;
	HashMap<Character,Integer> pieceTypeBySymbol;
	static int colourToMove;
	public static boolean isWhiteMove = false;
	
	Board() {
		String startFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq 1";
//		String mock = "R1BQ1B1R/8/8/8/8/8/8/8 w KQkq 1";
		squares = new int[64];
		this.fen(startFen);
		colourToMove = isWhiteMove ? Pieces.white : Pieces.black;
		
		for(int i = 0; i<squares.length;i++) {
			int piece = squares[i];
			if((piece&Pieces.none)== Pieces.none) {
					System.out.print("[ ]");
			}else if((piece&Pieces.pawn)== Pieces.pawn) {
				if((piece&Pieces.white)== Pieces.white)
					System.out.print("["+Character.toUpperCase('p')+"]");
				else if ((piece&Pieces.black)==Pieces.black)
					System.out.print("["+Character.toLowerCase('p')+"]");
			}
			if(i%8 == 7) {
				System.out.print('\n');
			}
		}
		ValidMoves.precomputedMoveData();
		ValidMoves.generateMove();
		
	}
	
	public void fen (String fen) {
		pieceTypeBySymbol = new HashMap<>();
		pieceTypeBySymbol.put('k', Pieces.king);
		pieceTypeBySymbol.put('q', Pieces.queen);
		pieceTypeBySymbol.put('n', Pieces.knignt);
		pieceTypeBySymbol.put('b', Pieces.bishop);
		pieceTypeBySymbol.put('r', Pieces.rook);
		pieceTypeBySymbol.put('p', Pieces.pawn);

		String startPos = fen.split(" ")[0];
		
		int file = 0, rank = 0;
		char[] symbols = new char[startPos.length()];
			for(int i = 0; i<startPos.length();i++) {
				symbols[i] = startPos.charAt(i);
			}
			for(char symbol : symbols) {
				if(symbol == '/') {
					file = 0;
					rank++;
				}else {
					
				if(Character.isDigit(symbol)) {
					
					file += (int) Character.getNumericValue(symbol);
				}
				else {
					int pieceType =  pieceTypeBySymbol.get(Character.toLowerCase(symbol));
					int pieceColour = Character.isUpperCase(symbol)? Pieces.white : Pieces.black;					
					squares[rank * 8 + file] = pieceType | pieceColour;
					file++;
				}
				}
					
			}
	}
}
