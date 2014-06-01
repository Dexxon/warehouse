package de.hsw_hameln.warehouse.model;

import java.util.ArrayList;

/**
 * Diese Klasse stellt einen einzelnen Lagerplatz dar. In einem Lagerplatz koennen mehrere Artikel
 * gelagert werden. Ein Lagerplatz hat ein bestimmmtes Volumen. Ist dieses ausgeschoepft, koennen
 * keine weiteren {@link de.hsw_hameln.warehouse.model.Article Artikel} hinzugefuegt werden. Dabei
 * wird nicht geprueft, ob noch genuegend Platz vorhanden ist. Diese Pruefung muss also auf hoeherer
 * Ebene stattfinden.
 * 
 * @author Constantin Grote
 * @version 30.05.2014
 */
public class Location
{
	private int capacity;
	private int load;
	private ArrayList<Article> articles = new ArrayList<Article>();

	/**
	 * Erzeugt einen Lagerplatz mit der angegebenen Kapazitaet.
	 * 
	 * @param volume Das Volumen des Lagerplatzes.
	 */
	public Location(int volume)
	{
		this.capacity = volume;
	}

	/**
	 * Gibt die aktuelle Auslastung des Lagerplatzes zurueck.
	 * 
	 * @return Die aktuelle Auslastung des Lagerplatzes.
	 */
	public int getLoad()
	{
		return this.load;
	}

	/**
	 * Gibt die Gesamtkapazitaet des Lagerplatzes zurueck.
	 * 
	 * @return Die Gesamtkapazitaet des Lagerplatzes.
	 */
	public int getCapacity()
	{
		return this.capacity;
	}

	/**
	 * Gibt den noch zur Verfuegung stehenden Platz in dem Lagerplatz zurueck.
	 * 
	 * @return Den noch zur Verfuegung stehenden Platz in dem Lagerplatz.
	 */
	public int getFreeSpace()
	{
		return this.capacity - this.load;
	}

	/**
	 * Gibt die in dem Lagerplatz gelagerten {@link de.hsw_hameln.warehouse.model.Article Artikel}
	 * zurueck.
	 * 
	 * @return Die in dem Lagerplatz gelagerten {@link de.hsw_hameln.warehouse.model.Article
	 *         Artikel}.
	 */
	public ArrayList<Article> getArticles()
	{
		return this.articles;
	}

	/**
	 * Fuegt dem Lagerplatz einen {@link de.hsw_hameln.warehouse.model.Article Artikel} hinzu. Dabei
	 * wird nicht geprueft, ob noch genuegend Platz vorhanden ist. Diese Pruefung muss also auf
	 * hoeherer Ebene stattfinden.
	 * 
	 * @param article Der hinzuzufuegende {@link de.hsw_hameln.warehouse.model.Article Artikel}.
	 */
	public void addArticle(Article article)
	{
		this.articles.add(article);
		this.load += article.getVolume();
	}

	/**
	 * Entfernt einen {@link de.hsw_hameln.warehouse.model.Article Artikel} aus dem Lagerplatz,
	 * falls er vorhanden ist.
	 * 
	 * @param article Der zu entfernende {@link de.hsw_hameln.warehouse.model.Article Artikel}.
	 */
	public void removeArticle(Article article)
	{
		if (this.articles.contains(article)) {
			this.articles.remove(this.articles.indexOf(article));
			this.load -= article.getVolume();
		}
	}
}
