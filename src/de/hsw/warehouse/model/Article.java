package de.hsw.warehouse.model;

public class Article {
	
	//Statische Arrays, dienen der Zuordnung von ArtikelNr zu Eigenschaften (müssen unbedingt gleich lang sein)
	public static int[] volumeArray = {3,6,5};
	public static String[] nameArray = {"TestArtikel0", "TestArtikel1", "TestArtikel2"};
	public static String[] commodityGroupArray = {"TestWarengruppe0", "TestWarengruppe1",  "TestWarengruppe2"};
	
	private int articleNumber;
	private String articleName;
	private String commodityGroup;
	private int volume;
	
	
	public Article(int articleNumber)
	{
		this.articleNumber = articleNumber;
		this.articleName = nameArray[articleNumber];
		this.commodityGroup = commodityGroupArray[articleNumber];
		this.volume = volumeArray[articleNumber];
	}

	public int getArticleNumber() 
	{
		return articleNumber;
	}

	public String getName() 
	{
		return articleName;
	}

	public String getCommodityGroup() 
	{
		return commodityGroup;
	}	
	
	public int getVolume() 
	{
		return volume;
	}

}
	