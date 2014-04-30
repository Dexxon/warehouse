package de.hsw.warehouse.model;

public class NotEnoughArticleException extends Exception
{
	private static final long serialVersionUID = 2350835064311252083L;

	public NotEnoughArticleException(){
		super();
	}
	
	public NotEnoughArticleException(String message){
		super(message);
	}
}
