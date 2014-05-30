package de.hsw.warehouse.ui;

/**
 * Diese Klasse repräsentiert einen Menüeintrag. Dieser wird durch einen {@link java.lang.String}
 * und einen {@link java.lang.Runnable ausführbaren Code} repräsentiert. Der String stellt dabei den
 * Titel des Menüeintrags dar. Der Code wird ausgeführt, wenn der Menüeintrag vom Nutzer ausgewählt
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
	 * Erstellt einen neuen Menüeintrag, welcher nur aus einem Titel besteht. Die Sichtbarkeit ist
	 * dabei auf diese Klasse und ihre Unterklassen beschränkt, da der Konstruktor nur aus der
	 * Klasse Menü aufgerufen werden darf.
	 * 
	 * @param title Der Titel des Menüeintrags.
	 */
	protected MenuItem(String title)
	{
		this.title = title;
	}

	/**
	 * Erstellt einen neuen Menüeintrag mit einem Titel und ausführbarem Code.
	 * 
	 * @param title Der Titel des Menüeintrags.
	 * @param action Der {@link java.lang.Runnable ausführbare Code}.
	 */
	public MenuItem(String title, Runnable action)
	{
		this.title = title;
		this.action = action;
	}

	/**
	 * Gibt den Titel des Menüeintrags zurück.
	 * 
	 * @return Der Titel des Menüeintrags.
	 */
	public String getTitle()
	{
		return this.title;
	}

	/**
	 * Gibt den {@link java.lang.Runnable ausführbaren Code} zurück.
	 * 
	 * @return Der {@link java.lang.Runnable ausführbare Code}.
	 */
	public Runnable getAction()
	{
		return this.action;
	}

	/**
	 * Setzt den {@link java.lang.Runnable ausführbaren Code}.
	 * 
	 * @param action Der {@link java.lang.Runnable ausführbare Code}.
	 */
	public void setAction(Runnable action)
	{
		this.action = action;
	}

	/**
	 * Stellt fest, ob der Menüeintrag {@link java.lang.Runnable ausführbaren Code} enthält.
	 * 
	 * @return true, wenn der Menüeintrags {@link java.lang.Runnable ausführbaren Code}, ansonsten
	 *         false.
	 */
	public boolean hasAction()
	{
		return this.action != null;
	}

	/**
	 * Führt den {@link java.lang.Runnable ausführbaren Code} aus und fängt dabei alle Exceptions
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
