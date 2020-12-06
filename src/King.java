/*
 * Copyright 2020 David Xu All rights reserved. Use for commercial purposes is prohibited.
 */
import java.util.ArrayList;

import java.awt.Image;
import javax.swing.ImageIcon;


public class King extends ChessPiece {

	private boolean hasMoved;
	
	public King(String color, int row, int col) {
		super(color, row, col);
		hasMoved = false;
		super.setTransparentImage(new ImageIcon(System.getProperty("user.dir")+"\\images\\"+getColor()+"King.png").getImage());
	}
	
	public ArrayList<ChessPiece> getPossibleMoves(ChessPiece[][] board) {
		ArrayList<ChessPiece> possibleMoves = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> intersections = getIntersectionsKingNearKing(board);
		
		//check all around
		
		for (int row = getRowIndex()-1; row < getRowIndex()+2; row++) {
			for (int col = getColIndex()-1; col < getColIndex()+2; col++) {
				//check if row, col is on the board (don't add)
				//check if row, col is getRowIndex(), getColIndex() (don't add) 
				//check if row, col is under attack (don't add)
				//check if row, col is an intersection between this king's surrouding 8 spots and the other king's surrounding 8 spots (don't add)
				//check if row, col has an empty slot or enemy piece (add)
					//if enemy piece, check if it is defended
				
				
				if (row < 0 || row > 7 || col < 0 || col > 7) {
					continue;
				}
				if (row == getRowIndex() && col == getColIndex()) {
					continue;
				}
				if (positionUnderAttack(board[row][col], board)) {
					continue;
				}
				
				boolean kingIntersection = false;
				
				for (ChessPiece intersection : intersections) {
					if (intersection.equals(board[row][col])) {
						kingIntersection = true;
						break;
					}
				}
				if (kingIntersection) {
					continue;
				}
				if (board[row][col] instanceof EmptySlot || !board[row][col].getColor().equals(getColor()) && !board[row][col].isDefended(board)) {
					possibleMoves.add(board[row][col]);
					continue;
				}
				
			}
		}
		
		//castling
		ChessPiece rightRook = null;
		ChessPiece leftRook = null;
		if (getColIndex()+3 < 8) {
			rightRook = board[getRowIndex()][getColIndex()+3];
		}
		if (getColIndex()-4 > -1) {
			leftRook = board[getRowIndex()][getColIndex()-4];
		}
		if (!(rightRook == null) && rightRook instanceof Rook) {
			if (canCastle((Rook)rightRook, board)) {
				possibleMoves.add(board[getRowIndex()][getColIndex()+2]);
			}
		}
		if (!(leftRook == null) && leftRook instanceof Rook) {
			if (canCastle((Rook)leftRook, board)) {
				possibleMoves.add(board[getRowIndex()][getColIndex()-2]);
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
	
	public boolean kingWouldBeUnderCheck(ChessPiece moveTo, ChessPiece[][] board) {

		ChessPiece[][] testBoard = new ChessPiece[board.length][board[0].length];
		for (int row = 0; row<board.length; row++) {
			for (int col = 0; col<board[row].length; col++) {
				testBoard[row][col] = (ChessPiece) board[row][col].clone();
			}
		}
		ChessPiece me = testBoard[getRowIndex()][getColIndex()];
		
		int toRow = moveTo.getRowIndex();
		int toCol = moveTo.getColIndex();
		int fromRow = getRowIndex();
		int fromCol = getColIndex();
		
		testBoard[toRow][toCol] = testBoard[fromRow][fromCol];
		testBoard[toRow][toCol].setColIndex(toCol);
		testBoard[toRow][toCol].setRowIndex(toRow);
		testBoard[fromRow][fromCol] = new EmptySlot(fromRow, fromCol);
		me = testBoard[toRow][toCol];
		if (me instanceof King) {
			return ((King)me).getUnderCheck(testBoard);
		}
		return false;
		
	}
	
	
	public ArrayList<ChessPiece> getDefendedPieces(ChessPiece[][] board) {
		ArrayList<ChessPiece> defendedPieces = new ArrayList<ChessPiece>();
		
		//check all around
		
		for (int row = getRowIndex()-1; row < getRowIndex()+2; row++) {
			for (int col = getColIndex()-1; col < getColIndex()+2; col++) {
				//check if row, col is on the board (don't add)
				//check if row, col is getRowIndex(), getColIndex() (don't add) 
				//check if row, col has an ally piece (add)
				
				if (row < 0 || row > 7 || col < 0 || col > 7) {
					continue;
				}
				if (row == getRowIndex() && col == getColIndex()) {
					continue;
				}
				if (!(board[row][col] instanceof EmptySlot) && board[row][col].getColor().equals(getColor())) {
					defendedPieces.add(board[row][col]);
				}
				
			}
		}
		
		return defendedPieces;
	}
	
	public ArrayList<ChessPiece> getAttackedPieces(ChessPiece[][] board) {
		ArrayList<ChessPiece> attackedPieces = new ArrayList<ChessPiece>();
		
		//check all around
		
		for (int row = getRowIndex()-1; row < getRowIndex()+2; row++) {
			for (int col = getColIndex()-1; col < getColIndex()+2; col++) {
				//check if row, col is on the board (don't add)
				//check if row, col is getRowIndex(), getColIndex() (don't add) 
				//check if row, col has an ally piece (add)
				
				if (row < 0 || row > 7 || col < 0 || col > 7) {
					continue;
				}
				if (row == getRowIndex() && col == getColIndex()) {
					continue;
				}
				if (!(board[row][col] instanceof EmptySlot) && !board[row][col].getColor().equals(getColor())) {
					attackedPieces.add(board[row][col]);
				}
				
			}
		}
		
		return attackedPieces;
	}
	
	private boolean canCastle(Rook r, ChessPiece[][] board) {
		//conditions for castling
		/**
		 *  Neither the king nor the rook has previously moved during the game.
		 *  There are no pieces between the king and the rook.
		 *  The king cannot be in check, nor can the king pass through any square that is under attack by 
		 *  an enemy piece, or move to a square that would result in check. 
		 * (Note that castling is permitted if the rook is under attack, or if the rook crosses an attacked square.)
		 */
		
		if (getUnderCheck(board)) {
			return false;
		}
		if (r.getHasMoved() || hasMoved) {
			return false;
		}
		if (piecesBetweenKingAndRook(r, board)) {
			return false;
		}
		if (castlingWouldResultInTheKingPassingThroughOrArrivingAtASquareUnderAttack(r, board)) {
			return false;
		}
		
		
		return true;
	}
	
	private boolean piecesBetweenKingAndRook(Rook r, ChessPiece[][] board) { 
		int col = getColIndex();
		if (r.getColIndex() > getColIndex()) {
			col = getColIndex()+1;
			for (; col < r.getColIndex(); col++) {
				if (!(board[getRowIndex()][col] instanceof EmptySlot)) {
					return true;
				}
			}
		} else {
			col = getColIndex()-1;
			for (; col > r.getColIndex(); col--) {
				if (!(board[getRowIndex()][col] instanceof EmptySlot)) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	private boolean castlingWouldResultInTheKingPassingThroughOrArrivingAtASquareUnderAttack(Rook r, ChessPiece[][] board) {
		int change = 0;
		if (r.getColIndex() > getColIndex()) {
			change = 1;
		} else {
			change = -1;
		}
		//loop through every piece on the board, if the piece is an enemy piece, 
		//check to see if one of its possible moves is the spot we are checking 
		//(one of the two spots the king will pass through)
		if (getColIndex() + change < 8 && getColIndex() + change > -1 && getColIndex() + 2*change < 8 && getColIndex() + 2*change > -1) {
			return (positionUnderAttack(board[getRowIndex()][getColIndex() + change], board) || positionUnderAttack(board[getRowIndex()][getColIndex() + change*2], board));
		}
		return true;
		
	}
	
	
	private boolean positionUnderAttack(ChessPiece position, ChessPiece[][] board) { //this method is used to determine if the king can move to position
		//loop through every piece on the board EXCLUDING KINGS, if the piece is an enemy piece, 
		//check to see if one of its possible moves is the spot we are checking 
		
		
		for (ChessPiece[] row : board) {
			for (ChessPiece piece : row) {
				if (!(piece instanceof EmptySlot) && !piece.getColor().equals(getColor())) {
					if (piece instanceof King) {
						continue;
					}
					if (piece instanceof Pawn) {
						for (ChessPiece attack : ((Pawn)piece).getPossibleAttacks(board)) {
							if (position.equals(attack)) {
								return true;
							}
						}
					} else {
						for (ChessPiece attack : piece.getPossibleMoves(board)) {
							if (position.equals(attack)) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	private ArrayList<ChessPiece> getIntersectionsKingNearKing(ChessPiece[][] board) {
//		System.out.println("This is inside the getIntersecitonsKingNearKing (King) function.");
		//create an arraylist of intersecting positions
		//check if the position we are questioning is contained in this list
		// if it is, return true
		// otherwise return false
		ArrayList<ChessPiece> intersections = new ArrayList<ChessPiece>();
		//find the other king
		int row = 0;
		int col = 0;
		while (!(board[row][col] instanceof King && !board[row][col].getColor().equals(getColor()))) {
			if (col + 1 == 8) {
				col = 0;
				row++;
			}
			col++;
			if (row == 8) {
				break; //didn't find the opposing king, shouldn't happen though
			}
		}
		for (int r = getRowIndex()-1; r < getRowIndex()+2; r++) {
			for (int c = getColIndex()-1; c < getColIndex()+2; c++) {
				if (r == getRowIndex() && c == getColIndex()) {
					continue;
				}
				int otherKingRowIndex = ((King)board[row][col]).getRowIndex();
				int otherKingColIndex = ((King)board[row][col]).getColIndex();
				for (int r2 = otherKingRowIndex-1; r2 < otherKingRowIndex+2; r2++) {
					for (int c2 = otherKingColIndex-1; c2 < otherKingColIndex+2; c2++) {
						if (r2 == otherKingRowIndex && c2 == otherKingColIndex) {
							continue;
						}
						if (r == r2 && c == c2 && (r > -1 && r < 8 && c > -1 && c < 8)) {
							intersections.add(board[r][c]);
						}
					}
				}
			}
		}
		return intersections;
		
	}

	public boolean getUnderCheck(ChessPiece[][] board) {

		for (ChessPiece[] row : board) {
			for (ChessPiece col : row) {
				if (!(col instanceof EmptySlot) && !(col instanceof King) && !col.getColor().equals(getColor())) {
					for (ChessPiece move : col.getAttackedPieces(board)) {
						if (move.equals(this)) {
							return true;
						}
					}
				}
			}
		}
		return false;
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
		String hasMovedOneLetter = "";
		if (hasMoved) {
			hasMovedOneLetter = "t";
		} else {
			hasMovedOneLetter = "f";
		}
		return "K"+Integer.toString(getRowIndex())+""+Integer.toString(getColIndex())+getColor().substring(0,1).toUpperCase()+hasMovedOneLetter;
	}
	
	public void setHasMoved(boolean bool) {
		hasMoved = bool;
	}
	
	public void setRowIndex(int row) {
		super.setRowIndex(row);
	}
	
	public void setColIndex(int col) {
		super.setColIndex(col);
	}
	
	public Image getImage() {
		return new ImageIcon(System.getProperty("user.dir")+"\\images\\"+getColor()+"King"+getBlackOrWhiteTile()+"Tile.png").getImage();
	}
	
	public void setTransparentImage(Image i) {
		super.setTransparentImage(i);
	}
	
	public Image getTransparentImage() {
		return super.getTransparentImage();
	}
	
	public Object clone() {
		return new King(getColor(), getRowIndex(), getColIndex());
	}
	
}
