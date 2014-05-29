package de.hsw.warehouse.model;

import java.util.ArrayList;

/**
 * Diese Klasse stellt einen einzelnen Lagerplatz dar. In einem Lagerplatz können mehrere Artikel
 * gelagert werden. Ein Lagerplatz hat ein bestimmmtes Volumen. Ist dieses ausgeschöpft, können
 * keine weiteren {@link de.hsw.warehouse.model.Article Artikel} hinzugefügt werden. Dabei wird
 * nicht geprüft, ob noch genügend Platz vorhanden ist. Diese Prüfung muss also auf höherer Ebene
 * stattfinden.
 * 
 * @author Constantin Grote
 * @version 29.05.2013
 */
public class Location
{
	private int capacity;
	private int load;
	private ArrayList<Article> articles = new ArrayList<Article>();

	/**
	 * Erzeugt einen Lagerplatz mit der angegebenen Kapazität.
	 * 
	 * @param volume Das Volumen des Lagerplatzes.
	 */
	public Location(int volume)
	{
		this.capacity = volume;
	}

	/**
	 * Gibt die aktuelle Auslastung des Lagerplatzes zurück.
	 * 
	 * @return Die aktuelle Auslastung des Lagerplatzes.
	 */
	public int getLoad()
	{
		return this.load;
	}

	/**
	 * Gibt die Gesamtkapazität des Lagerplatzes zurück.
	 * 
	 * @return Die Gesamtkapazität des Lagerplatzes.
	 */
	public int getCapacity()
	{
		return this.capacity;
	}

	/**
	 * Gibt den noch zur Verfügung stehenden Platz in dem Lagerplatz zurück.
	 * 
	 * @return Den noch zur Verfügung stehenden Platz in dem Lagerplatz.
	 */
	public int getFreeSpace()
	{
		return this.capacity - this.load;
	}

	/**
	 * Gibt die in dem Lagerplatz gelagerten {@link de.hsw.warehouse.model.Article Artikel} zurück.
	 * 
	 * @return Die in dem Lagerplatz gelagerten {@link de.hsw.warehouse.model.Article Artikel}.
	 */
	public ArrayList<Article> getArticles()
	{
		return this.articles;
	}

	/**
	 * Fügt dem Lagerplatz einen {@link de.hsw.warehouse.model.Article Artikel} hinzu. Dabei wird
	 * nicht geprüft, ob noch genügend Platz vorhanden ist. Diese Prüfung muss also auf höherer
	 * Ebene stattfinden.
	 * 
	 * @param article Der hinzuzufügende {@link de.hsw.warehouse.model.Article Artikel}.
	 */
	public void addArticle(Article article)
	{
		this.articles.add(article);
		this.load += article.getVolume();
	}

	/**
	 * Entfernt einen {@link de.hsw.warehouse.model.Article Artikel} aus dem Lagerplatz, falls er
	 * vorhanden ist.
	 * 
	 * @param article Der zu entfernende {@link de.hsw.warehouse.model.Article Artikel}.
	 */
	public void removeArticle(Article article)
	{
		if (this.articles.contains(article)) {
			this.articles.remove(this.articles.indexOf(article));
			this.load -= article.getVolume();
		}
	}
}
