package de.hsw.warehouse.model;

public class NotEnoughSpaceException extends Exception
{
	private static final long serialVersionUID = -3557633092630798000L;

	public NotEnoughSpaceException(){
		super();
	}
	
	public NotEnoughSpaceException(String message){
		super(message);
	}
}
