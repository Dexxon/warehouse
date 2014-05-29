package de.hsw.warehouse.ui;


/**
 * Diese Klasse repräsentiert einen <code>Menüeintrag</code>. Dieser wird durch einen {@link java.lang.String} und einen {@link java.lang.Runnable ausführbaren Code} repräsentiert. Der String stellt dabei den Titel des <code>Menüeintrags</code> dar. Der Code wird ausgeführt, wenn der <code>Menüeintrag</code> vom Nutzer ausgewählt wird.
 * @author Timo Rodenwaldt
 * @version 
 * @see <a href="http://bytes.com/topic/java/insights/870013-text-based-menus">http://bytes.com/topic/java/insights/870013-text-based-menus</a>
 */
public class MenuItem implements Runnable
{

	private String title;
	private Runnable action;

	/**
	 * Erstellt einen neuen <code>Menüeintrag</code>, welcher nur aus einem Titel besteht. Die Sichtbarkeit ist dabei auf diese Klasse und ihre Unterklassen beschränkt, da der Konstruktor nur aus der Klasse Menü aufgerufen werden darf.
	 * @param title Der Titel des <code>Menüeintrags</code>.
	 */
	protected MenuItem(String title)
	{
		this.title = title;
	}

	/**
	 * Erstellt einen neuen <code>Menüeintrag</code> mit einem Titel und ausführbarem Code.
	 * @param title Der Titel des <code>Menüeintrag</code>s.
	 * @param action Der {@link java.lang.Runnable ausführbare Code}.
	 */
	public MenuItem(String title, Runnable action)
	{
		this.title = title;
		this.action = action;
	}

	/**
	 * Gibt den Titel des <code>Menüeintrags</code> zurück.
	 * @return Der Titel des <code>Menüeintrags</code>.
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * Gibt den {@link java.lang.Runnable ausführbaren Code} zurück.
	 * @return Der {@link java.lang.Runnable ausführbare Code}.
	 */
	public Runnable getAction()
	{
		return action;
	}

	/**
	 * Setzt den {@link java.lang.Runnable ausführbaren Code}.
	 * @param action Der {@link java.lang.Runnable ausführbare Code}.
	 */
	public void setAction(Runnable action)
	{
		this.action = action;
	}

	/**
	 * Stellt fest, ob der <code>Menüeintrag</code> {@link java.lang.Runnable ausführbaren Code} enthält.
	 * @return <code>true</code>, wenn der <code>Menüeintrags</code> {@link java.lang.Runnable ausführbaren Code}, ansonsten <code>false</code>.
	 */
	public boolean hasAction()
	{
		return action != null;
	}

	/**
	 * Führt den {@link java.lang.Runnable ausführbaren Code} aus und fängt dabei alle Exceptions ab. Diese werden als StackTrace auf der Errorkonsole ausgegeben.
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
