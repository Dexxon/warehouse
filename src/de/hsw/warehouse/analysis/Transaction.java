package de.hsw.warehouse.analysis;

import java.util.GregorianCalendar;

import de.hsw.warehouse.model.Assortment;

/**
 * Diese Klasse stellt eine einzelne Bewegung dar. Dabei werden der Zeitpunkt der Bewegung sowie die Artikelnummer, die Anzahl und das Gesamtvolumen festgehalten.
 * Daraus ergibt sich, dass eine Bewegung immer nur Artikel eines Typs enthält.
 * @author Constantin Grote
 * @version
 */
public class Transaction
{

	private GregorianCalendar date;
	private int articleID, quantity, volume;

	/**
	 * Erzeugung einer Lagerbewegung.
	 * @param artikelID Die Artikelnummer der bewegten Artikel.
	 * @param quantity Die Anzahl der bewegten Artikel.
	 * @param date Das Datum der Bewegung.
	 */
	public Transaction(int artikelID, int quantity, GregorianCalendar date)
	{
		this.articleID = artikelID;
		this.quantity = quantity;
		this.date = date;
		this.volume = Assortment.getArticleVolume(articleID);
	}

	/**
	 * Gibt das Volumen der Bewegung zurück. Dieses wird errechnet, indem das Volumen eines Artikels mit der Anzahl der Artikel multipliziert wird.
	 * @return Das Volumen der Bewegung.
	 */
	int getVolume()
	{
		return volume;
	}

	/**
	 * Gibt die Artikelnummer der bewegten Artikel zurück.
	 * @return Die Artikelnummer der bewegten Artikel.
	 */
	int getArticleID()
	{
		return articleID;
	}

	/**
	 * Gibt die Anzahl der bewegten Artikel zurück.
	 * @return Die Anzahl der bewegten Artikel.
	 */
	int getQuantity()
	{
		return quantity;
	}

	/**
	 * Gibt das Datum der Bewegung zurück.
	 * @return Das Datum der Bewegung.
	 */
	GregorianCalendar getDate()
	{
		return date;
	}
}
