package chess;

import java.util.List;
import java.util.LinkedList;

public class ValidMoves {
	public static final int[] directionOffsets = { -8, 8, -1, 1, -7, 7, 9, -9 };

	public static int[][] NumberOfSquaresToEdge = new int[64][];

	static void precomputedMoveData() {
		for (int file = 0; file < 8; file++) {
			for (int rank = 0; rank < 8; rank++) {
				int numNorth = 7 - rank;
				int numSouth = rank;
				int numWest = file;
				int numEast = 7 - file;

				int squareIndex = rank * 8 + file;

				NumberOfSquaresToEdge[squareIndex] = new int[] { numSouth, numNorth, numWest, numEast,
						Math.min(numSouth, numEast), Math.min(numNorth, numWest), Math.min(numNorth, numEast),
						Math.min(numSouth, numWest) };
			}
		}
	}

	static List<Move> moves;

	public static List<Move> generateMove() {
		Board.colourToMove = Board.getIsWhiteMove() ? Pieces.white : Pieces.black;
		moves = new LinkedList<>();
		for (int startSquare = 0; startSquare < 64; startSquare++) {
			int piece = Board.squares[startSquare];
			if (Pieces.isColour(piece, Board.colourToMove)) {
				if (Pieces.isSlidingPiece(piece)) {
					generateSlidingMoves(startSquare, piece);
				} else if (Pieces.isType(piece, Pieces.pawn)) {
					generatePawnMoves(startSquare, piece);
				} else if (Pieces.isType(piece, Pieces.knight)) {
					generateKnightMoves(startSquare, piece);
				} else if (Pieces.isType(piece, Pieces.king)) {
					generateKingMoves(startSquare, piece);
				}
			}
		}
		for (Move move : moves) {
			System.out.println(move.getStartSquare() + " " + move.getTargetSquare());
		}
		return moves;
	}

	private static void generateSlidingMoves(int startSquare, int piece) {
		int friendlyPiece = Board.getIsWhiteMove() ? Pieces.white : Pieces.black;
		int opponentPiece = Board.getIsWhiteMove() ? Pieces.black : Pieces.white;
		int startDirectionIndex = (Pieces.isType(piece, Pieces.bishop)) ? 4 : 0;
		int endDirectionIndex = (Pieces.isType(piece, Pieces.rook)) ? 4 : 8;

		for (int directionIndex = startDirectionIndex; directionIndex < endDirectionIndex; directionIndex++) {
			for (int n = 0; n < NumberOfSquaresToEdge[startSquare][directionIndex]; n++) {
//				out of bound index
				int targetSquare = startSquare + directionOffsets[directionIndex] * (n + 1);

				if (targetSquare >= 0 && targetSquare < 64) {
					int pieceOnTargetSquare = Board.squares[targetSquare];

//				blocked by friendly piece
					if (Pieces.isColour(pieceOnTargetSquare, friendlyPiece)) {
						break;
					}

					moves.add(new Move(startSquare, targetSquare, piece, pieceOnTargetSquare));

					if (Pieces.isColour(pieceOnTargetSquare, opponentPiece)) {
						break;
					}

				}

			}
		}
	}

	private static void generatePawnMoves(int startSquare, int piece) {
		int friendlyPiece = Board.getIsWhiteMove() ? Pieces.white : Pieces.black;
		int opponentPiece = Board.getIsWhiteMove() ? Pieces.black : Pieces.white;
		int startDirIndex = (Pieces.isColour(piece, Pieces.black)) ? 1 : 0;
		int endDirIndex = (Pieces.isColour(piece, Pieces.white)) ? 1 : 2;
		for (int directionIndex = startDirIndex; directionIndex < endDirIndex; directionIndex++) {

			int targetSquare = startSquare + directionOffsets[directionIndex];
			int rank = startSquare / 8;
			int file = startSquare % 8;

			if (targetSquare >= 0 && targetSquare < 64) {
				int pieceOnTargetSquare = Board.squares[targetSquare];

				if (Pieces.isColour(pieceOnTargetSquare, friendlyPiece))
					continue;
				if (Pieces.isColour(pieceOnTargetSquare, opponentPiece))
					continue;

				moves.add(new Move(startSquare, targetSquare, piece, pieceOnTargetSquare));

			}
			for (int directionIdx = startDirIndex; directionIdx < endDirIndex; directionIdx++) {

				int pieceOnTargetSquare = Board.squares[targetSquare];

				if ((rank == 6 && Pieces.isColour(piece, Pieces.white))
						|| (rank == 1 && Pieces.isColour(piece, Pieces.black))) {
					targetSquare = startSquare + directionOffsets[directionIdx] * 2;
					if (Pieces.isColour(pieceOnTargetSquare, friendlyPiece))
						continue;
					if (Pieces.isColour(pieceOnTargetSquare, opponentPiece))
						continue;

					moves.add(new Move(startSquare, targetSquare, piece, pieceOnTargetSquare));

				}
			}

//			check for captures with a pawn
//			white pawns
			if (Pieces.isColour(piece, Pieces.white)) {

				int pieceToCapture = Board.squares[(file - 1) + (rank - 1) * 8];
				if (Pieces.isColour(pieceToCapture, opponentPiece)) {
					moves.add(new Move(startSquare, (file - 1) + (rank - 1) * 8, piece, pieceToCapture));
				}
				pieceToCapture = Board.squares[(file + 1) + (rank - 1) * 8];
				if (Pieces.isColour(pieceToCapture, opponentPiece)) {
					moves.add(new Move(startSquare, (file + 1) + (rank - 1) * 8, piece, pieceToCapture));
				}
			}

//			black pawns
			if (Pieces.isColour(piece, Pieces.black)) {

				int pieceToCapture = Board.squares[(file - 1) + (rank + 1) * 8];
				if (Pieces.isColour(pieceToCapture, opponentPiece)) {
					moves.add(new Move(startSquare, (file - 1) + (rank + 1) * 8, piece, pieceToCapture));
				}
				pieceToCapture = Board.squares[(file + 1) + (rank + 1) * 8];
				if (Pieces.isColour(pieceToCapture, opponentPiece)) {
					moves.add(new Move(startSquare, (file + 1) + (rank + 1) * 8, piece, pieceToCapture));
				}
			}

		}
	}

	private static void generateKnightMoves(int startSquare, int piece) {
		int friendlyPiece = Board.getIsWhiteMove() ? Pieces.white : Pieces.black;
		int opponentPiece = Board.getIsWhiteMove() ? Pieces.black : Pieces.white;
		int rank = startSquare / 8;
		int file = startSquare % 8;
		List<Integer> knightOffset = new LinkedList<>();
		if (file + 2 <= 7 && rank + 1 <= 7) {
			knightOffset.add((file + 2) + (rank + 1) * 8);
		}
		if (file - 2 >= 0 && rank + 1 <= 7) {
			knightOffset.add((file - 2) + (rank + 1) * 8);
		}
		if (file + 2 <= 7 && rank - 1 >= 0) {
			knightOffset.add((file + 2) + (rank - 1) * 8);
		}
		if (file - 2 >= 0 && rank - 1 >= 0) {
			knightOffset.add((file - 2) + (rank - 1) * 8);
		}
		if (file + 1 <= 7 && rank + 2 <= 7) {
			knightOffset.add((file + 1) + (rank + 2) * 8);
		}
		if (file - 1 >= 0 && rank + 2 <= 7) {
			knightOffset.add((file - 1) + (rank + 2) * 8);
		}
		if (file + 1 <= 7 && rank - 2 >= 0) {
			knightOffset.add((file + 1) + (rank - 2) * 8);
		}
		if (file - 1 >= 0 && rank - 2 >= 0) {
			knightOffset.add((file - 1) + (rank - 2) * 8);
		}
		for (int directionIndex = 0; directionIndex < knightOffset.size(); directionIndex++) {
			int targetSquare = knightOffset.get(directionIndex);
			if (targetSquare >= 0 && targetSquare < 64) {
				int pieceOnTargetSquare = Board.squares[targetSquare];

				if (Pieces.isColour(pieceOnTargetSquare, friendlyPiece))
					continue;

				moves.add(new Move(startSquare, targetSquare, piece, pieceOnTargetSquare));

				if (Pieces.isColour(pieceOnTargetSquare, opponentPiece))
					continue;
			}
		}
	}

	private static void generateKingMoves(int startSquare, int piece) {
		int friendlyPiece = Board.getIsWhiteMove() ? Pieces.white : Pieces.black;
		int opponentPiece = Board.getIsWhiteMove() ? Pieces.black : Pieces.white;

		for (int directionIndex = 0; directionIndex < directionOffsets.length; directionIndex++) {

			int targetSquare = startSquare + directionOffsets[directionIndex];

			if (targetSquare >= 0 && targetSquare < 64) {

				int pieceOnTargetSqaure = Board.squares[targetSquare];

				if (Pieces.isColour(pieceOnTargetSqaure, friendlyPiece)) {
					continue;
				}

				moves.add(new Move(startSquare, targetSquare, piece, pieceOnTargetSqaure));

				if (Pieces.isColour(pieceOnTargetSqaure, opponentPiece))
					continue;
			}
		}
	}

//	implementing check 

}
