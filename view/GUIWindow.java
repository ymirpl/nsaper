package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.HiScoreRowModel;
import controller.FieldController;
import controller.FieldIs;

public class GUIWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private static FieldController game = new FieldController(); // domysla gra na pierwsze uruchomienie
	private FieldButton[][] grid;

	private JPanel jContentPane = null;
	private ImageIcon bombIcon = new ImageIcon("view/bomb.png");
	private ImageIcon flagIcon = new ImageIcon("view/flag.png");

	/**
	 * Konstruktor domyslny okienka
	 */
	public GUIWindow() {
		super();
		this.setSize(800, 600);
		initialize();
		
		// ustawiamy menuski
		JMenuBar menuBar = new JMenuBar();
		JMenu saper = new JMenu("Saper");
		
		JMenuItem zakoncz = new JMenuItem("Skończone"); // zakoczone gre, czas dopisac do hiscore
		zakoncz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(!game.isValid()) {
					JOptionPane.showMessageDialog(null, "Nie można kończyć po śmierci :P", "Błąd!", JOptionPane.ERROR_MESSAGE); 
				return; 
				}
				int score = game.computeScore(); // obliczamy wynik
				game.setValid(false); // koniec gry
				game.showAll();
				update();

				String name = (String)JOptionPane.showInputDialog( // jak masz na imie?
	                    "Zdobyto " + score + " punktów!\n Jak masz na imię?", 
	                    "Ziemowit"
	            );
				
				if(name == null) // jezeli zawodnik nie podal imienia
					name = "Gracz";
				Console.hiScore.insertScore(score, name); // dopisujemy go do hajskora
			}
		});
		
		JMenu help = new JMenu("Pomoc"); //@TODO trza by dopisac panelik
		JMenu newgame = new JMenu("Nowa gra");
		
		JMenuItem  easy = new JMenuItem("Łatwa");
		easy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game = new FieldController(model.Level.EASY);
				initialize(); //reinitialzuj gre
			}
		});
		JMenuItem  medium = new JMenuItem("Średnia");
		medium.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game = new FieldController(model.Level.NORMAL);
				initialize(); //reinitialzuj gre
			}
		});
		JMenuItem  hard = new JMenuItem("Trudna");
		hard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game = new FieldController(model.Level.HARD);
				initialize(); //reinitialzuj gre
			}
		});
		JMenuItem exit = new JMenuItem("Zakończ	");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		JMenuItem hiscore = new JMenuItem("HiScore");
		hiscore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = "";
				List<HiScoreRowModel> rows = Console.hiScore.getHiScore();
				
				for(int i=0; i < 10 ;i++) {
					s += rows.get(i).getPosition() + ". ";
					s += rows.get(i).getName();
					s += "    ";
					s += rows.get(i).getScore() + "\n";
				}
				JOptionPane.showMessageDialog(null, s, "HiScore", JOptionPane.CLOSED_OPTION);
			}
		});
		newgame.add(easy);
		newgame.add(medium);
		newgame.add(hard);
		saper.add(newgame);
		saper.add(hiscore);
		saper.add(exit);
		menuBar.add(saper);
		menuBar.add(zakoncz);
		menuBar.add(help);
		setJMenuBar(menuBar);
		
		
		
		
	}

	/**
	 * Metoda inicjujaca realizacje gry
	 */
	private void initialize() {
		
		this.setContentPane(getJContentPane(game.getSize(), game.getSize()));
		this.setTitle("Saper");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		grid = new FieldButton[game.getSize()][game.getSize()]; // sitka przyciskow
		
		for (int y = 0; y < game.getSize(); y++)
			for (int x = 0; x < game.getSize(); x++) {
				grid[y][x] = new FieldButton(x, y);
				grid[y][x].addMouseListener(new MouseAdapter() { // klikniecie w pole gry
					public void mouseClicked(MouseEvent e) {
						FieldButton b = (FieldButton)e.getSource();
						if(!game.isValid()) // jezeli gra sie skonczyla klikani nic nie daje
							return; 
						if (e.getModifiers() == InputEvent.BUTTON1_MASK) { // lewym przyciskiem
							game.touch(b.getCol(), b.getRow()); 
							update();
						} else { // prawym przyciskiem
							game.flag(b.getCol(), b.getRow());
							update();
						}
					}
				}); // koniec obslugi klikniecia
				jContentPane.add(grid[y][x]);
			}
		
		update();
	}

	/**
	 * inicjalizuje panel z gra o odpowiedniej wielkosci
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane(int x, int y) {
		jContentPane = new JPanel();
		jContentPane.setLayout(new GridLayout(x, y));
		return jContentPane;
	}
	
	/**
	 * Ustawia elemetny siatki przycikow zgodnie ze stanem gry
	 */
	private void update() {
		FieldIs[][] board = game.makeArray();
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[0].length; x++) {
				if (board[x][y] == FieldIs.FLAG && game.isValid()) {
					grid[x][y].setText("");
					grid[x][y].setIcon(flagIcon);
				}
				else if (board[x][y] == FieldIs.FLAG && !game.isValid())
					grid[x][y].setBackground(Color.green);
				else if (board[x][y] == FieldIs.BOMB_FLAG && game.isValid()) { 
					grid[x][y].setText("");
					grid[x][y].setIcon(flagIcon);
				}
					else if (board[x][y] == FieldIs.BOMB_FLAG && !game.isValid())
					grid[x][y].setBackground(Color.red);
				else if (board[x][y] == FieldIs.HIDDEN) {
					grid[x][y].setText(" ");
					grid[x][y].setIcon(null);
				}
				else if (board[x][y] == FieldIs.BOMB) {
					grid[x][y].setText("");
					grid[x][y].setIcon(bombIcon);
				}
				else if (board[x][y] == FieldIs.ONE)
					grid[x][y].setText("1");
				else if (board[x][y] == FieldIs.TWO)
					grid[x][y].setText("2");
				else if (board[x][y] == FieldIs.THREE)
					grid[x][y].setText("3");
				else if (board[x][y] == FieldIs.FOUR)
					grid[x][y].setText("4");
				else if (board[x][y] == FieldIs.FIVE)
					grid[x][y].setText("5");
				else if (board[x][y] == FieldIs.SIX)
					grid[x][y].setText("6");
				else if (board[x][y] == FieldIs.SEVEN)
					grid[x][y].setText("7");
				else if (board[x][y] == FieldIs.EIGHT)
					grid[x][y].setText("8");
				else
					grid[x][y].setText(" ");
				if (board[x][y] != FieldIs.HIDDEN && board[x][y] != FieldIs.FLAG  && board[x][y] != FieldIs.BOMB_FLAG) 
					grid[x][y].setBackground(Color.white);
			}
		}
		jContentPane.repaint(); //przerysuj gre
		System.out.println("update!" + "game is "+ game.isValid());
	}

}
