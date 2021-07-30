package src.pieces;

import java.util.ArrayList;
import java.awt.Image;


public abstract class ChessPiece {
	
	private int rowIndex;
	private int colIndex;
	private String color;
	private Image image;

	public ChessPiece(String color, int row, int col) {
		this.color = color;
		rowIndex = row;
		colIndex = col;
	}
	
	public abstract ArrayList<ChessPiece> getPossibleMoves(ChessPiece[][] board);
	
	public abstract ArrayList<ChessPiece> getDefendedPieces(ChessPiece[][] board);
	
	public abstract ArrayList<ChessPiece> getAttackedPieces(ChessPiece[][] board);
	
	public boolean isDefended(ChessPiece[][] board) {
		for (ChessPiece[] row : board) {
			for (ChessPiece col : row) {
				if (!(col instanceof EmptySlot) && col.getColor().equals(getColor())) {
					for (ChessPiece defendedPiece : col.getDefendedPieces(board)) {
						if (defendedPiece.equals(this)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public boolean kingWouldBeUnderCheck(ChessPiece moveTo, ChessPiece[][] board) {
		ChessPiece[][] testBoard = new ChessPiece[board.length][board[0].length];
		for (int row = 0; row<board.length; row++) {
			for (int col = 0; col<board[row].length; col++) {
				testBoard[row][col] = (ChessPiece) board[row][col].clone();
			}
		}
		King myKing = null;
		for (ChessPiece[] row : testBoard) {
			for (ChessPiece col : row) {
				if (!(col instanceof EmptySlot) && col instanceof King && col.getColor().equals(getColor())){
					myKing = (King) col;
				}
			}
		}

		int toRow = moveTo.getRowIndex();
		int toCol = moveTo.getColIndex();
		int fromRow = getRowIndex();
		int fromCol = getColIndex();
		
		testBoard[toRow][toCol] = testBoard[fromRow][fromCol];
		testBoard[toRow][toCol].setColIndex(toCol);
		testBoard[toRow][toCol].setRowIndex(toRow);
		testBoard[fromRow][fromCol] = new EmptySlot(fromRow, fromCol);
		return myKing.getUnderCheck(testBoard);
		
	}
	
	
	
	public String getColor() {
		return color;
	}
	
	public int getRowIndex() {
		return rowIndex;
	}
	
	public int getColIndex() {
		return colIndex;
	}
	
	public void setRowIndex(int row) {
		rowIndex = row;
	}
	
	public void setColIndex(int col) {
		colIndex = col;
	}

	public abstract Image getImage();
	
	protected String getBlackOrWhiteTile() {
		int row = rowIndex+1;
		int col = colIndex+1;
		if (row % 2 == 0) {
			if (col % 2 == 0) {
				return "White";
			} else {
				return "Black";
			}
		} else {
			if (col % 2 != 0) {
				return "White";
			} else {
				return "Black";
			}
		}
	}
	
	public void setTransparentImage(Image i) {
		image = i;
	}
	
	public Image getTransparentImage() {
		return image;
	}
	
	public abstract Object clone();
	
	
}
