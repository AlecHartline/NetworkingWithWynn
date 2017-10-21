import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Window extends JFrame{
	JTextArea chatText = new JTextArea("");
	JScrollPane scrollBox = new JScrollPane(chatText);
	JTextField inputField = new JTextField();
	public Window() {
		super("Pee-paw turk turk chat");
		
		setMinimumSize(new Dimension(600, 600));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		chatText.setEditable(false);
		chatText.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		inputField.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		
		inputField.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar() == '\n') {
					if(inputField.getText().length() != 0){
						ClientMain.serverOut.println(inputField.getText());
						ClientMain.appendTextOfScreen(ClientMain.me, inputField.getText());
						ClientMain.serverOut.flush();
						inputField.setText("");
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyTyped(KeyEvent e) {}
			
		});
		
		setLayout(new BorderLayout());
		
		add(scrollBox, BorderLayout.CENTER);
		add(inputField, BorderLayout.SOUTH);
		
		pack();
	}
	
}
