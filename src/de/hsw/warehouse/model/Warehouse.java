package de.hsw.warehouse.model;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map.Entry;

import de.hsw.warehouse.analysis.Transaction;

/**
 * Diese Klasse repräsentiert ein Lager. Ein Lager enthält Lagerplätze, in welchen wiederum Arikel gelagert werden.
 * In dieser Klasse findet das ein- und auslagern von Artikeln stall.
 * @author Constantin
 * @version
 */
public class Warehouse
{

	private Location[] locations;
	private int size, volumePerLocation;

	/**
	 * @param size Die Anzahl der Lagerplätze.
	 * @param volumePerLocation Das Volumen pro Lagerplatz.
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
	 * @return Die Anzahl der Lagerplätze des Lagers.
	 */
	public int getSize()
	{
		return size;
	}

	/**
	 * @return Das Volumen der Lagerplätze des Lagers.
	 */
	public int getVolumePerLocation()
	{
		return volumePerLocation;
	}

	/**
	 * Diese Methode lagert Artikel einer bestimmten Artikelnummer in einer bestimmten Menge ein.
	 * Dabei wird konrolliert, ob genug Platz in dem Lager vorhanden ist.
	 * @param articleID Die Artikelnummer der einzulagernden Artikel.
	 * @param quantity Die Anzahl der einzulagernden Artikel.
	 * @param date Das Datum, an den die Einlagerung stattfindet/stattgefunden hat.
	 * @return Eine {@link de.hsw.warehouse.analysis.Transaction Transaktion}, die die Einlagerung widerspiegelt.
	 * @throws NotEnoughSpaceException Wenn nicht genug Platz für den Artikel im Lager vorhanden ist.
	 */
	public Transaction store(int articleID, int quantity, GregorianCalendar date)
			throws NotEnoughSpaceException
	{
		if (enoughFreeSpace(articleID, quantity)) {
			int tempQuantity = quantity;
			
			while(tempQuantity > 0){
				for (int j = 0; j < locations.length && tempQuantity > 0; j++) {
					while(locations[j].getFreeSpace() > Assortment.getArticleVolume(articleID) && tempQuantity > 0){
						locations[j].addArticle(new Article(articleID));
						tempQuantity--;
						//System.out.println("Eingelagert: " + articleID + " in " + j);
					}
				}
			}
			return new Transaction(articleID, quantity, date);
		} else {
			throw new NotEnoughSpaceException();
		}

	}

	/**
	 * Diese Methode lagert Artikel einer bestimmten Artikelnummer in einer bestimmten Menge aus.
	 * Dabei wird konrolliert, ob die gewünschte Anzahl Artikel in dem Lager vorhanden ist.
	 * @param articleID Die Artikelnummer der auszulagernden Artikel.
	 * @param quantity Die Anzahl der auszulagernden Artikel.
	 * @param date Das Datum, an den die Auslagerung stattfindet/stattgefunden hat.
	 * @return Eine {@link de.hsw.warehouse.analysis.Transaction Transaktion}, die die Auslagerung widerspiegelt.
	 * @throws NotEnoughArticleException Wenn nicht genug Artikel im Lager vorhanden sind.
	 */
	public Transaction age(int articleID, int quantity, GregorianCalendar date)
			throws NotEnoughArticleException
	{
		HashMap<Article, Location> articleToLocation = findArticle(articleID,
				quantity);
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
	 * Sucht Artikel mit der angegebenen Artikelnummer.
	 * @param articleID Die Artikelnummer, deren entsprechender Artikel gesucht werden soll.
	 * @param quantity Die maximale Anzahl, nach der gesucht werden soll.
	 * @return Hashmap, in der Artikel Lagerplätzen zugeordnet werden.
	 */
	private HashMap<Article, Location> findArticle(int articleID, int quantity)
	{
		HashMap<Article, Location> map = new HashMap<Article, Location>();

		for (Location location : locations) {
			for (int i = 0; i < location.getArticle().size() && quantity > 0; i++) {
				if (location.getArticle().get(i).getArticleID() == articleID) {
					map.put(location.getArticle().get(i), location);
					quantity--;
				}
			}
		}
		return map;
	}

	/**
	 * Sucht Artikel mit der angegebenen Artikelnummer.
	 * @param articleID Die Artikelnummer, deren entsprechender Artikel gesucht werden soll.
	 * @return Hashmap, in der Artikel Lagerplätzen zugeordnet werden.
	 */
	private HashMap<Article, Location> findArticle(int articleID)
	{
		HashMap<Article, Location> map = new HashMap<Article, Location>();

		for (Location location : locations) {
			for (Article artikel : location.getArticle()) {
				if (artikel.getArticleID() == articleID) {
					map.put(artikel, location);
				}
			}
		}
		return map;
	}

	/**
	 * Prüft, ob für den angegebenen Artikel in der angegebenen Anzahl genug Platz verfügbar ist.
	 * @param articleID Die Artikelnummer des gewünschten Artikels.
	 * @param quantity Die Anzahl des Artikels.
	 * @return true, wenn genug Platz vorhanden ist.
	 */
	private boolean enoughFreeSpace(int articleID, int quantity)
	{
		int quantityPerLocation;
		for (Location location : locations) {
			quantityPerLocation = 1;
			while(true){
				if (location.getFreeSpace() >= Assortment.getArticleVolume(articleID) * quantityPerLocation){
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
