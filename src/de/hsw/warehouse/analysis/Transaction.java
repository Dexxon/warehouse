package de.hsw.warehouse.analysis;

import java.util.GregorianCalendar;

import de.hsw.warehouse.model.Assortment;

/**
 * Diese Klasse stellt eine einzelne Bewegung dar. Dabei werden der Zeitpunkt der Bewegung sowie die
 * {@link de.hsw.warehouse.model.Article Artikel}nummer, die Anzahl und das Gesamtvolumen
 * festgehalten. Daraus ergibt sich, dass eine Bewegung immer nur
 * {@link de.hsw.warehouse.model.Article Artikel} eines Typs enthält.
 * 
 * @author Constantin Grote
 * @version
 */
public class Transaction
{
	private GregorianCalendar date;
	private int articleID, quantity, volume;

	/**
	 * Erzeugung einer Lagerbewegung.
	 * 
	 * @param articleID Die {@link de.hsw.warehouse.model.Article Artikel}nummer der bewegten
	 *            {@link de.hsw.warehouse.model.Article Artikel}.
	 * @param quantity Die Anzahl der bewegten {@link de.hsw.warehouse.model.Article Artikel}.
	 * @param date Das Datum der Bewegung.
	 */
	public Transaction(int articleID, int quantity, GregorianCalendar date)
	{
		this.articleID = articleID;
		this.quantity = quantity;
		this.date = date;
		this.volume = Assortment.getArticleVolume(this.articleID) * this.quantity;
	}

	/**
	 * Gibt das Volumen der Bewegung zurück. Dieses wird errechnet, indem das Volumen eines
	 * {@link de.hsw.warehouse.model.Article Artikels} mit der Anzahl der
	 * {@link de.hsw.warehouse.model.Article Artikel} multipliziert wird.
	 * 
	 * @return Das Volumen der Bewegung.
	 */
	public int getVolume()
	{
		return this.volume;
	}

	/**
	 * Gibt die {@link de.hsw.warehouse.model.Article Artikel}nummer der bewegten
	 * {@link de.hsw.warehouse.model.Article Artikel} zurück.
	 * 
	 * @return Die {@link de.hsw.warehouse.model.Article Artikel}nummer der bewegten
	 *         {@link de.hsw.warehouse.model.Article Artikel}.
	 */
	public int getArticleID()
	{
		return this.articleID;
	}

	/**
	 * Gibt die Anzahl der bewegten {@link de.hsw.warehouse.model.Article Artikel} zurück.
	 * 
	 * @return Die Anzahl der bewegten {@link de.hsw.warehouse.model.Article Artikel}.
	 */
	public int getQuantity()
	{
		return this.quantity;
	}

	/**
	 * Gibt das Datum der Bewegung zurück.
	 * 
	 * @return Das Datum der Bewegung.
	 */
	public GregorianCalendar getDate()
	{
		return this.date;
	}
}
