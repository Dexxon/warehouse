package de.hsw_hameln.warehouse.model;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map.Entry;

import de.hsw_hameln.warehouse.analysis.Transaction;

/**
 * Diese Klasse repraesentiert ein Lager. Ein Lager enthaelt {@link de.hsw_hameln.warehouse.model.Location
 * Lagerplaetze}, in welchen wiederum {@link de.hsw_hameln.warehouse.model.Article Artikel} gelagert werden.
 * In dieser Klasse findet das ein- und auslagern von {@link de.hsw_hameln.warehouse.model.Article
 * Artikeln} stall.
 * 
 * @author Constantin Grote
 * @version 29.05.2014
 */
public class Warehouse
{
	private Location[] locations;
	private int size, volumePerLocation;

	/**
	 * @param size Die Anzahl der {@link de.hsw_hameln.warehouse.model.Location Lagerplaetze}.
	 * @param volumePerLocation Das Volumen pro {@link de.hsw_hameln.warehouse.model.Location Lagerplatz}.
	 */
	public Warehouse(int size, int volumePerLocation)
	{
		this.size = size;
		this.volumePerLocation = volumePerLocation;
		locations = new Location[size];
		for (int i = 0; i < size; i++) {
			locations[i] = new Location(volumePerLocation);
		}
	}

	/**
	 * Gibt die Anzahl der {@link de.hsw_hameln.warehouse.model.Location Lagerplaetze} des Lagers zurueck.
	 * 
	 * @return Die Anzahl der {@link de.hsw_hameln.warehouse.model.Location Lagerplaetze} des Lagers.
	 */
	public int getSize()
	{
		return this.size;
	}

	/**
	 * Gibt das Volumen der {@link de.hsw_hameln.warehouse.model.Location Lagerplaetze} des Lagers zurueck.
	 * 
	 * @return Das Volumen der {@link de.hsw_hameln.warehouse.model.Location Lagerplaetze} des Lagers.
	 */
	public int getVolumePerLocation()
	{
		return this.volumePerLocation;
	}

	/**
	 * Diese Methode lagert {@link de.hsw_hameln.warehouse.model.Article Artikel} einer bestimmten
	 * Artikelnummer in einer bestimmten Menge ein. Dabei wird kontrolliert, ob genug Platz in dem
	 * Lager vorhanden ist.
	 * 
	 * @param articleID Die Artikelnummer der einzulagernden {@link de.hsw_hameln.warehouse.model.Article
	 *            Artikel}.
	 * @param quantity Die Anzahl der einzulagernden {@link de.hsw_hameln.warehouse.model.Article Artikel}.
	 * @param date Das Datum, an den die Einlagerung stattfindet/stattgefunden hat.
	 * @return Eine {@link de.hsw_hameln.warehouse.analysis.Transaction Transaktion}, die die Einlagerung
	 *         widerspiegelt.
	 * @throws NotEnoughSpaceException Wenn nicht genug Platz fuer den
	 *             {@link de.hsw_hameln.warehouse.model.Article Artikel} im Lager vorhanden ist.
	 */
	public Transaction store(int articleID, int quantity, GregorianCalendar date)
			throws NotEnoughSpaceException
	{
		if (enoughFreeSpace(articleID, quantity)) {
			int tempQuantity = quantity;

			while (tempQuantity > 0) {
				for (int j = 0; j < locations.length && tempQuantity > 0; j++) {
					while (locations[j].getFreeSpace() > Assortment.getArticleVolume(articleID)
							&& tempQuantity > 0) {
						locations[j].addArticle(new Article(articleID));
						tempQuantity--;
					}
				}
			}
			return new Transaction(articleID, quantity, date);
		} else {
			throw new NotEnoughSpaceException();
		}

	}

	/**
	 * Diese Methode lagert {@link de.hsw_hameln.warehouse.model.Article Artikel} einer bestimmten
	 * Artikelnummer in einer bestimmten Menge aus. Dabei wird konrolliert, ob die gewuenschte Anzahl
	 * {@link de.hsw_hameln.warehouse.model.Article Artikel} in dem Lager vorhanden ist.
	 * 
	 * @param articleID Die Artikelnummer der auszulagernden {@link de.hsw_hameln.warehouse.model.Article
	 *            Artikel}.
	 * @param quantity Die Anzahl der auszulagernden {@link de.hsw_hameln.warehouse.model.Article Artikel}.
	 * @param date Das Datum, an den die Auslagerung stattfindet/stattgefunden hat.
	 * @return Eine {@link de.hsw_hameln.warehouse.analysis.Transaction Transaktion}, die die Auslagerung
	 *         widerspiegelt.
	 * @throws NotEnoughArticleException Wenn nicht genug {@link de.hsw_hameln.warehouse.model.Article
	 *             Artikel} im Lager vorhanden sind.
	 */
	public Transaction age(int articleID, int quantity, GregorianCalendar date)
			throws NotEnoughArticleException
	{
		HashMap<Article, Location> articleToLocation = findArticle(articleID, quantity);

		if (articleToLocation.size() >= quantity) {

			for (Entry<Article, Location> map : articleToLocation.entrySet()) {
				map.getValue().removeArticle(map.getKey());
			}

			return new Transaction(articleID, -quantity, date);
		} else {
			throw new NotEnoughArticleException();
		}
	}

	/**
	 * Sucht {@link de.hsw_hameln.warehouse.model.Article Artikel} mit der angegebenen Artikelnummer.
	 * 
	 * @param articleID Die Artikelnummer, deren entsprechender
	 *            {@link de.hsw_hameln.warehouse.model.Article Artikel} gesucht werden soll.
	 * @param quantity Die maximale Anzahl, nach der gesucht werden soll.
	 * @return Hashmap, in der {@link de.hsw_hameln.warehouse.model.Article Artikel}
	 *         {@link de.hsw_hameln.warehouse.model.Location Lagerplaetzen} zugeordnet werden.
	 */
	private HashMap<Article, Location> findArticle(int articleID, int quantity)
	{
		HashMap<Article, Location> map = new HashMap<Article, Location>();

		for (Location location : locations) {

			for (int i = 0; i < location.getArticles().size() && quantity > 0; i++) {
				if (location.getArticles().get(i).getArticleID() == articleID) {
					map.put(location.getArticles().get(i), location);
					quantity--;
				}
			}
		}
		return map;
	}

	/**
	 * Prueft, ob fuer den angegebenen {@link de.hsw_hameln.warehouse.model.Article Artikel} in der
	 * angegebenen Anzahl genug Platz verfuegbar ist.
	 * 
	 * @param articleID Die Artikelnummer des gewuenschten {@link de.hsw_hameln.warehouse.model.Article
	 *            Artikels}.
	 * @param quantity Die Anzahl des {@link de.hsw_hameln.warehouse.model.Article Artikels}.
	 * @return &emsp;-true, wenn genug Platz vorhanden ist.<br>
	 *         &emsp;-false, wenn nicht genug Platz vorhanden ist.
	 */
	private boolean enoughFreeSpace(int articleID, int quantity)
	{
		int quantityPerLocation;

		for (Location location : locations) {
			quantityPerLocation = 1;

			while (true) {
				if (location.getFreeSpace() >= Assortment.getArticleVolume(articleID)
						* quantityPerLocation) {
					quantity--;
					quantityPerLocation++;
				} else {
					break;
				}
			}
		}
		return quantity <= 0;
	}
}
