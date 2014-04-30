package de.hsw.warehouse.model;

import java.util.GregorianCalendar;

public class Transaction
{
	
	private GregorianCalendar date;
	private int articleID, quantity;

	public Transaction(int artikelID, int quantity, GregorianCalendar date)
	{
		this.articleID = artikelID;
		this.quantity = quantity;
		this.date = date;
	}

	public int getVolume()
	{
		return Article.volumePool[articleID] * quantity;
	}

	public int getArticleID()
	{
		return articleID;
	}

	public int getQuantity()
	{
		return quantity;
	}

	public GregorianCalendar getDate()
	{
		return date;
	}
}
