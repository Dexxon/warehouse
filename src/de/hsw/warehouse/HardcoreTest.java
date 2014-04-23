package de.hsw.warehouse;


import java.util.GregorianCalendar;

import de.hsw.warehouse.model.Article;
import de.hsw.warehouse.model.Transaction;
import de.hsw.warehouse.model.Warehouse;

public class HardcoreTest {

	public static Warehouse warehouse = new Warehouse(2000,20);
	
	public static void main(String[] args) 
	{
		GregorianCalendar startDate = new GregorianCalendar(2013, GregorianCalendar.AUGUST, 4);
		GregorianCalendar endDate = new GregorianCalendar(2014, GregorianCalendar.AUGUST, 4);
		createTestData(startDate, endDate, 2000);
	}
	
	public static void createTestData(GregorianCalendar startDate, GregorianCalendar endDate, int amountOfTransactions)
	{
		GregorianCalendar currentDate = (GregorianCalendar) startDate.clone();
		
		int articleID, quantity, transactionsPerDay;
		int days = (int) ((endDate.getTimeInMillis() - startDate.getTimeInMillis())/(1000*60*60*24));
		transactionsPerDay = (int) amountOfTransactions/days;
		System.out.println(days);
		
		for(int day = 0; day < days; day++)
		{
			currentDate.add(GregorianCalendar.DAY_OF_YEAR, 1);
			for(int transactions = transactionsPerDay; transactions > 0; transactions--)
			{
				articleID = (int) Math.round(Math.random() * (Article.nameArray.length - 1));
				quantity = (int) Math.round(Math.random()*5);
				System.out.println(currentDate.getTime());
				switch((int) Math.round(Math.random()))
				{
				case 1:
					if(warehouse.enoughFreeSpace(articleID, quantity))
						warehouse.store(articleID, quantity, (GregorianCalendar) currentDate.clone());
					break;
				default:
					if(warehouse.findArticlel(articleID, quantity).size() == quantity)
						warehouse.age(articleID, quantity, (GregorianCalendar) currentDate.clone());
				}
			}
		}
		
		System.out.println(warehouse.transactions.size());
		System.out.println("Artikelnummer\tAnzahl\tDatum");
		for(Transaction transaction : warehouse.transactions)
		{
			System.out.println(transaction.getArticleID() + "\t" + transaction.getQuantity() + "\t" + transaction.getDate().getTime());
		}
		
	}
	

}
