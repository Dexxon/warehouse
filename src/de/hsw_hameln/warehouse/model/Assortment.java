package de.hsw_hameln.warehouse.model;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

import de.hsw_hameln.warehouse.util.Util;

/**
 * In dieser Klasse wird das Sortiment statisch definiert.
 * 
 * @author Constantin Grote
 * @version 29.05.2014
 */
public class Assortment
{
	/**
	 * Diese anonyme Klasse wird fuer den Aufbau des Sortiments benoetigt. Da sie die gleichen
	 * Eigenschaften hat wie die Klasse "{@link de.hsw_hameln.warehouse.model.Article}", erbt sie
	 * von dieser.
	 * 
	 * @author Constantin Grote
	 * @version 29.05.2014
	 */
	static class AssortmentArticle extends Article
	{
		/**
		 * Erstellt einen einzelnen Artikel.
		 * 
		 * @param name Der Name des {@link de.hsw_hameln.warehouse.model.Article Artikels}.
		 * @param commodityGroup Die Warengruppe des {@link de.hsw_hameln.warehouse.model.Article
		 *            Artikels} .
		 * @param volume Das Volumen des {@link de.hsw_hameln.warehouse.model.Article Artikels}.
		 */
		public AssortmentArticle(String name, String commodityGroup, int volume)
		{
			this.articleName = name;
			this.commodityGroup = commodityGroup;
			this.volume = volume;
		}
	}

	private static AssortmentArticle[] articlePool = initialiseArticlePool(Util.inputPath(
			Paths.get("C:\\Lagerbewegungen\\Artikelpool.csv"),
			"Bitte geben Sie den Pfad zu der Artikelliste ein"));

	/**
	 * Liest den Artikelpool aus einer .csv-Datei von der Festplatte ein.
	 * 
	 * @param path Der Pfad zu dem Artikelpool.
	 * @return Ein Array, welches alle {@link de.hsw_hameln.warehouse.model.Article Artikel} des
	 *         Sortiments enthaelt.
	 */
	private static AssortmentArticle[] initialiseArticlePool(Path path)
	{
		LinkedList<AssortmentArticle> articlePool = new LinkedList<AssortmentArticle>();
		String[] line;
		String[] lines = null;

		try {
			lines = Util.readFromDisk(path);
		} catch (FileNotFoundException e) {
			System.out.println("Fehler beim Einlesen der Artikelliste.");
			Runtime.getRuntime().exit(1);
		}

		for (int i = 0; i < lines.length; i++) {
			line = lines[i].split(";");
			articlePool.add(new AssortmentArticle(line[0], line[1], Integer.parseInt(line[2])));
		}

		return articlePool.toArray(new AssortmentArticle[articlePool.size()]);
	}

	/**
	 * Gibt den der Artikelnummer entsprechenden Artikelnamen zurueck.
	 * 
	 * @param articleID Artikelnummer des gewuenschten {@link de.hsw_hameln.warehouse.model.Article
	 *            Artikels}.
	 * @return Name des der Artikelnummer entsprechenden
	 *         {@link de.hsw_hameln.warehouse.model.Article Artikels}.
	 */
	public static String getArticleName(int articleID)
	{
		return articlePool[articleID].articleName;
	}

	/**
	 * Gibt die der Artikelnummer entsprechende Warengruppe zurueck.
	 * 
	 * @param ArticleID Artikelnummer des gewuenschten {@link de.hsw_hameln.warehouse.model.Article
	 *            Artikels}.
	 * @return Warengruppe des der Artikelnummer entsprechenden
	 *         {@link de.hsw_hameln.warehouse.model.Article Artikels}.
	 */
	public static String getArticleCommodityGroup(int ArticleID)
	{
		return articlePool[ArticleID].commodityGroup;
	}

	/**
	 * Gibt das der Artikelnummer entsprechende Volumen zurueck.
	 * 
	 * @param articleID Artikelnummer des gewuenschten {@link de.hsw_hameln.warehouse.model.Article
	 *            Artikels}.
	 * @return Volumen des der Artikelnummer entsprechenden
	 *         {@link de.hsw_hameln.warehouse.model.Article Artikels}.
	 */
	public static int getArticleVolume(int articleID)
	{
		return articlePool[articleID].volume;
	}

	/**
	 * Gibt die Anzahl der im Sortiment vorhandenen {@link de.hsw_hameln.warehouse.model.Article
	 * Artikel} zurueck.
	 * 
	 * @return Die Anzahl der im Sortiment vorhandenen {@link de.hsw_hameln.warehouse.model.Article
	 *         Artikel}.
	 */
	public static int getSize()
	{
		return articlePool.length;
	}
}
