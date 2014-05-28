package de.hsw.warehouse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import de.hsw.warehouse.analysis.Analysis;
import de.hsw.warehouse.analysis.Testdata;
import de.hsw.warehouse.analysis.Transaction;
import de.hsw.warehouse.model.Warehouse;
import de.hsw.warehouse.util.Util;

public class meinTest {
	public static Warehouse warehouse = new Warehouse(2000, 20);
	public static GregorianCalendar startDate = new GregorianCalendar(2012, GregorianCalendar.AUGUST, 16);
	public static GregorianCalendar endDate = new GregorianCalendar(2013, GregorianCalendar.AUGUST, 16);

	public static void main(String[] args){
		Testdata data = new Testdata(warehouse, startDate, endDate);
		//Analysis.stockUtilization(endDate, data);
		startDate.roll(Calendar.MONTH, 1);
		System.out.println(Util.parseDate(startDate));
		endDate = (GregorianCalendar) startDate.clone();
		endDate.roll(Calendar.MONTH, 1);
		System.out.println(Util.parseDate(endDate));
		//Analysis.stockUtilizationInPeriod(startDate, endDate, data);
//		//ArrayList<Transaction> trans = new ArrayList<Transaction>();
//		
//		//trans.addAll(data.getTransactionsInPeriod(startDate, endDate));
//		for (Transaction transaction : trans){
//			System.out.println(transaction.getQuantity());
//		}
//		System.out.println(trans.isEmpty());
		Analysis.turnFrequency(startDate, endDate, data);
		Analysis.stockUtilizationInPeriod(startDate, endDate, data);
	}
}
