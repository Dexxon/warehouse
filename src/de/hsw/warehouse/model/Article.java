package de.hsw.warehouse.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Article
{

	public static String[][] articlePool;
	public static String[] namePool;
	public static String[] commodityGroupPool;
	public static int[] volumePool;

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

	public Article(int articleID)
	{
		this.articleID = articleID;
		this.articleName = namePool[articleID];
		this.commodityGroup = commodityGroupPool[articleID];
		this.volume = volumePool[articleID];

	}

	public int getArticleID()
	{
		return articleID;
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
