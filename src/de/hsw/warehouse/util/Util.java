package de.hsw.warehouse.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Util
{
	public static GregorianCalendar[] inputDateOrPeriod(String message, GregorianCalendar defaultDate)
	{
		System.out.print(message);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		try {
			input = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] inputArray = input.split("-");
		ArrayList<GregorianCalendar> result = parseInputToGregorianCalendar(inputArray, defaultDate);
		return result.toArray(new GregorianCalendar[result.size()]);
	}
	
	private static ArrayList<GregorianCalendar> parseInputToGregorianCalendar(String input[], GregorianCalendar defaultDate)
	{
		ArrayList<GregorianCalendar> result = new ArrayList<GregorianCalendar>();
		SimpleDateFormat sdf = new SimpleDateFormat();
		for(int i = 0; i < input.length; i++){
			sdf.applyPattern("dd.MM.yyy");
			result.add(new GregorianCalendar());
			try {
			result.get(i).setTime(sdf.parse(input[i]));
			if(input.length == 1){
				result.add((GregorianCalendar) result.get(i).clone());
				result.get(i+1).add(GregorianCalendar.DAY_OF_YEAR, 1);
				result.get(i+1).add(GregorianCalendar.SECOND, -1);
			} else if(i !=0 ){
				result.get(i).add(GregorianCalendar.DAY_OF_YEAR, 1);
				result.get(i).add(GregorianCalendar.SECOND, -1);
			}
			} catch (ParseException e) {
			sdf.applyPattern("MM.yyy");
			try {
				result.get(i).setTime(sdf.parse(input[i]));
				if(input.length == 1){
					result.add((GregorianCalendar) result.get(i).clone());
					result.get(i+1).add(GregorianCalendar.MONTH, 1);
					result.get(i+1).add(GregorianCalendar.SECOND, -1);
				} else if(i !=0 ){
					result.get(i).add(GregorianCalendar.MONTH, 1);
					result.get(i).add(GregorianCalendar.SECOND, -1);
				}
				} catch (ParseException e1) {
					result.set(i, defaultDate);
				}
			}
		}
		return result;
	}
	
	public static void writeToDisk(String[] lines, String path) throws IOException
	{
		FileWriter fileWriter = new FileWriter(path);
		for(int i = 0; i < lines.length; i++){
			fileWriter.write(lines[i]);
			fileWriter.write(System.lineSeparator());
		}
		fileWriter.close();
	}
	
	public static String[] readFromDisk(String path) throws FileNotFoundException
	{
		Scanner input = new Scanner(new File(path));
		ArrayList<String> lines = new ArrayList<String>();
		while(input.hasNext()){
			lines.add(input.nextLine() + System.lineSeparator());
		}
		input.close();
		return lines.toArray(new String[lines.size()]);
	}
	
	public static String parseDate(GregorianCalendar gc){
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.YY");
		return sdf.format(gc.getTime());
	}
	
	public static GregorianCalendar[] inputPrompt(){
		System.out.println("Geben Sie einen Zeitpunkt oder Zeitraum an: ");
		Scanner inputTime = new Scanner(System.in);
		String time = inputTime.nextLine();
		inputTime.close();
		System.out.println("Zeit eingegeben: " + time);
		time = time.trim();//entfernt LEER am anfang und Ende
		//String[] longDate = time.split(" ");
		//parseInputToGregorianCalendar(longDate, defaultDate)
		return inputDateOrPeriod(time, new GregorianCalendar());
	}
}
