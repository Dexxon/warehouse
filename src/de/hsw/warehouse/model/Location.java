package de.hsw.warehouse.model;

import java.util.ArrayList;

public class Location
{

	private int capacity;
	private int load;
	private ArrayList<Article> article = new ArrayList<Article>();

	public Location(int volumen)
	{
		this.capacity = volumen;
	}

	public int getLoad()
	{
		return load;
	}

	public void setLoad(int load)
	{
		this.load = load;
	}

	public int getCapacity()
	{
		return capacity;
	}

	public ArrayList<Article> getArticle()
	{
		return article;
	}

	public int getFreeSpace()
	{
		return this.capacity - this.load;
	}

	public void addArticle(Article article)
	{
		this.article.add(article);
		this.load += article.getVolume();
	}

	public void removeArticlel(Article article)
	{
		if (this.article.contains(article)) {
			this.article.remove(this.article.indexOf(article));
			this.load -= article.getVolume();
		}
	}
}
