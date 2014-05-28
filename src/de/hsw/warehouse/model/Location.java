package de.hsw.warehouse.model;

import java.util.ArrayList;

/**
 * Diese Klasse stellt einen einzelnen Lagerplatz dar. In einem Lagerplatz können mehrere Artikel gelagert werden. Ein Lagerplatz hat ein bestimmmtes Volumen. Ist dieses ausgeschöpft, können keine weiteren Artikel hinzugefügt werden.
 * @author Constantin Grote
 * @version 
 */
class Location
{

	private int capacity;
	private int load;
	private ArrayList<Article> article = new ArrayList<Article>();

	/**
	 * @param volumen Das Volumen des Lagerplatzes.
	 */
	Location(int volumen)
	{
		this.capacity = volumen;
	}

	/**
	 * @return Die aktuelle Auslastung des Lagerplatzes.
	 */
	int getLoad()
	{
		return load;
	}

	/**
	 * @return Die Gesamtkapazität des Lagerplatzes.
	 */
	int getCapacity()
	{
		return capacity;
	}

	/**
	 * @return Den noch zur Verfügung stehenden Platz in dem Lagerplatz.
	 */
	int getFreeSpace()
	{
		return this.capacity - this.load;
	}
	
	/**
	 * @return Die in dem Lagerplatz gelagerten Artikel.
	 */
	ArrayList<Article> getArticle()
	{
		return article;
	}

	/**
	 * Fügt dem Lagerplatz einen Artikel hinzu. Dabei wird nicht geprüft, ob noch genügend Platz vorhanden ist. Diese Prüfung muss also auf höherer Ebene stattfinden.
	 * @param article Der hinzuzufügende Artikel.
	 */
	void addArticle(Article article)
	{
		this.article.add(article);
		this.load += article.getVolume();
	}

	/**
	 * Entfernt einen Artikel aus dem Lagerplatz, falls er vorhanden ist.
	 * @param article Der zu entfernende Artikel.
	 */
	void removeArticle(Article article)
	{
		if (this.article.contains(article)) {
			this.article.remove(this.article.indexOf(article));
			this.load -= article.getVolume();
		}
	}
}
