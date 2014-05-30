package de.hsw.warehouse.model;

/**
 * Diese Exception wird geworfen, wenn nicht genug Platz für einen Artikel vorhanden ist.
 * 
 * @author Constantin Grote
 * @version 29.05.2014
 */
public class NotEnoughSpaceException extends Exception
{
	private static final long serialVersionUID = -3557633092630798000L;

	/**
	 * Konstruktor für die Exception.
	 */
	public NotEnoughSpaceException()
	{
		super();
	}
}
