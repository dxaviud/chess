/*
 * Copyright © 2020 David Xu All rights reserved. Use only for non-commercial purposes. Authorization required for other purposes. Use at your own risk.
 * This class is part of a chess game made for the AP Computer Science A's final project at University High School, Irvine CA. 
 */
import java.util.ArrayList;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Bishop extends ChessPiece {

	public Bishop(String color, int row, int col) {
		super(color, row, col);
		super.setTransparentImage(new ImageIcon("src\\"+getColor()+"Bishop.png").getImage());
	}
	
	public ArrayList<ChessPiece> getPossibleMoves(ChessPiece[][] board) {
		ArrayList<ChessPiece> possibleMoves = new ArrayList<ChessPiece>();
		//check to up and to the left
		int col = getColIndex()-1;
		int row = getRowIndex()-1;
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
			if (kingWouldBeUnderCheck(possibleMoves.get(i), board)) {
				possibleMoves.remove(i);
				i--;
			}
		}
			
		return possibleMoves;
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
		return "b"+Integer.toString(getRowIndex())+""+Integer.toString(getColIndex())+getColor().substring(0,1).toUpperCase();
	}
	
	public void setRowIndex(int row) {
		super.setRowIndex(row);
	}
	
	public void setColIndex(int col) {
		super.setColIndex(col);
	}
	
	public Image getImage() {
		return new ImageIcon("src\\"+getColor()+"Bishop"+getBlackOrWhiteTile()+"Tile.png").getImage();
	}
	
	public void setTransparentImage(Image i) {
		super.setTransparentImage(i);
	}
	
	public Image getTransparentImage() {
		return super.getTransparentImage();
	}
	
	public Object clone() {
		return new Bishop(getColor(), getRowIndex(), getColIndex());
	}
}
