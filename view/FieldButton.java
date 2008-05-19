package view;

import javax.swing.JButton;

/**
 * Klasa rozszerza normalny swingowy przycisk, w celu przechowywania w nim informacji
 * o poloezniu na planszy gry. 
 * @author ymir
 *
 */
public class FieldButton extends JButton {

	private static final long serialVersionUID = 1L;
	/**
	 * koordynaty przycisku
	 */
	private int x;
	private int y;
	
	/**
	 * Konstruktor, jako argumenty pobiera koordynaty przycisku na planszy
	 * @param x wiersz
	 * @param y kolumna
	 */
	public FieldButton(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Zwraca wspolrzedna x przycisku na planszy
	 * @return int x
	 */
	public int getRow() {
		return x;
	}

	/**
	 * Zwraca wspolrzedna y przycisku na planszy
	 * @return int y
	 */
	public int getCol() {
		return y;
	}
	
}
