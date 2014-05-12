package de.hsw.warehouse.ui;

public class MenuStructure
{

	private static Menu testDataMenu = new Menu("Testdaten", true, false);
	private static Menu analysisMenu = new Menu("Auswertungen", true, false);
	private static Menu mainMenu = new Menu("Hauptmenü", false, true, testDataMenu,
			analysisMenu);

	public static void main(String[] args)
	{

		testDataMenu.add(new MenuItem("Testdaten erzeugen", new Runnable()
		{

			@Override
			public void run()
			{
				System.out.println("Testdaten werden erzeugt...");

			}
		}));

		testDataMenu.add(new MenuItem("Testdaten einlesen", new Runnable()
		{

			@Override
			public void run()
			{
				System.out.println("Testdaten werden eingelesen...");

			}
		}));

		testDataMenu.add(new MenuItem("Testdaten löschen", new Runnable()
		{

			@Override
			public void run()
			{
				System.out.println("Testdaten werden erzeugt...");

			}
		}));

		analysisMenu.add(new MenuItem("Artikelbestand", new Runnable()
		{

			@Override
			public void run()
			{
				System.out.println("Artikelbestand.");

			}
		}));

		analysisMenu.add(new MenuItem("Differenzmenge", new Runnable()
		{

			@Override
			public void run()
			{
				System.out.println("Differenzmenge.");

			}
		}));

		analysisMenu.add(new MenuItem("Umschlagshäufigkeit", new Runnable()
		{

			@Override
			public void run()
			{
				System.out.println("Umschagshäufigkeit.");

			}
		}));

		analysisMenu.add(new MenuItem("Bestandsverlauf", new Runnable()
		{

			@Override
			public void run()
			{
				System.out.println("Bestandsverlauf.");

			}
		}));

		analysisMenu.add(new MenuItem("Auslastung", new Runnable()
		{

			@Override
			public void run()
			{
				System.out.println("Auslastung.");

			}
		}));
		mainMenu.run();
	}
}
