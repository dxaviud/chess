package src.pieces;

import java.util.ArrayList;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Rook extends ChessPiece {

	private boolean hasMoved;

	public Rook(String color, int row, int col) {
		super(color, row, col);
		hasMoved = false;
		super.setTransparentImage(new ImageIcon(System.getProperty("user.dir")+"\\chess\\images\\"+getColor()+"Rook.png").getImage());
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
		
		//check to the left
		int col = getColIndex()-1;
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
		int row = getRowIndex()-1;
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
		
		//check to the left
		int col = getColIndex()-1;
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
		int row = getRowIndex()-1;
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
	
	public String toString() {
		String hasMovedOneLetter = "";
		if (hasMoved) {
			hasMovedOneLetter = "t";
		} else {
			hasMovedOneLetter = "f";
		}
		return "r"+Integer.toString(getRowIndex())+""+Integer.toString(getColIndex())+getColor().substring(0,1).toUpperCase()+hasMovedOneLetter;
	}
	
	public boolean getHasMoved() {
		return hasMoved;
	}
	
	public void setHasMoved(boolean bool) {
		hasMoved = bool;
	}
	
	
	public int getRowIndex() {
		return super.getRowIndex();
	}
	
	public int getColIndex() {
		return super.getColIndex();
	}
	
	public void setRowIndex(int row) {
		super.setRowIndex(row);
	}
	
	public void setColIndex(int col) {
		super.setColIndex(col);
	}

	public Image getImage() {
		return new ImageIcon(System.getProperty("user.dir")+"\\chess\\images\\"+getColor()+"Rook"+getBlackOrWhiteTile()+"Tile.png").getImage();
	}
	
	public void setTransparentImage(Image i) {
		super.setTransparentImage(i);
	}
	
	public Image getTransparentImage() {
		return super.getTransparentImage();
	}
	
	public Object clone() {
		return new Rook(getColor(), getRowIndex(), getColIndex());
	}
}
