package chess;

import java.util.List;
import java.util.LinkedList;

public class ValidMoves {
	public static final int[] directionOffsets = { 8, -8, -1, 1, 7, -7, -9, 9 };
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
		moves = new LinkedList<>();
		for (int startSquare = 0; startSquare < 64; startSquare++) {
			int piece = Board.squares[startSquare];
			if (Pieces.isColour(piece, Board.colourToMove)) {
				if (Pieces.isSlidingPiece(piece)) {
					generateSlidingMoves(startSquare, piece);
				} else if (Pieces.isType(piece, Pieces.pawn)) {
					generatePawnMoves(startSquare, piece);
				}
			}
		}
		for (Move move : moves) {
			System.out.println(move.getStartSquare() + " " + move.getTargetSquare());
		}
		return moves;
	}

	private static int friendlyPiece = Board.isWhiteMove ? Pieces.white : Pieces.black;
	private static int opponentPiece = Board.isWhiteMove ? Pieces.black : Pieces.white;

	private static void generateSlidingMoves(int startSquare, int piece) {
		int startDirectionIndex = (Pieces.isType(piece, Pieces.bishop)) ? 4 : 0;
		int endDirectionIndex = (Pieces.isType(piece, Pieces.rook)) ? 4 : 8;

		for (int directionIndex = startDirectionIndex; directionIndex < endDirectionIndex; directionIndex++) {
			for (int n = 0; n < NumberOfSquaresToEdge[startSquare][directionIndex]; n++) {
//				out of bound index
				int targetSquare = startSquare + directionOffsets[directionIndex] * n + 1;

				if (targetSquare >= 0 && targetSquare < 64) {
					int pieceOnTargetSquare = Board.squares[targetSquare];

//				blocked by friendly piece
					if (Pieces.isColour(pieceOnTargetSquare, friendlyPiece)) {
						break;
					}

					moves.add(new Move(startSquare, targetSquare));

					if (Pieces.isColour(pieceOnTargetSquare, opponentPiece)) {
						break;
					}

				}

			}
		}
	}
	
	private static void generatePawnMoves(int startSquare, int piece) {
		int startDirIndex = (Pieces.isColour(piece, Pieces.black))?0 : 1;
		int endDirIndex = (Pieces.isColour(piece, Pieces.white))?2:1;
		for(int directionIndex = startDirIndex; directionIndex < endDirIndex; directionIndex++) {
			for(int n = 1; n <= NumberOfSquaresToEdge[startSquare][directionIndex]; n++) {
				int targetSquare = startSquare + directionOffsets[directionIndex] *n;
				
				if(targetSquare >=0 && targetSquare<64) {
					int pieceOnTargetSquare = Board.squares[targetSquare];
					
					if(Pieces.isColour(pieceOnTargetSquare, friendlyPiece))break;
					
					moves.add(new Move(startSquare, targetSquare));
					
					if(Pieces.isColour(pieceOnTargetSquare, opponentPiece)) break;
				}
			}
		}
	}

}
