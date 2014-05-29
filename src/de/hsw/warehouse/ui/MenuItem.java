package de.hsw.warehouse.ui;

/**
 * Diese Klasse repr�sentiert einen Men�eintrag. Dieser wird durch einen String und einen ausf�hrbaren Code repr�sentiert. Der String stellt dabei den Titel des Eintrags dar. Der Code wird ausgef�hrt, wenn der Men�eintrag vom Nutzer ausgew�hlt wird.
 * @author Timo Rodenwaldt
 * @version 
 * @see <a href="http://bytes.com/topic/java/insights/870013-text-based-menus">http://bytes.com/topic/java/insights/870013-text-based-menus</a>
 */
public class MenuItem implements Runnable
{

	private String title;
	private Runnable action;

	/**
	 * Erstellt einen neuen Men�eintrag, welcher nur aus einem Titel besteht. Die Sichtbarkeit ist dabei auf diese Klasse und ihre Unterklassen beschr�nkt, da der Konstruktor nur aus der Klasse Men� aufgerufen werden darf.
	 * @param title Der Titel des Men�eintrags.
	 */
	protected MenuItem(String title)
	{
		this.title = title;
	}

	/**
	 * Erstellt einen neuen Men�eintrag mit einem Titel und ausf�hrbarem Code.
	 * @param title Der Titel des Men�eintrags.
	 * @param action Der ausf�hrbare Code.
	 */
	MenuItem(String title, Runnable action)
	{
		this.title = title;
		this.action = action;
	}

	/**
	 * Gibt den Titel des Eintrags zur�ck.
	 * @return Der Titel des Men�eintrags.
	 */
	String getTitle()
	{
		return title;
	}

	/**
	 * Gibt den ausf�hrbaren Code zur�ck.
	 * @return Der ausf�hrbare Code.
	 */
	Runnable getAction()
	{
		return action;
	}

	/**
	 * Setzt den ausf�hrbaren Code.
	 * @param action Der ausf�hrbare Code.
	 */
	void setAction(Runnable action)
	{
		this.action = action;
	}

	/**
	 * Stellt fest, ob der Men�eintrag ausf�hrbaren Code enth�lt.
	 * @return <code>true</code>, wenn der Eintrag ausf�hrbaren Code enth�lt, ansonsten <code>false</code>.
	 */
	boolean hasAction()
	{
		return action != null;
	}

	/**
	 * F�hrt den ausf�hrbaren Code aus und f�ngt dabei alle Exceptions ab. Diese werden als StackTrace auf der Errorkonsole ausgegeben.
	 */
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
