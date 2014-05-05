package de.hsw.warehouse.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Util
{
	public static GregorianCalendar[] inputDateOrPeriod()
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		try {
			input = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] inputArray = input.split("-");
		System.out.println(inputArray.length);
		ArrayList<GregorianCalendar> result = parseInputToGregorianCalendar(inputArray);
		return result.toArray(new GregorianCalendar[result.size()]);
	}
	
	private static ArrayList<GregorianCalendar> parseInputToGregorianCalendar(String input[])
	{
		ArrayList<GregorianCalendar> result = new ArrayList<GregorianCalendar>();
		SimpleDateFormat sdf = new SimpleDateFormat();
		
		for(int i = 0; i < input.length; i++){
			sdf.applyPattern("dd.MM.yyy");
			try {
			result.add(new GregorianCalendar());
			result.get(i).setTime(sdf.parse(input[i]));
			if(i != 0){
				result.get(i).add(GregorianCalendar.DAY_OF_YEAR, 1);
				result.get(i).add(GregorianCalendar.SECOND, -1);
			}
			} catch (ParseException e) {
			sdf.applyPattern("MM.yyy");
			try {
				result.get(i).setTime(sdf.parse(input[i]));
				if(i != 0){
					result.get(i).add(GregorianCalendar.MONTH, 1);
					result.get(i).add(GregorianCalendar.SECOND, -1);
				}
				} catch (ParseException e1) {
				e1.printStackTrace();
				}
			}
		}
		return result;
	}
}
