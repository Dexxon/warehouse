package de.hsw.warehouse.analysis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import de.hsw.warehouse.model.Assortment;
import de.hsw.warehouse.model.NotEnoughArticleException;
import de.hsw.warehouse.model.NotEnoughSpaceException;
import de.hsw.warehouse.model.Warehouse;
import de.hsw.warehouse.util.Util;

/**
 * Diese Klasse repr�sentiert die Testdaten. Ein Testdatensatz entspricht dabei einer {@link de.hsw.warehouse.analysis.Transaction Lagerbewegung}.
 * Au�erdem wird hier das Gesamtvolumen des {@link de.hsw.warehouse.model.Warehouse Lagers} festgehalten, welches den Testdaten zugrunde liegt, sowie Start- und Endzeitpunkt der Testdaten.
 * @author Nico Tietje, Constantin Grote
 * @version
 */
public class Testdata
{
	private LinkedList<Transaction> transactions;
	private GregorianCalendar startDate, endDate;
	private int sizeOfWarehouse;

	/**
	 * Erzeugt neue, zuf�llige Testdatens�tze. Dabei wird gepr�ft, ob das Startdatum ein arbeitsfreier Tag ist und in diesem Fall am n�chsten Arbeitstag mit der {@link de.hsw.warehouse.analysis.Testdata#generateTestData(Warehouse, GregorianCalendar, GregorianCalendar) Testdatengenerierung} begonnen.
	 * @param warehouse Das zugrunde liegende {@link de.hsw.warehouse.model.Warehouse Lager}.
	 * @param startDate Der Startzeitpunkt f�r die Erstellung von Testdatens�tzen.
	 * @param endDate Der Endzeitpunkt f�r die Erstellung von Testdatens�tzen.
	 */
	public Testdata(Warehouse warehouse, GregorianCalendar startDate,
			GregorianCalendar endDate)
	{
		this.sizeOfWarehouse = warehouse.getSize() * warehouse.getVolumePerLocation();
		
		if(startDate.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SATURDAY) {
			startDate.add(GregorianCalendar.DAY_OF_WEEK, 2);
		} else if (startDate.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SUNDAY) {
			startDate.add(GregorianCalendar.DAY_OF_WEEK, 1);
			}
		
		if(endDate.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SATURDAY) {
			endDate.add(GregorianCalendar.DAY_OF_WEEK, -1);
		} else if (endDate.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SUNDAY) {
			endDate.add(GregorianCalendar.DAY_OF_WEEK, -2);
		}
		
		this.startDate = startDate;
		this.endDate = endDate;
		this.transactions = new LinkedList<Transaction>();
		generateTestData(warehouse, startDate, endDate);
	}

	/**
	 * {@link de.hsw.warehouse.analysis.Testdata#readFromDisk(Path) Liest} bereits vorhandene Testdaten aus dem angegebenen Pfad ein.
	 * @param path Der Pfad zu der Datei, in welcher die Testdaten gespeichert sind.
	 * @throws Exception Wenn die Datei nicht vorhanden ist oder keine Testdatens�tze enth�lt.
	 */
	public Testdata(Path path) throws Exception
	{
		this.readFromDisk(path);
		this.startDate = this.transactions.getFirst().getDate();
		this.endDate = this.transactions.getLast().getDate();
	}
	
	/**
	 * Gibt das Gesamtvolumen des zugrunde liegenden {@link de.hsw.warehouse.model.Warehouse Lagers} zur�ck.
	 * @return Das Gesamtvolumen des zugrunde liegenden {@link de.hsw.warehouse.model.Warehouse Lagers}.
	 */
	public int getSizeOfWarehouse()
	{
		return this.sizeOfWarehouse;
	}

	/**
	 * Gibt eine Liste aller {@link de.hsw.warehouse.analysis.Transaction Lagerbewegungen}/Testdatens�tze zur�ck.
	 * @return Eine Liste aller Testdatens�tze.
	 */
	public LinkedList<Transaction> getTransactions()
	{
		return transactions;
	}
	
	/**
	 * Gibt eine Liste aller {@link de.hsw.warehouse.analysis.Transaction Lagerbewegungen}/Testdatens�tze zur�ck.
	 * @param startOfPeriod Startpunkt des Zeitraums.
	 * @param endOfPeriod Endpunkt des Zeitraums.
	 * @return Eine Liste aller Testdatens�tze in dem angegebenen Zeitraum.
	 */
	public List<Transaction> getTransactionsInPeriod(GregorianCalendar startOfPeriod, GregorianCalendar endOfPeriod)
	{
		int fromIndex = 0;
		int toIndex = this.transactions.size();
		int index = -1;
		for(Transaction transaction : this.transactions){
			if(transaction.getDate().compareTo(startOfPeriod) <= 0 && fromIndex == 0){
				fromIndex = index + 1;
			}
			if(transaction.getDate().compareTo(endOfPeriod) >= 0 && toIndex == this.transactions.size()){
				toIndex = index + 1;
				break;
			}
			index++;
		}
		return this.transactions.subList(fromIndex, toIndex);
	}

	/**
	 * Gibt das Startdatum der Testdaten zur�ck.
	 * @return Das Startdatum der Testdaten.
	 */
	public GregorianCalendar getStartDate()
	{
		return startDate;
	}

	/**
	 * Gibt das Enddatum der Testdaten zur�ck.
	 * @return Das Enddatum der Testdaten.
	 */
	public GregorianCalendar getEndDate()
	{
		return endDate;
	}

	/**
	 * Diese Methode generiert die einzelnen Testdatens�tze. Dabei wurden folgende Annahmen getroffen:<br>
	 * 		&emsp;1. Ein Arbeitstag startet um 7:00 Uhr und dauert 8:00 Stunden.<br>
	 * 		&emsp;2. An einem Tag finden maximal 20 {@link de.hsw.warehouse.analysis.Transaction Lagerbewegungen} statt.<br>
	 * 		&emsp;3. Eine {@link de.hsw.warehouse.analysis.Transaction Lagerbewegung} besteht aus der {@link de.hsw.warehouse.model.Warehouse#store(int, int, GregorianCalendar) Ein}- bzw. {@link de.hsw.warehouse.model.Warehouse#store(int, int, GregorianCalendar) Aus}lagerung von max. 20 {@link de.hsw.warehouse.model.Article Artikeln} eines Typen.
	 * @param warehouse Das zugrunde liegende {@link de.hsw.warehouse.model.Warehouse Lager}.
	 * @param startDate Der Startzeitpunkt f�r die Erstellung von Testdatens�tzen.
	 * @param endDate Der Endzeitpunkt f�r die Erstellung von Testdatens�tzen.
	 */
	private void generateTestData(Warehouse warehouse,
			GregorianCalendar startDate, GregorianCalendar endDate)
	{
		GregorianCalendar currentDate = (GregorianCalendar) startDate.clone();
		int articleID, quantity, transactionsPerDay;
		
		while (currentDate.before(endDate)) {
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
					}
				} else {
					try {
						this.transactions.add(warehouse.age(articleID,
								quantity,
								(GregorianCalendar) currentDate.clone()));
					} catch (NotEnoughArticleException e) {
					}
				}
				transactionsPerDay--;
			}
			
			currentDate.add(GregorianCalendar.DAY_OF_YEAR, 1);
			if (currentDate.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SATURDAY) {
				currentDate.add(GregorianCalendar.DAY_OF_YEAR, 2);
			}
		}
	}
	
	/**
	 * Hier werden die Testdaten an den angegebenen Pfad auf die Festplatte geschrieben.
	 * @param path Der Pfad zu der zu schreibenden Datei.
	 */
	public void writeToDisk(Path path)
	{
		String[] lines = new String[this.getTransactions().size() + 1];
		lines[0] = String.valueOf(this.getSizeOfWarehouse()) + ";";
		int index = 1;
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy-HH.mm");
		for(Transaction transaction : this.transactions){
			lines[index] = transaction.getArticleID() + ";" + sdf.format(transaction.getDate().getTime()) + ";" + transaction.getQuantity() + ";";
			index++;
		}
		try {
			Util.writeToDisk(lines, path);
		} catch (IOException e) {
			System.err.println("Fehler beim Schreiben auf die Festplatte.");
		}
	}
	
	/**
	 * Hier werden Testdaten von der Festplatte eingelesen.
	 * @param path Der Pfad zu der Datei, in welcher die Testdaten gespeichert sind.
	 * @throws Exception
	 */
	public void readFromDisk(Path path) throws Exception
	{
		String[] lines = null;
		int quantity, articleID;
		this.transactions = new LinkedList<>();
		GregorianCalendar date = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy-HH.mm");
		try {
			lines = Util.readFromDisk(path);
		} catch (FileNotFoundException e) {
			System.err.println("Fehler beim Einlesen der Testdaten. Stellen Sie sicher, dass Sie den richtigen Pfad angegeben haben.");
		}
		this.sizeOfWarehouse = Integer.parseInt(lines[0].split(";")[0]);
		for(int i = 1; i < lines.length; i++) {
			articleID = Integer.parseInt(lines[i].split(";")[0]);
			try {
				date.setTime(sdf.parse(lines[i].split(";")[1]));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			quantity = Integer.parseInt(lines[i].split(";")[2]);
			this.transactions.add(new Transaction(articleID, quantity, (GregorianCalendar) date.clone()));
		}
	}
}
