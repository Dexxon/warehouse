package de.hsw.warehouse.analysis;

import java.util.GregorianCalendar;

import de.hsw.warehouse.model.Assortment;
import de.hsw.warehouse.util.Util;

public class Analysis {

	private static int[] calculateQuantityPerDay(GregorianCalendar date, Testdata data) {

		int[] quantity = new int[Assortment.getSize()];

		for (Transaction transaction : data.getTransactionsInPeriod(data.getTransactions().getFirst().getDate(), date)) {
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
		tempDate.add(GregorianCalendar.DAY_OF_YEAR, 1);
		tempDate.add(GregorianCalendar.MINUTE, -1);
		
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

	public static void turnFrequency(GregorianCalendar startDate, GregorianCalendar endDate, Testdata data){
		Integer[] disposals = new Integer[Assortment.getSize()];
		for (int i =0; i < disposals.length; i++){
			disposals[i]=0;
		}
			
		
		for (Transaction transaction : data.getTransactionsInPeriod(startDate, endDate)) {
			if (transaction.getQuantity() < 0) {
				disposals[transaction.getArticleID()] += transaction.getQuantity();
			}
		}
		int[] averageQuantity = calculateAverageQuantity(startDate, endDate, data);
		float[] turnFrequencyPerArticle = new float[Assortment.getSize()];
		System.out.printf("%-13s %-60s %-15s\n", "Artikelnummer", "Artikelname", "Umschlagsh�ufigkeit");
		for (int i = 0; i< Assortment.getSize(); i++){
			if (disposals[i] < 0 && averageQuantity[i] > 0){
			turnFrequencyPerArticle[i] = (float)disposals[i] / averageQuantity[i] * -1;
			System.out.printf("%-13d %-60s %.2f\n", i, Assortment.getArticleName(i), turnFrequencyPerArticle[i]);
			}
			else System.out.printf("%-13d %-60s" + " 0\n", i, Assortment.getArticleName(i));
		}
		
		
	}

	public static void stockCourseOfPeriod(int articleID, GregorianCalendar startDate, GregorianCalendar endDate, Testdata data) {

		if(data == null) {
			throw new NullPointerException();
		}
		
		GregorianCalendar tempDate = (GregorianCalendar) startDate.clone();
		System.out.println("\n");
		System.out.println("Bestand vom " + Util.parseDate(startDate) + " bis zum " + Util.parseDate(endDate) + " f�r den Artikel '" + Assortment.getArticleName(articleID) + "':\n");
		System.out.printf("%-13s %-13s %-60s %-15s\n","Datum" , "Artikelnummer", "Artikelname", "Bestand");
		
		int days = calculateDays(startDate, endDate);
		for(int i = 0; i < days; i ++) {
			System.out.printf("%-13s %-13d %-60s %-15d\n",Util.parseDate(tempDate), articleID, Assortment.getArticleName(articleID), calculateQuantityPerDay(tempDate, data)[articleID]);
			tempDate.add(GregorianCalendar.DAY_OF_YEAR, 1);
		}
		
	}

	public static void stockUtilizationInPeriod(GregorianCalendar startDate, GregorianCalendar endDate, Testdata data) {
		
		GregorianCalendar currentDate = (GregorianCalendar) startDate.clone();
		int utilization = 0;
		if(data.getTransactions().getFirst().getDate().before(currentDate)){
			for (Transaction transaction : data.getTransactionsInPeriod(data.getTransactions().getFirst().getDate(), currentDate)) {
				utilization += transaction.getVolume() * transaction.getQuantity();
			}
		}
		int[] volumePerDay = new int[calculateDays(currentDate, endDate)];
		System.out.printf("%-29s %-10s\n", "Datum: ", " Auslastung: ");
		for (int i = 0; i < volumePerDay.length; i++){
			int[] articleQuantities = calculateQuantityPerDay(currentDate, data);
			for (int j = 0; j < articleQuantities.length; j++){
				volumePerDay[i] += articleQuantities[j]*Assortment.getArticleVolume(j);
			}
			//System.out.println("Datum: " + Util.parseDate(startDate) + " betr�gt " + (float)volumePerDay[i]/data.getSizeOfWarehouse());
			System.out.printf("%-30s %.2f %-10s\n", Util.parseDate(currentDate), (float)volumePerDay[i]/data.getSizeOfWarehouse()*100, "%");
			utilization += volumePerDay[i];
			currentDate.add(GregorianCalendar.DAY_OF_YEAR, 1);	
		}
		//System.out.printf("Die durchschnittliche Auslastung ist: " + (float)utilization/volumePerDay.length/data.getSizeOfWarehouse() + "%\n");
		System.out.printf("\n%-19s %.2f %-10s\n", "Durchschnittliche Auslastung: ", (float)utilization/volumePerDay.length/data.getSizeOfWarehouse()*100, "%");
		System.out.println();
		
		
	}

}
