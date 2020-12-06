/*
 * Copyright 2020 David Xu All rights reserved. Use for commercial purposes is prohibited.
 */
 
import java.util.ArrayList;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Queen extends ChessPiece {

	public Queen(String color, int row, int col) {
		super(color, row, col);
		super.setTransparentImage(new ImageIcon(System.getProperty("user.dir")+"\\images\\"+getColor()+"Queen.png").getImage());
	} 
	
	public ArrayList<ChessPiece> getPossibleMoves(ChessPiece[][] board) {
		ArrayList<ChessPiece> possibleMoves = new ArrayList<ChessPiece>();
		//check to the left
		int col = getColIndex()-1;
		while (col > -1) {
			if (board[getRowIndex()][col] instanceof EmptySlot) { //empty space
				possibleMoves.add(board[getRowIndex()][col]);
			} else if (!board[getRowIndex()][col].getColor().equals(getColor())) { //opponent piece
				possibleMoves.add(board[getRowIndex()][col]);
				break;
			} else if (board[getRowIndex()][col].getColor().equals(getColor())) { //ally piece
				break;
			}
			col--;
		}
		//check to the right
		col = getColIndex()+1;
		while (col < 8) {
			if (board[getRowIndex()][col] instanceof EmptySlot) { //empty space
				possibleMoves.add(board[getRowIndex()][col]);
			} else if (!board[getRowIndex()][col].getColor().equals(getColor())) { //opponent piece
				possibleMoves.add(board[getRowIndex()][col]);
				break;
			} else if (board[getRowIndex()][col].getColor().equals(getColor())) { //ally piece
				break;
			}
			col++;
		}
		//check up
		int row = getRowIndex()-1;
		while (row > -1) {
			if (board[row][getColIndex()] instanceof EmptySlot) { //empty space
				possibleMoves.add(board[row][getColIndex()]);
			} else if (!board[row][getColIndex()].getColor().equals(getColor())) { //opponent piece
				possibleMoves.add(board[row][getColIndex()]);
				break;
			} else if (board[row][getColIndex()].getColor().equals(getColor())) { //ally piece
				break;
			}
			row--;
		}
		//check down
		row = getRowIndex()+1;
		while (row < 8) {
			if (board[row][getColIndex()] instanceof EmptySlot) { //empty space
				possibleMoves.add(board[row][getColIndex()]);
			} else if (!board[row][getColIndex()].getColor().equals(getColor())) { //opponent piece
				possibleMoves.add(board[row][getColIndex()]);
				break;
			} else if (board[row][getColIndex()].getColor().equals(getColor())) { //ally piece
				break;
			}
			row++;
		}
		
		//check to up and to the left
		col = getColIndex()-1;
		row = getRowIndex()-1;
		while (col > -1 && row > -1) {
			if (board[row][col] instanceof EmptySlot) { //empty space
				possibleMoves.add(board[row][col]);
			} else if (!board[row][col].getColor().equals(getColor())) { //opponent piece
				possibleMoves.add(board[row][col]);
				break;
			} else if (board[row][col].getColor().equals(getColor())) { //ally piece
				break;
			}
			col--;
			row--;
		}
		//check up and to the right
		col = getColIndex()+1;
		row = getRowIndex()-1;
		while (col < 8 && row > -1) {
			if (board[row][col] instanceof EmptySlot) { //empty space
				possibleMoves.add(board[row][col]);
			} else if (!board[row][col].getColor().equals(getColor())) { //opponent piece
				possibleMoves.add(board[row][col]);
				break;
			} else if (board[row][col].getColor().equals(getColor())) { //ally piece
				break;
			}
			col++;
			row--;
		}
		//check down and to the left
		col = getColIndex()-1;
		row = getRowIndex()+1;
		while (col > -1 && row < 8) {
			if (board[row][col] instanceof EmptySlot) { //empty space
				possibleMoves.add(board[row][col]);
			} else if (!board[row][col].getColor().equals(getColor())) { //opponent piece
				possibleMoves.add(board[row][col]);
				break;
			} else if (board[row][col].getColor().equals(getColor())) { //ally piece
				break;
			}
			col--;
			row++;
		}
		//check down and to the right
		col = getColIndex()+1;
		row = getRowIndex()+1;
		while (col < 8 && row < 8) {
			if (board[row][col] instanceof EmptySlot) { //empty space
				possibleMoves.add(board[row][col]);
			} else if (!board[row][col].getColor().equals(getColor())) { //opponent piece
				possibleMoves.add(board[row][col]);
				break;
			} else if (board[row][col].getColor().equals(getColor())) { //ally piece
				break;
			}
			col++;
			row++;
		}
		
		for (int i = 0; i < possibleMoves.size(); i++) {
			if (kingStillUnderCheck(possibleMoves.get(i), board)) {
				possibleMoves.remove(i);
				i--;
			}
		}
			
		return possibleMoves;
	}
	
	private boolean kingStillUnderCheck(ChessPiece moveTo, ChessPiece[][] board) {

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

//		if (!myKing.getUnderCheck(testBoard)) {
//			return false;
//		}
		
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
	
	public ArrayList<ChessPiece> getDefendedPieces(ChessPiece[][] board) {
		ArrayList<ChessPiece> defendedPieces = new ArrayList<ChessPiece>();
		
		
		//check to up and to the left
		int col = getColIndex()-1;
		int row = getRowIndex()-1;
		while (col > -1 && row > -1) {
			if (!board[row][col].getColor().equals(getColor())) {
				break;
			}
			if (!(board[row][col] instanceof EmptySlot) && board[row][col].getColor().equals(getColor())) { //ally piece

				defendedPieces.add(board[row][col]);
				break;
			}
			col--;
			row--;
		}
		//check up and to the right
		col = getColIndex()+1;
		row = getRowIndex()-1;
		while (col < 8 && row > -1) {
			if (!board[row][col].getColor().equals(getColor())) {
				break;
			}
			if (!(board[row][col] instanceof EmptySlot) && board[row][col].getColor().equals(getColor())) { //ally piece

				defendedPieces.add(board[row][col]);
				break;
			}
			col++;
			row--;
		}
		//check down and to the left
		col = getColIndex()-1;
		row = getRowIndex()+1;
		while (col > -1 && row < 8) {
			if (!board[row][col].getColor().equals(getColor())) {
				break;
			}
			if (!(board[row][col] instanceof EmptySlot) && board[row][col].getColor().equals(getColor())) { //ally piece

				defendedPieces.add(board[row][col]);
				break;
			}
			col--;
			row++;
		}
		//check down and to the right
		col = getColIndex()+1;
		row = getRowIndex()+1;
		while (col < 8 && row < 8) {
			if (!board[row][col].getColor().equals(getColor())) {
				break;
			}
			if (!(board[row][col] instanceof EmptySlot) && board[row][col].getColor().equals(getColor())) { //ally piece

				defendedPieces.add(board[row][col]);
				break;
			}
			col++;
			row++;
		}
		
		//check to the left
		col = getColIndex()-1;
		while (col > -1) {
			if (!board[getRowIndex()][col].getColor().equals(getColor())) {
				break;
			}
			if (!(board[getRowIndex()][col] instanceof EmptySlot) && board[getRowIndex()][col].getColor().equals(getColor())) { 

				defendedPieces.add(board[getRowIndex()][col]);
				break;
			} 
			col--;
		}
		//check to the right
		col = getColIndex()+1;
		while (col < 8) {
			if (!board[getRowIndex()][col].getColor().equals(getColor())) {
				break;
			}
			if (!(board[getRowIndex()][col] instanceof EmptySlot) && board[getRowIndex()][col].getColor().equals(getColor())) { 

				defendedPieces.add(board[getRowIndex()][col]);
				break;
			} 
			col++;
		}
		//check up
		row = getRowIndex()-1;
		while (row > -1) {
			if (!board[row][getColIndex()].getColor().equals(getColor())) {
				break;
			}
			if (!(board[row][getColIndex()] instanceof EmptySlot) && board[row][getColIndex()].getColor().equals(getColor())) { 

				defendedPieces.add(board[row][getColIndex()]);
				break;
			}
			row--;
		}
		//check down
		row = getRowIndex()+1;
		while (row < 8) {
			if (!board[row][getColIndex()].getColor().equals(getColor())) {
				break;
			}
			if (!(board[row][getColIndex()] instanceof EmptySlot) && board[row][getColIndex()].getColor().equals(getColor())) { 

				defendedPieces.add(board[row][getColIndex()]);
				break;
			}
			row++;
		}
		
		return defendedPieces;
	}
	
	public ArrayList<ChessPiece> getAttackedPieces(ChessPiece[][] board) {
		ArrayList<ChessPiece> attackedPieces = new ArrayList<ChessPiece>();
		
		
		//check to up and to the left
		int col = getColIndex()-1;
		int row = getRowIndex()-1;
		while (col > -1 && row > -1) {
			if (board[row][col].getColor().equals(getColor())) {
				break;
			}
			if (!(board[row][col] instanceof EmptySlot) && !board[row][col].getColor().equals(getColor())) { //enemy piece

				attackedPieces.add(board[row][col]);
				break;
			}
			col--;
			row--;
		}
		//check up and to the right
		col = getColIndex()+1;
		row = getRowIndex()-1;
		while (col < 8 && row > -1) {
			if (board[row][col].getColor().equals(getColor())) {
				break;
			}
			if (!(board[row][col] instanceof EmptySlot) && !board[row][col].getColor().equals(getColor())) { //enemy piece

				attackedPieces.add(board[row][col]);
				break;
			}
			col++;
			row--;
		}
		//check down and to the left
		col = getColIndex()-1;
		row = getRowIndex()+1;
		while (col > -1 && row < 8) {
			if (board[row][col].getColor().equals(getColor())) {
				break;
			}
			if (!(board[row][col] instanceof EmptySlot) && !board[row][col].getColor().equals(getColor())) { //enemy piece
				attackedPieces.add(board[row][col]);
				break;
			}
			col--;
			row++;
		}
		//check down and to the right
		col = getColIndex()+1;
		row = getRowIndex()+1;
		while (col < 8 && row < 8) {
			if (board[row][col].getColor().equals(getColor())) {
				break;
			}
			if (!(board[row][col] instanceof EmptySlot) && !board[row][col].getColor().equals(getColor())) { //enemy piece
				attackedPieces.add(board[row][col]);
				break;
			}
			col++;
			row++;
		}
		//check to the left
		col = getColIndex()-1;
		while (col > -1) {
			if (board[getRowIndex()][col].getColor().equals(getColor())) {
				break;
			}
			if (!(board[getRowIndex()][col] instanceof EmptySlot) && !board[getRowIndex()][col].getColor().equals(getColor())) {
				attackedPieces.add(board[getRowIndex()][col]);
				break;
			} 
			col--;
		}
		//check to the right
		col = getColIndex()+1;
		while (col < 8) {
			if (board[getRowIndex()][col].getColor().equals(getColor())) {
				break;
			}
			if (!(board[getRowIndex()][col] instanceof EmptySlot) && !board[getRowIndex()][col].getColor().equals(getColor())) {
				attackedPieces.add(board[getRowIndex()][col]);
				break;
			} 
			col++;
		}
		//check up
		row = getRowIndex()-1;
		while (row > -1) {
			if (board[row][getColIndex()].getColor().equals(getColor())) {
				break;
			}
			if (!(board[row][getColIndex()] instanceof EmptySlot) && !board[row][getColIndex()].getColor().equals(getColor())) { 
				attackedPieces.add(board[row][getColIndex()]);
				break;
			}
			row--;
		}
		//check down
		row = getRowIndex()+1;
		while (row < 8) {
			if (board[row][getColIndex()].getColor().equals(getColor())) {
				break;
			}
			if (!(board[row][getColIndex()] instanceof EmptySlot) && !board[row][getColIndex()].getColor().equals(getColor())) {
				attackedPieces.add(board[row][getColIndex()]);
				break;
			}
			row++;
		}
		
		return attackedPieces;
	}
	
	
	public String getColor() {
		return super.getColor();
	}
	
	public int getRowIndex() {
		return super.getRowIndex();
	}
	
	public int getColIndex() {
		return super.getColIndex();
	}
	
	public String toString() {
		return "q"+Integer.toString(getRowIndex())+""+Integer.toString(getColIndex())+getColor().substring(0,1).toUpperCase();
	}
	
	public void setRowIndex(int row) {
		super.setRowIndex(row);
	}
	
	public void setColIndex(int col) {
		super.setColIndex(col);
	}
	
	public Image getImage() {
		return new ImageIcon(System.getProperty("user.dir")+"\\images\\"+getColor()+"Queen"+getBlackOrWhiteTile()+"Tile.png").getImage();
	}
	
	public Image getTransparentImage() {
		return super.getTransparentImage();
	}
	
	public void setTransparentImage(Image i) {
		super.setTransparentImage(i);
	}
	
	public Object clone() {
		return new Queen(getColor(), getRowIndex(), getColIndex());
	}

}
