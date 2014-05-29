package de.hsw.warehouse.model;

/**
 * Diese Klasse stellt einen Artikel dar. Dieser besteht aus einer Artikelnummer, einem Namen, einer
 * Warengruppe und einem Volumen.
 * 
 * @author Constantin Grote
 * @version 29.05.2013
 */
public class Article
{
	private int articleID;
	private String articleName;
	private String commodityGroup;
	private int volume;

	/**
	 * Erstellt einen Artikel. Die Attribute des Artikels werden anhand der Artikelnummer bestimmt.
	 * 
	 * @param articleID Die Artikelnummer des zu erzeugenden Artikels.
	 */
	public Article(int articleID)
	{
		this.articleID = articleID;
		this.articleName = Assortment.getArticleName(articleID);
		this.commodityGroup = Assortment.getArticleCommodityGroup(articleID);
		this.volume = Assortment.getArticleVolume(articleID);

	}

	/**
	 * Gibt die Artikelnummer des Artikels zurück.
	 * 
	 * @return Die Artikelnummer des Artikels.
	 */
	public int getArticleID()
	{
		return this.articleID;
	}

	/**
	 * Gibt den Namen des Artikels zurück.
	 * 
	 * @return Der Name des Artikels.
	 */
	public String getName()
	{
		return this.articleName;
	}

	/**
	 * Gibt die Warengruppe des Artikels zurück.
	 * 
	 * @return Die Warengruppe des Artikels.
	 */
	public String getCommodityGroup()
	{
		return this.commodityGroup;
	}

	/**
	 * Gibt das Volumen des Artikels zurück.
	 * 
	 * @return Das Volumen des Artikels.
	 */
	public int getVolume()
	{
		return this.volume;
	}

}
