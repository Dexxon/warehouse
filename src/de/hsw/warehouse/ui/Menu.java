package de.hsw.warehouse.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Menu extends MenuItem
{
	ArrayList<MenuItem> menuItems;
	String title;

	static final MenuItem exit = new MenuItem("Beenden", new Runnable()
	{
		public void run()
		{
			System.exit(0);
		}
	});

	static final MenuItem back = new MenuItem("Zurück");

	Menu(String title, MenuItem... menuItems)
	{
		this(title, false, true, menuItems);
	}

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

	void display()
	{
		int option = 0;
		System.out.println("\n" + getTitle() + ":");
		for (MenuItem item : menuItems) {
			System.out.println((option++) + ": " + item.getTitle());
		}
		System.out.print("Wählen Sie eine Option: ");
	}

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

	Menu add(MenuItem menueItem)
	{
		menuItems.add(menueItem);
		return this;
	}
}
