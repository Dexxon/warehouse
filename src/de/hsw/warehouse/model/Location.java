package de.hsw.warehouse.model;

import java.util.ArrayList;

/**
 * Diese Klasse stellt einen einzelnen Lagerplatz dar. In einem Lagerplatz k�nnen mehrere Artikel gelagert werden. Ein Lagerplatz hat ein bestimmmtes Volumen. Ist dieses ausgesch�pft, k�nnen keine weiteren Artikel hinzugef�gt werden.
 * @author Constantin
 * @version 
 */
public class Location
{

	private int capacity;
	private int load;
	private ArrayList<Article> article = new ArrayList<Article>();

	/**
	 * @param volumen Das Volumen des Lagerplatzes.
	 */
	public Location(int volumen)
	{
		this.capacity = volumen;
	}

	/**
	 * @return Die aktuelle Auslastung des Lagerplatzes.
	 */
	public int getLoad()
	{
		return load;
	}

	/**
	 * @return Die Gesamtkapazit�t des Lagerplatzes.
	 */
	public int getCapacity()
	{
		return capacity;
	}

	/**
	 * @return Den noch zur Verf�gung stehenden Platz in dem Lagerplatz.
	 */
	public int getFreeSpace()
	{
		return this.capacity - this.load;
	}
	
	/**
	 * @return Die in dem Lagerplatz gelagerten Artikel.
	 */
	public ArrayList<Article> getArticle()
	{
		return article;
	}

	/**
	 * F�gt dem Lagerplatz einen Artikel hinzu. Dabei wird nicht gepr�ft, ob noch gen�gend Platz vorhanden ist. Diese Pr�fung muss also auf h�herer Ebene stattfinden.
	 * @param article Der hinzuzuf�gende Artikel.
	 */
	public void addArticle(Article article)
	{
		this.article.add(article);
		this.load += article.getVolume();
	}

	/**
	 * Entfernt einen Artikel aus dem Lagerplatz, falls er vorhanden ist.
	 * @param article Der zu entfernende Artikel.
	 */
	public void removeArticle(Article article)
	{
		if (this.article.contains(article)) {
			this.article.remove(this.article.indexOf(article));
			this.load -= article.getVolume();
		}
	}
}
