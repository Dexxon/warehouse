package de.hsw.warehouse.ui;

public class MenuStructure
{

	private static Menu testData = new Menu("Testdaten", true, false);
	private static Menu analysis = new Menu("Auswertungen", true, false);
	private static Menu mainMenu = new Menu("Hauptmen�", false, true, testData,
			analysis);

	public static void main(String[] args)
	{

		testData.add(new MenuItem("Testdaten erzeugen", new Runnable()
		{

			@Override
			public void run()
			{
				System.out.println("Testdaten werden erzeugt...");

			}
		}));

		testData.add(new MenuItem("Testdaten einlesen", new Runnable()
		{

			@Override
			public void run()
			{
				System.out.println("Testdaten werden eingelesen...");

			}
		}));

		testData.add(new MenuItem("Testdaten l�schen", new Runnable()
		{

			@Override
			public void run()
			{
				System.out.println("Testdaten werden erzeugt...");

			}
		}));

		analysis.add(new MenuItem("Artikelbestand", new Runnable()
		{

			@Override
			public void run()
			{
				System.out.println("Artikelbestand.");

			}
		}));

		analysis.add(new MenuItem("Differenzmenge", new Runnable()
		{

			@Override
			public void run()
			{
				System.out.println("Differenzmenge.");

			}
		}));

		analysis.add(new MenuItem("Umschlagsh�ufigkeit", new Runnable()
		{

			@Override
			public void run()
			{
				System.out.println("Umschagsh�ufigkeit.");

			}
		}));

		analysis.add(new MenuItem("Bestandsverlauf", new Runnable()
		{

			@Override
			public void run()
			{
				System.out.println("Bestandsverlauf.");

			}
		}));

		analysis.add(new MenuItem("Auslastung", new Runnable()
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
