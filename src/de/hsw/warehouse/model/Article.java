package de.hsw.warehouse.model;

/**
 * Instanzen dieser Klasse stellen einen Artikel dar. Dieser besteht aus einer Artikelnummer, einem Namen, einer Warengruppe und einem Volumen.
 * @author Constantin Grote
 * @version 
 */
class Article
{
	private int articleID;
	private String articleName;
	private String commodityGroup;
	private int volume;

	/**
	 * Konstruktor der Klasse Article. Die Attribute des Artikels werden anhand der Artikelnummer bestimmt.
	 * @param articleID Die Artikelnummer des zu erzeugenden Artikels.
	 */
	Article(int articleID)
	{
		this.articleID = articleID;
		this.articleName = Assortment.getArticleName(articleID);
		this.commodityGroup = Assortment.getArticleCommodityGroup(articleID);
		this.volume = Assortment.getArticleVolume(articleID);

	}

	/**
	 * Gibt die Artikelnummer des Artikels zurück.
	 * @return Die Artikelnummer des Artikels.
	 */
	int getArticleID()
	{
		return articleID;
	}

	/**
	 * Gibt den Namen des Artikels zurück.
	 * @return Der Name des Artikels.
	 */
	String getName()
	{
		return articleName;
	}

	/**
	 * Gibt die Warengruppe des Artikels zurück.
	 * @return Die Warengruppe des Artikels.
	 */
	String getCommodityGroup()
	{
		return commodityGroup;
	}

	/**
	 * Gibt das Volumen des Artikels zurück.
	 * @return Das Volumen des Artikels.
	 */
	int getVolume()
	{
		return volume;
	}

}
