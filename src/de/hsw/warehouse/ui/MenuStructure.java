package de.hsw.warehouse.ui;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import de.hsw.warehouse.meinTest;
import de.hsw.warehouse.analysis.Analysis;
import de.hsw.warehouse.analysis.Testdata;
import de.hsw.warehouse.util.Util;

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
				Testdata data = new Testdata(meinTest.warehouse, meinTest.startDate, meinTest.endDate);
				int articleID = Util.inputArticleID();
				GregorianCalendar[] periodOfTime = Util.inputDateOrPeriod("Geben Sie einen Zeitraum oder einen Zeitpunkt ein: ", new GregorianCalendar());
				if (periodOfTime.length == 1){
					Analysis.stockUtilization(periodOfTime[0], data);
				}
				if (periodOfTime.length == 2){
					System.out.println(Util.parseDate(periodOfTime[0]));
					System.out.println(Util.parseDate(periodOfTime[1]));
					Analysis.stockUtilizationInPeriod(periodOfTime[0], periodOfTime[1], data);
				}
				else ;
				
				

			}
		}));
		mainMenu.run();
	}
}
