package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI implements ActionListener  {
	private JFrame frame = new JFrame();
	private JPanel panelPrint = new JPanel();
	private JPanel panelButtons = new JPanel();
	
	private Word wd = new Word();
	
	private JLabel hiddenWordLabel = new JLabel("");
	private JLabel rulesLabel;
	private JLabel mistakePointLabel;
	private JButton btnRestart;
	
	
	private int mistakePoint = 8;
	private String randomWord;
	private char[] randomWordChar;
	private String hiddenWord;
	private char[] hiddenWordChar;
	
	private int lengthWord;
	
	
	public void drawGame(int mistakeCount, String hiddenWord) {
		panelPrint.removeAll();
		panelButtons.removeAll();
		
		hiddenWordLabel.setText(hiddenWord);
		mistakePointLabel = new JLabel("Осталось попыток : " + mistakeCount);
		
		panelPrint.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
		panelPrint.setBackground(new Color(188,155,155));
		panelPrint.setPreferredSize(new Dimension(450,200));
		
		btnRestart = new JButton("Новая игра");
		btnRestart.setBounds(10, 20, 80, 20);
		btnRestart.setPreferredSize(new Dimension(420, 60));
		btnRestart.addActionListener(this);
		
		hiddenWordLabel.setForeground(new Color(11,244,22));
		hiddenWordLabel.setFont(new Font("Serif", Font.BOLD, 20));
		hiddenWordLabel.setBounds(0, 20, 80, 20);
		panelPrint.add(hiddenWordLabel);
		
		rulesLabel.setForeground(new Color(244,244,244));
		rulesLabel.setFont(new Font("Serif", Font.PLAIN, 18));
		rulesLabel.setBounds(0, 20, 400, 120);
		rulesLabel.setPreferredSize(new Dimension(420, 70));
		panelPrint.add(rulesLabel);
		
		mistakePointLabel.setForeground(new Color(59,59,133));
		mistakePointLabel.setFont(new Font("Serif", Font.BOLD, 20));
		mistakePointLabel.setBounds(0, 20, 400, 120);
		mistakePointLabel.setPreferredSize(new Dimension(420, 60));
		panelPrint.add(mistakePointLabel);
		
		panelButtons.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
		panelButtons.setBackground(Color.blue);
		panelButtons.setPreferredSize(new Dimension(450,250));
		
		//filling buttons with alphabet characters
		for (int i = 0; i < wd.alphabet.length(); i++) {
			JButton btn = new JButton(String.valueOf(wd.alphabet.charAt(i)));
			btn.setBounds(10, 20, 80, 20);
			panelButtons.add(btn);
			btn.addActionListener(this);
		}
		
		
		frame.add(panelPrint, BorderLayout.NORTH);
		frame.add(panelButtons, BorderLayout.WEST);
		
		frame.setSize(450, 400);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Guess Word Budiuk");
		frame.setVisible(true);
	}
	
	public void printWhenWin() {
		rulesLabel.setText("Поздравляем, вы отгадали наше слово!!!!");
		rulesLabel.setFont(new Font("Serif", Font.BOLD, 22));
		mistakePointLabel.setText("");
		panelPrint.remove(mistakePointLabel);
		panelPrint.add(btnRestart);
	}
	
	public void printWhenLose(String hiddenWord) {
		rulesLabel.setText("<html><body>К сожалению вы не угадали. Это было слово: <i>" + hiddenWord + "</i></body></html>");
		rulesLabel.setFont(new Font("Serif", Font.BOLD, 18));
		mistakePointLabel.setText("");
		panelPrint.remove(mistakePointLabel);
		panelPrint.add(btnRestart);
	}
	
	public void setHiddenWord(String hiddenWord) {
		hiddenWordLabel.setText(hiddenWord);
	}
	
	public void setMistakePoint(int mistakeCount) {
		mistakePointLabel.setText("Осталось попыток : " + mistakeCount);
	}
	
	public void logicOfGame() {
		try {
			mistakePoint = 8;
			randomWord = wd.takeRandomWord();
			randomWordChar = randomWord.toCharArray();
			lengthWord = randomWordChar.length;
			hiddenWordChar = new char[lengthWord];
			rulesLabel = new JLabel("<html><body>Вы можете выбрать одну букву, и если эта буква будет в нашем слове, то мы откроем ее. Если у вас будет 8 штрафов - вы проиграли. Удачи!!</body></html>");
			
			for (int i = 0; i < lengthWord; i++) {
				hiddenWordChar[i] = '_';              
	        }
			hiddenWordChar[0] = (char)randomWordChar[0];
			hiddenWordChar[lengthWord - 1] = (char)randomWordChar[lengthWord - 1];
			
			hiddenWord = new String(hiddenWordChar);
			
			drawGame(mistakePoint, hiddenWord);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void checkLetter (char letter) {
		int countBefore = 0, countAfter = 0;

        for (int i = 0; i < lengthWord; i++) {
            if (hiddenWordChar[i] != '_')
                countBefore++;
            
            if (randomWordChar[i] == letter) {
            	hiddenWordChar[i] = letter;
            	hiddenWord = new String(hiddenWordChar);
            }

            if (hiddenWordChar[i] != '_')
                countAfter++;
        }
        
        setHiddenWord(hiddenWord);
        
        if (countBefore != countAfter) {
        	setMistakePoint(mistakePoint);
        } else {
        	mistakePoint--;
        	setMistakePoint(mistakePoint);
        }
        
        if (mistakePoint == 0) {
			printWhenLose(randomWord);
		} else {
			if (hiddenWord.equals(randomWord)) {
				printWhenWin();                      
            }
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e)  {		
		((JButton) e.getSource()).setBackground(new Color(100,22,130));
		((JButton) e.getSource()).setEnabled(false);
		checkLetter(((JButton) e.getSource()).getText().charAt(0));

		if(e.getSource() == btnRestart) {
			logicOfGame();
		}
		
	}
}
