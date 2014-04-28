package de.hsw.warehouse.model;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map.Entry;

public class Warehouse
{

	Location[] locations;

	public Warehouse(int size, int volume)
	{
		locations = new Location[size];
		for (int i = 0; i < size; i++) {
			locations[i] = new Location(volume);
		}
	}

	public Transaction store(int articleID, int quantity, GregorianCalendar date)
			throws WarehouseFullException
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
						break;
					}
				}
			}
			return new Transaction(articleID, quantity, date);
		} else {
			throw new WarehouseFullException("Lager voll");
		}

	}

	public Transaction age(int articleID, int quantity, GregorianCalendar date)
			throws NotEnoughArticelException
	{
		HashMap<Article, Location> articleToLocation = findArticlel(articleID,
				quantity);
		if (articleToLocation.size() >= quantity) {
			for (Entry<Article, Location> map : articleToLocation.entrySet()) {
				map.getValue().removeArticlel(map.getKey());
			}
			return new Transaction(articleID, -quantity, date);
		} else {
			throw new NotEnoughArticelException("Konnte nicht auslagern");
		}
	}

	public HashMap<Article, Location> findArticlel(int articleID, int quantity)
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

	public HashMap<Article, Location> findArticle(int articleID)
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

	public boolean enoughFreeSpace(int articleId, int quantity)
	{
		int tempQuantity = quantity;
		for (Location location : locations) {
			if (location.getFreeSpace() >= Article.volumePool[articleId])
				tempQuantity--;
		}
		return tempQuantity <= 0;
	}
}
