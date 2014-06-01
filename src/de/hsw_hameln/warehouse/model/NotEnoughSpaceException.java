package de.hsw_hameln.warehouse.model;

/**
 * Diese Exception wird geworfen, wenn nicht genug Platz fuer einen Artikel vorhanden ist.
 * 
 * @author Constantin Grote
 * @version 30.05.2014
 */
public class NotEnoughSpaceException extends Exception
{
	private static final long serialVersionUID = -3557633092630798000L;

	/**
	 * Konstruktor fuer die Exception.
	 */
	public NotEnoughSpaceException()
	{
		super();
	}
}
