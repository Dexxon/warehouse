package de.hsw.warehouse.ui;

public class MenuItem implements Runnable
{

	private String title;
	private Runnable action;

	protected MenuItem(String title)
	{
		this.title = title;
	}

	MenuItem(String title, Runnable action)
	{
		this.title = title;
		this.action = action;
	}

	String getTitle()
	{
		return title;
	}

	Runnable getAction()
	{
		return action;
	}

	void setAction(Runnable action)
	{
		this.action = action;
	}

	boolean hasAction()
	{
		return action != null;
	}

	@Override
	public void run()
	{
		try {
			action.run();
		} catch (Throwable t) {
			t.printStackTrace(System.err);
		}
	}

}
