package de.hsw.warehouse.analysis;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import de.hsw.warehouse.model.Assortment;
import de.hsw.warehouse.util.Util;

public class Analysis {

	/*public static Warehouse warehouse = new Warehouse(2000, 20);
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

	}*/

	/*
	 * Methode wird für einen bestimmten Artikel (Artikelnummer) bis zu einem
	 * bestimmten Datum ausgeführt. Wenn man den gesamten Bestand ausrechnen
	 * möchte, muss man diesen Vorgang in eine Schleife "verpacken", sodass für
	 * jeden Artikel der Bestand zum Zeipunkt x bestimmt werden kann.
	 */

	private static int[] calculateQuantityPerDay(GregorianCalendar date, Testdata data) {

		int[] quantity = new int[Assortment.getSize()];

		for (Transaction transaction : data.getTransactionsInPeriod(data.getTransactions().getFirst().getDate(), (GregorianCalendar) date.clone())) {
			quantity[transaction.getArticleID()] += transaction.getQuantity();
		}
		return quantity;
	}

	private static int calculateDays(GregorianCalendar startDate, GregorianCalendar endDate) {
	
		long timeInMillis = endDate.getTimeInMillis() - startDate.getTimeInMillis();
		int timeInDays = (int) ((timeInMillis / (1000 * 60 * 60 * 24)) + 1);
		return timeInDays;
	}

	private static int[] calculateAverageQuantity(GregorianCalendar startDate, GregorianCalendar endDate, Testdata data) {

		int[] average = new int[Assortment.getSize()];
		int[] subtotal = new int[Assortment.getSize()];
		int[] tempSubtotal;
		int days = calculateDays(startDate, endDate);
		GregorianCalendar tempDate = (GregorianCalendar) startDate.clone();
		
		for (int i = 0; i < days; i ++) {
			tempSubtotal = calculateQuantityPerDay(tempDate, data);
			for(int j = 0; j < tempSubtotal.length; j++) {
				subtotal[j] += tempSubtotal[j];
			}
			tempDate.add(GregorianCalendar.DAY_OF_YEAR, 1);
		}
		
		for ( int i = 0; i < Assortment.getSize(); i++){
			average[i] = subtotal[i] / days;
		}
		return average;

	}

	public static void quantityPerDay(GregorianCalendar date, Testdata data) {
		
		int quantity[] = calculateQuantityPerDay(date, data);

		System.out.println("\n");
		System.out.println("Bestand am " + date.getTime() + ":\n");
		System.out.println("Artikelnummer \t Artikelname \t Anzahl");
		

		for(int articleID = 0; articleID < Assortment.getSize();articleID++) {
			System.out.println(articleID + "\t" + Assortment.getArticleName(articleID) + "\t" + quantity[articleID]);
		}
	}

	public static void quantityOfPeriod(GregorianCalendar startDate, GregorianCalendar endDate, Testdata data) {

		int quantityBegin[] = calculateQuantityPerDay(startDate, data);
		int quantityEnd[] = calculateQuantityPerDay(endDate, data);
		int averageQuantity[] = calculateAverageQuantity(startDate, endDate, data);

		System.out.println("\n");
		System.out.println("Bestand vom " + Util.parseDate(startDate) + " bis zum " + Util.parseDate(endDate) + ":\n");
		System.out.printf("%-13s %-60s %-15s %-12s %s\n","Artikelnummer" , "Artikelname", "Anfangsbestand", "Endbestand", "Durchschnittsbestand");
		
		for(int articleID = 0; articleID < Assortment.getSize(); articleID++) {
			System.out.printf("%-13d %-60s %-15d %-12d %d\n" ,articleID, Assortment.getArticleName(articleID), quantityBegin[articleID], quantityEnd[articleID], averageQuantity[articleID]);
		}
	}

	public static void stockCourseOfPeriod(int articleID, GregorianCalendar startDate, GregorianCalendar endDate, Testdata data) {

		GregorianCalendar tempDate = (GregorianCalendar) startDate.clone();
		System.out.println("\n");
		System.out.println("Bestand vom " + Util.parseDate(startDate) + " bis zum " + Util.parseDate(endDate) + " für den Artikel '" + Assortment.getArticleName(articleID) + "':\n");
		System.out.printf("%-13s %-13s %-60s %-15s\n","Datum" , "Artikelnummer", "Artikelname", "Bestand");
		
		int days = calculateDays(startDate, endDate);
		for(int i = 0; i < days; i ++) {
			System.out.printf("%-13s %-13d %-60s %-15d\n",Util.parseDate(tempDate), articleID, Assortment.getArticleName(articleID), calculateQuantityPerDay(tempDate, data)[articleID]);
			tempDate.add(GregorianCalendar.DAY_OF_YEAR, 1);
		}
		
	}

	public static void differenceOfPeriod(GregorianCalendar startDate, GregorianCalendar endDate, Testdata data) {

		int quantityStart[] = calculateQuantityPerDay(startDate, data);
		int quantityEnd[] = calculateQuantityPerDay(endDate, data);
		System.out.println("\n");
		System.out.println("Differenzmengen vom " + Util.parseDate(startDate) + " bis zum " + Util.parseDate(endDate) + ":\n");
		System.out.printf("%-13s %-60s %-15s\n", "Artikelnummer", "Artikelname", "Differenzmenge");

		for (int articleID = 0; articleID < Assortment.getSize(); articleID++) {
			System.out.printf("%-13d %-60s %-15d\n", articleID, Assortment.getArticleName(articleID), (quantityEnd[articleID] - quantityStart[articleID]));
			/*System.out.println(articleID + 
					"\t" + Assortment.getName(articleID) + 
					"\t" + (quantityEnd[articleID] - quantityStart[articleID]));*/
		}
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
