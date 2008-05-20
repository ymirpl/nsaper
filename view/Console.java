package view;

import model.HiScoreModel;
import controller.FieldIs;

/**
 * Klasa z metodą main. Podstawowy typ widoku (konsola). 
 * @author Marcin Mincer
 * @since RC1
 *
 */
public class Console {


	public static HiScoreModel hiScore = new HiScoreModel(); 
	
	@Deprecated
	/**
	 * Drukuje planszę do gry w konsoli. Używana tylko jako debug. 
	 * @param board {@link FieldIs}[][] plansza
	 */
	static void print(FieldIs board[][]) {
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[0].length; x++) {
				if (board[x][y] == controller.FieldIs.HIDDEN)
					System.out.print("=");
				else if (board[x][y] == FieldIs.BOMB)
					System.out.print("B");
				else if (board[x][y] == FieldIs.FLAG)
					System.out.print("F");
				else if (board[x][y] == FieldIs.BOMB_FLAG)
					System.out.print("E");
				else if (board[x][y] == FieldIs.ONE)
					System.out.print("1");
				else if (board[x][y] == FieldIs.TWO)
					System.out.print("2");
				else if (board[x][y] == FieldIs.THREE)
					System.out.print("3");
				else if (board[x][y] == FieldIs.FOUR)
					System.out.print("4");
				else if (board[x][y] == FieldIs.FIVE)
					System.out.print("5");
				else if (board[x][y] == FieldIs.SIX)
					System.out.print("6");
				else if (board[x][y] == FieldIs.SEVEN)
					System.out.print("7");
				else if (board[x][y] == FieldIs.EIGHT)
					System.out.print("8");
				else
					System.out.print(" ");

			}
			System.out.println(""); 
		}
	}
	/**
	 * Metoda main, wywołuje okno Swing
	 * @param args
	 */
	public static void main(String[] args) {

		// okno swing uruchamiane w nowym wątku
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUIWindow w = new GUIWindow();

				w.pack();
				w.setVisible(true);
			}
		});

		
	}

}
