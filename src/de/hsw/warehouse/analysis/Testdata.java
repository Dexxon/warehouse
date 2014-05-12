package de.hsw.warehouse.analysis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import de.hsw.warehouse.model.Assortment;
import de.hsw.warehouse.model.NotEnoughArticleException;
import de.hsw.warehouse.model.NotEnoughSpaceException;
import de.hsw.warehouse.model.Transaction;
import de.hsw.warehouse.model.Warehouse;
import de.hsw.warehouse.util.Util;

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

	public Testdata(String path)
	{
		this.transactions = this.readFromDisk(path);
		this.startDate = this.transactions.getFirst().getDate();
		this.endDate = this.transactions.getLast().getDate();
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
		int fromIndex = 0;
		int toIndex = this.transactions.size();
		endOfPeriod.add(GregorianCalendar.DAY_OF_YEAR, 1);
		for(int i = 0;i < this.transactions.size(); i++){
			if(this.transactions.get(i).getDate().after(startOfPeriod) && fromIndex == 0){
				fromIndex = i;
			}
			if(this.transactions.get(i).getDate().after(endOfPeriod) && toIndex == this.transactions.size()){
				toIndex = i;
				break;
			}
		}
		return this.transactions.subList(fromIndex, toIndex);
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
						* (Assortment.getSize() - 1));

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
	
	public void writeToDisk(String path)
	{
		String[] lines = new String[this.getTransactions().size() + 1];
		lines[0] = this.warehouse.getSize() + ";"+ this.warehouse.getVolumePerLocation();
		int index = 1;
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy-HH.mm");
		for(Transaction transaction : this.transactions){
			lines[index] = transaction.getArticleID() + ";" + sdf.format(transaction.getDate().getTime()) + ";" + transaction.getQuantity() + ";";
			index++;
		}
		try {
			Util.writeToDisk(lines, path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public LinkedList<Transaction> readFromDisk(String path)
	{
		String[] lines = null;
		int quantity, articleID;
		GregorianCalendar date = new GregorianCalendar();
		LinkedList<Transaction> transactions = new LinkedList<Transaction>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy-HH.mm");
		try {
			lines = Util.readFromDisk(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < lines.length; i++) {
			articleID = Integer.parseInt(lines[i].split(";")[0]);
			try {
				date.setTime(sdf.parse(lines[i].split(";")[1]));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			quantity = Integer.parseInt(lines[i].split(";")[2]);
			transactions.add(new Transaction(articleID, quantity, (GregorianCalendar) date.clone()));
		}
		return transactions;
	}
}
