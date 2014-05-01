package de.hsw.warehouse.analysis;

import java.util.GregorianCalendar;
import java.util.LinkedList;

import de.hsw.warehouse.model.Article;
import de.hsw.warehouse.model.NotEnoughArticleException;
import de.hsw.warehouse.model.Transaction;
import de.hsw.warehouse.model.Warehouse;
import de.hsw.warehouse.model.NotEnoughSpaceException;

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

		int articleID, quantity, transactionsPerDay, hour, minute;

		while (currentDate.before(endDate)) {
			currentDate.add(GregorianCalendar.DAY_OF_YEAR, 1);

			if (currentDate.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SATURDAY) {
				currentDate.add(GregorianCalendar.DAY_OF_YEAR, 2);
			}

			transactionsPerDay = (int) Math.round(Math.random() * 20);
			hour = 7 + (int) (Math.round(Math.random()) * 8)
					/ (transactionsPerDay + 1);
			currentDate.set(GregorianCalendar.HOUR, hour);
			minute = (int) Math.round(Math.random() * 60);
			currentDate.set(GregorianCalendar.MINUTE, minute);

			while (transactionsPerDay > 0) {
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
		}
		System.out.println("");
	}
}
