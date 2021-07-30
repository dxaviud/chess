package src;

import chess.pieces.*;

public class MoveCollection {
	private ChessPiece takenPiece;
	private ChessPiece movedPiecePreviousPosition;
	private int whiteTimeRemaining;
	private int blackTimeRemaining;
	
	public MoveCollection(ChessPiece lastTaken, ChessPiece lastMovedPrevPos, int whiteTime, int blackTime) {
		takenPiece = lastTaken;
		movedPiecePreviousPosition = lastMovedPrevPos;
		whiteTimeRemaining = whiteTime;
		blackTimeRemaining = blackTime;
	}
	public ChessPiece getTakenPiece() {
		return takenPiece;
	}
	public ChessPiece getMovedPiecePreviousPosition() {
		return movedPiecePreviousPosition;
	}
	
	public int getWhiteTimeRemaining() {
		return whiteTimeRemaining;
	}
	
	public int getBlackTimeRemaining() {
		return blackTimeRemaining;
	}
}
