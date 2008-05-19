package controller;

import model.BoardModel;
import model.FieldModel;

public class FieldController {
	
	private BoardModel board;

	private boolean isValid = true;
	
	/**
	 * Domyslny konstruktor kontolera pola (i calej gry)
	 *
	 */
	public FieldController() {
		board = new BoardModel();
	}
	
	/**
	 * Konstruktor z parametrami, ktory przekazuje je do tworzonego modelu planszy
	 * @param a wymiar planszy
	 * @param l poziom trudnosci
	 * @see Model#Level
	 */
	public FieldController(model.Level l) {
		board = new BoardModel(25, l);
	}
	
	/**
	 * Dotyka pola
	 * Odkrywa pola zgodnie z zasadami, zwraca false, jezli zyjemy nadal, true, jak dotknelismy bomby
	 * @param x numer wiersza 
	 * @param y numer kolumny
	 * @return flase: nie bomba, true: bomba
	 */
	public boolean touch(int x, int y) { // @TODO co tak serio tu trzeba rzucac?
		FieldModel f = board.getField(x, y);
			
		if(f.isBomb()) {
			showAll();
			isValid = false; // nie mozna juz dalej grac
			return true;
		}
		
		reveal(x, y);
		return false;
	}
	
	/**
	 * Metoda odkrywa rekursywnie pola, wywolywana z touch
	 * @param x kolumna
	 * @param y wiersz
	 */
	private void reveal(int x, int y)  {
		FieldModel f = board.getField(x, y);
		if (!f.isBomb() && !f.isVisible()) {
			f.show();
			if (f.getDigit() == 0) { // odkrywaj dalej rekursywnie
				if ((y - 1) >= 0 && (x - 1) >= 0) {
					reveal(x-1,y-1);
				}
				if ((y - 1) >= 0 ) {
					reveal(x, y-1);
				}
				if ((x - 1) >= 0) {
					reveal(x-1, y);
				}
				
				if ((y + 1) < board.getSize() && (x + 1) < board.getSize()) {
					reveal(x+1, y+1);
				}
				if ((x + 1) < board.getSize()) {
					reveal(x+1, y);
				}
				if ((y + 1) < board.getSize()) {
					reveal(x, y+1);
				}
				
				if ((y + 1) < board.getSize() && (x - 1) >= 0)
					reveal(x-1, y+1);
				if ((y - 1) >= 0 && (x + 1) < board.getSize())
					reveal(x+1, y-1);
			}
		}
		
	}
	
	/**
	 * Flaguje co bez flagi, zabiera flage tam, gdzie jest
	 * @param x kolumna
	 * @param y rzad
	 */
	public void flag(int x, int y) {
		FieldModel f = board.getField(x, y);
		if(f.isFlagged())
			f.setFlagged(false);
		else
			f.setFlagged(true);
	}

	/**
	 * Tworzy tablice wartosci enum FieldIs, ktora bedzie przekazana do widoku.
	 * Jest to jednyna informacja o stanie gry dostepna dla widoku.
	 * @return Fieldis[][] tablica mowiaca co widac w danym polu
	 */
	public FieldIs[][] makeArray() {
		FieldIs[][] a = new FieldIs[board.getSize()][board.getSize()];
		FieldModel f;
		for(int y=0; y < board.getSize(); y++)
			for(int x=0; x < board.getSize(); x++) {
				f = board.getField(x, y);
				
				if(f.isBomb() && f.isFlagged()) {
					a[x][y] = FieldIs.BOMB_FLAG;
					continue;
				}
				
				if(f.isFlagged()) {
					a[x][y] = FieldIs.FLAG;
					continue;
				}
				
				if(!f.isVisible()) {
					a[x][y] = FieldIs.HIDDEN;
					continue;
				}
				
				if(f.isBomb()) {
					a[x][y] = FieldIs.BOMB;
					continue;
				}
				
				switch(f.getDigit()) {
				case 1: a[x][y] = FieldIs.ONE; break;
				case 2: a[x][y] = FieldIs.TWO; break;
				case 3: a[x][y] = FieldIs.THREE; break;
				case 4: a[x][y] = FieldIs.FOUR; break;
				case 5: a[x][y] = FieldIs.FIVE; break;
				case 6: a[x][y] = FieldIs.SIX; break;
				case 7: a[x][y] = FieldIs.SEVEN; break;
				case 8: a[x][y] = FieldIs.EIGHT; break;
				}
			}
		return a;
	}
	
	/**
	 * Oblicza liczbe punktow. +1 za flage na bombie, -2 za flage na pustym
	 * @return short liczba punktow
	 */
	public short computeScore() {
		FieldModel f;
		short score = 0;
		for(int y=0; y < board.getSize(); y++)
			for(int x=0; x < board.getSize(); x++) {
				f = board.getField(x, y);
				if(f.isFlagged() && f.isBomb())
					score++;
				else if(f.isFlagged())
					score -= 2;
			}
		return score;
	}
	
	/**
	 * Metoda odkrywa cala plansze (na koniec gry)
	 *
	 */
	public void showAll() {
		FieldModel f;
		for (int i = 0; i < board.getSize(); i++)
			// pokaz wsie bomby
			for (int j = 0; j < board.getSize(); j++) {
				f = board.getField(j, i);
				f.show();
			}
	}
	
	/**
	 * @return wymiar planszy
	 */
	public int getSize() {
		return board.getSize();
	}
	
	/**
	 * @return czy plansza jeszcze gra
	 */
	public boolean isValid() {
		return isValid;
	}
	
	/**
	 * Setter do isValid
	 * @param v boolean 
	 */
	public void setValid(boolean v) {
		isValid = v;
	}

}
