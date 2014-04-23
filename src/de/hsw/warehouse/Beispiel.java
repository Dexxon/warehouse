package de.hsw.warehouse;

import java.util.GregorianCalendar;

import de.hsw.warehouse.model.Transaction;
import de.hsw.warehouse.model.Warehouse;

public class Beispiel
{

	public static void main(String[] args)
	{

		Warehouse lager = new Warehouse(2000, 20); // Erstellen eines Lagers mit
													// 2000 Lagerplätzen á 20
													// Platzeinheiten
		lager.store(0, 40, new GregorianCalendar());
		lager.age(0, 37, new GregorianCalendar());

		for (Transaction bewegung : lager.transactions) {
			System.out.println("Transaction: Artikelnummer: "
					+ bewegung.getArticleID() + " Anzahl: "
					+ bewegung.getQuantity() + " Datum: "
					+ bewegung.getDate().getTime() + " Volumen: "
					+ bewegung.getVolume());
		}
		System.out.println("Anzahl im Warehouse: "
				+ lager.findArticle(0).size());
		/*
		 * Transaction testBewegung = warehouse.einlagern(0, 3); Transaction
		 * einlagern = warehouse.einlagern(1, 40, new GregorianCalendar());
		 * Transaction auslagern = warehouse.auslagern(1, 40, new
		 * GregorianCalendar()); System.out.println("Einlagern: Artikelnummer: "
		 * + einlagern.getArtikelnummer() + " Anzahl: " + einlagern.getAnzahl()
		 * + " Datum: " + einlagern.getDatum().getTime());
		 * System.out.println("Auslagern: Artikelnummer: " +
		 * auslagern.getArtikelnummer() + " Anzahl: " + auslagern.getAnzahl() +
		 * " Datum: " + auslagern.getDatum().getTime());
		 */

	}

}
