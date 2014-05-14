package de.hsw.warehouse;

import java.util.GregorianCalendar;

import de.hsw.warehouse.analysis.Analysis;
import de.hsw.warehouse.analysis.Testdata;
import de.hsw.warehouse.model.Warehouse;
import de.hsw.warehouse.util.Util;

public class AnalysisExample
{
	private static Warehouse warehouse = new Warehouse(2000,20);
	public static void main(String[] args)
	{
		// Eingabe des Zeitraums
		GregorianCalendar[] period = Util.inputDateOrPeriod("Bitte geben Sie den Zeitraum für die Testdatenerzeugung ein: ", new GregorianCalendar());
		
		//Initialisierung der Testdaten
		Testdata data = new Testdata(warehouse, period[0], period[1]);
		//Einlesen von Testdaten (optional)
		//Testdata date = new Testdata("C:\\Testdata.csv");
		
		//Auswertung Bestand
		period = Util.inputDateOrPeriod("Bitte geben Sie ein Datum oder einen Zeitraum ein: ", new GregorianCalendar());
		Analysis.quantityOfPeriod(period[0], period[1], data);
		
		//Auswertung Bestandsverlauf
		//period = Util.inputDateOrPeriod("Bitte geben Sie ein Datum oder einen Zeitraum ein: ", new GregorianCalendar());
		Analysis.stockCourseOfPeriod(5, period[0], period[1], data);
		
		//Auswertung Differenzmenge
		Analysis.differenceOfPeriod(period[0], period[1], data);
	}

}
