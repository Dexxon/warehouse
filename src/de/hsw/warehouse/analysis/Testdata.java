package de.hsw.warehouse.analysis;

import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import de.hsw.warehouse.model.Article;
import de.hsw.warehouse.model.NotEnoughArticleException;
import de.hsw.warehouse.model.NotEnoughSpaceException;
import de.hsw.warehouse.model.Transaction;
import de.hsw.warehouse.model.Warehouse;

public class Testdata
{
	private Warehouse warehouse;
	private LinkedList<Transaction> transactions;
	private GregorianCalendar startDate, endDate;

	public Testdata(Warehouse warehouse, GregorianCalendar startDate,
			GregorianCalendar endDate)
	{
		this.warehouse = warehouse;
		this.startDate = startDate;
		this.endDate = endDate;
		this.transactions = new LinkedList<Transaction>();
		generateTestData(warehouse, startDate, endDate);
	}

	public Warehouse getWarehouse()
	{
		return warehouse;
	}

	public LinkedList<Transaction> getTransactions()
	{
		return transactions;
	}
	
	public List<Transaction> getTransactionsInPeriod(GregorianCalendar startOfPeriod, GregorianCalendar endOfPeriod)
	{
		int startIndex = 0;
		int endIndex = 0;
		endOfPeriod.add(GregorianCalendar.DAY_OF_YEAR, 1);
		for(int i = 0;i < this.transactions.size(); i++){
			if(this.transactions.get(i).getDate().after(startOfPeriod) && startIndex == 0){
				startIndex = i;
			}
			if(this.transactions.get(i).getDate().after(endOfPeriod) && endIndex == 0){
				endIndex = i;
				break;
			}
		}
		return this.transactions.subList(startIndex, endIndex);
	}

	public GregorianCalendar getStartDate()
	{
		return startDate;
	}

	public GregorianCalendar getEndDate()
	{
		return endDate;
	}

	private void generateTestData(Warehouse warehouse,
			GregorianCalendar startDate, GregorianCalendar endDate)
	{
		GregorianCalendar currentDate = (GregorianCalendar) startDate.clone();
		endDate.add(GregorianCalendar.DAY_OF_YEAR, 1);
		int articleID, quantity, transactionsPerDay;

		while (currentDate.before(endDate)) {

			if (currentDate.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SATURDAY) {
				currentDate.add(GregorianCalendar.DAY_OF_YEAR, 2);
			}

			currentDate.set(GregorianCalendar.HOUR_OF_DAY, 7);
			transactionsPerDay = (int) Math.round(Math.random() * 19) + 1;
			int tempTransactionsPerDay = transactionsPerDay;

			while (transactionsPerDay > 0) {
				currentDate.add(GregorianCalendar.MINUTE, 480 / tempTransactionsPerDay );;
				quantity = (int) Math.round(Math.random() * 19) + 1;
				articleID = (int) Math.round(Math.random()
						* (Article.namePool.length - 1));

				if ((int) Math.round(Math.random()) == 1) {
					try {
						this.transactions.add(warehouse.store(articleID,
								quantity,
								(GregorianCalendar) currentDate.clone()));
					} catch (NotEnoughSpaceException e) {
						System.out.print(e.getMessage());
					}
				} else {
					try {
						this.transactions.add(warehouse.age(articleID,
								quantity,
								(GregorianCalendar) currentDate.clone()));
					} catch (NotEnoughArticleException e) {
						System.out.print(e.getMessage());
					}
				}
				transactionsPerDay--;
			}
			currentDate.add(GregorianCalendar.DAY_OF_YEAR, 1);
		}
		System.out.println("");
	}
}
