import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CountriesApp {
	
	private static Scanner scnr = new Scanner(System.in);
	private static Path filePath = Paths.get("countries.txt");
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		while (true) {
			System.out.print("Enter a command (list, add, quit): ");
			String command = scnr.nextLine();
			if (command.equals("quit")) {
				break;
			} else if (command.equals("list")) {
				List<Country> things = readFile();
				int i = 1; 
				for (Country thing : things) {
					System.out.println(i++ + ". " + thing);
				}
			} else if (command.equals("add")) {
				Country country = getCountryFromUser();
				System.out.println("Adding " + country);
				appendLineToFile(country);
			} else {
				System.out.println("Invalid command.");
			}
		}
		System.out.println("Goodbye.");
		scnr.close();
	}

	
	
	private static Country getCountryFromUser() {
		String name = Validator.getString(scnr, "Enter a country: ");
		int population = Validator.getPositiveInt(scnr, "Enter that country's population: ");
		return new Country(name, population);
		
	}
	
	public static void appendLineToFile(Country name) {
		String line = name.getName() + "~~~" + name.getPopulation();
		List<String> lines = Collections.singletonList(line);
		try {
			Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch (IOException e) {
			System.out.println("Unable to write to file.");
		}
	}
	
	public static List<Country> readFile() {
		try {
			List<String> lines = Files.readAllLines(filePath);
			List<Country> Countries = new ArrayList<>();
			for (String line : lines) {
				String[] parts = line.split("~~~");
				String name = parts[0];
				int population = Integer.parseInt(parts[1]);
				Countries.add(new Country(name, population));
			}
			
			return Countries;
		} catch (IOException e) {
			System.out.println("Unable to read file.");
			return new ArrayList<>();
		}
	}

}
