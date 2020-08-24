/*
 * Copyright © 2020 David Xu All rights reserved. Use only for non-commercial purposes. Authorization required for other purposes. Use at your own risk.
 * This class is part of a chess game made for the AP Computer Science A's final project at University High School, Irvine CA. 
 */
import java.util.ArrayList;

import java.awt.Image;
import javax.swing.ImageIcon;

public class EmptySlot extends ChessPiece {

	
	public EmptySlot(int row, int col) { 
		super("none", row, col);
		super.setTransparentImage(getCorrectTile());
	}
	
	private Image getCorrectTile() {
		return new ImageIcon("src\\"+getBlackOrWhiteTile().toLowerCase()+"Tile.png").getImage();
	}
	
	public ArrayList<ChessPiece> getPossibleMoves(ChessPiece[][] board) {
		return new ArrayList<ChessPiece>();
	}
	
	public ArrayList<ChessPiece> getAttackedPieces(ChessPiece[][] board) {
		return new ArrayList<ChessPiece>();
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
		return  "O"+Integer.toString(getRowIndex())+""+Integer.toString(getColIndex())+" ";
	}
	
	public void setRowIndex(int row) {
		super.setRowIndex(row);
	}
	
	public void setColIndex(int col) {
		super.setColIndex(col);
	}

	@Override
	public ArrayList<ChessPiece> getDefendedPieces(ChessPiece[][] board) {
		// TODO Auto-generated method stub
		return new ArrayList<ChessPiece>();
	}
	
	public Image getImage() {
		return super.getTransparentImage();
	}
	
	public void setImage(Image i) {
		super.setTransparentImage(i);
	}
	
	public Image getTransparentImage() {
		return getImage();
	}
	
	public Object clone() {
		return new EmptySlot(getRowIndex(), getColIndex());
	}
}
