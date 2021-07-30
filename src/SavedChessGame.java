package src;

import chess.pieces.*;
 
import java.io.FileReader;
import java.util.ArrayList;

public class SavedChessGame {
	private String gameName;
	private ChessPiece[][] chessBoard;
	private String playerTurn;
	private King whiteKing;
	private King blackKing;
	private ChessPiece selectedPiece;
	private ChessPiece selectedPosition;
	private ChessPiece lastMovedPiece;
	private ArrayList<MoveCollection> pastMoves;
	private int turns;
	private int whiteTimeRemaining;
	private int blackTimeRemaining;
	public SavedChessGame(FileReader fileReader) {
	
	}

	public ChessPiece[][] getChessBoard() {
		return chessBoard;
	}
	public void setChessBoard(ChessPiece[][] chessBoard) {
		this.chessBoard = chessBoard;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getPlayerTurn() {
		return playerTurn;
	}

	public void setPlayerTurn(String playerTurn) {
		this.playerTurn = playerTurn;
	}

	public King getWhiteKing() {
		return whiteKing;
	}

	public void setWhiteKing(King whiteKing) {
		this.whiteKing = whiteKing;
	}

	public King getBlackKing() {
		return blackKing;
	}

	public void setBlackKing(King blackKing) {
		this.blackKing = blackKing;
	}

	public ChessPiece getSelectedPiece() {
		return selectedPiece;
	}

	public void setSelectedPiece(ChessPiece selectedPiece) {
		this.selectedPiece = selectedPiece;
	}

	public ChessPiece getSelectedPosition() {
		return selectedPosition;
	}

	public void setSelectedPosition(ChessPiece selectedPosition) {
		this.selectedPosition = selectedPosition;
	}

	public ChessPiece getLastMovedPiece() {
		return lastMovedPiece;
	}

	public void setLastMovedPiece(ChessPiece lastMovedPiece) {
		this.lastMovedPiece = lastMovedPiece;
	}

	public ArrayList<MoveCollection> getPastMoves() {
		return pastMoves;
	}

	public void setPastMoves(ArrayList<MoveCollection> pastMoves) {
		this.pastMoves = pastMoves;
	}

	public int getTurns() {
		return turns;
	}

	public void setTurns(int turns) {
		this.turns = turns;
	}

	public int getWhiteTimeRemaining() {
		return whiteTimeRemaining;
	}

	public void setWhiteTimeRemaining(int whiteTimeRemaining) {
		this.whiteTimeRemaining = whiteTimeRemaining;
	}

	public int getBlackTimeRemaining() {
		return blackTimeRemaining;
	}

	public void setBlackTimeRemaining(int blackTimeRemaining) {
		this.blackTimeRemaining = blackTimeRemaining;
	}
}
