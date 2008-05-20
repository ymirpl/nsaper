package model;

import java.util.Random;
/**
 * Klasa reprezentująca model planszy
 * 
 * Plansza jest tablica dwuwymiarową pól typu FieldModel. 
 * Klasa zawiera metodę generujacą planszę z bombami i cyframi.
 * @author Marcin Mincer
 * @since RC1
 * @see FieldModel
 */
public class BoardModel {

	private FieldModel[][] board;
	
	private int size;
	
	
	/**
	 * poziom trudnosci
	 */
	private Level level;
	

	private static Random random = new Random();

	
	/**
	 * Konstruktor. Losuje zawartość planszy zgodnie z podanym rozmiarem.
	 * Poziom trudonśsci określa ilość bomb.
	 * @param a  int wymiar planszy
	 * @param l  Level poziom trudności
	 */
	public BoardModel(int a, Level l) {
		this.size = a;
		this.level = l;
		board  = new FieldModel[size][size];
		generateBoard();
	}
	
	public BoardModel() {
		this(25, Level.NORMAL);
	}
	
	/**
	 * Generuje planszę z bombami i polami z odpowiednimi cyframi.
	 * W zależnosci od poziomu trudnosci wcześniej ustawionego
	 * zmienia sie ilość bomb na planszy. 
	 */
	private void generateBoard() {
		
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				board[i][j] = new FieldModel();
		
		// pierwsze przejscie generuje bomby (ilosc zalezy od poziomu)
		int bombs = (int) ((Math.abs(random.nextGaussian()) + 1) * Math.ceil(0.05 * size * size));// ile bomb
		
		//System.out.println(bombs); //betatest only
		
		// Jezeli level == easy nie dodajemy wiecej bomb
		if(level == Level.NORMAL)
			bombs += ((int)Math.ceil((0.05 * size * size)));
		if(level == Level.HARD)
			bombs += ((int)Math.ceil((0.15 * size * size)));
		
		//System.out.println(bombs); //betatest only
		
		for(int i = 0; i <= bombs; i++) {
			int x = Math.abs(random.nextInt() % size);
			int y = Math.abs(random.nextInt() % size);
			
			// mozliwe jest staniecie na polu z boba, co spowoduje spadek liczby
			// bomb. To wprowadzao dodatkowy czynnik losowy liczby bomb.
			board[y][x].setBomb(true);
/*
			if (board[y][x].isBomb() == false)
				board[y][x].setBomb(true);
			else
				// jezeli tam juz byla bomba nie liczymy tego przejscia
				--i;
*/
		}
		
		// obliczanie cyferek
		
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) {
				byte counter = 0; // zliczanie przyleglych bomb

				if (!board[i][j].isBomb()) {
					if ((i - 1) >= 0 && (j - 1) >= 0) {
						if (board[i - 1][j - 1].isBomb() == true)
							counter++;
					}
					if ((i - 1) >= 0) {
						if (board[i - 1][j].isBomb() == true)
							counter++;
					}
					if ((j - 1) >= 0) {
						if (board[i][j - 1].isBomb() == true)
							counter++;
					}

					if ((i + 1) < size && (j + 1) < size) {
						if (board[i + 1][j + 1].isBomb() == true)
							counter++;
					}
					if ((j + 1) < size) {
						if (board[i][j + 1].isBomb() == true)
							counter++;
					}
					if ((i + 1) < size) {
						if (board[i + 1][j].isBomb() == true)
							counter++;
					}

					if ((i + 1) < size && (j - 1) >= 0
							&& board[i + 1][j - 1].isBomb() == true)
						counter++;
					if ((i - 1) >= 0 && (j + 1) < size
							&& board[i - 1][j + 1].isBomb() == true)
						counter++;

					board[i][j].setDigit(counter);

				}
			}

	}

	
	/**
	 * Getter do pojedynczego pola z planszy.
	 * @param x int kolumna
	 * @param y int wiersz
	 * @return FieldModel pole
	 */
	public FieldModel getField(int x, int y) {
		return board[y][x];
	}

	/**
	 * Getter rozmiaru planszy
	 * @return int rozmiar
	 */
	public int getSize() {
		return size;
	}
}
