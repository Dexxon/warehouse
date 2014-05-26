package de.hsw.warehouse.model;

/**
 * Diese Exception wird geworfen, wenn nicht genügend Artikel vorhanden sind.
 * @author Constantin
 *
 */
public class NotEnoughArticleException extends Exception
{
	private static final long serialVersionUID = 2350835064311252083L;

	/**
	 * Konstruktor für die Exception.
	 */
	public NotEnoughArticleException(){
		super();
	}
	
	public NotEnoughArticleException(String message){
		super(message);
	}
}
