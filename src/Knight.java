
import java.util.ArrayList;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Knight extends ChessPiece {

	public Knight(String color, int row, int col) {
		super(color, row, col);
		super.setTransparentImage(new ImageIcon("src\\"+getColor()+"Knight.png").getImage());
	}
	
	public ArrayList<ChessPiece> getPossibleMoves(ChessPiece[][] board) {
		ArrayList<ChessPiece> possibleMoves = new ArrayList<ChessPiece>();
		
		//check 2 up positions
		if (getRowIndex()-2 > -1) {
			if (getColIndex()-1 > -1) {
				if (board[getRowIndex()-2][getColIndex()-1] instanceof EmptySlot || !board[getRowIndex()-2][getColIndex()-1].getColor().equals(getColor())) {
					possibleMoves.add(board[getRowIndex()-2][getColIndex()-1]);
				}
			}
			if (getColIndex()+1 < 8) {
				if (board[getRowIndex()-2][getColIndex()+1] instanceof EmptySlot || !board[getRowIndex()-2][getColIndex()+1].getColor().equals(getColor())) {
					possibleMoves.add(board[getRowIndex()-2][getColIndex()+1]);
				}
			}
		}
		//check 2 left positions
		if (getColIndex()-2 > -1) {
			if (getRowIndex()-1 > -1) {
				if (board[getRowIndex()-1][getColIndex()-2] instanceof EmptySlot || !board[getRowIndex()-1][getColIndex()-2].getColor().equals(getColor())) {
					possibleMoves.add(board[getRowIndex()-1][getColIndex()-2]);
				}
			}
			if (getRowIndex()+1 < 8) {
				if (board[getRowIndex()+1][getColIndex()-2] instanceof EmptySlot || !board[getRowIndex()+1][getColIndex()-2].getColor().equals(getColor())) {
					possibleMoves.add(board[getRowIndex()+1][getColIndex()-2]);
				}
			}
		}
		//check 2 right positions
		if (getColIndex()+2 < 8) {
			if (getRowIndex()-1 > -1) {
				if (board[getRowIndex()-1][getColIndex()+2] instanceof EmptySlot || !board[getRowIndex()-1][getColIndex()+2].getColor().equals(getColor())) {
					possibleMoves.add(board[getRowIndex()-1][getColIndex()+2]);
				}
			}
			if (getRowIndex()+1 < 8) {
				if (board[getRowIndex()+1][getColIndex()+2] instanceof EmptySlot || !board[getRowIndex()+1][getColIndex()+2].getColor().equals(getColor())) {
					possibleMoves.add(board[getRowIndex()+1][getColIndex()+2]);
				}
			}
		}
		//check 2 down positions
		if (getRowIndex()+2 < 8) {
			if (getColIndex()-1 > -1) {
				if (board[getRowIndex()+2][getColIndex()-1] instanceof EmptySlot || !board[getRowIndex()+2][getColIndex()-1].getColor().equals(getColor())) {
					possibleMoves.add(board[getRowIndex()+2][getColIndex()-1]);
				}
			}
			if (getColIndex()+1 < 8) {
				if (board[getRowIndex()+2][getColIndex()+1] instanceof EmptySlot || !board[getRowIndex()+2][getColIndex()+1].getColor().equals(getColor())) {
					possibleMoves.add(board[getRowIndex()+2][getColIndex()+1]);
				}
			}
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
		
		//check 2 up positions
		if (getRowIndex()-2 > -1) {
			if (getColIndex()-1 > -1) {
				if (!(board[getRowIndex()-2][getColIndex()-1] instanceof EmptySlot) && board[getRowIndex()-2][getColIndex()-1].getColor().equals(getColor())) {
					defendedPieces.add(board[getRowIndex()-2][getColIndex()-1]);
				}
			}
			if (getColIndex()+1 < 8) {
				if (!(board[getRowIndex()-2][getColIndex()+1] instanceof EmptySlot) && board[getRowIndex()-2][getColIndex()+1].getColor().equals(getColor())) {
					defendedPieces.add(board[getRowIndex()-2][getColIndex()+1]);
				}
			}
		}
		//check 2 left positions
		if (getColIndex()-2 > -1) {
			if (getRowIndex()-1 > -1) {
				if (!(board[getRowIndex()-1][getColIndex()-2] instanceof EmptySlot) && board[getRowIndex()-1][getColIndex()-2].getColor().equals(getColor())) {
					defendedPieces.add(board[getRowIndex()-1][getColIndex()-2]);
				}
			}
			if (getRowIndex()+1 < 8) {
				if (!(board[getRowIndex()+1][getColIndex()-2] instanceof EmptySlot) && board[getRowIndex()+1][getColIndex()-2].getColor().equals(getColor())) {
					defendedPieces.add(board[getRowIndex()+1][getColIndex()-2]);
				}
			}
		}
		//check 2 right positions
		if (getColIndex()+2 < 8) {
			if (getRowIndex()-1 > -1) {
				if (!(board[getRowIndex()-1][getColIndex()+2] instanceof EmptySlot) && board[getRowIndex()-1][getColIndex()+2].getColor().equals(getColor())) {
					defendedPieces.add(board[getRowIndex()-1][getColIndex()+2]);
				}
			}
			if (getRowIndex()+1 < 8) {
				if (!(board[getRowIndex()+1][getColIndex()+2] instanceof EmptySlot) && board[getRowIndex()+1][getColIndex()+2].getColor().equals(getColor())) {
					defendedPieces.add(board[getRowIndex()+1][getColIndex()+2]);
				}
			}
		}
		//check 2 down positions
		if (getRowIndex()+2 < 8) {
			if (getColIndex()-1 > -1) {
				if (!(board[getRowIndex()+2][getColIndex()-1] instanceof EmptySlot) && board[getRowIndex()+2][getColIndex()-1].getColor().equals(getColor())) {
					defendedPieces.add(board[getRowIndex()+2][getColIndex()-1]);
				}
			}
			if (getColIndex()+1 < 8) {
				if (!(board[getRowIndex()+2][getColIndex()+1] instanceof EmptySlot) && board[getRowIndex()+2][getColIndex()+1].getColor().equals(getColor())) {
					defendedPieces.add(board[getRowIndex()+2][getColIndex()+1]);
				}
			}
		}
	
		return defendedPieces;
	}
	
	public ArrayList<ChessPiece> getAttackedPieces(ChessPiece[][] board) {
		ArrayList<ChessPiece> attackedPieces = new ArrayList<ChessPiece>();
		
		//check 2 up positions
		if (getRowIndex()-2 > -1) {
			if (getColIndex()-1 > -1) {
				if (!(board[getRowIndex()-2][getColIndex()-1] instanceof EmptySlot) && !board[getRowIndex()-2][getColIndex()-1].getColor().equals(getColor())) {
					attackedPieces.add(board[getRowIndex()-2][getColIndex()-1]);
				}
			}
			if (getColIndex()+1 < 8) {
				if (!(board[getRowIndex()-2][getColIndex()+1] instanceof EmptySlot) && !board[getRowIndex()-2][getColIndex()+1].getColor().equals(getColor())) {
					attackedPieces.add(board[getRowIndex()-2][getColIndex()+1]);
				}
			}
		}
		//check 2 left positions
		if (getColIndex()-2 > -1) {
			if (getRowIndex()-1 > -1) {
				if (!(board[getRowIndex()-1][getColIndex()-2] instanceof EmptySlot) && !board[getRowIndex()-1][getColIndex()-2].getColor().equals(getColor())) {
					attackedPieces.add(board[getRowIndex()-1][getColIndex()-2]);
				}
			}
			if (getRowIndex()+1 < 8) {
				if (!(board[getRowIndex()+1][getColIndex()-2] instanceof EmptySlot) && !board[getRowIndex()+1][getColIndex()-2].getColor().equals(getColor())) {
					attackedPieces.add(board[getRowIndex()+1][getColIndex()-2]);
				}
			}
		}
		//check 2 right positions
		if (getColIndex()+2 < 8) {
			if (getRowIndex()-1 > -1) {
				if (!(board[getRowIndex()-1][getColIndex()+2] instanceof EmptySlot) && !board[getRowIndex()-1][getColIndex()+2].getColor().equals(getColor())) {
					attackedPieces.add(board[getRowIndex()-1][getColIndex()+2]);
				}
			}
			if (getRowIndex()+1 < 8) {
				if (!(board[getRowIndex()+1][getColIndex()+2] instanceof EmptySlot) && !board[getRowIndex()+1][getColIndex()+2].getColor().equals(getColor())) {
					attackedPieces.add(board[getRowIndex()+1][getColIndex()+2]);
				}
			}
		}
		//check 2 down positions
		if (getRowIndex()+2 < 8) {
			if (getColIndex()-1 > -1) {
				if (!(board[getRowIndex()+2][getColIndex()-1] instanceof EmptySlot) && !board[getRowIndex()+2][getColIndex()-1].getColor().equals(getColor())) {
					attackedPieces.add(board[getRowIndex()+2][getColIndex()-1]);
				}
			}
			if (getColIndex()+1 < 8) {
				if (!(board[getRowIndex()+2][getColIndex()+1] instanceof EmptySlot) && !board[getRowIndex()+2][getColIndex()+1].getColor().equals(getColor())) {
					attackedPieces.add(board[getRowIndex()+2][getColIndex()+1]);
				}
			}
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
		return "n"+Integer.toString(getRowIndex())+""+Integer.toString(getColIndex())+getColor().substring(0,1).toUpperCase();
	}
	
	public void setRowIndex(int row) {
		super.setRowIndex(row);
	}
	
	public void setColIndex(int col) {
		super.setColIndex(col);
	}
	
	public Image getImage() {
		return new ImageIcon("src\\"+getColor()+"Knight"+getBlackOrWhiteTile()+"Tile.png").getImage();
	}
	
	public void setTransparentImage(Image i) {
		super.setTransparentImage(i);
	}
	
	public Image getTransparentImage() {
		return super.getTransparentImage();
	}
	
	public Object clone() {
		return new Knight(getColor(), getRowIndex(), getColIndex());
	}
}
