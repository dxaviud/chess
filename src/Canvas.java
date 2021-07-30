package src;

import chess.pieces.ChessPiece;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import java.util.ArrayList;

public class Canvas extends JComponent{
	private final int WIDTH;
	private final int HEIGHT;
	private ChessGame game;
	
	
	
	public Canvas(int width, int height, ChessGame game) {
		WIDTH = width;
		HEIGHT = height;
		this.game = game;
	}
	
	public void paintComponent(Graphics gr) {
		Graphics2D g = (Graphics2D) gr;
		if (game.getShowTitleScreen()) {
			g.drawImage(game.getTitleScreenImage(), 0, 0, this);
		} else {
			g.setFont(new Font("default", Font.BOLD, 16));
			g.drawString("Player turn: " + game.getPlayerTurn(), 25, 658);
			if (game.getPlayerTurn().equals(ChessGame.WHITE)) {
				for (ChessPiece[] row : game.getBoard()) {
					for (ChessPiece col : row) {
						g.drawImage(col.getImage(), col.getColIndex()*80, col.getRowIndex()*80, this);
						if (game.getSelectedPiece() != null && game.getSelectedPiece().equals(col)) {
							g.drawImage(new ImageIcon(System.getProperty("user.dir")+"\\chess\\images\\selectedPiece.png").getImage(), col.getColIndex()*80, col.getRowIndex()*80, this);
						}
						if (game.getSelectedPiece() != null && isPossibleMove(col, game.getSelectedPiece().getPossibleMoves(game.getBoard()))) {
							g.drawImage(new ImageIcon(System.getProperty("user.dir")+"\\chess\\images\\possibleMove.png").getImage(), col.getColIndex()*80, col.getRowIndex()*80, this);
						}
						if (game.getLastMovedPiece() != null && game.getLastMovedPiece().equals(col)) {
							g.drawImage(new ImageIcon(System.getProperty("user.dir")+"\\chess\\images\\lastMoved.png").getImage(), col.getColIndex()*80, col.getRowIndex()*80, this);
						}
					}
				}
			} else {
				for (int row = 7; row > -1; row--) {
					for (int col = 7; col > -1; col--) {
						g.drawImage(game.getBoard()[row][col].getImage(), (7-col)*80, (7-row)*80, this);
						if (game.getSelectedPiece() != null && game.getSelectedPiece().equals(game.getBoard()[row][col])) {
							g.drawImage(new ImageIcon(System.getProperty("user.dir")+"\\chess\\images\\selectedPiece.png").getImage(), (7-col)*80, (7-row)*80, this);
						}
						if (game.getSelectedPiece() != null && isPossibleMove(game.getBoard()[row][col], game.getSelectedPiece().getPossibleMoves(game.getBoard()))) {
							g.drawImage(new ImageIcon(System.getProperty("user.dir")+"\\chess\\images\\possibleMove.png").getImage(), (7-col)*80, (7-row)*80, this);
						}
						if (game.getLastMovedPiece() != null && game.getLastMovedPiece().equals(game.getBoard()[row][col])) {
							g.drawImage(new ImageIcon(System.getProperty("user.dir")+"\\chess\\images\\lastMoved.png").getImage(), (7-col)*80, (7-row)*80, this);
						}
					}
				}
			}
			if (game.getAnimating()) {
				g.drawImage(game.getAnimatedPiece().getTransparentImage(), game.getAnimatedPieceCol(), game.getAnimedPieceRow(), this);
			} else if (game.getGameOver()) {
				if (game.getWinner().equals(ChessGame.WHITE)) {
					g.drawImage(new ImageIcon(System.getProperty("user.dir")+"\\chess\\images\\gameOverWhiteWin.png").getImage(), 80, 160, this);
				} else if (game.getWinner().equals(ChessGame.BLACK)){
					g.drawImage(new ImageIcon(System.getProperty("user.dir")+"\\chess\\images\\gameOverBlackWin.png").getImage(), 80, 160, this);
				} else {
					g.drawImage(new ImageIcon(System.getProperty("user.dir")+"\\chess\\images\\stalemate.png").getImage(), 80, 160, this);
				}
			}
		}
		if (game.getTimedGame()) {
			if (game.getPlayerTurn().equals(ChessGame.WHITE)) {
				g.setFont(new Font("default", Font.BOLD, 16));
				g.drawString("White time left: " + game.getWhiteTimeRemaining()/1000 + " seconds", 400, 658);
			} else {
				g.drawString("Black time left: " + game.getBlackTimeRemaining()/1000 + " seconds", 400, 658);
			}
		}
		
		
		
	}
	
	private boolean isPossibleMove(ChessPiece move, ArrayList<ChessPiece> possibleMoves) {
		for (ChessPiece m : possibleMoves) {
			if (m.equals(move)) {
				return true;
			}
		}
		return false;
	}
	
	


	public ChessPiece getClickedPiece(int x, int y) {
		if (game.getShowTitleScreen()) {
			return null;
		} else if (game.getAnimating()) {
			return null;
		}
		int imageX = 0;
		int imageY = 0;
		if (game.getPlayerTurn().equals(ChessGame.WHITE)) {
			for (ChessPiece[] row : game.getBoard()) {
				for (ChessPiece col : row) {
					imageX = col.getColIndex()*80;
					imageY = col.getRowIndex()*80;
					if (x>=imageX && x <= imageX + col.getImage().getWidth(this) && y >= imageY && y<= imageY + col.getImage().getHeight(this)) {
						return col;
					}
				}
			}
		} else {
			for (int row = 7; row > -1; row--) {
				for (int col = 7; col > -1; col--) {
					imageX = (7-col)*80;
					imageY = (7-row)*80;
					if (x>=imageX && x <= imageX + game.getBoard()[row][col].getImage().getWidth(this) && y >= imageY && y<= imageY + game.getBoard()[row][col].getImage().getHeight(this)) {
						return game.getBoard()[row][col];
					}
				}
			}
		}
		
		return null;
	}
	
	

	public void refresh() {
		repaint();
	}
	
	public int getWidth() {
		return WIDTH;
	}
	
	public int getHeight() {
		return HEIGHT;
	}
	
	

}

