import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JLabel;

public class First {

	public JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					First window = new First();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public First() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(300, 100, 464, 403);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Play");
		btnNewButton.setFont(new Font("Showcard Gothic", Font.PLAIN, 11));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSound();
				frame.dispose();
				Second frame2 = new Second();
				frame2.setVisible(true);
			}
		});
		btnNewButton.setBounds(180, 145, 89, 23);
		frame.getContentPane().add(btnNewButton);
		//When you click button "Score", it'll read the text in file "Score.txt", and shows it as a message.
		JButton btnNewButton_1 = new JButton("Score");
		btnNewButton_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 11));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSound();
				try {
					File file = new File("Score.txt");
					if(file.exists()) {
						BufferedReader buf = new BufferedReader(new FileReader(file));
						String l;
						String sc= " ";
						while((l = buf.readLine()) != null) {
							sc+=l+"\n";
						}
						JTextArea txtArea = new JTextArea(sc);
						JScrollPane scrlPane = new JScrollPane(txtArea);
						scrlPane.setPreferredSize(new Dimension(320, 80));
						JOptionPane.showMessageDialog(null, scrlPane, "Score", 1);
						buf.close();
					}
				} catch (Exception e2) {
					 JOptionPane.showMessageDialog(null, e2.getMessage());
				} 
			}
		});
		btnNewButton_1.setBounds(180, 179, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
		/*When you click button "Exit", this frame will be closed. since all other frames are already closed, with closing
		 * this frame, program running will be finished. 
		 */
		JButton btnNewButton_2 = new JButton("Exit");
		btnNewButton_2.setFont(new Font("Showcard Gothic", Font.PLAIN, 11));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playSound();
				frame.dispose();
			}
		});
		btnNewButton_2.setBounds(180, 213, 89, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		//with code below, i added an image as background to my frame.
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, 0, 448, 364);
		Image im = new ImageIcon(this.getClass().getResource("/image/Halma.gif")).getImage();
		lblNewLabel.setIcon(new ImageIcon(im.getScaledInstance(448, 364, Image.SCALE_SMOOTH)));
		frame.getContentPane().add(lblNewLabel);
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
