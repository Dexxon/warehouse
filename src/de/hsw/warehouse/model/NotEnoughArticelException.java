package de.hsw.warehouse.model;

public class NotEnoughArticelException extends Exception
{
	private static final long serialVersionUID = 2350835064311252083L;

	public NotEnoughArticelException(String message){
		super(message);
	}
}
