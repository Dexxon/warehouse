package de.hsw_hameln.warehouse.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Diese Klasse repraesentiert ein Menue. Dieses kann mehrere
 * {@link de.hsw_hameln.warehouse.ui.MenuItem Menueeintrage} beinhalten.<br>
 * Da ein Menue als einzelne Zeile in einem anderen Menue angezeigt wird, ist es gleichzeitig ein
 * Menueeintrag und kann eine Unterklasse der Klasse {@link de.hsw_hameln.warehouse.ui.MenuItem}
 * sein.
 * 
 * @author Timo Rodenwaldt
 * @version 29.05.2014
 * @see <a
 *      href="http://bytes.com/topic/java/insights/870013-text-based-menus">http://bytes.com/topic/java/insights/870013-text-based-menus</a>
 */
public class Menu extends MenuItem
{
	ArrayList<MenuItem> menuItems;

	/**
	 * Der "Beenden"-Eintrag. Wird dieser ausgewaehlt, beendet sich das Programm normal.
	 */
	public static final MenuItem exit = new MenuItem("Beenden", new Runnable()
	{
		public void run()
		{
			System.exit(0);
		}
	});

	/**
	 * Der "Zurueck"-Eintrag. Dieser hat keinen {@link java.lang.Runnable ausfuehrbaren Code} und
	 * wird mit dem Konstruktor {@link de.hsw_hameln.warehouse.ui.MenuItem#MenuItem(String)
	 * MenuItem(String)} erstellt. Aus diesem Grund exisitiert dieser Konstruktor und ist nur in
	 * dieser Klasse und der Oberklasse sichtbar, damit er nicht woanders aufgerufen werden kann.
	 */
	public static final MenuItem back = new MenuItem("Zurueck");

	/**
	 * Erstellt ein Menue mit den angegebenen Eintraegen und dem angegebenen Titel.
	 * 
	 * @param title Der Titel des Menues.
	 * @param back Gibt an, ob ein {@link de.hsw_hameln.warehouse.ui.Menu#back "Zurueck"-Eintrag}
	 *            vorhanden sein soll.
	 * @param exit Gibt an, ob ein {@link de.hsw_hameln.warehouse.ui.Menu#exit "Beenden"-Eintrag}
	 *            vorhanden sein soll.
	 * @param menuItems Die hinzuzufuegenden {@link de.hsw_hameln.warehouse.ui.MenuItem
	 *            Menueeintraege}.
	 */
	public Menu(String title, boolean back, boolean exit, MenuItem... menuItems)
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
	 * Gibt alle {@link de.hsw_hameln.warehouse.ui.MenuItem Menueeintraege} mit vorangestellter
	 * Nummerierung zeilenweise auf der Konsole aus.
	 */
	public void display()
	{
		int option = 0;
		System.out.println("\n" + getTitle() + ":");

		for (MenuItem item : this.menuItems) {
			System.out.println((option++) + ": " + item.getTitle());
		}

		System.out.print("Waehlen Sie eine Option: ");
	}

	/**
	 * Fragt den Benutzer nach einer Eingabe, mit der er einen
	 * {@link de.hsw_hameln.warehouse.ui.MenuItem Menueeintrag} auswaehlen kann.
	 * 
	 * @return Der ausgewaehlte {@link de.hsw_hameln.warehouse.ui.MenuItem Menueeintrag}.
	 * @throws IOException Wenn bei der Eingabe Fehler auftreten.
	 */
	private MenuItem prompt() throws IOException
	{
		int option;

		while (true) {
			display();
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			String line = input.readLine();
			try {
				option = Integer.parseInt(line);
				if (option >= 0 && option < this.menuItems.size()) {
					return this.menuItems.get(option);
				}
			} catch (NumberFormatException e) {
			}
			System.err.println(line + " ist keine gueltige Option\n");

		}
	}

	/**
	 * Der {@link java.lang.Runnable ausfuehrbare Code} des Menues.<br>
	 * Der Benutzer wird zunaechst nach der Eingabe gefragt. Wenn ein
	 * {@link de.hsw_hameln.warehouse.ui.MenuItem Menueeintrag} ausgewaehlt wurde, wird dessen
	 * {@link java.lang.Runnable Code} ausgefuehrt. Handelt es sich bei dem
	 * {@link de.hsw_hameln.warehouse.ui.MenuItem Menueeintrag} um ein Untermenue, wird diese
	 * Methode fuer jenes Menue ausgefuehrt. Handelt es sich um einen
	 * {@link de.hsw_hameln.warehouse.ui.MenuItem Menueeintrag}, wird dessen
	 * {@link java.lang.Runnable Code} ausgefuehrt. Ist dieser {@link java.lang.Runnable Code} nicht
	 * vorhanden (das bedeutet es handelt sich um den "Zurueck"-Eintrag), wird die Methode verlassen
	 * und in das Obermenue zurueckgekehrt.
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
	 * Fuegt dem Menue einen {@link de.hsw_hameln.warehouse.ui.MenuItem Menueeintrag} hinzu.
	 * 
	 * @param menuItem Der hinzuzufuegende {@link de.hsw_hameln.warehouse.ui.MenuItem Menueeintrag}.
	 */
	public void add(MenuItem menuItem)
	{
		this.menuItems.add(menuItem);
	}
}
