package chess;

import java.util.HashMap;

public class Board {
	static int[] squares;
	HashMap<Character, Integer> pieceTypeBySymbol;
	HashMap<Integer, Character> pieceTypeByNumber = new HashMap<>();
	static int colourToMove;
	public static boolean isWhiteMove = true;

	Board() {
		String startFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq 1";
//		String mock = "R1BQ1B1R/8/8/3N4/8/8/8/8 w KQkq 1";
		squares = new int[64];
		this.pieceSymbolMapper();
		this.fen(startFen);
		colourToMove = isWhiteMove ? Pieces.white : Pieces.black;

		for (int i = 0; i < squares.length; i++) {
			int piece = squares[i];
			char symbol = pieceTypeByNumber.get(piece);
			System.out.print("[" + symbol + "]");

			if (i % 8 == 7) {
				System.out.print('\n');
			}
		}
		ValidMoves.precomputedMoveData();
		ValidMoves.generateMove();

	}

	private void pieceSymbolMapper() {
//		piece number to symbol map
		pieceTypeByNumber.put(Pieces.king | Pieces.black, 'k');
		pieceTypeByNumber.put(Pieces.queen | Pieces.black, 'q');
		pieceTypeByNumber.put(Pieces.rook | Pieces.black, 'r');
		pieceTypeByNumber.put(Pieces.knight | Pieces.black, 'n');
		pieceTypeByNumber.put(Pieces.bishop | Pieces.black, 'b');
		pieceTypeByNumber.put(Pieces.pawn | Pieces.black, 'p');
		pieceTypeByNumber.put(Pieces.king | Pieces.white, Character.toUpperCase('k'));
		pieceTypeByNumber.put(Pieces.queen | Pieces.white, Character.toUpperCase('q'));
		pieceTypeByNumber.put(Pieces.rook | Pieces.white, Character.toUpperCase('r'));
		pieceTypeByNumber.put(Pieces.knight | Pieces.white, Character.toUpperCase('n'));
		pieceTypeByNumber.put(Pieces.bishop | Pieces.white, Character.toUpperCase('b'));
		pieceTypeByNumber.put(Pieces.pawn | Pieces.white, Character.toUpperCase('p'));
		pieceTypeByNumber.put(Pieces.none, ' ');

//		piece symbol to number map
		pieceTypeBySymbol = new HashMap<>();
		pieceTypeBySymbol.put('k', Pieces.king);
		pieceTypeBySymbol.put('q', Pieces.queen);
		pieceTypeBySymbol.put('n', Pieces.knight);
		pieceTypeBySymbol.put('b', Pieces.bishop);
		pieceTypeBySymbol.put('r', Pieces.rook);
		pieceTypeBySymbol.put('p', Pieces.pawn);

	}

	public void fen(String fen) {

		String startPos = fen.split(" ")[0];

		int file = 0, rank = 0;
		char[] symbols = new char[startPos.length()];
		for (int i = 0; i < startPos.length(); i++) {
			symbols[i] = startPos.charAt(i);
		}
		for (char symbol : symbols) {
			if (symbol == '/') {
				file = 0;
				rank++;
			} else {

				if (Character.isDigit(symbol)) {

					file += (int) Character.getNumericValue(symbol);
				} else {
					int pieceType = pieceTypeBySymbol.get(Character.toLowerCase(symbol));
					int pieceColour = Character.isUpperCase(symbol) ? Pieces.white : Pieces.black;
					squares[rank * 8 + file] = pieceType | pieceColour;
					file++;
				}
			}

		}
	}
}
