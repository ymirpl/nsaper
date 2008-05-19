package model;

import java.util.Random;

public class BoardModel {

	private FieldModel[][] board;
	
	private int size;
	
	
	/**
	 * poziom trudnosci
	 */
	private Level level;
	

	private static Random random = new Random();

	
	/**
	 * Konstruktor
	 * @param h  wysokosc planszy
	 * @param w  szerokosc planszy
	 * @param l  poziom trudnosci
	 */
	public BoardModel(int a, Level l) {
		this.size = a;
		this.level = l;
		board  = new FieldModel[size][size];
		generateBoard();
	}
	
	public BoardModel() {
		this.size = 25;
		this.level = Level.NORMAL;
		board  = new FieldModel[size][size];
		generateBoard();
	}
	
	/**
	 * Generuje plansze z bombami i polami z odpowiednimi cyframi
	 */
	private void generateBoard() {
		
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				board[i][j] = new FieldModel();
		
		// pierwsze przejscie generuje bomby (ilosc zalezy od poziomu)
		int bombs = (int) ((Math.abs(random.nextGaussian()) + 1) * Math.ceil(0.05 * size * size));// ile bomb
		System.out.println(bombs);
		//if(level == Level.EASY)
		//	bombs += random.nextInt() % ((int)(0.1 * height * width));
		if(level == Level.NORMAL)
			bombs += ((int)Math.ceil((0.05 * size * size)));
		if(level == Level.HARD)
			bombs += ((int)Math.ceil((0.15 * size * size)));
		
		System.out.println(bombs);
		for(int i = 0; i <= bombs; i++) {
			int x = Math.abs(random.nextInt() % size);
			int y = Math.abs(random.nextInt() % size);
			
			//if(board[y][x].isBomb() == false)
				board[y][x].setBomb(true);
			//else // jezeli tam juz byla bomba nie liczymy tego przejscia petli
				//--i;
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
					if ((i - 1) >= 0 ) {
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
					
					if ((i + 1) < size && (j - 1) >= 0 && board[i + 1][j - 1].isBomb() == true)
						counter++;
					if ((i - 1) >= 0 && (j + 1) < size && board[i - 1][j + 1].isBomb() == true)
						counter++;
					
					board[i][j].setDigit(counter);
					
				}
			}
			
		
	}

	
	/**
	 * @return the board element
	 */
	public FieldModel getField(int x, int y) {
		return board[y][x];
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}
}
