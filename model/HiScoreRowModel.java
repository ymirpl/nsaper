package model;

/**
 * Klasa pomocniczna, reprezentuje jeden wiersz w tabeli najlepszych wyników.
 * Implementuje Comparable, aby można było sortować wyniki. 
 * @author Marcin Mincer
 * @since RC1
 */
public class HiScoreRowModel implements Comparable<HiScoreRowModel> {
	private Integer score;
	private String name;
	private int position;

	/**
	 * Konstruktor domyślny, przyjumuje parametry wiersza w tabeli wyników.
	 * @param score punkty
	 * @param name imie
	 * @param i miejsce w tabeli
	 */
	public HiScoreRowModel(int score, String name, int i) {
		this.score = score;
		this.name = name;
		this.position = i;
	}
	
	/**
	 * Getter imienia. 
	 * @return Strign imię gracza
	 */
	public String getName() {
		return name;
	}
	/**
	 * Setter imienia
	 * @param name String imię gracza
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Getter pozycji
	 * @return int pozycja w rankingu
	 */
	public int getPosition() {
		return position;
	}
	/**
	 * Setter pozycji
	 * @param position int pozycja w rankingu
	 */
	public void setPosition(int position) {
		this.position = position;
	}
	/**
	 * Getter ilości punktów. 
	 * @return int ile punktów
	 */
	public Integer getScore() {
		return score;
	}
	/**
	 * Setter ilości punktów
	 * @param score int ile punktów
	 */
	public void setScore(int score) {
		this.score = score;
	}


	/**
	 * Metoda wymagana przez interfejs Comparable. 
	 * Potrzebna, aby móc sortować wyniki zgodnie z ilością punktów uzyskanych.
	 * @param r {@link HiScoreRowModel} do porównania 
	 * @return int zgodnie z wytycznymi Comparable
	 */
	public int compareTo(HiScoreRowModel r) {
		if (this.score < r.score)
			return 1;
		else
			return -1;
	}
}

