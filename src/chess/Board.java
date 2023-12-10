package chess;

import java.util.HashMap;
import java.util.Stack;


public class Board {
	static int[] squares;
	static Stack<Move> playedMoves = new Stack<>();
	static HashMap<Character, Integer> pieceTypeBySymbol;
	static HashMap<Integer, Character> pieceTypeByNumber = new HashMap<>();
	static int colourToMove;
	private static boolean isWhiteMove = true;
	
	public static void setIsWhiteMove(boolean isWhiteMove) {
		Board.isWhiteMove = isWhiteMove;
	}
	public static boolean getIsWhiteMove() {
		return Board.isWhiteMove;
	}

	Board() {
		String startFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq 1";
//		String mock = "R1BQ1B1R/8/8/3N4/8/8/8/8 w KQkq 1";
		squares = new int[64];
		this.pieceSymbolMapper();
		this.fen(startFen);
		colourToMove = isWhiteMove ? Pieces.white : Pieces.black;

		printBoard();
		ValidMoves.precomputedMoveData();
		ValidMoves.generateMove();
		
		int rand = (int) Math.floor(Math.random()*ValidMoves.moves.size());
		Move move = ValidMoves.moves.get(rand);
		makeMove(move);
		
		rand = (int) Math.floor(Math.random()*ValidMoves.moves.size());
		move = ValidMoves.moves.get(rand);
		makeMove(move);
		
		rand = (int) Math.floor(Math.random()*ValidMoves.moves.size());
		move = ValidMoves.moves.get(rand);
		makeMove(move);
		
		unMakeMove();
		unMakeMove();
		unMakeMove();
	}

	private static void printBoard() {
		for (int i = 0; i < squares.length; i++) {
			int piece = squares[i];
			char symbol = pieceTypeByNumber.get(piece);
			System.out.print("[" + symbol + "]");

			if (i % 8 == 7) {
				System.out.print('\n');
			}
		}
		System.out.println();
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
	
	public static void makeMove(Move move) {
		int piece = Board.squares[move.getStartSquare()];
		
		Board.squares[move.getStartSquare()] = Pieces.none;
		Board.squares[move.getTargetSquare()] = piece;
		
		playedMoves.push(move);
		isWhiteMove = !isWhiteMove;
		Board.printBoard();
		ValidMoves.generateMove();
	}
	
	public static void unMakeMove() {
		Move move = playedMoves.pop();
		
		Board.squares[move.getStartSquare()] = move.getPiece();
		Board.squares[move.getTargetSquare()] = move.getPieceOnTargetSquare();
		
		Board.printBoard();
	}
}
