package de.hsw.warehouse.analysis;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import de.hsw.warehouse.model.Assortment;

public class Transaction
{

	private GregorianCalendar date;
	private int articleID, quantity, volume;

	public Transaction(int artikelID, int quantity, GregorianCalendar date)
	{
		this.articleID = artikelID;
		this.quantity = quantity;
		this.date = date;
		this.volume = Assortment.getArticleVolume(articleID);
	}

	int getVolume()
	{
		return volume;
	}

	int getArticleID()
	{
		return articleID;
	}

	int getQuantity()
	{
		return quantity;
	}

	GregorianCalendar getDate()
	{
		return date;
	}

	@Override
	public String toString()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.YY HH:mm");
		return sdf.format(this.date.getTime()) + ":\t"
				+ String.format("%3d", this.quantity) + "\t"
				+ String.format("%3d", this.articleID);
	}
}
