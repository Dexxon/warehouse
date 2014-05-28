package de.hsw.warehouse.ui;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.GregorianCalendar;

import de.hsw.warehouse.analysis.Analysis;
import de.hsw.warehouse.analysis.Testdata;
import de.hsw.warehouse.model.Warehouse;
import de.hsw.warehouse.util.Util;

public class MenuStructure
{

	private static Menu testDataMenu = new Menu("Testdaten", true, false);
	private static Menu analysisMenu = new Menu("Auswertungen", true, false);
	private static Menu mainMenu = new Menu("Hauptmenü", false, true, testDataMenu,
			analysisMenu);

	private static GregorianCalendar[] period = {new GregorianCalendar(2012, GregorianCalendar.JULY, 16),new GregorianCalendar(2013, GregorianCalendar.JULY, 16, 23, 59, 59)};
	private static Warehouse warehouse;
	private static Testdata testdata;
	private static Path path = Paths.get("C:\\Testdata\\testdata.csv");
	private static int articleID = 0;
	
	public static void main(String[] args)
	{
		System.out.println("Herzlich Willkommen!");
		System.out.println("Bitte erzeugen Sie zuerst die Testdaten oder lesen Sie bereits bestehende Testdaten ein.");

		testDataMenu.add(new MenuItem("Testdaten erzeugen", new Runnable()
		{

			@Override
			public void run()
			{
				warehouse = new Warehouse(2000, 20);
				
				if(testdata == null) {
					period = Util.inputDateOrPeriod("Bitte geben Sie einen Zeitraum ein [" + Util.parseDate(period[0]) + "-" + Util.parseDate(period[1]) + "]:", period);
					testdata = new Testdata(warehouse, period[0], period[1]);
				} else {
					do {
						period = Util.inputDateOrPeriod("Bitte geben Sie einen Zeitraum ein [" + Util.parseDate(period[0]) + "-" + Util.parseDate(period[1]) + "]:", period);
						testdata = new Testdata(warehouse, period[0], period[1]);
					} while (testdata.getTransactions().isEmpty());
				}
				period[0] = testdata.getStartDate();
				period[1] = testdata.getEndDate();
				System.out.println("Es wurden " + NumberFormat.getInstance().format(testdata.getTransactions().size()) + " Testdatensätze in dem Zeitraum vom " + Util.parseDate(period[0]) + " bis zum " + Util.parseDate(period[1]) + " erstellt.");
				testdata.writeToDisk(path);
			}
		}));

		testDataMenu.add(new MenuItem("Testdaten einlesen", new Runnable()
		{

			@Override
			public void run()
			{
				Path tempPath = Util.inputPath(path);
				warehouse = new Warehouse(2000, 20);
				try {
					testdata = new Testdata(tempPath);
					period[0] = testdata.getStartDate();
					period[1] = testdata.getEndDate();
					System.out.println("Es wurden " + NumberFormat.getInstance().format(testdata.getTransactions().size()) + " Testdatensätze in dem Zeitraum vom " + Util.parseDate(period[0]) + " bis zum " + Util.parseDate(period[1]) + " eingelesen.");
					path = tempPath;
				}
				catch (Exception e) {
					System.err.println("Fehler beim Einlesen der Testdaten.");
				}
			}
		}));

		testDataMenu.add(new MenuItem("Testdaten löschen", new Runnable()
		{

			@Override
			public void run()
			{
				File file = new File(path.toUri());
				if(file.delete()) {
					System.out.println("Testdaten erfolgreich gelöscht.");
				} else {
					System.err.println("Die Testdaten konnten nicht gelöscht werden. Stellen Sie sicher, dass die Datei nicht in einem anderen Programm geöffnet ist.");
				}
			}
		}));

		analysisMenu.add(new MenuItem("Artikelbestand", new Runnable()
		{

			@Override
			public void run()
			{
				period = Util.inputDateOrPeriod("Bitte geben Sie einen Zeitraum oder ein Datum ein [" + Util.parseDate(period[0]) + " - " + Util.parseDate(period[1]) + "]: ", period);
				try {
					Analysis.quantityOfPeriod(period[0], period[1], testdata);
				} catch (NullPointerException e) {
					System.err.println(" Bitte erzeugen Sie zuerst Testdaten.");
				}
			}
		}));

		analysisMenu.add(new MenuItem("Differenzmenge", new Runnable()
		{

			@Override
			public void run()
			{
				period = Util.inputDateOrPeriod("Bitte geben Sie einen Zeitraum ein: [" + Util.parseDate(period[0]) + " - " + Util.parseDate(period[1]) + "]:", period);
				try {
					Analysis.differenceOfPeriod(period[0], period[1], testdata);
				} catch (NullPointerException e) {
					System.err.println("Bitte erzeugen Sie zuerst Testdaten.");
				}
			}
		}));

		analysisMenu.add(new MenuItem("Umschlagshäufigkeit", new Runnable()
		{

			@Override
			public void run()
			{
				period = Util.inputDateOrPeriod("Bitte geben Sie einen Zeitraum ein [" + Util.parseDate(period[0]) + " - " + Util.parseDate(period[1]) + "]:", period);
				try {
					Analysis.turnFrequency(period[0], period[1], testdata);
				} catch (NullPointerException e) {
					System.err.println("Bitte erzeugen Sie zuerst Testdaten.");
				}
			}
		}));

		analysisMenu.add(new MenuItem("Bestandsverlauf", new Runnable()
		{
			
			@Override
			public void run()
			{
				period = Util.inputDateOrPeriod("Bitte geben Sie einen Zeitraum ein [" + Util.parseDate(period[0]) + " - " + Util.parseDate(period[1]) + "]:", period);
				articleID = Util.inputArticleID(0);
				try {
					Analysis.stockCourseOfPeriod(articleID, period[0], period[1], testdata);
				} catch (NullPointerException e) {
					System.err.println("Bitte erzeugen Sie zuerst Testdaten.");
				}
			}
		}));

		analysisMenu.add(new MenuItem("Auslastung", new Runnable()
		{

			@Override
			public void run()
			{
				period = Util.inputDateOrPeriod("Bitte geben Sie einen Zeitraum ein [" + Util.parseDate(period[0]) + " - " + Util.parseDate(period[1]) + "]:", period);
				System.out.println(period[0].getTime());
				System.out.println(period[1].getTime());
				System.out.println(testdata.getSizeOfWarehouse());
				try {
					Analysis.stockUtilizationInPeriod(period[0], period[1], testdata);
				} catch (NullPointerException e) {
					System.err.println("Bitte erzeugen Sie zuerst Testdaten.");
				}

			}
		}));
		mainMenu.run();
	}
}
