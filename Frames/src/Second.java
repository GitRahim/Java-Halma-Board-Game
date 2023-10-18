import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Second extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtPlayer1;
	private JTextField txtPlayer2;
	private JTextField txtBoard;
	private JTextField txtRow;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Second frame = new Second();
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
	public Second() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 100, 464, 403);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPlayer1 = new JLabel("Player1:");
		lblPlayer1.setForeground(Color.black);
		lblPlayer1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPlayer1.setBounds(90, 73, 62, 14);
		contentPane.add(lblPlayer1);
		
		JLabel lblPlayer2 = new JLabel("Player2:");
		lblPlayer2.setForeground(Color.black);
		lblPlayer2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPlayer2.setBounds(90, 98, 62, 14);
		contentPane.add(lblPlayer2);
		
		JLabel lblBoardLength = new JLabel("Board length:");
		lblBoardLength.setForeground(Color.black);
		lblBoardLength.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBoardLength.setBounds(54, 123, 98, 14);
		contentPane.add(lblBoardLength);
		
		JLabel lblRowsOfPieces = new JLabel("Rows of pieces:");
		lblRowsOfPieces.setForeground(Color.black);
		lblRowsOfPieces.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRowsOfPieces.setBounds(54, 148, 98, 14);
		contentPane.add(lblRowsOfPieces);
		
		txtPlayer1 = new JTextField();
		txtPlayer1.setText("Player1");
		txtPlayer1.setBounds(259, 70, 86, 20);
		contentPane.add(txtPlayer1);
		txtPlayer1.setColumns(10);
		
		txtPlayer2 = new JTextField();
		txtPlayer2.setText("Player2");
		txtPlayer2.setColumns(10);
		txtPlayer2.setBounds(259, 95, 86, 20);
		contentPane.add(txtPlayer2);
		
		txtBoard = new JTextField();
		txtBoard.setText("8");
		txtBoard.setColumns(10);
		txtBoard.setBounds(259, 120, 86, 20);
		contentPane.add(txtBoard);
		
		txtRow = new JTextField();
		txtRow.setText("4");
		txtRow.setColumns(10);
		txtRow.setBounds(259, 145, 86, 20);
		contentPane.add(txtRow);
		/*with try catch function it'll be checked whether field are filed correctly or not.
		 * if correctly, then Play Board should be visible.
		 */
		JButton btnStart = new JButton("Start");
		btnStart.setFont(new Font("Showcard Gothic", Font.PLAIN, 11));
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSound();
				String BL = txtBoard.getText();
				String RP = txtRow.getText();
				try {
					int b = Integer.parseInt(BL);
					int r = Integer.parseInt(RP);
					if(b%2==0 & b>1 & r>0 & r<b ) {
						contentPane.setVisible(false);
						dispose();
						Third panel3 = new Third();
						panel3.setVisible(true);
						panel3.b = b;
						panel3.r = r;
						panel3.p1 = txtPlayer1.getText();
						panel3.p2 = txtPlayer2.getText();
						panel3.Board(b, r, txtPlayer1.getText(), txtPlayer2.getText());
					}
					else
						JOptionPane.showMessageDialog(null, "Wrong input!");
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Wrong input!");
				}
			}
		});
		btnStart.setBounds(173, 228, 89, 23);
		contentPane.add(btnStart);
		
		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Showcard Gothic", Font.PLAIN, 11));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSound();
				contentPane.setVisible(false);
				dispose();
				First panel1 = new First();
				panel1.frame.setVisible(true);
			}
		});
		btnBack.setBounds(173, 267, 89, 23);
		contentPane.add(btnBack);
		
		//like previous frame, i added the same image.
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, 0, 448, 364);
		Image im = new ImageIcon(this.getClass().getResource("/image/Halma.gif")).getImage();
		lblNewLabel.setIcon(new ImageIcon(im.getScaledInstance(448, 364, Image.SCALE_SMOOTH)));
		contentPane.add(lblNewLabel);
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
