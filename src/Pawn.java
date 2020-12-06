/*
 * Copyright 2020 David Xu All rights reserved. Use for commercial purposes is prohibited.
 */
import java.util.ArrayList;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Pawn extends ChessPiece {

	private boolean firstMove;
	private boolean enPassantable;
	public static final String KNIGHT_PROMOTION = "knight";
	public static final String BISHOP_PROMOTION = "bishop";
	public static final String ROOK_PROMOTION = "rook";
	public static final String QUEEN_PROMOTION = "queen";
	private static String promotionType = QUEEN_PROMOTION;


	public Pawn(String color, int row, int col) {
		super(color, row, col);
		firstMove = true;
		enPassantable = false;
		super.setTransparentImage(new ImageIcon(System.getProperty("user.dir")+"\\images\\"+getColor()+"Pawn.png").getImage());

	}
	
	/**
	 * Promotes the pawn to a queen
	 * @param board The chess board
	 */
	public void promote(ChessPiece[][] board) {
		System.out.println("promotion type: " + promotionType);
		if (promotionType.equals(QUEEN_PROMOTION)) {
			board[getRowIndex()][getColIndex()] = new Queen(getColor(), getRowIndex(), getColIndex());
		} else if (promotionType.equals(ROOK_PROMOTION)) {
			board[getRowIndex()][getColIndex()] = new Rook(getColor(), getRowIndex(), getColIndex());
		} else if (promotionType.equals(BISHOP_PROMOTION)) {
			board[getRowIndex()][getColIndex()] = new Bishop(getColor(), getRowIndex(), getColIndex());
		} else if (promotionType.equals(KNIGHT_PROMOTION)) {
			board[getRowIndex()][getColIndex()] = new Knight(getColor(), getRowIndex(), getColIndex());
		}
		
	}
	
	/**
	 * Return an ArrayList of all the possible moves of this piece
	 */
	public ArrayList<ChessPiece> getPossibleMoves(ChessPiece[][] board) {

		ArrayList<ChessPiece> possibleMoves = new ArrayList<ChessPiece>();
		
		
		int forward = 0;
		//if the pawn is white
		if (getColor().equals(ChessGame.WHITE)) {
			forward = -1;
		} else if (getColor().equals(ChessGame.BLACK)) {
			forward = 1; 
		}
		
		if (getRowIndex()+forward > -1 && getRowIndex()+forward < 8 && board[getRowIndex()+forward][getColIndex()] instanceof EmptySlot) { //checking spot directly in front of pawn
			possibleMoves.add(board[getRowIndex()+forward][getColIndex()]);
			if (firstMove) { //first move where pawn can move two forwards instead of one
				if (board[getRowIndex()+2*forward][getColIndex()] instanceof EmptySlot) {
					possibleMoves.add(board[getRowIndex()+2*forward][getColIndex()]);
				}
			}
		} 
		//checking diagonals
		if (getRowIndex()+forward > -1 && getRowIndex()+forward < 8) {
			if (getColIndex() > 0) {
				if (!(board[getRowIndex()+forward][getColIndex()-1] instanceof EmptySlot) && !board[getRowIndex()+forward][getColIndex()-1].getColor().equals(getColor())) {
					// if the top left chess piece is not an empty slot and its getColor() is not this pawn's getColor() (an enemy piece)
					possibleMoves.add(board[getRowIndex()+forward][getColIndex()-1]);
				}
			}
			if (getColIndex() < 7) {
				if (!(board[getRowIndex()+forward][getColIndex()+1] instanceof EmptySlot) && !board[getRowIndex()+forward][getColIndex()+1].getColor().equals(getColor())) {
					possibleMoves.add(board[getRowIndex()+forward][getColIndex()+1]);
				}
			}
		}
		
		//en passant
		if (getColIndex() < 7) {
			if (board[getRowIndex()][getColIndex()+1] instanceof Pawn && ((Pawn) board[getRowIndex()][getColIndex()+1]).getEnPassantable()) {
				possibleMoves.add(board[getRowIndex()+forward][getColIndex()+1]);
			}
		} 
		if (getColIndex() > 0) {
			if (board[getRowIndex()][getColIndex()-1] instanceof Pawn && ((Pawn) board[getRowIndex()][getColIndex()-1]).getEnPassantable()) {
				possibleMoves.add(board[getRowIndex()+forward][getColIndex()-1]);
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
	

	/**
	 * Returns list of all of this pawn's possible attacks
	 * @param board The chess board
	 * @return List of all attack
	 */
	public ArrayList<ChessPiece> getPossibleAttacks(ChessPiece[][] board) { //this method is used by the King class in the positionUnderAttack method. 
		ArrayList<ChessPiece> possibleAttacks = new ArrayList<ChessPiece>();
		
		if (getColor().equals(ChessGame.WHITE) && getRowIndex() > 0) {
			if (getColIndex() > 0) {
				possibleAttacks.add(board[getRowIndex()-1][getColIndex()-1]);
			}
			if (getColIndex() < 7) {
				possibleAttacks.add(board[getRowIndex()-1][getColIndex()+1]);
			}
		} else if (getColor().equals(ChessGame.BLACK) && getRowIndex() < 7) {
			if (getColIndex() > 0) {
				possibleAttacks.add(board[getRowIndex()+1][getColIndex()-1]);
			}
			if (getColIndex() < 7) {
				possibleAttacks.add(board[getRowIndex()+1][getColIndex()+1]);
			}
		}
		
		return possibleAttacks;
	}

	/**
	 * Returns list of all pieces this piece is defending
	 * @return List of defended pieces
	 */
	public ArrayList<ChessPiece> getDefendedPieces(ChessPiece[][] board) {
		ArrayList<ChessPiece> defendedPieces = new ArrayList<ChessPiece>();
		
		int forward = 0;
		//if the pawn is white
		if (getColor().equals(ChessGame.WHITE)) {
			forward = -1;
		} else if (getColor().equals(ChessGame.BLACK)) {
			forward = 1; 
		}
		if (forward == -1 && getRowIndex() > 0 || forward == 1 && getRowIndex() < 7 ) {
			
			if (getColIndex() > 0 && board[getRowIndex()+forward][getColIndex()-1].getColor().equals(getColor())) {
				defendedPieces.add(board[getRowIndex()+forward][getColIndex()-1]);
			}
			if (getColIndex() < 7 && board[getRowIndex()+forward][getColIndex()+1].getColor().equals(getColor())) {
				defendedPieces.add(board[getRowIndex()+forward][getColIndex()+1]);
			}

		} 
		
		return defendedPieces;
	}
	
	/**
	 * Returns all pieces this piece is attacking
	 * @return List of attacked pieces
	 */
	public ArrayList<ChessPiece> getAttackedPieces(ChessPiece[][] board) {
		ArrayList<ChessPiece> attackedPieces = new ArrayList<ChessPiece>();
		int forward = 0;
		//if the pawn is white
		if (getColor().equals(ChessGame.WHITE)) {
			forward = -1;
		} else if (getColor().equals(ChessGame.BLACK)) {
			forward = 1; 
		}
		if (forward == -1 && getRowIndex() > 0 || forward == 1 && getRowIndex() < 7 ) {
			
			if (getColIndex() > 0 && !board[getRowIndex()+forward][getColIndex()-1].getColor().equals(getColor())) {
				attackedPieces.add(board[getRowIndex()+forward][getColIndex()-1]);
			}
			if (getColIndex() < 7 && !board[getRowIndex()+forward][getColIndex()+1].getColor().equals(getColor())) {
				attackedPieces.add(board[getRowIndex()+forward][getColIndex()+1]);
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
	
	public void setRowIndex(int row) {
		super.setRowIndex(row);
	}
	
	public void setColIndex(int col) {
		super.setColIndex(col);
	}
	
	public boolean getEnPassantable() {
		return enPassantable;
	}
	
	public String toString() {
		String firstMoveOneLetter = "";
		if (firstMove) {
			firstMoveOneLetter = "t";
		} else {
			firstMoveOneLetter = "f";
		}
		String enPassantableOneLetter = "";
		if (enPassantable) {
			enPassantableOneLetter = "t";
		} else {
			enPassantableOneLetter = "f";
		}
		return "p"+Integer.toString(getRowIndex())+""+Integer.toString(getColIndex())+getColor().substring(0,1).toUpperCase()+firstMoveOneLetter+enPassantableOneLetter;
	}
	
	public void setFirstMove(boolean bool) {
		firstMove = bool;
	}
	
	public void setEnPassantable(boolean bool) {
		enPassantable = bool;
	}

	public Image getImage() {
		return new ImageIcon(System.getProperty("user.dir")+"\\images\\"+getColor()+"Pawn"+getBlackOrWhiteTile()+"Tile.png").getImage();
	}
	
	public void setTransparentImage(Image i) {
		super.setTransparentImage(i);
	}
	
	public Image getTransparentImage() {
		return super.getTransparentImage();
	}
	
	public Object clone() {
		return new Pawn(getColor(), getRowIndex(), getColIndex());
	}
	
	public static void setPromotionType(String type) {
		promotionType = type;
	}
	
	public static String getPromotionType() {
		return promotionType;
	}
}
