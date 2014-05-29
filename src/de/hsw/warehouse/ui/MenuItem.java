package de.hsw.warehouse.ui;

/**
 * Diese Klasse repräsentiert einen Menüeintrag. Dieser wird durch einen String und einen ausführbaren Code repräsentiert. Der String stellt dabei den Titel des Eintrags dar. Der Code wird ausgeführt, wenn der Menüeintrag vom Nutzer ausgewählt wird.
 * @author Timo Rodenwaldt
 * @version 
 * @see <a href="http://bytes.com/topic/java/insights/870013-text-based-menus">http://bytes.com/topic/java/insights/870013-text-based-menus</a>
 */
public class MenuItem implements Runnable
{

	private String title;
	private Runnable action;

	/**
	 * Erstellt einen neuen Menüeintrag, welcher nur aus einem Titel besteht. Die Sichtbarkeit ist dabei auf diese Klasse und ihre Unterklassen beschränkt, da der Konstruktor nur aus der Klasse Menü aufgerufen werden darf.
	 * @param title Der Titel des Menüeintrags.
	 */
	protected MenuItem(String title)
	{
		this.title = title;
	}

	/**
	 * Erstellt einen neuen Menüeintrag mit einem Titel und ausführbarem Code.
	 * @param title Der Titel des Menüeintrags.
	 * @param action Der ausführbare Code.
	 */
	MenuItem(String title, Runnable action)
	{
		this.title = title;
		this.action = action;
	}

	/**
	 * Gibt den Titel des Eintrags zurück.
	 * @return Der Titel des Menüeintrags.
	 */
	String getTitle()
	{
		return title;
	}

	/**
	 * Gibt den ausführbaren Code zurück.
	 * @return Der ausführbare Code.
	 */
	Runnable getAction()
	{
		return action;
	}

	/**
	 * Setzt den ausführbaren Code.
	 * @param action Der ausführbare Code.
	 */
	void setAction(Runnable action)
	{
		this.action = action;
	}

	/**
	 * Stellt fest, ob der Menüeintrag ausführbaren Code enthält.
	 * @return <code>true</code>, wenn der Eintrag ausführbaren Code enthält, ansonsten <code>false</code>.
	 */
	boolean hasAction()
	{
		return action != null;
	}

	/**
	 * Führt den ausführbaren Code aus und fängt dabei alle Exceptions ab. Diese werden als StackTrace auf der Errorkonsole ausgegeben.
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
