package de.hsw.warehouse.ui;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import de.hsw.warehouse.analysis.Analysis;
import de.hsw.warehouse.analysis.Testdata;
import de.hsw.warehouse.model.Warehouse;
import de.hsw.warehouse.util.Util;

/**
 * In dieser Klasse werden die einzelnen {@link de.hsw.warehouse.ui.MenuItem Menüeinträge} und {@link de.hsw.warehouse.ui.Menu Menüs} erstellt und zu einer <code>Menüstruktur</code> zusammengefügt.<br>
 * Außerdem enthält diese Klasse den {@link de.hsw.warehouse.ui.MenuStructure#main(String[]) Einstiegspunkt} für das Programm.<br>
 * Die Felder dieser Klasse sind Parameter für die {@link de.hsw.warehouse.analysis.Analysis Auswertungen}. Sie stellen Standardwerte dar, mit denen die {@link de.hsw.warehouse.analysis.Analysis Auswertungen} stattfinden, wenn keine oder eine falsche Benutzereingabe gemacht wurde.
 * Dabei werden bei der Ausführung einer {@link de.hsw.warehouse.analysis.Analysis Auswertung} die übergebenen Parameter in den Feldern gespeichert, so dass die Felder immer die zuletzt eingegebenen Werte enthalten.
 * @author Constantin
 * @version
 * @see <a href="http://bytes.com/topic/java/insights/870013-text-based-menus">http://bytes.com/topic/java/insights/870013-text-based-menus</a>
 */
public class MenuStructure
{

	private static Menu testDataMenu = new Menu("Testdaten", true, false);
	private static Menu analysisMenu = new Menu("Auswertungen", true, false);
	private static Menu mainMenu = new Menu("Hauptmenü", false, true, testDataMenu,
			analysisMenu);

	private static ArrayList<GregorianCalendar> period = new ArrayList<GregorianCalendar>();
	private static Warehouse warehouse;
	private static Testdata testdata;
	private static Path path = Paths.get("C:\\Testdata\\testdata.csv");
	private static int articleID = 0;
	
	/**
	 * Stellt den Einstiegspunkt für die Applikation dar. Hier werden die {@link de.hsw.warehouse.ui.MenuItem Menüeintrage} erstellt und zusammengefügt. Anschließend wird für das Hauptmenü die Methode {@link de.hsw.warehouse.ui.Menu#run()} aufgerufen, welche das Menü aufruft und die Benutzerinteraktion startet.
	 * @param args Die Kommandozeilenargumente. Werden hier nicht verwertet.
	 */
	public static void main(String[] args)
	{
		System.out.println("Herzlich Willkommen!");
		System.out.println("Bitte erzeugen Sie zuerst die Testdaten oder lesen Sie bereits bestehende Testdaten ein.");

		period.add(new GregorianCalendar(2012, GregorianCalendar.JULY, 16));
		period.add(new GregorianCalendar(2013, GregorianCalendar.JULY, 16, 23, 59, 59));
		
		testDataMenu.add(new MenuItem("Testdaten erzeugen", new Runnable()
		{

			@Override
			public void run()
			{
				warehouse = new Warehouse(2000, 20);
				
				if(testdata == null) {
					period = Util.inputDateOrPeriod("Bitte geben Sie einen Zeitraum ein [" + Util.parseDate(period.get(0)) + "-" + Util.parseDate(period.get(1)) + "]:", period);
					testdata = new Testdata(warehouse, period.get(0), period.get(1));
				} else {
					do {
						period = Util.inputDateOrPeriod("Bitte geben Sie einen Zeitraum ein [" + Util.parseDate(period.get(0)) + "-" + Util.parseDate(period.get(1)) + "]:", period);
						testdata = new Testdata(warehouse, period.get(0), period.get(1));
					} while (testdata.getTransactions().isEmpty());
				}
				period.set(0, testdata.getStartDate());
				period.set(1, testdata.getEndDate());
				System.out.println("Es wurden " + NumberFormat.getInstance().format(testdata.getTransactions().size()) + " Testdatensätze in dem Zeitraum vom " + Util.parseDate(period.get(0)) + " bis zum " + Util.parseDate(period.get(1)) + " erstellt.");
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
					period.set(0, testdata.getStartDate());
					period.set(1, testdata.getEndDate());
					System.out.println("Es wurden " + NumberFormat.getInstance().format(testdata.getTransactions().size()) + " Testdatensätze in dem Zeitraum vom " + Util.parseDate(period.get(0)) + " bis zum " + Util.parseDate(period.get(1)) + " eingelesen.");
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
					testdata = null;
					warehouse = null;
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
				period = Util.inputDateOrPeriod("Bitte geben Sie einen Zeitraum oder ein Datum ein [" + Util.parseDate(period.get(0)) + " - " + Util.parseDate(period.get(1)) + "]: ", period);
				try {
					Analysis.quantityOfPeriod(period.get(0), period.get(1), testdata);
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
				period = Util.inputDateOrPeriod("Bitte geben Sie einen Zeitraum ein: [" + Util.parseDate(period.get(0)) + " - " + Util.parseDate(period.get(1)) + "]:", period);
				try {
					Analysis.differenceOfPeriod(period.get(0), period.get(1), testdata);
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
				period = Util.inputDateOrPeriod("Bitte geben Sie einen Zeitraum ein [" + Util.parseDate(period.get(0)) + " - " + Util.parseDate(period.get(1)) + "]:", period);
				try {
					Analysis.turnFrequency(period.get(0), period.get(1), testdata);
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
				period = Util.inputDateOrPeriod("Bitte geben Sie einen Zeitraum ein [" + Util.parseDate(period.get(0)) + " - " + Util.parseDate(period.get(1)) + "]:", period);
				articleID = Util.inputArticleID(0);
				try {
					Analysis.stockCourseOfPeriod(articleID, period.get(0), period.get(1), testdata);
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
				period = Util.inputDateOrPeriod("Bitte geben Sie einen Zeitraum ein [" + Util.parseDate(period.get(0)) + " - " + Util.parseDate(period.get(1)) + "]:", period);
				try {
					Analysis.stockUtilizationInPeriod(period.get(0), period.get(1), testdata);
				} catch (NullPointerException e) {
					System.err.println("Bitte erzeugen Sie zuerst Testdaten.");
				}

			}
		}));
		mainMenu.run();
	}
}
