package de.hsw_hameln.warehouse.model;

/**
 * Diese Klasse stellt einen Artikel dar. Dieser besteht aus einer Artikelnummer, einem Namen, einer
 * Warengruppe und einem Volumen.
 * 
 * @author Constantin Grote
 * @version 31.05.2014
 */
public class Article
{
	protected int articleID;
	protected String articleName;
	protected String commodityGroup;
	protected int volume;

	/**
	 * Standardkonstruktor. Initialisiert alle Felder mit einem Standardwert.
	 */
	protected Article()
	{
		this.articleID = 0;
		this.articleName = "";
		this.commodityGroup = "";
		this.volume = 0;
	}

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
	 * Gibt die Artikelnummer des Artikels zurueck.
	 * 
	 * @return Die Artikelnummer des Artikels.
	 */
	public int getArticleID()
	{
		return this.articleID;
	}

	/**
	 * Gibt den Namen des Artikels zurueck.
	 * 
	 * @return Der Name des Artikels.
	 */
	public String getName()
	{
		return this.articleName;
	}

	/**
	 * Gibt die Warengruppe des Artikels zurueck.
	 * 
	 * @return Die Warengruppe des Artikels.
	 */
	public String getCommodityGroup()
	{
		return this.commodityGroup;
	}

	/**
	 * Gibt das Volumen des Artikels zurueck.
	 * 
	 * @return Das Volumen des Artikels.
	 */
	public int getVolume()
	{
		return this.volume;
	}

}
