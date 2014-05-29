package de.hsw.warehouse.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Diese Klasse repräsentiert ein Menü. Dieses kann mehrere {@link de.hsw.warehouse.ui.MenuItem Menüeintrage} beinhalten.<br>
 * Da ein Menü als einzelne Zeile in einem anderen Menü angezeigt wird, ist es gleichzeitig ein Menüeintrag und kann eine Unterklasse der Klasse {@link de.hsw.warehouse.ui.MenuItem} sein.
 * @author Timo Rodenwaldt
 * @version 
 * @see <a href="http://bytes.com/topic/java/insights/870013-text-based-menus">http://bytes.com/topic/java/insights/870013-text-based-menus</a>
 */
public class Menu extends MenuItem
{
	ArrayList<MenuItem> menuItems;
	String title;

	/**
	 * Der "Beenden"-Eintrag. Wird dieser ausgewählt, beendet sich das Programm normal.
	 */
	static final MenuItem exit = new MenuItem("Beenden", new Runnable()
	{
		public void run()
		{
			System.exit(0);
		}
	});

	/**
	 * Der "Zurück"-Eintrag. Dieser hat keinen ausführbaren Code und wird mit dem Konstruktor {@link de.hsw.warehouse.ui.MenuItem#MenuItem(String) MenuItem(String)} erstellt.
	 * Aus diesem Grund exisitiert dieser Konstruktor und ist nur in dieser Klasse und der Oberklasse sichtbar, damit er nicht woanders aufgerufen werden kann.
	 */
	static final MenuItem back = new MenuItem("Zurück");

	Menu(String title, MenuItem... menuItems)
	{
		this(title, false, true, menuItems);
	}

	/**
	 * Erstellt ein Menü mit den angegebenen Einträgen und dem angegebenen Titel.
	 * @param title Der Titel des Menüs.
	 * @param back Gibt an, ob ein {@link de.hsw.warehouse.ui.Menu#back "Zurück"-Eintrag} vorhanden sein soll.
	 * @param exit Gibt an, ob ein {@link de.hsw.warehouse.ui.Menu#exit "Beenden"-Eintrag} vorhanden sein soll.
	 * @param menuItems Die hinzuzufügenden {@link de.hsw.warehouse.ui.MenuItem Menüeinträge}.
	 */
	Menu(String title, boolean back, boolean exit, MenuItem... menuItems)
	{
		super(title);
		setAction(this);
		this.menuItems = new ArrayList<MenuItem>(Arrays.asList(menuItems));
		if (back)
			this.menuItems.add(0, Menu.back);
		if (exit)
			this.menuItems.add(0, Menu.exit);

	}

	/**
	 * Gibt alle {@link de.hsw.warehouse.ui.MenuItem Menüeinträge} mit vorangestellter Nummerierung zeilenweise auf der Konsole aus.
	 */
	void display()
	{
		int option = 0;
		System.out.println("\n" + getTitle() + ":");
		for (MenuItem item : menuItems) {
			System.out.println((option++) + ": " + item.getTitle());
		}
		System.out.print("Wählen Sie eine Option: ");
	}

	/**
	 * Fragt den Benutzer nach einer Eingabe, mit der er einen  {@link de.hsw.warehouse.ui.MenuItem Menüeintrag} auswählen kann.
	 * @return Der ausgewählte  {@link de.hsw.warehouse.ui.MenuItem Menüeintrag}.
	 * @throws IOException Wenn bei der Eingabe Fehler auftreten.
	 */
	private MenuItem prompt() throws IOException
	{
		int option;
		while (true) {
			display();
			BufferedReader input = new BufferedReader(new InputStreamReader(
					System.in));
			String line = input.readLine();
			try {
				option = Integer.parseInt(line);
				if (option >= 0 && option < menuItems.size()) {
					return menuItems.get(option);
				}
			} catch (NumberFormatException e) {}
			System.err.println(line + " ist keine gültige Option\n");

		}
	}

	/**
	 * Der ausführbare Code des Menüs.<br>
	 * Der Benutzer wird zunächst nach der Eingabe gefragt. Wenn ein  {@link de.hsw.warehouse.ui.MenuItem Menüeintrag} ausgewählt wurde, wird dessen Code ausgeführt.
	 * Handelt es sich bei dem  {@link de.hsw.warehouse.ui.MenuItem Menüeintrag} um ein Untermenü, wird diese Methode für jenes Menü ausgeführt. Handelt es sich um einen  {@link de.hsw.warehouse.ui.MenuItem Menüeintrag}, wird dessen Code ausgeführt.
	 * Ist dieser Code nicht vorhanden (das bedeutet es handelt sich um den "Zurück"-Eintrag), wird die Methode verlassen und in das Obermenü zurückgekehrt.
	 */
	@Override
	public void run()
	{
		try {
			for (MenuItem item = prompt(); item.hasAction(); item = prompt()) {
				item.run();
			}

		} catch (Throwable t) {
			t.printStackTrace(System.out);
		}
	}

	/**
	 * Fügt dem Menü einen  {@link de.hsw.warehouse.ui.MenuItem Menüeintrag} hinzu.
	 * @param menueItem Der hinzuzufügende  {@link de.hsw.warehouse.ui.MenuItem Menüeintrag}.
	 * @return
	 */
	Menu add(MenuItem menueItem)
	{
		menuItems.add(menueItem);
		return this;
	}
}
