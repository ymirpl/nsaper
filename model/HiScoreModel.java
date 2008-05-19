package model;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class HiScoreModel {
	List<HiScoreRowModel> rows = new LinkedList<HiScoreRowModel>(); // lista z wierwszami wpisow punktow
	
	/**
	 * Domyslny konstruktor. Wczytuje dane z pliku, jesli go nie ma, to go tworzy. 
	 * 
	 */
	public HiScoreModel() {

		SAXBuilder builder = new SAXBuilder();
		Document xml;
		
		try {
			xml = builder.build(new File("score.xml"));
			Element score = xml.getRootElement();
			
			List nodes = score.getChildren(); // jest plik, wczytujemy do tablicy
			
			for (int i = 1; i <= 10; i++) {
				Element row = (Element) nodes.get(i - 1);
				String name = row.getChildText("name");
				int points = Short.valueOf(row.getChildText("points"));
				byte pos = Byte.valueOf(row.getAttributeValue("id"));
				rows.add(new HiScoreRowModel(points, name, pos));
			}
			
			Collections.sort(rows); // sortujemy wczytana liste (w sumie to nie powinno byc potrzebne @TODO)
		
		} catch (Exception e) { // nie ma takiego pliku lub cos gorszego
			
			// towrzymy nowy plik o korzeniu score
			Element score = new Element("score");
			xml = new Document(score);
			
			// wypelniamy go jasiami
			for(int i=1; i <=10;i++) {
				Element pos = new Element("position");
				pos.setAttribute("id", Integer.toString(i));
				
				Element name = new Element("name");
				name.setText("John");
				
				Element points = new Element("points");
				points.setText("2");
				
				pos.addContent(name);
				pos.addContent(points);
				
				score.addContent(pos);
				
				rows.add(new HiScoreRowModel(2, "John", i)); // tablice wypelniamy tak samo
			}
			
			save(xml); // zapisujemy plik

		}
		
	}
	
	private void save(Document xml) {
		try {
			FileWriter writer = new FileWriter("score.xml");
			XMLOutputter out = new XMLOutputter();
			out.setFormat(Format.getPrettyFormat());
			out.output(xml, writer);
			writer.close();
		} catch (IOException e1) { // nie mozna utorzyc nowego pliku
			System.err.println("Nie mozna otoworzyc pliku do zapisu!");
		} 
	}

	/**
	 * @return lista wpisow do tabeli
	 */
	public List<HiScoreRowModel> getHiScore() {
		return rows;
	}
	
	/**
	 * Metoda wstawia wynik uzyskany przez gracza.
	 */
	public void insertScore(Integer points, String name) {
		rows.add(new HiScoreRowModel(points, name, 0));
		Collections.sort(rows);
		rows.remove(10); // wywalamy ostatni wynik
		
		// towrzymy nowy plik o korzeniu score
		Element score = new Element("score");
		Document xml = new Document(score);
		
		for(int i=0; i <10;i++) {
			Element pos = new Element("position");
			pos.setAttribute("id", Integer.toString(i+1));
			rows.get(i).setPosition(i+1); // przy okazji ustawiamy tez w tablicy
			
			Element xname = new Element("name");
			xname.setText(rows.get(i).getName());
			
			Element xpoints = new Element("points");
			xpoints.setText(rows.get(i).getScore().toString());
			
			pos.addContent(xname);
			pos.addContent(xpoints);
			
			score.addContent(pos);
			
		}
		File f = new File("score.xml");
		f.delete(); //usuwamy stary plik i wpisujemy nowy z nowwa tabela
		save(xml);
	
	}
}
