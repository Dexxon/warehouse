package de.hsw.warehouse.model;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

public class Warehouse
{

	Location[] locations;
	public LinkedList<Transaction> transactions = new LinkedList<Transaction>();

	public Warehouse(int size, int volume)
	{
		locations = new Location[size];
		for (int i = 0; i < size; i++) {
			locations[i] = new Location(volume);
		}
	}

	public void store(int articleID, int quantity, GregorianCalendar date)
	{
		if (enoughFreeSpace(articleID, quantity)) {
			Article artikel;
			HashMap<Article, Location> artilcleToLocation = new HashMap<Article, Location>();

			for (int i = 0; i < quantity; i++) {
				artikel = new Article(articleID);
				for (int j = 0; j < locations.length; j++) {
					if (locations[j].getFreeSpace() > artikel.getVolume()) {
						locations[j].addArticle(artikel);
						artilcleToLocation.put(new Article(articleID),
								locations[j]);
						// System.out.println("Article eingelagert: " +
						// artikel.getArtikelNummer() + " in " + j +
						// " Freier Platz: " + locations[j].getFreierPlatz());
						break;
					}
				}
			}
			transactions.add(new Transaction(articleID, quantity, date));
		} else
			System.out.println("Warehouse voll!");
	}

	public void age(int articleID, int quantity, GregorianCalendar date)
	{
		HashMap<Article, Location> articleToLocation = findArticlel(articleID,
				quantity);
		if (articleToLocation.size() >= quantity) {
			// System.out.println("Anzahl Article: " +
			// zuordnungArtikelZuLagerplatz.size() + ". Zu löschen: " + anzahl);
			for (Entry<Article, Location> map : articleToLocation.entrySet()) {
				map.getValue().removeArticlel(map.getKey());
			}
			transactions.add(new Transaction(articleID, -quantity, date));
		} else {
			System.out.println("Konnte nicht auslagern!");
		}

		// System.out.println("Anzahl Article nach Auslagerung: " +
		// findeArtikel(artikelnummer).size());
	}

	public HashMap<Article, Location> findArticlel(int articleID, int quantity)
	{
		HashMap<Article, Location> map = new HashMap<Article, Location>();

		for (Location location : locations) {
			for (int i = 0; i < location.getArticle().size() && quantity > 0; i++) {
				if (location.getArticle().get(i).getArticleNumber() == articleID) {
					map.put(location.getArticle().get(i), location);
					quantity--;
				}
			}
		}
		return map;
	}

	public HashMap<Article, Location> findArticle(int articleID)
	{
		HashMap<Article, Location> map = new HashMap<Article, Location>();

		for (Location location : locations) {
			for (Article artikel : location.getArticle()) {
				if (artikel.getArticleNumber() == articleID) {
					map.put(artikel, location);
				}
			}
		}
		return map;
	}

	public boolean enoughFreeSpace(int articleId, int quantity)
	{
		int tempQuantity = quantity;
		for (Location location : locations) {
			if (location.getFreeSpace() >= Article.volumeArray[articleId])
				tempQuantity--;
		}
		return tempQuantity <= 0;
	}
}
