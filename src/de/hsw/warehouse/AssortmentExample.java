package de.hsw.warehouse;

import de.hsw.warehouse.model.Assortment;

public class AssortmentExample
{

	public static void main(String[] args)
	{
		//Gr��e des Artikelpools
		System.out.println("Gr��e des Artikelpools: " + Assortment.getSize());
		
		//Name eines Artikels
		System.out.println("Artikel mit Nummer 3: " + Assortment.getArticleName(3));
		
		//Warengruppe eines Artikels:
		System.out.println("Warengruppe: " + Assortment.getArticleCommodityGroup(3));
		
		//Volumen eines Artikels:
		System.out.println("Volumen: " + Assortment.getArticleVolume(3));
	}

}
