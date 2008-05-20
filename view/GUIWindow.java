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
import javax.swing.JTable;

import model.HiScoreRowModel;
import controller.FieldController;
import controller.FieldIs;

/**
 * Klasa jest Swingowym/okienowym interfejsem do gry. 
 * @author Marcin Mincer
 * @since RC1
 *
 */
public class GUIWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private static FieldController game = new FieldController(); // domysla gra na pierwsze uruchomienie
	private FieldButton[][] grid; // sitaka przyciskow

	private JPanel jContentPane = null;
	private ImageIcon bombIcon = new ImageIcon("view/bomb.png"); // ikonka bomby
	private ImageIcon flagIcon = new ImageIcon("view/flag.png"); // ikonka flagi

	/**
	 * Konstruktor domyslny okienka
	 */
	public GUIWindow() {
		super();
		//this.setSize(800, 600);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		initialize();
		
		// ustawiamy menuski
		JMenuBar menuBar = new JMenuBar();
		JMenu saper = new JMenu("Saper");
		
		JMenuItem zakoncz = new JMenuItem("Skończone"); // zakoczone gre, czas
														// dopisac do hiscore
		zakoncz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!game.isValid()) {
					JOptionPane.showMessageDialog(null,
							"Nie można kończyć po śmierci :P", "Błąd!",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				int score = game.computeScore(); // obliczamy wynik
				game.setValid(false); // koniec gry
				game.showAll(); // odslon plansze
				update();

				String name = (String) JOptionPane.showInputDialog(
						// jak masz na imie?
						"Zdobyto " + score + " punktów!\n Jak masz na imię?",
						"Ziemowit");

				if (name == null) // jezeli zawodnik nie podal imienia
					name = "Gracz";
				Console.hiScore.insertScore(score, name); // dopisujemy go do
															// hajskora
			}
		});
		
		JMenu help = new JMenu("Pomoc");
		JMenuItem helpItem = new JMenuItem("Instrukcje");
		helpItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane
						.showMessageDialog(
								null,
								"Celem gry jest zdobycie największej ilości punktów stawiając flagi (PPM) tam, gdzie są miny. \n"
										+ "Za każdą dobrze postawioną flagę (po skończeniu gry podświetloną na zielono) \n"
										+ "dostaniejsz 1 pkt. Za każdą źle postawioną (po skończeniu gry podświetloną na czerwono) \n"
										+ "-2 pkt. Kliknięcie LPM dotyka pola. Dotknięcie miny kończy grę, nie dostajesz punktów. \n"
										+ "Dotknięcie pola bez miny odkrywa liczby mówiące o ilości min sąsiadujących z danym polem \n"
										+ "(również po przekątnej). Kliknij \"zakończ\", gdy uważasz, że dobrze rozstawiłeś flagi.",
								"Pomoc", JOptionPane.INFORMATION_MESSAGE);

			}
		});

		JMenu newgame = new JMenu("Nowa gra");

		JMenuItem easy = new JMenuItem("Mało bomb");
		easy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game = new FieldController(model.Level.EASY);
				initialize(); // reinitialzuj gre
			}
		});
		JMenuItem medium = new JMenuItem("Średnia ilość bomb");
		medium.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game = new FieldController(model.Level.NORMAL);
				initialize(); // reinitialzuj gre
			}
		});
		JMenuItem hard = new JMenuItem("Dużo bomb");
		hard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game = new FieldController(model.Level.HARD);
				initialize(); // reinitialzuj gre
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
				
				// narzut wykonania na tyle maly, ze mozna za kadzym razem otwrcia okienka to robic
				String[] columnNames = { "Lp.", "Imię", "Punkty" };
				String[][] data = new String[10][3];
				List<HiScoreRowModel> rows = Console.hiScore.getHiScore();

				for (int i = 0; i < 10; i++) { // towrzymy tabele wynikow
					data[i][0] = ((Integer) rows.get(i).getPosition())
							.toString();
					data[i][1] = rows.get(i).getName();
					data[i][2] = ((Integer) rows.get(i).getScore()).toString();
				}
				JTable table = new JTable(data, columnNames);
				JOptionPane.showMessageDialog(null, table, "HiScore", // wyswietlamy ja
						JOptionPane.CLOSED_OPTION);
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
		help.add(helpItem);
		menuBar.add(help);
		setJMenuBar(menuBar);

	}

	/**
	 * Metoda inicjujaca realizacje gry
	 */
	private void initialize() {
		
		this.setContentPane(getJContentPane(game.getSize()));
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
	 * Tworzy panel z grą o odpowiedniej wielkosci
	 * 
	 * @param x int rozmiar planszy
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane(int x) {
		jContentPane = new JPanel();
		jContentPane.setLayout(new GridLayout(x, x));
		return jContentPane;
	}
	
	/**
	 * Ustawia elemetny siatki przyciskow zgodnie ze stanem gry
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
				if (board[x][y] != FieldIs.HIDDEN
						&& board[x][y] != FieldIs.FLAG
						&& board[x][y] != FieldIs.BOMB_FLAG) {
					grid[x][y].setBackground(Color.white);
					grid[x][y].setIcon(null);
				}
			}
		}
		jContentPane.repaint(); //przerysuj gre
		 System.out.println("update!" + "game is "+ game.isValid()); //debug only
	}

}
