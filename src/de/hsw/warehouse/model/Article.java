package de.hsw.warehouse.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
/**
 * Diese Klasse beschreibt einen einzigen Artikel sowie den statisch definierten Artikelpool.
 * @author Constantin Grote
 *
 */
public class Article
{

	public static String[][] articlePool;
	public static String[] namePool;
	public static String[] commodityGroupPool;
	public static int[] volumePool;

	/**
	 * Initialisiert den Artikelpool. Dabei werden die statischen Arrays articlePool, namePool, commodityGroupPool und volumePool mit Werten gefüllt.
	 * @param pathToArticleList Pfad zu der .csv Datei, welche die Auflistung aller Artikel und deren Eigenschaften enthält.
	 * @throws FileNotFoundException Wird geworfen, wenn die Datei nicht an dem angegebenen Pfad gefunden wird.
	 * @throws NumberFormatException Wird geworfen, wenn bei der Umwandlung eines Strings in einen Integer einFehler auftritt.
	 */
	public static void initialiseArticlePool(String pathToArticleList)
			throws FileNotFoundException, NumberFormatException
	{
		LinkedList<String[]> articleList = new LinkedList<String[]>();
		Scanner input = new Scanner(new File(pathToArticleList));

		while (input.hasNextLine()) {
			articleList.add(input.nextLine().split(";"));
		}

		input.close();
		articlePool = new String[articleList.size()][6];
		namePool = new String[articleList.size()];
		commodityGroupPool = new String[articleList.size()];
		volumePool = new int[articleList.size()];

		for (int i = 0; i < articleList.size(); i++) {
			namePool[i] = articleList.get(i)[0];
			commodityGroupPool[i] = articleList.get(i)[1];
			volumePool[i] = Integer.parseInt(articleList.get(i)[3]);
		}

	}

	private int articleID;
	private String articleName;
	private String commodityGroup;
	private int volume;

	/**
	 * Konstruktor der Klasse Article. Die Attribute des Artikels werden anhand der Artikelnummer bestimmt.
	 * @param articleID Die Artikelnummer des zu erzeugenden Artikels.
	 */
	public Article(int articleID)
	{
		this.articleID = articleID;
		this.articleName = namePool[articleID];
		this.commodityGroup = commodityGroupPool[articleID];
		this.volume = volumePool[articleID];

	}

	/**
	 * @return Artikelnummer des Artikels.
	 */
	public int getArticleID()
	{
		return articleID;
	}

	/**
	 * @return Name des Artikels
	 */
	public String getName()
	{
		return articleName;
	}

	/**
	 * @return Warengruppe des Artikels
	 */
	public String getCommodityGroup()
	{
		return commodityGroup;
	}

	/**
	 * @return Volumen des Artikels
	 */
	public int getVolume()
	{
		return volume;
	}

}
