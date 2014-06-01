package de.hsw_hameln.warehouse.ui;

/**
 * Diese Klasse repraesentiert einen Menueeintrag. Dieser wird durch einen {@link java.lang.String}
 * und einen {@link java.lang.Runnable ausfuehrbaren Code} repraesentiert. Der String stellt dabei
 * den Titel des Menueeintrags dar. Der Code wird ausgefuehrt, wenn der Menueeintrag vom Nutzer
 * ausgewaehlt wird.
 * 
 * @author Timo Rodenwaldt
 * @version 30.05.2014
 * @see <a
 *      href="http://bytes.com/topic/java/insights/870013-text-based-menus">http://bytes.com/topic/java/insights/870013-text-based-menus</a>
 */
public class MenuItem implements Runnable
{
	private String title;
	private Runnable action;

	/**
	 * Erstellt einen neuen Menueeintrag, welcher nur aus einem Titel besteht. Die Sichtbarkeit ist
	 * dabei auf diese Klasse und ihre Unterklassen beschraenkt, da der Konstruktor nur aus der
	 * Klasse Menue aufgerufen werden darf.
	 * 
	 * @param title Der Titel des Menueeintrags.
	 */
	protected MenuItem(String title)
	{
		this.title = title;
	}

	/**
	 * Erstellt einen neuen Menueeintrag mit einem Titel und ausfuehrbarem Code.
	 * 
	 * @param title Der Titel des Menueeintrags.
	 * @param action Der {@link java.lang.Runnable ausfuehrbare Code}.
	 */
	public MenuItem(String title, Runnable action)
	{
		this.title = title;
		this.action = action;
	}

	/**
	 * Gibt den Titel des Menueeintrags zurueck.
	 * 
	 * @return Der Titel des Menueeintrags.
	 */
	public String getTitle()
	{
		return this.title;
	}

	/**
	 * Gibt den {@link java.lang.Runnable ausfuehrbaren Code} zurueck.
	 * 
	 * @return Der {@link java.lang.Runnable ausfuehrbare Code}.
	 */
	public Runnable getAction()
	{
		return this.action;
	}

	/**
	 * Setzt den {@link java.lang.Runnable ausfuehrbaren Code}.
	 * 
	 * @param action Der {@link java.lang.Runnable ausfuehrbare Code}.
	 */
	public void setAction(Runnable action)
	{
		this.action = action;
	}

	/**
	 * Stellt fest, ob der Menueeintrag {@link java.lang.Runnable ausfuehrbaren Code} enthaelt.
	 * 
	 * @return true, wenn der Menueeintrags {@link java.lang.Runnable ausfuehrbaren Code}, ansonsten
	 *         false.
	 */
	public boolean hasAction()
	{
		return this.action != null;
	}

	/**
	 * Fuehrt den {@link java.lang.Runnable ausfuehrbaren Code} aus und faengt dabei alle Exceptions
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
