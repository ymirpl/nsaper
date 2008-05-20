package model;
/**
 * Klasa reprezentująca pojedyńcze pole w grze
 * 
 * Zawiera metody pozwalające na odczytnie i zmian stanu pola gry. 
 * @author ymir
 * @since RC1
 * @see BoardModel
 */
public class FieldModel {
	/**
	 * Czy pole jest bombą;
	 */
	private boolean isBomb;
	
	/**
	 * Zawiera cyferkę mówiącą o okolicznych polach
	 */
	private byte digit;
	
	/**
	 * Czy odkryte
	 */
	private boolean isVisible;
	
	/**
	 * Czy oflagowane
	 */
	private boolean isFlagged;
	
	
	/**
	 * Getter cyferki na polu. 
	 * @return byte cyferka na polu
	 */
	public byte getDigit() {
		return digit;
	}

	/**
	 * Setter cyferki na polu. 
	 * @param byte cyferka
	 */
	void setDigit(byte digit) {
		this.digit = digit;
	}

	/**
	 * Czy pole jest bombą?
	 * @return boolean true jeżeli jest bombą
	 */
	public boolean isBomb() {
		return isBomb;
	}

	/**
	 * Ustawia bombę
	 * @param boolean true jeżeli ma być bombą
	 */
	void setBomb(boolean isBomb) {
		this.isBomb = isBomb;
	}


	/**
	 * Czy jest oflagowane?
	 * @return boolean true jeżeli jest
	 */
	public boolean isFlagged() {
		return isFlagged;
	}

	/**
	 * Nadaje/zabiera flagę
	 * @param boolean czy ma być flaga
	 */
	public void setFlagged(boolean isFlagged) {
		this.isFlagged = isFlagged;
	}

	/**
	 * Czy pole jest odkryte (widoczne)?
	 * @return boolean true jeżeli jest widoczne
	 */
	public boolean isVisible() {
		return isVisible;
	}

	/**
	 * Odkrywa pole (isVisible staje się true, odflagowuje się)
	 */
	public void show() {
		this.isVisible = true;
		this.isFlagged = false;
	}

	/**
	 * Domyślny konstruktor
	 * 
	 * Produkuje pole bez bomby, cyfry ni flagi
	 */
	public FieldModel() {
		this.digit = 0;
		this.isBomb = false;
		this.isVisible = false;
		this.isFlagged = false;
	}
}
