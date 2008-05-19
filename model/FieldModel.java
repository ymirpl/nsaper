package model;
/**
 * Klasa reprezentująca pojedyńcze pole w grze
 * @author ymir
 *
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
	 * @return the digit
	 */
	public byte getDigit() {
		return digit;
	}

	/**
	 * @param digit the digit to set
	 */
	void setDigit(byte digit) {
		this.digit = digit;
	}

	/**
	 * @return the isBomb
	 */
	public boolean isBomb() {
		return isBomb;
	}

	/**
	 * @param isBomb the isBomb to set
	 */
	void setBomb(boolean isBomb) {
		this.isBomb = isBomb;
	}


	/**
	 * @return the isFlagged
	 */
	public boolean isFlagged() {
		return isFlagged;
	}

	/**
	 * @param isFlagged the isFlagged to set
	 */
	public void setFlagged(boolean isFlagged) {
		this.isFlagged = isFlagged;
	}

	/**
	 * @return the isVisible
	 */
	public boolean isVisible() {
		return isVisible;
	}

	/**
	 * Odkrywa pole
	 */
	public void show() {
		this.isVisible = true;
	}

	/**
	 * Domyślny konstruktor
	 */
	public FieldModel() {
		this.digit = 0;
		this.isBomb = false;
		//this.isVisible = false;
		this.isVisible = false;
		this.isFlagged = false;
	}
}
