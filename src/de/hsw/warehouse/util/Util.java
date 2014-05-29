package de.hsw.warehouse.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

import de.hsw.warehouse.model.Assortment;

/**
 * Diese Klasse stellt statische Hilfsmethoden zur Verf�gung. Dazu geh�ren Eingagemethoden sowie
 * Methoden f�r das Lesen und Schreiben von Dateien auf der Festplatte.
 * 
 * @author Nico Tietje, Lorenz Surkemper, Constantin Grote
 * @version 29.05.2013
 */
public class Util
{

	/**
	 * Diese Methode fragt eine Datums- oder Datumseingabe ab. Dabei k�nnen flexibel ein Tag, ein
	 * Monat oder ein Zeitraum eingegeben werden. Es wird ein Array zur�ckgegeben, welches den
	 * eingegebenen Zeitraum repr�sentiert.
	 * 
	 * @param message Die Eingabeaufforderung
	 * @param defaultDate Wird zur�ckgegeben, wenn keine oder eine falsche Eingabe gemacht wurde.
	 * @return Ein Array, welches den eingegebenen Zeitraum darstellt. Das erste Element ist der
	 *         Startzeitpunkt, das zweite der Endzeitpunkt.
	 */
	public static ArrayList<GregorianCalendar> inputDateOrPeriod(String message,
			ArrayList<GregorianCalendar> defaultDate)
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
		if (result.get(0).after(result.get(1))) {
			result = inputDateOrPeriod("Bitte geben Sie korrekte Daten ein: ", defaultDate);
		}
		return result;
	}

	/**
	 * Diese Methode wandelt eine Datums- bzw. Zeitraumeingabe in einen Zeitraum um. Enth�lt "input"
	 * nur ein Element (Datum), wird der Zeitraum zur�ckgegeben, welcher diesen Tag widerspiegelt.
	 * 
	 * Beispiel:<br>
	 * <br>
	 * &emsp;Eingabe: 16.07.2013<br>
	 * &emsp;R�ckgabe: 16.07.2013 00:00; 16.07.2013 23:59<br>
	 * <br>
	 * &emsp;Eingabe: 07.2013<br>
	 * &emsp;R�ckgabe: 01.07.2013 00:00;31.07.2013 23:59<br>
	 * <br>
	 * &emsp;Eingabe: 16.07.2013-18.07.2013<br>
	 * &emsp;R�ckgabe: 16.07.2013 00:00;18.07.2013 23:59
	 * 
	 * @param input Die Eingabe. Ein Element entspricht dabei einem Datums-String.
	 * @param defaultDate Wird zur�ckgegeben, wenn keine oder eine falsche Eingabe gemacht wurde.
	 * @return Eine {@link java.util.ArrayList ArrayList}, welche den angegebenen Zeitraum
	 *         darstellt.
	 */
	private static ArrayList<GregorianCalendar> parseInputToGregorianCalendar(String input[],
			ArrayList<GregorianCalendar> defaultDate)
	{
		ArrayList<GregorianCalendar> result = new ArrayList<GregorianCalendar>();
		SimpleDateFormat sdf = new SimpleDateFormat();
		for (int i = 0; i < input.length; i++) {
			sdf.applyPattern("dd.MM.yyy");
			result.add(new GregorianCalendar());
			try {
				result.get(i).setTime(sdf.parse(input[i]));
				if (input.length == 1) {
					result.add((GregorianCalendar) result.get(i).clone());
					result.get(i + 1).add(GregorianCalendar.DAY_OF_YEAR, 1);
					result.get(i + 1).add(GregorianCalendar.SECOND, -1);
				} else if (i != 0) {
					result.get(i).add(GregorianCalendar.DAY_OF_YEAR, 1);
					result.get(i).add(GregorianCalendar.SECOND, -1);
				}
			} catch (ParseException e) {
				sdf.applyPattern("MM.yyy");
				try {
					result.get(i).setTime(sdf.parse(input[i]));
					if (input.length == 1) {
						result.add((GregorianCalendar) result.get(i).clone());
						result.get(i + 1).add(GregorianCalendar.MONTH, 1);
						result.get(i + 1).add(GregorianCalendar.SECOND, -1);
					} else if (i != 0) {
						result.get(i).add(GregorianCalendar.MONTH, 1);
						result.get(i).add(GregorianCalendar.SECOND, -1);
					}
				} catch (ParseException e1) {
					result = defaultDate;
				}
			}
		}
		return result;
	}

	/**
	 * Diese Methode schreibt die �bergebenen Zeilen in eine Datei.
	 * 
	 * @param lines Die zu schreibenden Zeilen.
	 * @param path Der Pfad zu der Datei, in die die Zeilen geschrieben werden.
	 * @throws IOException Wenn ein I/O Fehler auftritt.
	 */
	public static void writeToDisk(String[] lines, Path path) throws IOException
	{
		FileWriter fileWriter = new FileWriter(path.toFile());
		for (int i = 0; i < lines.length; i++) {
			fileWriter.write(lines[i]);
			fileWriter.write(System.lineSeparator());
		}
		fileWriter.close();
	}

	/**
	 * Diese Methode liest Zeilen aus einer Datei.
	 * 
	 * @param path Der Pfad zu der zu lesenden Datei.
	 * @return Ein String-Array. Jedes Element enth�lt eine Zeile der Datei.
	 * @throws FileNotFoundException Wenn die Datei nicht gefunden wird, weil z.B. ein ung�ltiger
	 *             Pfad angegeben wurde.
	 */
	public static String[] readFromDisk(Path path) throws FileNotFoundException
	{
		Scanner input = new Scanner(path.toFile());
		ArrayList<String> lines = new ArrayList<String>();
		while (input.hasNext()) {
			lines.add(input.nextLine() + System.lineSeparator());
		}
		input.close();
		return lines.toArray(new String[lines.size()]);
	}

	/**
	 * Diese Methode parst ein Datum in einen lesbaren String.
	 * 
	 * @param gc Das zu parsende Datum.
	 * @return Einen String, welcher das Datum in dem Format "dd.MM.YY" enth�lt.
	 */
	public static String parseDate(GregorianCalendar gc)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.YYYY");
		return sdf.format(gc.getTime());
	}

	/**
	 * Eingabe einer Artikelnummer. Dabei wird gepr�ft, ob diese g�lig ist.
	 * 
	 * @param defaultArticleID Wird zur�ckgegeben, wenn nichts eingegeben wird.
	 * @return Die eingegebene Artikelnummer.
	 */
	public static int inputArticleID(int defaultArticleID)
	{
		int id = -1;
		String input = "";
		do {
			System.out.print("Geben Sie die ID des gew�nschten Artikels ein (0-"
					+ (Assortment.getSize() - 1) + ")[" + defaultArticleID + "]: ");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			try {
				input = br.readLine();
				id = Integer.parseInt(input);
			} catch (IOException e) {
				System.err.println("Fehler beim Einlesen.");
				;
			} catch (NumberFormatException e1) {
				if (input.equals("")) {
					return defaultArticleID;
				}
			}
		} while (id < 0 || id >= Assortment.getSize());
		return id;
	}

	/**
	 * Eingabe eines Pfades.
	 * 
	 * @param defaultPath Wird verwendet, wenn kein oder ein falscher Pfad eingegeben wird.
	 * @return Den eingebene Pfad.
	 */
	public static Path inputPath(Path defaultPath)
	{
		System.out.print("Bitte geben Sie einen Pfad ein[" + defaultPath + "]: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		try {
			input = br.readLine();
		} catch (IOException e) {
			System.out.println("Fehler bei der Eingabe");
		}
		if (input.equals("")) {
			return defaultPath;
		}
		return Paths.get(input);
	}
}
