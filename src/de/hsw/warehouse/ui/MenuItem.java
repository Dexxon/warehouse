package de.hsw.warehouse.ui;

/**
 * Diese Klasse repr�sentiert einen Men�eintrag. Dieser wird durch einen {@link java.lang.String}
 * und einen {@link java.lang.Runnable ausf�hrbaren Code} repr�sentiert. Der String stellt dabei den
 * Titel des Men�eintrags dar. Der Code wird ausgef�hrt, wenn der Men�eintrag vom Nutzer ausgew�hlt
 * wird.
 * 
 * @author Timo Rodenwaldt
 * @version 29.05.2014
 * @see <a
 *      href="http://bytes.com/topic/java/insights/870013-text-based-menus">http://bytes.com/topic/java/insights/870013-text-based-menus</a>
 */
public class MenuItem implements Runnable
{
	private String title;
	private Runnable action;

	/**
	 * Erstellt einen neuen Men�eintrag, welcher nur aus einem Titel besteht. Die Sichtbarkeit ist
	 * dabei auf diese Klasse und ihre Unterklassen beschr�nkt, da der Konstruktor nur aus der
	 * Klasse Men� aufgerufen werden darf.
	 * 
	 * @param title Der Titel des Men�eintrags.
	 */
	protected MenuItem(String title)
	{
		this.title = title;
	}

	/**
	 * Erstellt einen neuen Men�eintrag mit einem Titel und ausf�hrbarem Code.
	 * 
	 * @param title Der Titel des Men�eintrags.
	 * @param action Der {@link java.lang.Runnable ausf�hrbare Code}.
	 */
	public MenuItem(String title, Runnable action)
	{
		this.title = title;
		this.action = action;
	}

	/**
	 * Gibt den Titel des Men�eintrags zur�ck.
	 * 
	 * @return Der Titel des Men�eintrags.
	 */
	public String getTitle()
	{
		return this.title;
	}

	/**
	 * Gibt den {@link java.lang.Runnable ausf�hrbaren Code} zur�ck.
	 * 
	 * @return Der {@link java.lang.Runnable ausf�hrbare Code}.
	 */
	public Runnable getAction()
	{
		return this.action;
	}

	/**
	 * Setzt den {@link java.lang.Runnable ausf�hrbaren Code}.
	 * 
	 * @param action Der {@link java.lang.Runnable ausf�hrbare Code}.
	 */
	public void setAction(Runnable action)
	{
		this.action = action;
	}

	/**
	 * Stellt fest, ob der Men�eintrag {@link java.lang.Runnable ausf�hrbaren Code} enth�lt.
	 * 
	 * @return true, wenn der Men�eintrags {@link java.lang.Runnable ausf�hrbaren Code}, ansonsten
	 *         false.
	 */
	public boolean hasAction()
	{
		return this.action != null;
	}

	/**
	 * F�hrt den {@link java.lang.Runnable ausf�hrbaren Code} aus und f�ngt dabei alle Exceptions
	 * ab. Diese werden als StackTrace auf der Errorkonsole ausgegeben.
	 */
	@Override
	public void run()
	{
		try {
			this.action.run();
			Thread.sleep(500);
		} catch (Throwable t) {
			t.printStackTrace(System.err);
		}
	}

}
