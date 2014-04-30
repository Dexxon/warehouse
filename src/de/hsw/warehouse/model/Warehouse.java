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
			throws NotEnoughSpaceException
	{
		if (enoughFreeSpace(articleID, quantity)) {
			Article article;
			HashMap<Article, Location> articleToLocation = new HashMap<Article, Location>();

			for (int i = 0; i < quantity; i++) {
				article = new Article(articleID);
				for (int j = 0; j < locations.length; j++) {
					if (locations[j].getFreeSpace() > article.getVolume()) {
						locations[j].addArticle(article);
						articleToLocation.put(new Article(articleID),
								locations[j]);
						break;
					}
				}
			}
			return new Transaction(articleID, quantity, date);
		} else {
			throw new NotEnoughSpaceException("Lager voll");
		}

	}

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
			throw new NotEnoughArticleException("Konnte nicht auslagern");
		}
	}

	public HashMap<Article, Location> findArticle(int articleID, int quantity)
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
		for (Location location : locations) {
			if (location.getFreeSpace() >= Article.volumePool[articleId])
				quantity--;
		}
		return quantity <= 0;
	}
}
