package de.hsw.warehouse.model;

/**
 * Diese Exception wird geworfen, wenn nicht gen�gend Artikel vorhanden sind.
 * @author Constantin
 *
 */
public class NotEnoughArticleException extends Exception
{
	private static final long serialVersionUID = 2350835064311252083L;

	/**
	 * Konstruktor f�r die Exception.
	 */
	public NotEnoughArticleException(){
		super();
	}
	
	public NotEnoughArticleException(String message){
		super(message);
	}
}
