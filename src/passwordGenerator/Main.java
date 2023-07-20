package passwordGenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class Main {

	static Scanner sc = new Scanner(System.in);

	public static char generateNumber() {

		String alphabet = "0123456789";
		char number;
		int rnd = new Random().nextInt(alphabet.length());
		number = alphabet.charAt(rnd);
		return number;
	}

	public static char generateSmallLetter() {

		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		char sLetter;
		int rnd = new Random().nextInt(alphabet.length());
		sLetter = alphabet.charAt(rnd);
		return sLetter;
	}

	public static char generateBigLetter() {

		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		char bLetter;
		int rnd = new Random().nextInt(alphabet.length());
		bLetter = alphabet.charAt(rnd);
		return bLetter;
	}

	public static char generateCharacter() {

		String alphabet = "!@#$?.*/";
		char character;
		int rnd = new Random().nextInt(alphabet.length());
		character = alphabet.charAt(rnd);
		return character;
	}

	public static String generatePassword(int digitNumber, boolean slChoice, boolean blChoice, boolean chrChoice,
			boolean nmbrChoice) {

		String password = "";
		char temp;
		int x = 0;
		String[] myarray = new String[4];

		if (slChoice) {
			myarray[x] = "slChoice";
			x++;
		}
		if (blChoice) {
			myarray[x] = "blChoice";
			x++;
		}
		if (chrChoice) {
			myarray[x] = "chrChoice";
			x++;
		}
		if (nmbrChoice) {
			myarray[x] = "nmbrChoice";
			x++;
		}

		if (x >= digitNumber) {
			System.out.println("Sifreniz, istediginiz kriterlere gore cok kisa !!!");

		}
		if (!(slChoice || blChoice || chrChoice || nmbrChoice)) {
			System.out.println("hic bir secim yapmadiniz! password olusturulamadi!");
		}

		for (int i = 0; i < digitNumber; i++) {

			int rndNumber = new Random().nextInt(x);
			String rndFunction = myarray[rndNumber];

			switch (rndFunction) {
			case "slChoice":
				temp = generateSmallLetter();
				password += temp;
				break;
			case "blChoice":
				temp = generateBigLetter();
				password += temp;
				break;
			case "chrChoice":
				temp = generateCharacter();
				password += temp;
				break;
			case "nmbrChoice":
				temp = generateNumber();
				password += temp;
				break;

			}

		}
		return password;
	}

	private static String generateValidPassword(int digitNumber, boolean slChoice, boolean blChoice, boolean chrChoice,
			boolean nmbrChoice) {

		String password;
		do {
			password = generatePassword(digitNumber, slChoice, blChoice, chrChoice, nmbrChoice);
		} while (!isValidPassword(password, digitNumber, slChoice, blChoice, chrChoice, nmbrChoice));

		return password;
	}

	private static void savePasswordToFile(String password) {
		try {
			FileWriter fileWriter = new FileWriter("password.txt", true);
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			fileWriter.write("Şifre: " + password + " Tarih/Saat: " + now.format(dateTimeFormatter) + "\n");
			fileWriter.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {

		while (true) {

			System.out.println("Sifre ureticiye hosgeldiniz.\n" + "1-Sifreniz kac haneli olsun");
			int digitNumber = sc.nextInt();

			System.out.println("1-Sifrenizde kucuk harf olsun mu? E/H");
			boolean slChoice = sc.next().equalsIgnoreCase("E");
			System.out.println("2-Sifrenizde buyuk harf olsun mu? E/H");
			boolean blChoice = sc.next().equalsIgnoreCase("E");
			System.out.println("3-Sifrenizde ozel karakter olsun mu? E/H");
			boolean chrChoice = sc.next().equalsIgnoreCase("E");
			System.out.println("4-Sifrenizde rakam olsun mu? E/H");
			boolean nmbrChoice = sc.next().equalsIgnoreCase("E");

			String password = generateValidPassword(digitNumber, slChoice, blChoice, chrChoice, nmbrChoice);
			System.out.println("Olusturulan Sifre: " + password);

			System.out.println("sifrenizi kaydetmek ister misiniz? (E/H)");
			boolean savePassword = sc.next().equalsIgnoreCase("E");

			if (savePassword) {
				savePasswordToFile(password);
			}

			System.out.println(
					"Cikis yapmak icin Q tusuna basin, sifrenizi tekrar oluşturmak icin herhangi bir tusa basin.");
			String choice = sc.next();
			if (choice.equalsIgnoreCase("Q")) {
				break;
			}

		}

	}

	private static boolean isValidPassword(String password, int digitNumber, boolean slChoice, boolean blChoice, boolean chrChoice,
			boolean nmbrChoice) {

		if (digitNumber != password.length()) {
			return false;
		}

		if (blChoice && !containsUpperCase(password)) {
			return false;
		}

		if (slChoice && !containsLowerCase(password)) {
			return false;
		}

		if (nmbrChoice && !containsNumber(password)) {
			return false;
		}

		if (chrChoice && !containsSpecialChar(password)) {
			return false;
		}

		return true;
	}

	private static boolean containsSpecialChar(String password) {
		String specialChars = "!@#$?.*/";
		for (char c : password.toCharArray()) {
			if (specialChars.indexOf(c) != -1) {
				return true;
			}
		}
		return false;
	}

	private static boolean containsNumber(String password) {
		for (char c : password.toCharArray()) {
			if (Character.isDigit(c)) {
				return true;
			}
		}
		return false;
	}

	private static boolean containsLowerCase(String password) {
		for (char c : password.toCharArray()) {
			if (Character.isLowerCase(c)) {
				return true;
			}
		}
		return false;
	}

	private static boolean containsUpperCase(String password) {
		for (char c : password.toCharArray()) {
			if (Character.isUpperCase(c)) {
				return true;
			}
		}
		return false;
	}

}
