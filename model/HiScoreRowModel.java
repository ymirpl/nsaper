package model;

/**
 * Klasa pomocniczna reprezenuje jeden wiersz w tabli najlepszych wynikow
 * @author ymir
 */
public class HiScoreRowModel implements Comparable<HiScoreRowModel> {
	private Integer score;
	private String name;
	private int position;

	/**
	 * Konstruktor domyslny, przyjumuje parametry wiersza w tabeli wynikow
	 * @param score punkty
	 * @param name imie
	 * @param i miejsce w tabeli
	 */
	public HiScoreRowModel(int score, String name, int i) {
		this.score = score;
		this.name = name;
		this.position = i;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

	public int compareTo(HiScoreRowModel r) {
		if (this.score < r.score)
			return 1;
		else
			return -1;
	}
}

