package de.hsw_hameln.warehouse.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Diese Klasse repr�sentiert ein Men�. Dieses kann mehrere {@link de.hsw_hameln.warehouse.ui.MenuItem
 * Men�eintrage} beinhalten.<br>
 * Da ein Men� als einzelne Zeile in einem anderen Men� angezeigt wird, ist es gleichzeitig ein
 * Men�eintrag und kann eine Unterklasse der Klasse {@link de.hsw_hameln.warehouse.ui.MenuItem} sein.
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
	 * Der "Beenden"-Eintrag. Wird dieser ausgew�hlt, beendet sich das Programm normal.
	 */
	public static final MenuItem exit = new MenuItem("Beenden", new Runnable()
	{
		public void run()
		{
			System.exit(0);
		}
	});

	/**
	 * Der "Zur�ck"-Eintrag. Dieser hat keinen {@link java.lang.Runnable ausf�hrbaren Code} und wird
	 * mit dem Konstruktor {@link de.hsw_hameln.warehouse.ui.MenuItem#MenuItem(String) MenuItem(String)}
	 * erstellt. Aus diesem Grund exisitiert dieser Konstruktor und ist nur in dieser Klasse und der
	 * Oberklasse sichtbar, damit er nicht woanders aufgerufen werden kann.
	 */
	public static final MenuItem back = new MenuItem("Zur�ck");

	/**
	 * Erstellt ein Men� mit den angegebenen Eintr�gen und dem angegebenen Titel.
	 * 
	 * @param title Der Titel des Men�s.
	 * @param back Gibt an, ob ein {@link de.hsw_hameln.warehouse.ui.Menu#back "Zur�ck"-Eintrag} vorhanden
	 *            sein soll.
	 * @param exit Gibt an, ob ein {@link de.hsw_hameln.warehouse.ui.Menu#exit "Beenden"-Eintrag} vorhanden
	 *            sein soll.
	 * @param menuItems Die hinzuzuf�genden {@link de.hsw_hameln.warehouse.ui.MenuItem Men�eintr�ge}.
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
	 * Gibt alle {@link de.hsw_hameln.warehouse.ui.MenuItem Men�eintr�ge} mit vorangestellter Nummerierung
	 * zeilenweise auf der Konsole aus.
	 */
	public void display()
	{
		int option = 0;
		System.out.println("\n" + getTitle() + ":");

		for (MenuItem item : this.menuItems) {
			System.out.println((option++) + ": " + item.getTitle());
		}

		System.out.print("W�hlen Sie eine Option: ");
	}

	/**
	 * Fragt den Benutzer nach einer Eingabe, mit der er einen {@link de.hsw_hameln.warehouse.ui.MenuItem
	 * Men�eintrag} ausw�hlen kann.
	 * 
	 * @return Der ausgew�hlte {@link de.hsw_hameln.warehouse.ui.MenuItem Men�eintrag}.
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
			System.err.println(line + " ist keine g�ltige Option\n");

		}
	}

	/**
	 * Der {@link java.lang.Runnable ausf�hrbare Code} des Men�s.<br>
	 * Der Benutzer wird zun�chst nach der Eingabe gefragt. Wenn ein
	 * {@link de.hsw_hameln.warehouse.ui.MenuItem Men�eintrag} ausgew�hlt wurde, wird dessen
	 * {@link java.lang.Runnable Code} ausgef�hrt. Handelt es sich bei dem
	 * {@link de.hsw_hameln.warehouse.ui.MenuItem Men�eintrag} um ein Untermen�, wird diese Methode f�r
	 * jenes Men� ausgef�hrt. Handelt es sich um einen {@link de.hsw_hameln.warehouse.ui.MenuItem
	 * Men�eintrag}, wird dessen {@link java.lang.Runnable Code} ausgef�hrt. Ist dieser
	 * {@link java.lang.Runnable Code} nicht vorhanden (das bedeutet es handelt sich um den
	 * "Zur�ck"-Eintrag), wird die Methode verlassen und in das Obermen� zur�ckgekehrt.
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
	 * F�gt dem Men� einen {@link de.hsw_hameln.warehouse.ui.MenuItem Men�eintrag} hinzu.
	 * 
	 * @param menuItem Der hinzuzuf�gende {@link de.hsw_hameln.warehouse.ui.MenuItem Men�eintrag}.
	 */
	public void add(MenuItem menuItem)
	{
		this.menuItems.add(menuItem);
	}
}