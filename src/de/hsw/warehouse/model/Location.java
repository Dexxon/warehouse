package de.hsw.warehouse.model;

import java.util.ArrayList;

/**
 * Diese Klasse stellt einen einzelnen Lagerplatz dar. In einem Lagerplatz können mehrere Artikel gelagert werden. Ein Lagerplatz hat ein bestimmmtes Volumen. Ist dieses ausgeschöpft, können keine weiteren {@link de.hsw.warehouse.model.Article Artikel} hinzugefügt werden.
 * @author Constantin Grote
 * @version 
 */
class Location
{

	private int capacity;
	private int load;
	private ArrayList<Article> article = new ArrayList<Article>();

	/**
	 * Gibt das Volumen des Lagerplatzes zurück.
	 * @param volume Das Volumen des Lagerplatzes.
	 */
	Location(int volume)
	{
		this.capacity = volume;
	}

	/**
	 * Gibt die aktuelle Auslastung des Lagerplatzes zurück.
	 * @return Die aktuelle Auslastung des Lagerplatzes.
	 */
	int getLoad()
	{
		return load;
	}

	/**
	 * Gibt die Gesamtkapazität des Lagerplatzes zurück.
	 * @return Die Gesamtkapazität des Lagerplatzes.
	 */
	int getCapacity()
	{
		return capacity;
	}

	/**
	 * Gibt den noch zur Verfügung stehenden Platz in dem Lagerplatz zurück.
	 * @return Den noch zur Verfügung stehenden Platz in dem Lagerplatz.
	 */
	int getFreeSpace()
	{
		return this.capacity - this.load;
	}
	
	/**
	 * Gibt die in dem Lagerplatz gelagerten {@link de.hsw.warehouse.model.Article Artikel} zurück.
	 * @return Die in dem Lagerplatz gelagerten {@link de.hsw.warehouse.model.Article Artikel}.
	 */
	ArrayList<Article> getArticle()
	{
		return article;
	}

	/**
	 * Fügt dem Lagerplatz einen {@link de.hsw.warehouse.model.Article Artikel} hinzu. Dabei wird nicht geprüft, ob noch genügend Platz vorhanden ist. Diese Prüfung muss also auf höherer Ebene stattfinden.
	 * @param article Der hinzuzufügende {@link de.hsw.warehouse.model.Article Artikel}.
	 */
	void addArticle(Article article)
	{
		this.article.add(article);
		this.load += article.getVolume();
	}

	/**
	 * Entfernt einen {@link de.hsw.warehouse.model.Article Artikel} aus dem Lagerplatz, falls er vorhanden ist.
	 * @param article Der zu entfernende {@link de.hsw.warehouse.model.Article Artikel}.
	 */
	void removeArticle(Article article)
	{
		if (this.article.contains(article)) {
			this.article.remove(this.article.indexOf(article));
			this.load -= article.getVolume();
		}
	}
}
