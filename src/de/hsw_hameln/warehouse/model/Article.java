package de.hsw_hameln.warehouse.model;

/**
 * Diese Klasse stellt einen Artikel dar. Dieser besteht aus einer Artikelnummer, einem Namen, einer
 * Warengruppe und einem Volumen.
 * 
 * @author Constantin Grote
 * @version 29.05.2014
 */
public class Article
{
	protected int articleID;
	protected String articleName;
	protected String commodityGroup;
	protected int volume;

	/**
	 * Wird f�r die Vererbung zu der Klasse "{@link de.hsw_hameln.warehouse.Assortment.SingleArticle}" ben�tigt. Initialisiert alle Werte mit einem Standardwert.
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
	 * Gibt die Artikelnummer des Artikels zur�ck.
	 * 
	 * @return Die Artikelnummer des Artikels.
	 */
	public int getArticleID()
	{
		return this.articleID;
	}

	/**
	 * Gibt den Namen des Artikels zur�ck.
	 * 
	 * @return Der Name des Artikels.
	 */
	public String getName()
	{
		return this.articleName;
	}

	/**
	 * Gibt die Warengruppe des Artikels zur�ck.
	 * 
	 * @return Die Warengruppe des Artikels.
	 */
	public String getCommodityGroup()
	{
		return this.commodityGroup;
	}

	/**
	 * Gibt das Volumen des Artikels zur�ck.
	 * 
	 * @return Das Volumen des Artikels.
	 */
	public int getVolume()
	{
		return this.volume;
	}

}