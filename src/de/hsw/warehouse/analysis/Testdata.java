package de.hsw.warehouse.analysis;

import java.util.GregorianCalendar;
import java.util.LinkedList;

import de.hsw.warehouse.model.Article;
import de.hsw.warehouse.model.NotEnoughArticelException;
import de.hsw.warehouse.model.Transaction;
import de.hsw.warehouse.model.Warehouse;
import de.hsw.warehouse.model.WarehouseFullException;

public class Testdata
{
	private Warehouse warehouse;
	private LinkedList<Transaction> transactions;
	private GregorianCalendar startDate, endDate;

	public Testdata(Warehouse warehouse, int maximumCountOfTransactions,
			GregorianCalendar startDate, GregorianCalendar endDate)
	{
		this.warehouse = warehouse;
		this.startDate = startDate;
		this.endDate = endDate;
		this.transactions = new LinkedList<Transaction>();
		generateTestData(warehouse, maximumCountOfTransactions, startDate,
				endDate);
	}

	public Warehouse getWarehouse()
	{
		return warehouse;
	}

	public LinkedList<Transaction> getTransactions()
	{
		return transactions;
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
			int maximumCountOfTransactions, GregorianCalendar startDate,
			GregorianCalendar endDate)
	{
		GregorianCalendar currentDate = (GregorianCalendar) startDate.clone();

		int articleID, quantity, transactionsPerDay;
		int days = (int) ((endDate.getTimeInMillis() - startDate
				.getTimeInMillis()) / (1000 * 60 * 60 * 24));
		transactionsPerDay = (int) maximumCountOfTransactions / days;

		for (int day = 0; day < days; day++) {
			currentDate.add(GregorianCalendar.DAY_OF_YEAR, 1);
			for (int transactions = transactionsPerDay; transactions > 0; transactions--) {
				articleID = (int) Math.round(Math.random()
						* (Article.namePool.length - 1));
				quantity = (int) Math.round(Math.random() * 5);
				switch ((int) Math.round(Math.random())) {
					case 1:
						if (warehouse.enoughFreeSpace(articleID, quantity))
							try {
								this.transactions.add(warehouse
										.store(articleID, quantity,
												(GregorianCalendar) currentDate
														.clone()));
							} catch (WarehouseFullException e) {
								System.out.println(e.getMessage());
							}
						break;
					default:
						if (warehouse.findArticlel(articleID, quantity).size() == quantity)
							try {
								this.transactions.add(warehouse
										.age(articleID, quantity,
												(GregorianCalendar) currentDate
														.clone()));
							} catch (NotEnoughArticelException e) {
								System.out.println(e.getMessage());
							}
				}
			}
		}
	}
}
