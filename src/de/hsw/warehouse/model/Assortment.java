package de.hsw.warehouse.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * In dieser Klasse wird das Sortiment statisch definiert.
 * @author Constantin
 * @version
 */
public class Assortment
{
	/**
	 * Diese anonyme Klasse wird für den Aufbau des Sortiments benötigt. Sie entspricht weitestgehend der Klasse "Article. Diese kann hier jedoch nicht verwendet werden (siehe Dokumentation).
	 * @author Constantin
	 * @version 
	 */
	static class SingleArticle
	{
		private String name;
		private String commodityGroup;
		private int volume;

		/**
		 * 
		 * @param name Der Name des Artikels.
		 * @param commodityGroup Die Warengruppe des Artikels.
		 * @param volume Das Volumen des Artikels.
		 */
		public SingleArticle(String name, String commodityGroup, int volume)
		{
			this.name = name;
			this.commodityGroup = commodityGroup;
			this.volume = volume;
		}
	}
	
	static SingleArticle[] articlePool = initialiseArticlePool("C:\\Artikelpool.csv");
	
	/**
	 * Liest den Artikelpool aus einer .csv-Datei von der Festplatte ein.
	 * @param path Der Pfad zu dem Artikelpool.
	 * @return Ein Array, welches alle Artikel des Sortiments enthält.
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
	 * @param articleID Artikelnummer des gewünschten Artikels.
	 * @return Name des der Artikelnummer entsprechenden Artikels.
	 */
	public static String getArticleName(int articleID)
	{
		return articlePool[articleID].name;
	}
	
	/**
	 * @param ArticleID Artikelnummer des gewünschten Artikels.
	 * @return Warengruppe des der Artikelnummer entsprechenden Artikels.
	 */
	public static String getArticleCommodityGroup(int ArticleID)
	{
		return articlePool[ArticleID].commodityGroup;
	}
	
	/**
	 * @param articleID Artikelnummer des gewünschten Artikels.
	 * @return Volumen des der Artikelnummer entsprechenden Artikels.
	 */
	public static int getArticleVolume(int articleID)
	{
		return articlePool[articleID].volume;
	}
	
	/**
	 * @return Die Anzahl der im Sortiment vorhandenen Artikel.
	 */
	public static int getSize()
	{
		return articlePool.length;
	}
}
