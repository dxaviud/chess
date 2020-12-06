
/*
 * Copyright 2020 David Xu All rights reserved. Use for commercial purposes is prohibited.
 */

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;



public class ChessGame {

	private ChessPiece[][] chessBoard;
	private String playerTurn;
	public final static String WHITE = "white";
	public final static String BLACK = "black";
	private King whiteKing;
	private King blackKing;
	private String winner;
	private static final int WIDTH = 640;
	private static final int HEIGHT = 670;
	private JFrame frame;
	private Canvas canvas;
	private ChessPiece selectedPiece;
	private ChessPiece selectedPosition;
	private ChessPiece lastMovedPiece;
	private boolean gameOver;
	private ArrayList<MoveCollection> pastMoves;
	private int turns;
	private ChessPiece animatedPiece;
	private boolean animating;
	private int animatedPieceCol;
	private int animatedPieceRow;
	private final int SLOW_ANIMATION = 30;
	private final int MEDIUM_ANIMATION = 15;
	private final int FAST_ANIMATION = 4;
	private final int NO_ANIMATION = 0;
	private int animationSpeed;
	private Image titleScreenImage;
	private boolean showTitleScreen;
	private int whiteTimeRemaining;
	private int blackTimeRemaining;
	private Timer whiteTimer;
	private Timer blackTimer;
	private boolean timedGame;
	private boolean bulletGame;
	private final int BULLET_TIME_INCREMENT = 3000;
	private String gameType;
	private final String NO_TIME_LIMIT = "no time limit";
	private final String TEN_MIN_BLITZ = "ten minute blitz";
	private final String THREE_MIN_BULLET = "three minute bullet";
	private final String ONE_MIN_BULLET = "one minute bullet";

	public ChessGame() { 
		
		gameType = NO_TIME_LIMIT;
		
		class WhiteTimerListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				whiteTimeRemaining -= 1000;
				gameOver = outOfTime();
				canvas.refresh();
			}
		}
		whiteTimer = new Timer(1000, new WhiteTimerListener());
		class BlackTimerListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				blackTimeRemaining -= 1000;
				gameOver = outOfTime();
				canvas.refresh();
			}
		}
		blackTimer = new Timer(1000, new BlackTimerListener());
		

		titleScreenImage = new ImageIcon(System.getProperty("user.dir")+"\\images\\chessBackgroundImage.jpg").getImage();
		showTitleScreen = true;
		animationSpeed = MEDIUM_ANIMATION;
		animating = false;
		pastMoves = new ArrayList<MoveCollection>();
		turns = 0;
		
		chessBoard = new ChessPiece[8][8];
		
		chessBoard[0][0] = new Rook(BLACK, 0,0);
		chessBoard[0][1] = new Knight(BLACK, 0,1);
		chessBoard[0][2] = new Bishop(BLACK, 0,2);
		chessBoard[0][3] = new Queen(BLACK, 0,3);
		chessBoard[0][4] = new King(BLACK, 0,4);
		blackKing = (King) chessBoard[0][4];
		chessBoard[0][5] = new Bishop(BLACK, 0,5);
		chessBoard[0][6] = new Knight(BLACK, 0,6);
		chessBoard[0][7] = new Rook(BLACK, 0,7);
		
		for (int col = 0; col<8; col++) {
			chessBoard[1][col] = new Pawn(BLACK, 1,col);
		}
	
		chessBoard[7][0] = new Rook(WHITE, 7,0);
		chessBoard[7][1] = new Knight(WHITE, 7,1);
		chessBoard[7][2] = new Bishop(WHITE, 7,2);
		chessBoard[7][3] = new Queen(WHITE, 7,3);
		chessBoard[7][4] = new King(WHITE, 7,4);
		whiteKing = (King) chessBoard[7][4];
		chessBoard[7][5] = new Bishop(WHITE, 7,5);
		chessBoard[7][6] = new Knight(WHITE, 7,6);
		chessBoard[7][7] = new Rook(WHITE, 7,7);
		
		for (int col = 0; col<8; col++) {
			chessBoard[6][col] = new Pawn(WHITE, 6,col);
		}
		
		
		for (int row = 2; row < 6; row++) {
			for (int col = 0; col < 8; col++) {
				chessBoard[row][col] = new EmptySlot(row, col);
			}
		}
		
		frame = new JFrame("My Chess Game");
		canvas = new Canvas(WIDTH, HEIGHT, this);
		canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
	
		JMenuBar menuBar = new JMenuBar();
		
		
		JMenu optionsMenu = new JMenu("Options");
		
		JMenuItem reset = new JMenuItem("Reset");
		JMenuItem undo = new JMenuItem("Undo");
		JMenuItem copyrights = new JMenuItem("Copyrights");
		
		class ResetMenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		}	
		
		reset.addActionListener(new ResetMenuItemListener());
		
		class UndoMenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				undoMove();
			}
		}
		
		undo.addActionListener(new UndoMenuItemListener());
				
		class CopyrightsMenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				MessageBox.infoBox("Copyright 2020 David Xu All rights reserved. Use for commercial purposes is prohibited.", "Copyrights");
			}
		}

		copyrights.addActionListener(new CopyrightsMenuItemListener());
		
		optionsMenu.add(copyrights);
		optionsMenu.add(reset);
		optionsMenu.add(undo);
		
		JMenu animateSpeedMenu = new JMenu("Animation");
			
		JMenuItem slow = new JMenuItem("Slow");
		JMenuItem medium = new JMenuItem("Medium");
		JMenuItem fast = new JMenuItem("Fast");
		JMenuItem none = new JMenuItem("None");
		
		class SlowListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				animationSpeed = SLOW_ANIMATION;
			}
		}
		
		class MediumListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				animationSpeed = MEDIUM_ANIMATION;
			}
		}
		
		class FastListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				animationSpeed = FAST_ANIMATION;
			}
		}
		
		class NoneListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				animationSpeed = NO_ANIMATION;
			}
		}
		
		slow.addActionListener(new SlowListener());
		medium.addActionListener(new MediumListener());
		fast.addActionListener(new FastListener());
		none.addActionListener(new NoneListener());
		
		animateSpeedMenu.add(slow);
		animateSpeedMenu.add(medium);
		animateSpeedMenu.add(fast);
		animateSpeedMenu.add(none);
		
		JMenu startNewGameMenu = new JMenu("New game");
		
		JMenuItem noTimeLimitGame = new JMenuItem("No time limit game");
		JMenuItem tenMinuteBlitz = new JMenuItem("Ten minute blitz game");
		JMenuItem threeMinuteBullet = new JMenuItem("Three minute bullet game");
		JMenuItem oneMinuteBullet = new JMenuItem("One minute bullet game");
		
		class NoTimeLimitGameListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				gameType = NO_TIME_LIMIT;
				reset();
				showTitleScreen = false;
				bulletGame = false;
				timedGame = false;
				whiteTimeRemaining = -1000;
				blackTimeRemaining = -1000;
				canvas.refresh();
			}
		}
		
		noTimeLimitGame.addActionListener(new NoTimeLimitGameListener());
		
		class TenMinBlitzListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				gameType = TEN_MIN_BLITZ;
				reset();
				showTitleScreen = false;
				timedGame = true;
				bulletGame = false;
				whiteTimeRemaining = 600000;
				blackTimeRemaining = 600000;
				whiteTimer.start();
			}
		}
		
		tenMinuteBlitz.addActionListener(new TenMinBlitzListener());
		
		class ThreeMinBulletListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				gameType = THREE_MIN_BULLET;
				reset();
				showTitleScreen = false;
				timedGame = true;
				bulletGame = true;
				whiteTimeRemaining = 180000;
				blackTimeRemaining = 180000;
				whiteTimer.start();	
			}
		}
		
		threeMinuteBullet.addActionListener(new ThreeMinBulletListener());
		
		class OneMinBulletListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				gameType = ONE_MIN_BULLET;
				reset();
				showTitleScreen = false;
				timedGame = true;
				bulletGame = true;
				whiteTimeRemaining = 60000;
				blackTimeRemaining = 60000;
				whiteTimer.start();		
			}
		}
		
		oneMinuteBullet.addActionListener(new OneMinBulletListener());
		
		
		startNewGameMenu.add(noTimeLimitGame);
		startNewGameMenu.add(tenMinuteBlitz);
		startNewGameMenu.add(threeMinuteBullet);
		startNewGameMenu.add(oneMinuteBullet);
		
		JMenu setPromotionTypeMenu = new JMenu("Pawn promotion");
		
		JMenuItem setPromoTypeToQueen = new JMenuItem("Queen");
		JMenuItem setPromoTypeToRook = new JMenuItem("Rook");
		JMenuItem setPromoTypeToBishop = new JMenuItem("Bishop");
		JMenuItem setPromoTypeToKnight = new JMenuItem("Knight");
		
		class SetPromoTypeQueenListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				Pawn.setPromotionType(Pawn.QUEEN_PROMOTION);
			}
		}
		
		class SetPromoTypeRookListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				Pawn.setPromotionType(Pawn.ROOK_PROMOTION);
			}
		}
		
		class SetPromoTypeBishopListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				Pawn.setPromotionType(Pawn.BISHOP_PROMOTION);
			}
		}
		
		class SetPromoTypeKnightListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				Pawn.setPromotionType(Pawn.KNIGHT_PROMOTION);
			}
		}
		
		setPromoTypeToQueen.addActionListener(new SetPromoTypeQueenListener());
		setPromoTypeToRook.addActionListener(new SetPromoTypeRookListener());
		setPromoTypeToBishop.addActionListener(new SetPromoTypeBishopListener());
		setPromoTypeToKnight.addActionListener(new SetPromoTypeKnightListener());
		
		setPromotionTypeMenu.add(setPromoTypeToQueen);
		setPromotionTypeMenu.add(setPromoTypeToRook);
		setPromotionTypeMenu.add(setPromoTypeToBishop);
		setPromotionTypeMenu.add(setPromoTypeToKnight);
		
		JMenu saveMenu = new JMenu("Save");
		
		JMenuItem saveGame = new JMenuItem("Save game");
		JMenuItem loadGame = new JMenuItem("Load game");
		
		class saveGameListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				try {

					BufferedWriter bw = new BufferedWriter(new FileWriter("saveFile.txt"));
					
					bw.write(playerTurn);
					bw.newLine();
					System.out.println("after writing player turn");
					bw.write(winner);
					bw.newLine();

					for (int row = 0; row < chessBoard.length; row++) {
						for (int col = 0; col < chessBoard[row].length; col++) {
							bw.write(chessBoard[row][col].toString());
							bw.newLine();
						}
					}
					bw.close();
					pastMoves.clear();
					showTitleScreen = false;
					
					
					
				} catch(Exception exception) {}
				
				
			}
		}
		
		class loadGameListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				try {
	
					BufferedReader br = new BufferedReader(new FileReader("saveFile.txt"));
	
					gameType = NO_TIME_LIMIT;
					timedGame = false;
					
					String whiteOrBlack = br.readLine();
					playerTurn = whiteOrBlack;
					String winnerBool = br.readLine();
					winner = winnerBool;
					String chessPieceInfo = br.readLine();
					String chessPieceType;
					int chessPieceRowIndex;
					int chessPieceColIndex;
					String chessPieceColor;
					do {

						for (int row = 0; row < chessBoard.length; row++) {
							for (int col = 0; col < chessBoard[row].length; col++) {
								chessPieceType = chessPieceInfo.substring(0,1);
								chessPieceRowIndex = Integer.parseInt(chessPieceInfo.substring(1,2));
								chessPieceColIndex = Integer.parseInt(chessPieceInfo.substring(2,3));
								chessPieceColor = chessPieceInfo.substring(3,4);
								switch(chessPieceColor) {
									case "W":
										chessPieceColor = WHITE;
										break;
									case "B":
										chessPieceColor = BLACK;
										break;
								}
								switch(chessPieceType) {
									case "O": //empty slot
										chessBoard[row][col] = new EmptySlot(chessPieceRowIndex, chessPieceColIndex);
								
										break;
									case "p": //pawn
										chessBoard[row][col] = new Pawn(chessPieceColor, chessPieceRowIndex, chessPieceColIndex);
										String firstMoveOneLetter = chessPieceInfo.substring(4,5);
										if (firstMoveOneLetter.equals("t")) {
											((Pawn) chessBoard[row][col]).setFirstMove(true);
										} else {
											((Pawn) chessBoard[row][col]).setFirstMove(false);
										}
										String enPassantableOneLetter = chessPieceInfo.substring(5,6);
										if (enPassantableOneLetter.equals("t")) {
											((Pawn) chessBoard[row][col]).setEnPassantable(true);
										} else {
											((Pawn) chessBoard[row][col]).setEnPassantable(false);
										}
									
										break;
									case "b": //bishop
										chessBoard[row][col] = new Bishop(chessPieceColor, chessPieceRowIndex, chessPieceColIndex);
									
										break;
									case "n": //knight
										chessBoard[row][col] = new Knight(chessPieceColor, chessPieceRowIndex, chessPieceColIndex);
									
										break;
									case "r": //rook
										chessBoard[row][col] = new Rook(chessPieceColor, chessPieceRowIndex, chessPieceColIndex);
										String hasMovedOneLetterRook = chessPieceInfo.substring(4,5);
										if (hasMovedOneLetterRook.equals("t")) {
											((Rook) chessBoard[row][col]).setHasMoved(true);
										} else {
											((Rook) chessBoard[row][col]).setHasMoved(false);
										}
									
										break;
									case "q": //queen
										chessBoard[row][col] = new Queen(chessPieceColor, chessPieceRowIndex, chessPieceColIndex);
					
										break;
									case "K": //king
										chessBoard[row][col] = new King(chessPieceColor, chessPieceRowIndex, chessPieceColIndex);
										String hasMovedOneLetter = chessPieceInfo.substring(4,5);
										if (hasMovedOneLetter.equals("t")) {
											((King) chessBoard[row][col]).setHasMoved(true);
										} else {
											((King) chessBoard[row][col]).setHasMoved(false);
										}
										if (chessPieceColor.equals(WHITE)) {
											whiteKing = ((King) chessBoard[row][col]);
										} else if (chessPieceColor.equals(BLACK)) {
											blackKing = ((King) chessBoard[row][col]);
										}

										break;
								}
								chessPieceInfo = br.readLine();
							}
						}
						
					} while (chessPieceInfo != null);
					br.close();
					canvas.refresh();
				} catch (Exception exception) {}
				
			}
		}
		
		saveGame.addActionListener(new saveGameListener());
		loadGame.addActionListener(new loadGameListener());
		
		saveMenu.add(saveGame);
		saveMenu.add(loadGame);
		
		menuBar.add(startNewGameMenu);
		menuBar.add(saveMenu);
		menuBar.add(animateSpeedMenu);
		menuBar.add(setPromotionTypeMenu);
		menuBar.add(optionsMenu);

		selectedPiece = null;
		selectedPosition = null;
		
		class ClickChessPieceListener implements MouseListener {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				ChessPiece clickedPiece = canvas.getClickedPiece(e.getX(), e.getY());
				if (!(clickedPiece == null) && clickedPiece.getColor().equals(playerTurn)) {
					selectedPiece = clickedPiece;
					selectedPosition = null;
					lastMovedPiece = null;
					canvas.refresh();
				} else {
					selectedPosition = clickedPiece;
					canvas.refresh();
				}
			
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		}
		
		canvas.addMouseListener(new ClickChessPieceListener());
		
		
		frame.setJMenuBar(menuBar);
		frame.add(canvas);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		
	
		playerTurn = WHITE;
		gameOver = false;
		winner = "none";

	}
	
	public void reset() {
		chessBoard = new ChessPiece[8][8];
		
		chessBoard[0][0] = new Rook(BLACK, 0,0);
		chessBoard[0][1] = new Knight(BLACK, 0,1);
		chessBoard[0][2] = new Bishop(BLACK, 0,2);
		chessBoard[0][3] = new Queen(BLACK, 0,3);
		chessBoard[0][4] = new King(BLACK, 0,4);
		blackKing = (King) chessBoard[0][4];
		chessBoard[0][5] = new Bishop(BLACK, 0,5);
		chessBoard[0][6] = new Knight(BLACK, 0,6);
		chessBoard[0][7] = new Rook(BLACK, 0,7);
		
		for (int col = 0; col<8; col++) {
			chessBoard[1][col] = new Pawn(BLACK, 1,col);
		}
	
		chessBoard[7][0] = new Rook(WHITE, 7,0);
		chessBoard[7][1] = new Knight(WHITE, 7,1);
		chessBoard[7][2] = new Bishop(WHITE, 7,2);
		chessBoard[7][3] = new Queen(WHITE, 7,3);
		chessBoard[7][4] = new King(WHITE, 7,4);
		whiteKing = (King) chessBoard[7][4];
		chessBoard[7][5] = new Bishop(WHITE, 7,5);
		chessBoard[7][6] = new Knight(WHITE, 7,6);
		chessBoard[7][7] = new Rook(WHITE, 7,7);
		
		for (int col = 0; col<8; col++) {
			chessBoard[6][col] = new Pawn(WHITE, 6,col);
		}
		
		
		for (int row = 2; row < 6; row++) {
			for (int col = 0; col < 8; col++) {
				chessBoard[row][col] = new EmptySlot(row, col);
			}
		}
		selectedPiece = null;
		selectedPosition = null;
		playerTurn = WHITE;
		gameOver = false;
		if (gameType.equals(NO_TIME_LIMIT)) {
			whiteTimeRemaining = -1000;
			blackTimeRemaining = -1000;
		} else if (gameType.equals(TEN_MIN_BLITZ)) {
			whiteTimeRemaining = 600000;
			blackTimeRemaining = 600000;
		} else if (gameType.equals(THREE_MIN_BULLET)) {
			whiteTimeRemaining = 180000;
			blackTimeRemaining = 180000;
		} else if (gameType.equals(ONE_MIN_BULLET)) {
			whiteTimeRemaining = 60000;
			blackTimeRemaining = 60000;
		}
		whiteTimer.restart();
		blackTimer.restart();
		whiteTimer.start();
		blackTimer.stop();
		canvas.refresh();
	}
	
	public void runGame() {
		
		boolean validPick;
		System.out.println("runGame");
		while(true) {
			
			validPick = false;
			while (!validPick) {
				System.out.println(selectedPiece); //why tf do I need to print for the program to work?????????????????
				if(selectedPiece != null && selectedPiece.getColor().equals(playerTurn)) {
					validPick = true;
					canvas.refresh();
				}
			}
			while (!validMove()) {
			}
			
			move(selectedPiece, selectedPosition);
			lastMovedPiece = selectedPiece;
			selectedPiece = null;
			selectedPosition = null;
			canvas.refresh();
	
			
			pause(500);
			
			canvas.refresh();
			
			gameOver = gameOver();
			
			if (!gameOver) {
				if (playerTurn.equals(WHITE)) {
					playerTurn = BLACK;
					if (timedGame) {
						blackTimer.start();
					}
				} else {
					playerTurn = WHITE;
					if (timedGame) {
						whiteTimer.start();
					}
				}
				canvas.refresh();
			} else {
				canvas.refresh();
			}
		}
	}

	private void pause(int miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * checks to see if the move selected by the player is one of the possible moves of the piece selected by the player
	 * @return true if the move is legal, false if not
	 */
	private boolean validMove() {
		if (selectedPiece != null) {
			for (ChessPiece move : selectedPiece.getPossibleMoves(chessBoard)) {
				if (move.equals(selectedPosition)) {
					return true;
				}
			}
		}
		selectedPosition = null;
		return false;
	}
	
	/**
	 * Moves the chess piece to a new position
	 * @param from The chess piece that is being moved
	 * @param to The position to move to
	 */
	private void move(ChessPiece from, ChessPiece to) {
		
		animateMove(from, to);
		
		whiteTimer.stop();
		blackTimer.stop();
		if (bulletGame) {
			if (playerTurn.equals(WHITE)) {
				whiteTimeRemaining += BULLET_TIME_INCREMENT;
			} else {
				blackTimeRemaining += BULLET_TIME_INCREMENT;
			}
		}
		ChessPiece lastMovedPiecePreviousPosition = (ChessPiece) from.clone();
		ChessPiece lastTakenPiece = (ChessPiece) to.clone();
		
		pastMoves.add(new MoveCollection(lastTakenPiece, lastMovedPiecePreviousPosition, whiteTimeRemaining, blackTimeRemaining));
		turns++;
		
		int row = to.getRowIndex();
		int col = to.getColIndex();
		
		if (from instanceof King) { //castle?
			if (from.getColIndex()-to.getColIndex() > 1) { //castle left
				int rookCol = from.getColIndex()-1;
				
				chessBoard[from.getRowIndex()][from.getColIndex()-1] = chessBoard[from.getRowIndex()][from.getColIndex()-4]; //swap rook
				chessBoard[from.getRowIndex()][from.getColIndex()-4] = new EmptySlot(from.getRowIndex(), from.getColIndex()-4);
				chessBoard[from.getRowIndex()][from.getColIndex()-1].setColIndex(rookCol);

			} else if (from.getColIndex()-to.getColIndex() < -1) { //castle right
				int rookCol = from.getColIndex()+1;
				
				chessBoard[from.getRowIndex()][from.getColIndex()+1] = chessBoard[from.getRowIndex()][from.getColIndex()+3]; //swap rook
				chessBoard[from.getRowIndex()][from.getColIndex()+3] = new EmptySlot(from.getRowIndex(), from.getColIndex()+3);
				chessBoard[from.getRowIndex()][from.getColIndex()+1].setColIndex(rookCol);

			}
			((King) from).setHasMoved(true);
		} else if (from instanceof Rook) {
			((Rook) from).setHasMoved(true);
		} else if (from instanceof Pawn) { //moving twice on first move, en passant, and promotion for pawns
			((Pawn) from).setFirstMove(false);
			if (Math.abs(to.getRowIndex()-from.getRowIndex())>1) {
				((Pawn) from).setEnPassantable(true);
			}
			for (ChessPiece[] r : chessBoard) {
				for (ChessPiece c : r) {
					if (c instanceof Pawn && !c.equals(from)) {
						((Pawn) c).setEnPassantable(false);
					}
				}
			}
			if (to instanceof EmptySlot && to.getColIndex() != from.getColIndex()) {
				chessBoard[from.getRowIndex()][to.getColIndex()] = new EmptySlot(from.getRowIndex(), to.getColIndex());
			}
			if (to.getRowIndex() == 0 || to.getRowIndex() == 7) {
				((Pawn) from).promote(chessBoard);
			}
		}
		
		
		chessBoard[to.getRowIndex()][to.getColIndex()] = chessBoard[from.getRowIndex()][from.getColIndex()]; //from moved to to

		chessBoard[from.getRowIndex()][from.getColIndex()] = new EmptySlot(from.getRowIndex(), from.getColIndex());
		
		chessBoard[to.getRowIndex()][to.getColIndex()].setRowIndex(row);
		chessBoard[to.getRowIndex()][to.getColIndex()].setColIndex(col);
	}

	private void undoMove() {
		if (pastMoves.isEmpty()) {
			return;
		}
		ChessPiece lastTakenPiece = pastMoves.get(turns-1).getTakenPiece();
		ChessPiece lastMovedPiecePreviousPosition = pastMoves.get(turns-1).getMovedPiecePreviousPosition();
		whiteTimeRemaining = pastMoves.get(turns-1).getWhiteTimeRemaining();
		blackTimeRemaining = pastMoves.get(turns-1).getBlackTimeRemaining();
		pastMoves.remove(turns-1);
		chessBoard[lastTakenPiece.getRowIndex()][lastTakenPiece.getColIndex()] = lastTakenPiece;
		chessBoard[lastMovedPiecePreviousPosition.getRowIndex()][lastMovedPiecePreviousPosition.getColIndex()] = lastMovedPiecePreviousPosition;
		if (playerTurn.equals(WHITE)) {
			playerTurn = BLACK;
			blackTimer.start();
		} else {
			playerTurn = WHITE;
			whiteTimer.start();
		}
		turns--;
		selectedPiece = lastMovedPiecePreviousPosition;
		gameOver = false;
		canvas.refresh();
	}

	/**
	 * Animates the movement of a chess piece
	 * @param from The piece being moved
	 * @param to The place to move to
	 */
	public void animateMove(ChessPiece from, ChessPiece to) {
		final int PAUSE_TIME = animationSpeed;
		animatedPiece = from;
		int fromRow = from.getRowIndex();
		int fromCol = from.getColIndex();
		int toRow = to.getRowIndex();
		int toCol = to.getColIndex();
	
		final int ROW_CHANGE = 4;
		final int COL_CHANGE = 4;
		
		int rowChange = 0;
		int colChange = 0;
		if (fromRow < toRow) {
			rowChange = ROW_CHANGE;
		} else if (fromRow > toRow) {
			rowChange = -ROW_CHANGE;
		}
		if (fromCol < toCol) {
			colChange = COL_CHANGE;
		} else if (fromCol > toCol) {
			colChange = -COL_CHANGE;
		}
		if (from instanceof Knight) {
			if (Math.abs(fromRow-toRow) == 2) {
				rowChange*=2;
			}
			if (Math.abs(fromCol-toCol) == 2) {
				colChange*=2;
			}
		}
		int animatedPieceFinalCol = 0;
		int animatedPieceFinalRow = 0;
		if (playerTurn.equals(WHITE)) {
			animatedPieceFinalCol = toCol*80;
			animatedPieceFinalRow = toRow*80;
	
			animatedPieceCol = animatedPiece.getColIndex()*80;
			animatedPieceRow = animatedPiece.getRowIndex()*80;
			
						
		} else {
			animatedPieceFinalCol = (7-toCol)*80;
			animatedPieceFinalRow = (7-toRow)*80;
	
			animatedPieceCol = (7-animatedPiece.getColIndex())*80;
			animatedPieceRow = (7-animatedPiece.getRowIndex())*80;
			
			rowChange *= -1;
			colChange *= -1;
		}
		
		animating = true;
		chessBoard[fromRow][fromCol] = new EmptySlot(fromRow, fromCol);
		while (animatedPieceRow != animatedPieceFinalRow || animatedPieceCol != animatedPieceFinalCol) {
			animatedPieceRow += rowChange;
			animatedPieceCol += colChange;
			canvas.refresh();
			pause(PAUSE_TIME);
		}
		animating = false;
		chessBoard[fromRow][fromCol] = from;
	}

	/**
	 * Checks to see if the game is over
	 * @return True if the game is over, false if not
	 */
	private boolean gameOver() {
		
		if (noPossibleMoves()) {
			if (playerTurn.equals(WHITE)) {
				if (!blackKing.getUnderCheck(chessBoard)) {
					winner = "draw";
				} else {
					winner = WHITE;
				}
			} else {
				if (!whiteKing.getUnderCheck(chessBoard)) {
					winner = "draw";
				} else {
					winner = BLACK;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the player is out of time
	 * @return True if the player is out of time, false otherwise
	 */
	private boolean outOfTime() {
		if (timedGame) {
			if (whiteTimeRemaining <= 0) {
				winner = BLACK;
				whiteTimer.restart();
				whiteTimer.stop();
				canvas.refresh();
				return true;
				
			} else if (blackTimeRemaining <= 0) {
				winner = WHITE;
				blackTimer.restart();
				blackTimer.stop();
				canvas.refresh();
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Checks to see if the player has no legal moves
	 * @return True if no legal moves, false if not
	 */
	private boolean noPossibleMoves() {
		for (ChessPiece[] row : chessBoard) {
			for (ChessPiece col : row) {
				if (!(col instanceof EmptySlot) && !col.getColor().equals(playerTurn)) {
					if (!col.getPossibleMoves(chessBoard).isEmpty()) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public ChessPiece[][] getBoard() {
		return chessBoard;
	}
	

	public King getWhiteKing() {
		return whiteKing;
	}
	
	public King getBlackKing() {
		return blackKing;
	}
	
	public String getPlayerTurn() {
		return playerTurn;
	}
	
	public ChessPiece getSelectedPiece() {
		return selectedPiece;
	}
	
	public ChessPiece getLastMovedPiece() {
		return lastMovedPiece;
	}
	
	public boolean getGameOver() {
		return gameOver;
	}
	
	public String getWinner() {
		return winner;
	}

	public boolean getAnimating() {
		return animating;
	}
	
	public ChessPiece getAnimatedPiece() {
		return animatedPiece;
	}
	
	public int getAnimatedPieceCol() {
		return animatedPieceCol;
	}
	
	public int getAnimedPieceRow() {
		return animatedPieceRow;
	}
	
	public Image getTitleScreenImage() {
		return titleScreenImage;
	}
	
	public boolean getShowTitleScreen() {
		return showTitleScreen;
	}
	
	public int getWhiteTimeRemaining() {
		return whiteTimeRemaining;
	}
	
	public int getBlackTimeRemaining() {
		return blackTimeRemaining;
	}
	
	public boolean getTimedGame() {
		return timedGame;
	}
}
