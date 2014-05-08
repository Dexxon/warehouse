package de.hsw.warehouse.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Assortment
{
	
	static class SingleArticle
	{
		private String name;
		private String commodityGroup;
		private int volume;
		
		public SingleArticle(String name, String commodityGroup, int volume)
		{
			this.name = name;
			this.commodityGroup = commodityGroup;
			this.volume = volume;
		}
	}
	
	static SingleArticle[] articlePool = initialiseArticlePool("C:\\Artikelpool.csv");
	
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
	
	public static String getName(int articleID)
	{
		return articlePool[articleID].name;
	}
	
	public static String getCommodityGroup(int ArticleID)
	{
		return articlePool[ArticleID].commodityGroup;
	}
	
	public static int getVolume(int articleID)
	{
		return articlePool[articleID].volume;
	}
	
	public static int getSize()
	{
		return articlePool.length;
	}
}
