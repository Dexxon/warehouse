package de.hsw.warehouse.model;

import java.util.ArrayList;

/**
 * Diese Klasse stellt einen einzelnen Lagerplatz dar. In einem Lagerplatz k�nnen mehrere Artikel gelagert werden. Ein Lagerplatz hat ein bestimmmtes Volumen. Ist dieses ausgesch�pft, k�nnen keine weiteren {@link de.hsw.warehouse.model.Article Artikel} hinzugef�gt werden.
 * @author Constantin Grote
 * @version 
 */
class Location
{

	private int capacity;
	private int load;
	private ArrayList<Article> article = new ArrayList<Article>();

	/**
	 * Gibt das Volumen des Lagerplatzes zur�ck.
	 * @param volume Das Volumen des Lagerplatzes.
	 */
	Location(int volume)
	{
		this.capacity = volume;
	}

	/**
	 * Gibt die aktuelle Auslastung des Lagerplatzes zur�ck.
	 * @return Die aktuelle Auslastung des Lagerplatzes.
	 */
	int getLoad()
	{
		return load;
	}

	/**
	 * Gibt die Gesamtkapazit�t des Lagerplatzes zur�ck.
	 * @return Die Gesamtkapazit�t des Lagerplatzes.
	 */
	int getCapacity()
	{
		return capacity;
	}

	/**
	 * Gibt den noch zur Verf�gung stehenden Platz in dem Lagerplatz zur�ck.
	 * @return Den noch zur Verf�gung stehenden Platz in dem Lagerplatz.
	 */
	int getFreeSpace()
	{
		return this.capacity - this.load;
	}
	
	/**
	 * Gibt die in dem Lagerplatz gelagerten {@link de.hsw.warehouse.model.Article Artikel} zur�ck.
	 * @return Die in dem Lagerplatz gelagerten {@link de.hsw.warehouse.model.Article Artikel}.
	 */
	ArrayList<Article> getArticle()
	{
		return article;
	}

	/**
	 * F�gt dem Lagerplatz einen {@link de.hsw.warehouse.model.Article Artikel} hinzu. Dabei wird nicht gepr�ft, ob noch gen�gend Platz vorhanden ist. Diese Pr�fung muss also auf h�herer Ebene stattfinden.
	 * @param article Der hinzuzuf�gende {@link de.hsw.warehouse.model.Article Artikel}.
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
