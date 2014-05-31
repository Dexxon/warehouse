package de.hsw_hameln.warehouse.model;

/**
 * Diese Exception wird geworfen, wenn nicht genügend Artikel vorhanden sind.
 * 
 * @author Constantin Grote
 * @version 29.05.2014
 */
public class NotEnoughArticleException extends Exception
{
	private static final long serialVersionUID = 2350835064311252083L;

	/**
	 * Konstruktor für die Exception.
	 */
	public NotEnoughArticleException()
	{
		super();
	}
}
