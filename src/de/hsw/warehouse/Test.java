package de.hsw.warehouse;

import java.util.GregorianCalendar;

import de.hsw.warehouse.analysis.Testdata;
import de.hsw.warehouse.model.Transaction;
import de.hsw.warehouse.model.Warehouse;

public class Test
{

	public static Warehouse warehouse = new Warehouse(2000, 20);
	public static GregorianCalendar startDate = new GregorianCalendar(2012,
			GregorianCalendar.AUGUST, 16);
	public static GregorianCalendar endDate = new GregorianCalendar(2013,
			GregorianCalendar.AUGUST, 16);

	public static void main(String[] args)
	{
		/*try {
			Article.initialiseArticlePool("C:\\ArtikelPool.csv");
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/
		Testdata testData = new Testdata(warehouse, startDate, endDate);
		
		System.out.println("Artikelnummer\tAnzahl\tDatum");
		for (Transaction transaction : testData.getTransactions()) {
			System.out.println(transaction.getArticleID() + "\t"
					+ transaction.getQuantity() + "\t"
					+ transaction.getDate().getTime());
		}
		System.out.println(testData.getTransactions().size());
		
		for (Transaction transaction : testData.getTransactionsInPeriod(startDate,endDate)){
			System.out.println(transaction);
		}
		testData.writeToDisk("C:\\Testdata\\testData.csv");
	}

}
