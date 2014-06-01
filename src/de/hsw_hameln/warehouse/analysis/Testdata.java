package de.hsw_hameln.warehouse.analysis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import de.hsw_hameln.warehouse.model.Assortment;
import de.hsw_hameln.warehouse.model.NotEnoughArticleException;
import de.hsw_hameln.warehouse.model.NotEnoughSpaceException;
import de.hsw_hameln.warehouse.model.Warehouse;
import de.hsw_hameln.warehouse.util.Util;

/**
 * Diese Klasse repraesentiert die Testdaten. Ein Testdatensatz entspricht dabei einer
 * {@link de.hsw_hameln.warehouse.analysis.Transaction Lagerbewegung}. Auﬂerdem wird hier das
 * Gesamtvolumen des {@link de.hsw_hameln.warehouse.model.Warehouse Lagers} festgehalten, welches
 * den Testdaten zugrunde liegt, sowie Start- und Endzeitpunkt der Testdaten.
 * 
 * @author Nico Tietje, Constantin Grote
 * @version 29.05.2014
 */
public class Testdata
{
	private LinkedList<Transaction> transactions;
	private GregorianCalendar startDate, endDate;
	private int sizeOfWarehouse;

	/**
	 * Erzeugt neue, zufaellige Testdatensaetze. Dabei wird geprueft, ob das Startdatum ein
	 * arbeitsfreier Tag ist und in diesem Fall am naechsten Arbeitstag mit der
	 * {@link de.hsw_hameln.warehouse.analysis.Testdata#generateTestData(Warehouse, GregorianCalendar, GregorianCalendar)
	 * Testdatengenerierung} begonnen.
	 * 
	 * @param warehouse Das zugrunde liegende {@link de.hsw_hameln.warehouse.model.Warehouse Lager}.
	 * @param startDate Der Startzeitpunkt fuer die Erstellung von Testdatensaetzen.
	 * @param endDate Der Endzeitpunkt fuer die Erstellung von Testdatensaetzen.
	 */
	public Testdata(Warehouse warehouse, GregorianCalendar startDate, GregorianCalendar endDate)
	{
		this.sizeOfWarehouse = warehouse.getSize() * warehouse.getVolumePerLocation();

		if (startDate.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SATURDAY) {
			startDate.add(GregorianCalendar.DAY_OF_WEEK, 2);
		} else if (startDate.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SUNDAY) {
			startDate.add(GregorianCalendar.DAY_OF_WEEK, 1);
		}

		if (endDate.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SATURDAY) {
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
	 * {@link de.hsw_hameln.warehouse.analysis.Testdata#readFromDisk(Path) Liest} bereits vorhandene
	 * Testdaten aus dem angegebenen Pfad ein.
	 * 
	 * @param path Der Pfad zu der Datei, in welcher die Testdaten gespeichert sind.
	 * @throws Exception Wenn die Datei nicht vorhanden ist oder keine Testdatensaetze enthaelt.
	 */
	public Testdata(Path path) throws Exception
	{
		this.readFromDisk(path);
		this.startDate = this.transactions.getFirst().getDate();
		this.endDate = this.transactions.getLast().getDate();
	}

	/**
	 * Gibt das Gesamtvolumen des zugrunde liegenden {@link de.hsw_hameln.warehouse.model.Warehouse
	 * Lagers} zurueck.
	 * 
	 * @return Das Gesamtvolumen des zugrunde liegenden
	 *         {@link de.hsw_hameln.warehouse.model.Warehouse Lagers}.
	 */
	public int getSizeOfWarehouse()
	{
		return this.sizeOfWarehouse;
	}

	/**
	 * Gibt eine Liste aller {@link de.hsw_hameln.warehouse.analysis.Transaction Lagerbewegungen}
	 * /Testdatensaetze zurueck.
	 * 
	 * @return Eine Liste aller Testdatensaetze.
	 */
	public LinkedList<Transaction> getTransactions()
	{
		return this.transactions;
	}

	/**
	 * Gibt eine Liste aller {@link de.hsw_hameln.warehouse.analysis.Transaction Lagerbewegungen}
	 * /Testdatensaetze zurueck.
	 * 
	 * @param startOfPeriod Startpunkt des Zeitraums.
	 * @param endOfPeriod Endpunkt des Zeitraums.
	 * @return Eine Liste aller Testdatensaetze in dem angegebenen Zeitraum.
	 */
	public List<Transaction> getTransactionsInPeriod(GregorianCalendar startOfPeriod,
			GregorianCalendar endOfPeriod)
	{
		int fromIndex = 0;
		int toIndex = this.transactions.size();
		int index = -1;
		for (Transaction transaction : this.transactions) {
			if (transaction.getDate().compareTo(startOfPeriod) <= 0 && fromIndex == 0) {
				fromIndex = index + 1;
			}
			if (transaction.getDate().compareTo(endOfPeriod) >= 0
					&& toIndex == this.transactions.size()) {
				toIndex = index + 1;
				break;
			}
			index++;
		}
		return this.transactions.subList(fromIndex, toIndex);
	}

	/**
	 * Gibt das Startdatum der Testdaten zurueck.
	 * 
	 * @return Das Startdatum der Testdaten.
	 */
	public GregorianCalendar getStartDate()
	{
		return this.startDate;
	}

	/**
	 * Gibt das Enddatum der Testdaten zurueck.
	 * 
	 * @return Das Enddatum der Testdaten.
	 */
	public GregorianCalendar getEndDate()
	{
		return this.endDate;
	}

	/**
	 * Diese Methode generiert die einzelnen Testdatensaetze. Dabei wurden folgende Annahmen
	 * getroffen:<br>
	 * &emsp;1. Ein Arbeitstag startet um 7:00 Uhr und dauert 8:00 Stunden.<br>
	 * &emsp;2. An einem Tag finden maximal 20 {@link de.hsw_hameln.warehouse.analysis.Transaction
	 * Lagerbewegungen} statt.<br>
	 * &emsp;3. Eine {@link de.hsw_hameln.warehouse.analysis.Transaction Lagerbewegung} besteht aus
	 * der {@link de.hsw_hameln.warehouse.model.Warehouse#store(int, int, GregorianCalendar) Ein}-
	 * bzw. {@link de.hsw_hameln.warehouse.model.Warehouse#store(int, int, GregorianCalendar) Aus}
	 * lagerung von max. 20 {@link de.hsw_hameln.warehouse.model.Article Artikeln} eines Typen.
	 * 
	 * @param warehouse Das zugrunde liegende {@link de.hsw_hameln.warehouse.model.Warehouse Lager}.
	 * @param startDate Der Startzeitpunkt fuer die Erstellung von Testdatensaetzen.
	 * @param endDate Der Endzeitpunkt fuer die Erstellung von Testdatensaetzen.
	 */
	private void generateTestData(Warehouse warehouse, GregorianCalendar startDate,
			GregorianCalendar endDate)
	{
		GregorianCalendar currentDate = (GregorianCalendar) startDate.clone();
		int articleID, quantity, transactionsPerDay;
		int minutesPerDay = 480; // hier kann die Laenge eines Arbeitstages angepasst werden
		while (currentDate.before(endDate)) {
			transactionsPerDay = (int) Math.round(Math.random() * 19) + 1; // hier kann der Wert
																			// fuer
																			// die maximale Anzahl
																			// Bewegungen pro Tag
																			// angepasst werden.
			int tempTransactionsPerDay = transactionsPerDay;
			currentDate.set(GregorianCalendar.HOUR_OF_DAY, 7); // hier kann der Beginn eines
																// Arbeitstages angepasst werden
			currentDate.clear(GregorianCalendar.MINUTE);

			while (transactionsPerDay > 0) {

				quantity = (int) Math.round(Math.random() * 19) + 1; // hier kann der Wert fuer die
																		// maximale Anzahl der
																		// Anzahl Artikel pro
																		// Bewegung angepasst
																		// werden.
				articleID = (int) Math.round(Math.random() * (Assortment.getSize() - 1));

				if ((int) Math.round(Math.random()) == 1) {
					try {
						this.transactions.add(warehouse.store(articleID, quantity,
								(GregorianCalendar) currentDate.clone()));
						currentDate.add(GregorianCalendar.MINUTE, minutesPerDay
								/ tempTransactionsPerDay);
						transactionsPerDay--;
					} catch (NotEnoughSpaceException e) {
					}
				} else {
					try {
						this.transactions.add(warehouse.age(articleID, quantity,
								(GregorianCalendar) currentDate.clone()));
						currentDate.add(GregorianCalendar.MINUTE, minutesPerDay
								/ tempTransactionsPerDay);
						transactionsPerDay--;
					} catch (NotEnoughArticleException e) {
					}
				}
			}

			currentDate.add(GregorianCalendar.DAY_OF_YEAR, 1);
			if (currentDate.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SATURDAY) {
				currentDate.add(GregorianCalendar.DAY_OF_YEAR, 2);
			}
		}
	}

	/**
	 * Hier werden die Testdaten an den angegebenen Pfad auf die Festplatte geschrieben.
	 * 
	 * @param path Der Pfad zu der zu schreibenden Datei.
	 */
	public void writeToDisk(Path path)
	{
		String[] lines = new String[this.getTransactions().size() + 1];
		lines[0] = String.valueOf(this.getSizeOfWarehouse()) + ";";
		int index = 1;
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy-HH.mm");

		for (Transaction transaction : this.transactions) {
			lines[index] = transaction.getArticleID() + ";"
					+ sdf.format(transaction.getDate().getTime()) + ";" + transaction.getQuantity()
					+ ";";
			index++;
		}

		try {
			Util.writeToDisk(lines, path);
			System.out.println("Daten erfolgreich geschrieben.");
		} catch (IOException e) {
			System.err.println("Fehler beim Schreiben auf die Festplatte.");
		}
	}

	/**
	 * Hier werden Testdaten von der Festplatte eingelesen.
	 * 
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
			System.err
					.println("Fehler beim Einlesen der Testdaten. Stellen Sie sicher, dass Sie den richtigen Pfad angegeben haben.");
		}

		this.sizeOfWarehouse = Integer.parseInt(lines[0].split(";")[0]);

		for (int i = 1; i < lines.length; i++) {
			articleID = Integer.parseInt(lines[i].split(";")[0]);
			try {
				date.setTime(sdf.parse(lines[i].split(";")[1]));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			quantity = Integer.parseInt(lines[i].split(";")[2]);
			this.transactions.add(new Transaction(articleID, quantity, (GregorianCalendar) date
					.clone()));
		}
	}
}
