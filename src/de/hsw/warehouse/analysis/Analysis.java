package de.hsw.warehouse.analysis;

import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import de.hsw.warehouse.model.Assortment;
import de.hsw.warehouse.model.Transaction;
import de.hsw.warehouse.model.Warehouse;
import de.hsw.warehouse.util.Util;

public class Analysis {

	public static Warehouse warehouse = new Warehouse(2000, 20);
	public static GregorianCalendar startDate = new GregorianCalendar(2012, GregorianCalendar.AUGUST, 16);
	public static GregorianCalendar endDate = new GregorianCalendar(2013, GregorianCalendar.AUGUST, 16);

	public static void main(String[] args) {
		Testdata data = new Testdata(warehouse, startDate, endDate);
		GregorianCalendar pointOfInventoryStart = new GregorianCalendar(2012, GregorianCalendar.AUGUST, 30);
		GregorianCalendar pointOfInventoryEnd = new GregorianCalendar(2012, GregorianCalendar.SEPTEMBER, 12);

		//quantityPerDay(pointOfInventoryStart, data);
		//System.out.println("\n\n\n");
		//quantityOfPeriod(pointOfInventoryStart, pointOfInventoryEnd, data);
		// quantityPerDay(pointOfInventoryEnd, data);
		//stockCourseOfPeriod(30,pointOfInventoryStart,pointOfInventoryEnd, data);
		//differenceCalculatorOfPeriod(pointOfInventoryStart,pointOfInventoryEnd, data);

	}

	/*
	 * Methode wird für einen bestimmten Artikel (Artikelnummer) bis zu einem
	 * bestimmten Datum ausgeführt. Wenn man den gesamten Bestand ausrechnen
	 * möchte, muss man diesen Vorgang in eine Schleife "verpacken", sodass für
	 * jeden Artikel der Bestand zum Zeipunkt x bestimmt werden kann.
	 */

	public static int quantityCalculator(int articleID, GregorianCalendar date, Testdata data) {

		int quantity = 0;

		for (Transaction transaction : data.getTransactions()) {
			if (transaction.getArticleID() == articleID || date.getTimeInMillis() >= transaction.getDate().getTimeInMillis()) {
				quantity = quantity + transaction.getQuantity();
			}
		}
		return quantity;
	}

	public static int quantityAverage(int articleID, GregorianCalendar startDate, GregorianCalendar endDate, Testdata data) {

		int average = 0;
		int subtotal = 0;

		GregorianCalendar tempDate = new GregorianCalendar();
		tempDate.setTimeInMillis(startDate.getTimeInMillis());

		int daysToWorkWith = dayCalculator(startDate, endDate);
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
			String articleName = Assortment.getName(counter);
			quantity = quantityCalculator(counter, date, data);
			System.out.println(counter + "\t" + articleName + "\t" + quantity);
			counter++;
		}

	}

	public static void quantityOfPeriod(GregorianCalendar startDate, GregorianCalendar endDate, Testdata data) {

		int counter = 0;
		int quantityBegin = 0;
		int quantityEnd = 0;
		int averageQuantity = 0;

		System.out.println("\n");
		System.out.println("Bestand vom " + startDate.getTime() + " bis zum " + endDate.getTime() + " : ");
		System.out.println("\n");
		System.out.println("Artikelnummer \t Artikelname \t Anfangsbestand \t Endbestand \t Durchschnittsbestand");

		while (counter < 60) {
			String articleName = Assortment.getName(counter);
			quantityBegin = quantityCalculator(counter, startDate, data);
			quantityEnd = quantityCalculator(counter, endDate, data);
			averageQuantity = quantityAverage(counter, startDate, endDate, data);
			System.out.println(counter + "\t" + articleName + "\t" + quantityBegin + "\t" + quantityEnd + "\t" + averageQuantity);
			counter++;
		}
	}

	public static void stockCourseOfPeriod(int articleID, GregorianCalendar startDate, GregorianCalendar endDate, Testdata data) {

		GregorianCalendar tempDate = new GregorianCalendar();
		tempDate.setTimeInMillis(startDate.getTimeInMillis());

		int daysToWorkWith = dayCalculator(startDate, endDate);
		int quantity = 0;

		System.out.println("\n");
		System.out.println("Bestand vom " + startDate.getTime() + " bis zum " + endDate.getTime() + " für den Artikel '" + Assortment.getName(articleID) + "' : ");
		System.out.println("\n");
		System.out.println("Datum \t Artikelnummer \t Artikelname \t Bestand");

		for (int i = 0; i < daysToWorkWith; i++) {
			quantity = quantityCalculator(articleID, tempDate, data);
			System.out.println(tempDate.getTime() + "\t" + articleID + "\t" + Assortment.getName(articleID) + "\t" + quantity);
			tempDate.add(5, 1);
		}

	}

	public static void differenceCalculatorOfPeriod(GregorianCalendar startDate, GregorianCalendar endDate, Testdata data) {

		int quantityStart = 0;
		int quantityEnd = 0;
		int difference = 0;
		int counter = 0;

		System.out.println("\n");
		System.out.println("Differenzmengen vom " + startDate.getTime() + " bis zum " + endDate.getTime() + " : ");
		System.out.println("\n");
		System.out.println("Artikelnummer \t Artikelname \t Differenzmenge");

		while (counter < 60) {
			quantityStart = quantityCalculator(counter, startDate, data);
			quantityEnd = quantityCalculator(counter, endDate, data);
			difference = quantityEnd - quantityStart;
			System.out.println(counter + "\t" + Assortment.getName(counter) + "\t" + difference);
			counter++;
		}

	}

	public static int dayCalculator(GregorianCalendar startDate, GregorianCalendar endDate) {

		long timeToWorkWith = endDate.getTimeInMillis() - startDate.getTimeInMillis();
		int daysToWorkWith = (int) ((timeToWorkWith / (1000 * 60 * 60 * 24)) + 1);

		return daysToWorkWith;
	}

	//Gibt die Lagerauslastung an einem bestimmten Datum aus
	public static void stockUtilization(GregorianCalendar date, Testdata data) {
		int utilization = 0;
		for (Transaction transaction : data.getTransactionsInPeriod(data.getTransactions().getFirst().getDate(), date)) {
			utilization += transaction.getVolume() * transaction.getQuantity();
		}
		System.out.println("Am " + Util.parseDate(date) + " beträgt die Auslastung des Lagers " + ((double) utilization) / data.getSizeOfWarehouse() * 100 + "%");

	}

	public static void stockUtilizationInPeriod(GregorianCalendar startDate, GregorianCalendar endDate, Testdata data) {
		int utilization = 0;
		System.out.println("START: erste Transaction am " + Util.parseDate(data.getTransactions().getFirst().getDate()));
		System.out.println("Übergebenses Startdatum: " + Util.parseDate(startDate));
		if(data.getTransactions().getFirst().getDate().before(startDate)){
			System.out.println("Vorherige Transactionen nachvollziehen...");
			for (Transaction transaction : data.getTransactionsInPeriod(data.getTransactions().getFirst().getDate(), startDate)) {
				utilization += transaction.getVolume() * transaction.getQuantity();
			}
		}		
		Integer dayBefore = 0, day = 0;
		int subtotalUtilization = utilization;
		System.out.println("Start Auslastung: " + subtotalUtilization);
		ArrayList<Integer> listOfUtilizations = new ArrayList<Integer>();
		ArrayList<Transaction> allTransactions = new ArrayList<Transaction>();
		allTransactions.addAll(data.getTransactionsInPeriod(startDate, endDate));
		dayBefore = allTransactions.get(0).getDate().get(Calendar.DAY_OF_YEAR);
		System.out.println("Tag: "+ dayBefore);
		for (Transaction transaction : allTransactions) {
			day = transaction.getDate().get(Calendar.DAY_OF_YEAR);
			if (dayBefore.equals(day)) {//hier Tag - Vortag und dann Differenz malnehmen
				subtotalUtilization += transaction.getVolume() * transaction.getQuantity();
				System.out.println("Transaktion ist am selben Tag, der Platzbedarf ist: " + transaction.getVolume() * transaction.getQuantity());
			} else {
				
				int missingDays = day - dayBefore-1;
				if (missingDays > 0){
					System.out.println(missingDays + " Tage ohne Transactionen werden nachgeholt");
					for (int i = 0; i<missingDays; i++){
						listOfUtilizations.add(subtotalUtilization);
					}
				}
				listOfUtilizations.add(subtotalUtilization);
				System.out.println("Tag fertig, Platzbedarf ist: " + subtotalUtilization);
				System.out.println("Tag geschrieben");
				System.out.println("Tag: "+ day);
				subtotalUtilization += transaction.getVolume() * transaction.getQuantity();
				dayBefore = day;
				
				
			}
		}
		System.out.println();
		int counter = 0;
		int sum = 0;
		for (Integer integer : listOfUtilizations){
			System.out.println("Auslastung an Tag " + counter + ":" + integer);
			sum+= integer;
			counter++;
		}
		System.out.println("Durchschnittsauslastung: "+ (double)sum/counter);
		System.out.println("Durchschnittsauslastung in %: "+ (double)sum/counter/data.getSizeOfWarehouse()*100 + "%");
		

		//System.out.println("Am " + Util.parseDate(date) + " beträgt die Auslastung des Lagers " + ((double) utilization) / data.getSizeOfWarehouse() * 100 + "%");

	}

}
