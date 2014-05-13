package de.hsw.warehouse.model;

/**
 * Diese Klasse beschreibt einen einzigen Artikel sowie den statisch definierten Artikelpool.
 * @author Constantin Grote
 *
 */
public class Article
{
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
		this.articleName = Assortment.getName(articleID);
		this.commodityGroup = Assortment.getCommodityGroup(articleID);
		this.volume = Assortment.getVolume(articleID);

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
