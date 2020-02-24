package planner;

import java.text.ParseException;
import java.util.Optional;
import java.util.Scanner;

/**
 * Start point for the WeeklyPlanner app.
 *
 * Allows a user to read a schedule from a file, adjust it and then print it again
 */
public class StartPlanning {
	public static void main(String[] args) {
		final String MENU_USER_MESSAGE = "Please select a number from 1 to 7 to use the menu";
		WeeklyPlanner weeklyPlanner = new WeeklyPlanner();
		weeklyPlanner.initialise();
		Scanner input = new Scanner(System.in);
		EventFactory factory = new EventFactory();
		
		int option = 0;
		printMenu();
		while(option != 7) {
			try {
				option = Integer.parseInt(retrieveUserInput());
			
				switch (option) {
					case 1:
					try {
						System.out.println(weeklyPlanner.buildCurrentPlan());
					} catch (ParseException e) {
						e.printStackTrace();
					}
						break;
					case 2:
						System.out.println("Enter new event (eg. Tuesday Appointment 14:00 - 15:00");
						String addData = retrieveUserInput();
						Optional.ofNullable(factory.getEvent(addData)).ifPresentOrElse(newEvent ->
						{
							weeklyPlanner.addEvent(newEvent.getDay(), newEvent);
						}, () -> System.out.println("Not a valid event format"));
						break;
					case 3:
						System.out.println("Choose event to delete (eg. Tuesday 14:00");
						String deleteData = retrieveUserInput();
						weeklyPlanner.deleteEvent(deleteData);
						break;
					case 4:
						weeklyPlanner.saveScheduleToFile();
						break;
					case 5:
						System.out.print("Total income for the week is expected to be " +
										 weeklyPlanner.displayIncome());
						break;
					case 6:
						printMenu();
						break;
					case 7:
						System.out.println("Bye Bye!");
						break;
					default:
						System.out.println(MENU_USER_MESSAGE);
						break;
				}
			} catch (NumberFormatException ex) {
				System.out.println(MENU_USER_MESSAGE);
			}
				
		}
		
		// Cleanup
		input.close();
	}
	
	public static String retrieveUserInput() {
		Scanner input = new Scanner(System.in);
		return input.nextLine();
	}

	public static void printMenu() {
		System.out.println("---------MENU--------");
		System.out.println("1. Print current plan");
		System.out.println("2. Add event");
		System.out.println("3. Delete event");
		System.out.println("4. Save");
		System.out.println("5. Display total income");
		System.out.println("6. Reprint menu");
		System.out.println("7. Exit");
	}
}
