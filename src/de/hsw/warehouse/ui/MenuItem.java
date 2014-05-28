package de.hsw.warehouse.ui;

public class MenuItem implements Runnable
{

	private String title;
	private Runnable action;

	protected MenuItem(String title)
	{
		this.title = title;
	}

	public MenuItem(String title, Runnable action)
	{
		this.title = title;
		this.action = action;
	}

	public String getTitle()
	{
		return title;
	}

	public Runnable getAction()
	{
		return action;
	}

	public void setAction(Runnable action)
	{
		this.action = action;
	}

	public boolean hasAction()
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
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
