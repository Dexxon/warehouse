package de.hsw.warehouse.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * In dieser Klasse wird das Sortiment statisch definiert.
 * @author Constantin Grote
 * @version
 */
public class Assortment
{
	/**
	 * Diese anonyme Klasse wird für den Aufbau des Sortiments benötigt. Sie entspricht weitestgehend der Klasse "{@link de.hsw.warehouse.model.Article}". Diese kann hier jedoch nicht verwendet werden (siehe Dokumentation).
	 * @author Constantin Grote
	 * @version 
	 */
	static class SingleArticle
	{
		private String name;
		private String commodityGroup;
		private int volume;

		/**
		 * 
		 * @param name Der Name des {@link de.hsw.warehouse.model.Article Artikels}.
		 * @param commodityGroup Die Warengruppe des {@link de.hsw.warehouse.model.Article Artikels}.
		 * @param volume Das Volumen des {@link de.hsw.warehouse.model.Article Artikels}.
		 */
		public SingleArticle(String name, String commodityGroup, int volume)
		{
			this.name = name;
			this.commodityGroup = commodityGroup;
			this.volume = volume;
		}
	}
	
	private static SingleArticle[] articlePool = initialiseArticlePool("C:\\Artikelpool.csv");
	
	/**
	 * Liest den Artikelpool aus einer .csv-Datei von der Festplatte ein.
	 * @param path Der Pfad zu dem Artikelpool.
	 * @return Ein Array, welches alle {@link de.hsw.warehouse.model.Article Artikel} des Sortiments enthält.
	 */
	private static SingleArticle[] initialiseArticlePool(String path) 
	{	
		LinkedList<SingleArticle> articlePool = new LinkedList<SingleArticle>();
		Scanner input = null;
		try {
			input = new Scanner(new File(path));
		} catch (FileNotFoundException e) {
			System.out.println("Fehler beim Einlesen der Artikelliste.");
		}
		String[] line;

		while (input.hasNextLine()) {
			line = input.nextLine().split(";");
			articlePool.add(new SingleArticle(line[0],line[1],Integer.parseInt(line[2])));
		}

		input.close();
		return articlePool.toArray(new SingleArticle[articlePool.size()]);
	}
	
	/**
	 * Gibt den der Artikelnummer entsprechenden Artikelnamen zurück.
	 * @param articleID Artikelnummer des gewünschten {@link de.hsw.warehouse.model.Article Artikels}.
	 * @return Name des der Artikelnummer entsprechenden {@link de.hsw.warehouse.model.Article Artikels}.
	 */
	public static String getArticleName(int articleID)
	{
		return articlePool[articleID].name;
	}
	
	/**
	 * Gibt die der Artikelnummer entsprechende Warengruppe zurück.
	 * @param ArticleID Artikelnummer des gewünschten {@link de.hsw.warehouse.model.Article Artikels}.
	 * @return Warengruppe des der Artikelnummer entsprechenden {@link de.hsw.warehouse.model.Article Artikels}.
	 */
	public static String getArticleCommodityGroup(int ArticleID)
	{
		return articlePool[ArticleID].commodityGroup;
	}
	
	/**
	 * Gibt das der Artikelnummer entsprechende Volumen zurück.
	 * @param articleID Artikelnummer des gewünschten {@link de.hsw.warehouse.model.Article Artikels}.
	 * @return Volumen des der Artikelnummer entsprechenden {@link de.hsw.warehouse.model.Article Artikels}.
	 */
	public static int getArticleVolume(int articleID)
	{
		return articlePool[articleID].volume;
	}
	
	/**
	 * Gibt die Anzahl der im Sortiment vorhandenen {@link de.hsw.warehouse.model.Article Artikel} zurück.
	 * @return Die Anzahl der im Sortiment vorhandenen {@link de.hsw.warehouse.model.Article Artikel}.
	 */
	public static int getSize()
	{
		return articlePool.length;
	}
}
