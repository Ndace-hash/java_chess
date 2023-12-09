package chess;

public class Move {
		private int startSquare;
		private int targetSquare;
		public Move(int startSquare, int targetSquare) {
			this.setStartSquare(startSquare);
			this.setTargetSquare(targetSquare);
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
	
}
