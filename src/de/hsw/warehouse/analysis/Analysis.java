package de.hsw.warehouse.analysis;

import java.io.FileNotFoundException;
import java.util.GregorianCalendar;

import de.hsw.warehouse.analysis.Testdata;
import de.hsw.warehouse.model.Article;
import de.hsw.warehouse.model.Transaction;
import de.hsw.warehouse.model.Warehouse;

public class Analysis {

	public static Warehouse warehouse = new Warehouse(2000, 20);
	public static GregorianCalendar startDate = new GregorianCalendar(2012,
			GregorianCalendar.AUGUST, 16);
	public static GregorianCalendar endDate = new GregorianCalendar(2013,
			GregorianCalendar.AUGUST, 16);

	public static void main(String[] args) {
		try {
			Article.initialiseArticlePool("C:\\Users\\Rodenwaldt\\Downloads\\ArtikelPool.csv");
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		Testdata data = new Testdata(warehouse, startDate, endDate);
		GregorianCalendar pointOfInventoryStart = new GregorianCalendar(2012,
				GregorianCalendar.AUGUST, 30);
		GregorianCalendar pointOfInventoryEnd = new GregorianCalendar(2012,
				GregorianCalendar.SEPTEMBER, 12);

		 quantityPerDay(pointOfInventoryStart, data);
		 System.out.println("\n\n\n");
		 quantityOfPeriod(pointOfInventoryStart, pointOfInventoryEnd, data);
		// quantityPerDay(pointOfInventoryEnd, data);

		
	}

	/*
	 * Methode wird f�r einen bestimmten Artikel (Artikelnummer) bis zu einem
	 * bestimmten Datum ausgef�hrt. Wenn man den gesamten Bestand ausrechnen
	 * m�chte, muss man diesen Vorgang in eine Schleife "verpacken", sodass f�r
	 * jeden Artikel der Bestand zum Zeipunkt x bestimmt werden kann.
	 */

	public static int quantityCalculator(int articleID, GregorianCalendar date,
			Testdata data) {

		int quantity = 0;

		for (Transaction transaction : data.getTransactions()) {
			if (transaction.getArticleID() == articleID
					|| date.getTimeInMillis() >= transaction.getDate()
							.getTimeInMillis()) {
				quantity = quantity + transaction.getQuantity();
			}
		}
		return quantity;
	}

	public static int quantityAverage(int articleID,
			GregorianCalendar startDate, GregorianCalendar endDate,
			Testdata data) {

		int average = 0;
		int subtotal = 0;
		
		GregorianCalendar tempDate = new GregorianCalendar();
		tempDate.setTimeInMillis(startDate.getTimeInMillis());
		
		long timeToWorkWith = endDate.getTimeInMillis()
				- startDate.getTimeInMillis();
		int daysToWorkWith = (int) ((timeToWorkWith / (1000 * 60 * 60 * 24)) + 1);
		int[] quantityOnDays = new int[daysToWorkWith];

		for (int i = 0; i < daysToWorkWith; i++) {
			quantityOnDays[i] = quantityCalculator(articleID, tempDate, data);
			tempDate.add(5, 1);
		}

		for (int j = 0; j < daysToWorkWith; j++) {
			subtotal = subtotal + quantityOnDays[j];
		}

		average = subtotal / daysToWorkWith;
		return average;
	
	}

	public static void quantityPerDay(GregorianCalendar date, Testdata data) {

		int counter = 0;
		int quantity = 0;

		System.out.println("\n");
		System.out.println("Bestand am " + date.getTime() + " : ");
		System.out.println("\n");
		System.out.println("Artikelnummer \t Artikelname \t Anzahl");

		while (counter < 60) {
			String articleName = Article.namePool[counter].intern();
			quantity = quantityCalculator(counter, date, data);
			System.out.println(counter + "\t" + articleName + "\t" + quantity);
			counter++;
		}

	}

	public static void quantityOfPeriod(GregorianCalendar startDate,
			GregorianCalendar endDate, Testdata data) {

		int counter = 0;
		int quantityBegin = 0;
		int quantityEnd = 0;
		int averageQuantity = 0;

		System.out.println("\n");
		System.out.println("Bestand vom " + startDate.getTime() + " bis zum "
				+ endDate.getTime() + " : ");
		System.out.println("\n");
		System.out
				.println("Artikelnummer \t Artikelname \t Anfangsbestand \t Endbestand \t Durchschnittsbestand");

		while (counter < 60) {
			String articleName = Article.namePool[counter].intern();
			quantityBegin = quantityCalculator(counter, startDate, data);
			quantityEnd = quantityCalculator(counter, endDate, data);
			averageQuantity = quantityAverage(counter, startDate, endDate, data);
			System.out.println(counter + "\t" + articleName + "\t"
					+ quantityBegin + "\t" + quantityEnd + "\t"
					+ averageQuantity);
			counter++;
		}
	}

}
