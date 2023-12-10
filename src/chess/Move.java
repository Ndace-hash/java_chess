package chess;

public class Move {
		private int startSquare;
		private int targetSquare;
		private int piece;
		private int pieceOnTargetSquare;
		public Move(int startSquare, int targetSquare, int piece, int pieceOnTargetSquare) {
			this.setStartSquare(startSquare);
			this.setTargetSquare(targetSquare);
			this.setPiece(piece);
			this.setPieceOnTargetSquare(pieceOnTargetSquare);
		}
		public int getTargetSquare() {
			return targetSquare;
		}
		public void setTargetSquare(int targetSquare) {
			this.targetSquare = targetSquare;
		}
		public int getStartSquare() {
			return startSquare;
		}
		public void setStartSquare(int startSquare) {
			this.startSquare = startSquare;
		}
		public int getPieceOnTargetSquare() {
			return pieceOnTargetSquare;
		}
		public void setPieceOnTargetSquare(int pieceOnTargetSquare) {
			this.pieceOnTargetSquare = pieceOnTargetSquare;
		}
		public int getPiece() {
			return piece;
		}
		public void setPiece(int piece) {
			this.piece = piece;
		}
	
}
