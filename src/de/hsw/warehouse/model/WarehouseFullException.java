package de.hsw.warehouse.model;

public class WarehouseFullException extends Exception
{
	private static final long serialVersionUID = -3557633092630798000L;

	public WarehouseFullException(String message){
		super(message);
	}
}
