package de.hsw.warehouse.ui;


/**
 * Diese Klasse repr�sentiert einen <code>Men�eintrag</code>. Dieser wird durch einen {@link java.lang.String} und einen {@link java.lang.Runnable ausf�hrbaren Code} repr�sentiert. Der String stellt dabei den Titel des <code>Men�eintrags</code> dar. Der Code wird ausgef�hrt, wenn der <code>Men�eintrag</code> vom Nutzer ausgew�hlt wird.
 * @author Timo Rodenwaldt
 * @version 
 * @see <a href="http://bytes.com/topic/java/insights/870013-text-based-menus">http://bytes.com/topic/java/insights/870013-text-based-menus</a>
 */
public class MenuItem implements Runnable
{

	private String title;
	private Runnable action;

	/**
	 * Erstellt einen neuen <code>Men�eintrag</code>, welcher nur aus einem Titel besteht. Die Sichtbarkeit ist dabei auf diese Klasse und ihre Unterklassen beschr�nkt, da der Konstruktor nur aus der Klasse Men� aufgerufen werden darf.
	 * @param title Der Titel des <code>Men�eintrags</code>.
	 */
	protected MenuItem(String title)
	{
		this.title = title;
	}

	/**
	 * Erstellt einen neuen <code>Men�eintrag</code> mit einem Titel und ausf�hrbarem Code.
	 * @param title Der Titel des <code>Men�eintrag</code>s.
	 * @param action Der {@link java.lang.Runnable ausf�hrbare Code}.
	 */
	public MenuItem(String title, Runnable action)
	{
		this.title = title;
		this.action = action;
	}

	/**
	 * Gibt den Titel des <code>Men�eintrags</code> zur�ck.
	 * @return Der Titel des <code>Men�eintrags</code>.
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * Gibt den {@link java.lang.Runnable ausf�hrbaren Code} zur�ck.
	 * @return Der {@link java.lang.Runnable ausf�hrbare Code}.
	 */
	public Runnable getAction()
	{
		return action;
	}

	/**
	 * Setzt den {@link java.lang.Runnable ausf�hrbaren Code}.
	 * @param action Der {@link java.lang.Runnable ausf�hrbare Code}.
	 */
	public void setAction(Runnable action)
	{
		this.action = action;
	}

	/**
	 * Stellt fest, ob der <code>Men�eintrag</code> {@link java.lang.Runnable ausf�hrbaren Code} enth�lt.
	 * @return <code>true</code>, wenn der <code>Men�eintrags</code> {@link java.lang.Runnable ausf�hrbaren Code}, ansonsten <code>false</code>.
	 */
	public boolean hasAction()
	{
		return action != null;
	}

	/**
	 * F�hrt den {@link java.lang.Runnable ausf�hrbaren Code} aus und f�ngt dabei alle Exceptions ab. Diese werden als StackTrace auf der Errorkonsole ausgegeben.
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
