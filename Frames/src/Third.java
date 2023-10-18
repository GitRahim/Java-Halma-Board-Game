import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class Third extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	//b: board's length. r: rows of pieces.
	int b, r;
	//p1: name of player 1. p2: name of player 2
	String p1, p2;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Third frame = new Third();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public Third() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 100, 464, 403);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JButton btnReplay = new JButton("Replay");
		btnReplay.setFont(new Font("Showcard Gothic", Font.PLAIN, 11));
		btnReplay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSound();
				replay();
			}
		});
		contentPane.add(btnReplay, BorderLayout.NORTH);
		
		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Showcard Gothic", Font.PLAIN, 11));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSound();
				contentPane.setVisible(false);
				dispose();
				Second panel2 = new Second();
				panel2.setVisible(true);
			}
		});
		contentPane.add(btnBack, BorderLayout.SOUTH);	
	}
	//image blue for Player1 and red for player 2
	Image red = new ImageIcon(this.getClass().getResource("/image/red.jpg")).getImage();
	Image blue = new ImageIcon(this.getClass().getResource("/image/blue.jpg")).getImage();
	//for switching between players. false: player1 and true: player2 .
	boolean turn = false;
	//counters for counting moves of each player. ct1 is for player1 .
	int ct1=0, ct2=0;
	//these variables will keep x and y of previous clicked button.
	int bx=0, by=0;
	
	//b is board length, r is rows of pieces and p1 is player1's name and p2 is player2's name.
	void Board(int b, int r, String p1, String p2) {
		//ct1 keeps the number of moves of player1 in itself.
		ct1=0;
		//ct2 keeps the number of moves of player2 in itself.
		ct2=0;
		JPanel GamePanel = new JPanel();
		contentPane.add(GamePanel, BorderLayout.CENTER);
		GamePanel.setLayout(new GridLayout(b, b, 0, 0));		
		//we add two more columns and rows in order to not see Error arrayindexoutofbounds while checking the cells of Game Board. 
		JButton[][] bt = new JButton[b+2][b+2];
		Cell[][] cl = new Cell[b+2][b+2];
		//h1 is the place of pieces of player1 at first. h2 is for player2. these arrays will be used to check who wins. 
		ArrayList<Cell> h1 = new ArrayList<Cell>();
		ArrayList<Cell> h2 = new ArrayList<Cell>();
		
		for(int i=0; i<b+2; i++) {
			for(int j=0; j<b+2; j++) {
				bt[i][j] = new JButton();
				bt[i][j].setBackground(Color.white);
				cl[i][j] = new Cell();
				//if cells are in border rows and columns, they're counted as border cells and are not in game board which will be shown.
				if(i==0 || j==0 || i==b+1 || j==b+1) {
					cl[i][j].Btxt= -1;
				}
				else {
					//here we define the places of player1 pieces.
					if(1<i+j && i+j<r+2) {
						cl[i][j].Btxt = 1;
						h1.add(cl[i][j]);
						bt[i][j].setIcon(new ImageIcon(blue.getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
						bt[i][j].setText("*");
						bt[i][j].setHorizontalTextPosition(JButton.CENTER);
						bt[i][j].setVerticalTextPosition(JButton.CENTER);
					}
					//here we define the places of player2 pieces.
					if(i+j>2*(b)-r && i+j<2*(b+1)) {
						cl[i][j].Btxt = 2;
						h2.add(cl[i][j]);
						bt[i][j].setIcon(new ImageIcon(red.getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
						bt[i][j].setText("*");
						bt[i][j].setHorizontalTextPosition(JButton.CENTER);
						bt[i][j].setVerticalTextPosition(JButton.CENTER);
					}
					bt[i][j].addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							int x=0, y=0;
lb:							for(int m=0; m<bt.length; m++) {
								for(int n=0; n<bt.length; n++) {
									if(e.getSource().equals(bt[m][n])) {
										x = m;
										y = n;
										break lb;
									}
								}
							}
							//first it's player1's turn, cause the default value of turn is false.
							if(turn == false) {
								//if player1 click on a button with number 1 on it, x and y of it will be saved and
								//valid buttons for moving will be green by method move.
								if(cl[x][y].Btxt == 1) {
									//if player1 clicks on another button, all previous green and blue buttons should be null and new
									//buttons should be green and blue.
									clearButton(bt);
									bx = x;
									by = y;
									move(cl, bt, x, y);
								}
								//if player1 clicks on green or blue button, the previous button should move to this green or blue button.
								if(bt[x][y].getBackground().equals(Color.green) || bt[x][y].getBackground().equals(Color.blue)) {
									bt[bx][by].setIcon(null);
									cl[bx][by].Btxt = 0;
									bt[x][y].setIcon(new ImageIcon(blue.getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
									cl[x][y].Btxt = 1;
									playSound();
									//after moving, green and blue buttons should be null again.
									clearButton(bt);
									//with every move, moves should be plus1.
									ct1++;
									//with Win method we check if player1 won.
									if(win(h2, 1)) {
										//if player won, then a message announce it and score will be written to file and game restarts.
										JOptionPane.showMessageDialog(null, p1+" won!");
										writeFile(ct1, p1, b);
										replay();
									}
									//after player1 moved, it's player 2 turn. so turn should be true.
									turn = true;
								}
							}
							if(turn == true) {
								if(cl[x][y].Btxt == 2) {
									clearButton(bt);
									bx = x;
									by = y;
									move(cl, bt, x, y);
								}
								if(bt[x][y].getBackground().equals(Color.green) || bt[x][y].getBackground().equals(Color.blue)) {
									bt[bx][by].setIcon(null);
									cl[bx][by].Btxt = 0;
									bt[x][y].setIcon(new ImageIcon(red.getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
									cl[x][y].Btxt = 2;
									playSound();
									clearButton(bt);
									ct2++;
									if(win(h1, 2)) {
										JOptionPane.showMessageDialog(null, p2+" won!");
										writeFile(ct2, p2, b);
										replay();
									}
									turn = false;
								}
							}

						}
					});
					GamePanel.add(bt[i][j]);
					bt[i][j].setBackground(Color.white);
				}
			}
		}	
	}
	void move(Cell[][] cl, JButton[][] bt, int x, int y) {
		//with these If s we make the background of valid buttons around this button green in order to be recognized.
		//if the button which is on right of clicked button is empty, make it green.
		if(cl[x][y+1].Btxt==0) {
			bt[x][y+1].setBackground(Color.green);
		}
		if(cl[x+1][y+1].Btxt==0) {
			bt[x+1][y+1].setBackground(Color.green);
		}
		if(cl[x-1][y+1].Btxt==0) {
			bt[x-1][y+1].setBackground(Color.green);
		}
		if(cl[x+1][y].Btxt==0) {
			bt[x+1][y].setBackground(Color.green);
		}
		if(cl[x-1][y].Btxt==0) {
			bt[x-1][y].setBackground(Color.green);
		}
		if(cl[x-1][y-1].Btxt==0) {
			bt[x-1][y-1].setBackground(Color.green);
		}
		if(cl[x][y-1].Btxt==0) {
			bt[x][y-1].setBackground(Color.green);
		}
		if(cl[x+1][y-1].Btxt==0) {
			bt[x+1][y-1].setBackground(Color.green);
		}
		//with these if s and the method after these if s, we make the background of other valid buttons blue.
		//if button on right of clicked button isn't empty, but the button after it is empty, make it blue.
		if(cl[x][y+1].Btxt>0) {
			if(cl[x][y+2].Btxt==0) {
				bt[x][y+2].setBackground(Color.blue);
			}
		}	
		if(cl[x+1][y+1].Btxt>0) {
			if(cl[x+2][y+2].Btxt==0) {
				bt[x+2][y+2].setBackground(Color.blue);
			}
		}	
		if(cl[x-1][y+1].Btxt>0) {
			if(cl[x-2][y+2].Btxt==0) {
				bt[x-2][y+2].setBackground(Color.blue);
			}
		}		
		if(cl[x+1][y].Btxt>0) {
			if(cl[x+2][y].Btxt==0) {
				bt[x+2][y].setBackground(Color.blue);
			}
		}	
		if(cl[x-1][y].Btxt>0) {
			if(cl[x-2][y].Btxt==0) {
				bt[x-2][y].setBackground(Color.blue);
			}
		}	
		if(cl[x-1][y-1].Btxt>0) {
			if(cl[x-2][y-2].Btxt==0) {
				bt[x-2][y-2].setBackground(Color.blue);
			}
		}	
		if(cl[x][y-1].Btxt>0) {
			if(cl[x][y-2].Btxt==0) {
				bt[x][y-2].setBackground(Color.blue);
			}
		}	
		if(cl[x+1][y-1].Btxt>0) {
			if(cl[x+2][y-2].Btxt==0) {
				bt[x+2][y-2].setBackground(Color.blue);
			}
		}
		//this method will do the above job for new blue buttons.
		move2(cl, bt);
		move2(cl, bt);
	}
	//As said before, this method makes the background of other valid buttons blue.
	void move2(Cell[][] cl, JButton[][] bt) {
		//first searches for blue buttons and then does the same for as above for them.
		//once from up to down
		for(int i=1; i<bt.length-1; i++) {
			for(int j=1; j<bt.length-1; j++) {
				subMove2(bt, cl, i, j);
			}
		}
		//once from down to up
		for(int i=bt.length-2; i>0; i--) {
			for(int j=bt.length-2; j>0; j--) {
				subMove2(bt, cl, i, j);
			}
		}
	}
	//method used in move2, to prevent copying. makes valid buttons around blue buttons blue.
	void subMove2(JButton[][] bt, Cell[][] cl, int i, int j) {
		if(bt[i][j].getBackground().equals(Color.blue)) {
			if(cl[i][j+1].Btxt>0) {
				if(cl[i][j+2].Btxt==0) {
					bt[i][j+2].setBackground(Color.blue);
				}
			}	
			if(cl[i+1][j+1].Btxt>0) {
				if(cl[i+2][j+2].Btxt==0) {
					bt[i+2][j+2].setBackground(Color.blue);
				}
			}	
			if(cl[i-1][j+1].Btxt>0) {
				if(cl[i-2][j+2].Btxt==0) {
					bt[i-2][j+2].setBackground(Color.blue);
				}
			}		
			if(cl[i+1][j].Btxt>0) {
				if(cl[i+2][j].Btxt==0) {
					bt[i+2][j].setBackground(Color.blue);
				}
			}	
			if(cl[i-1][j].Btxt>0) {
				if(cl[i-2][j].Btxt==0) {
					bt[i-2][j].setBackground(Color.blue);
				}
			}	
			if(cl[i-1][j-1].Btxt>0) {
				if(cl[i-2][j-2].Btxt==0) {
					bt[i-2][j-2].setBackground(Color.blue);
				}
			}	
			if(cl[i][j-1].Btxt>0) {
				if(cl[i][j-2].Btxt==0) {
					bt[i][j-2].setBackground(Color.blue);
				}
			}	
			if(cl[i+1][j-1].Btxt>0) {
				if(cl[i+2][j-2].Btxt==0) {
					bt[i+2][j-2].setBackground(Color.blue);
				}
			}
		}
	}
	//Clear green and blue buttons after moving
	void clearButton(JButton[][] bt) {
		for(int m=0; m<bt.length; m++) {
			for(int n=0; n<bt.length; n++) {
				bt[m][n].setBackground(Color.white);
			}
		}
	}
	//this method is used to check if anyone won. e.g. if house of pieces of player1 is filled with pieces of player2, player2 wins.
	boolean win(ArrayList<Cell> h, int n) {
		boolean result=false;
		int w=0;
		for(int i=0; i<h.size(); i++) {
			if(h.get(i).Btxt == n) {
				w++;
			}
		}
		if(w == h.size()) {
			result = true;
		}
		return result;
	}
	//with this method we write the score and name of player to a file.
	void writeFile(int m, String name, int b) {
		String moves = Integer.toString(m);
		String board = Integer.toString(b);
		try {
			File file = new File("Score.txt");
			if(!file.exists()) {
				file.createNewFile();
			}
			BufferedWriter buf = new BufferedWriter(new FileWriter(file, true));
			buf.write(" \n"+name+"   Moves: "+moves+"   Board: "+board+"*"+board+"   Rows of pieces: "+r);
			buf.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	//does the replay function.
	void replay() {
		contentPane.setVisible(false);
		dispose();
		Third newPanel3 = new Third();
		newPanel3.setVisible(true);
		newPanel3.b = b;
		newPanel3.r = r;
		newPanel3.p1 = p1;
		newPanel3.p2 = p2;
		newPanel3.Board(b, r, p1, p2);
	}
	//sound of dropping piece.
	void playSound() {
		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(this.getClass().getResource("/image/Piecedrop.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(audio);
			clip.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
