package de.hsw.warehouse.model;

/**
 * Diese Exception wird geworfen, wenn nicht genug Platz f�r einen Artikel vorhanden ist.
 * @author Constantin
 * @version
 */
public class NotEnoughSpaceException extends Exception
{
	private static final long serialVersionUID = -3557633092630798000L;

	/**
	 * Konstruktor f�r die Exception.
	 */
	public NotEnoughSpaceException(){
		super();
	}
	
	public NotEnoughSpaceException(String message){
		super(message);
	}
}
