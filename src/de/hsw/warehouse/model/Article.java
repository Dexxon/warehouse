package de.hsw.warehouse.model;

/**
 * Instanzen dieser Klasse stellen einen Artikel dar. Dieser besteht aus einer Artikelnummer, einem Namen, einer Warengruppe und einem Volumen.
 * @author Constantin Grote
 * @version 
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
	 * @return Die Artikelnummer des Artikels.
	 */
	public int getArticleID()
	{
		return articleID;
	}

	/**
	 * @return Der Name des Artikels.
	 */
	public String getName()
	{
		return articleName;
	}

	/**
	 * @return Die Warengruppe des Artikels.
	 */
	public String getCommodityGroup()
	{
		return commodityGroup;
	}

	/**
	 * @return Das Volumen des Artikels.
	 */
	public int getVolume()
	{
		return volume;
	}

}
