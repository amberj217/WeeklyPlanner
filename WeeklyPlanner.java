package planner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

import planner.PlannerUtils.DayOfWeek;

/**
 * Class containing all data available for the current schedule
 * and performs all relevant maintenance opperations on this data
 */
public class WeeklyPlanner {
	private final String DELIMITER = "\t|\t";
	
	// Holds the event planner data
	Map<DayOfWeek, LinkedList<Event>> oracle = 
			new HashMap<DayOfWeek, LinkedList<Event>>();
		
	/**
	 * Initialises the data available in the default file
	 */
	public void initialise() {
		File plannerFile = new File(PlannerUtils.WEEKLY_SCHEDULE_FILE_PATH);
	    if (!plannerFile.exists() || !plannerFile.isFile()) {
	    	return;
	    }

	    try (BufferedReader reader = new BufferedReader( new FileReader(plannerFile));) {
	    	ArrayList<DayOfWeek> days = new ArrayList<DayOfWeek>();
	    	
	    	// First line should be days of the week
		     Optional.ofNullable(reader.readLine()).ifPresent(daysLine -> 
		     {
		    	 // Take every element delimited by "|", trim of whitespaces,
		    	 // turn it into a Day of the week element, filter out elements
		    	 // that failed conversion and add them to the oracle
	    	     Arrays.asList(daysLine.split("\\|")).stream().map(String::trim).map(
	    	    		 DayOfWeek::toDayOfWeek).filter(day -> day != null).forEach(day -> {
	    	    			 days.add(day);
	    	    			 oracle.put(day, new LinkedList<Event>());
	    	    		 });
		     });
		     
		     // Next lines would be the appointments with the times
		     // Iterate line by line until the end
		     String line = reader.readLine();
		     while(line != null) {
		    	 String[] data = line.split("\\|");
		    	 if (data.length > 1) {
		    		 // First element is the time
		    		 if(!PlannerUtils.isTime(data[0].trim())) {
		    			 // Invalid line, no start time
		    			 continue;
		    		 }
		    		 Date startTime = PlannerUtils.getTime(data[0].trim());
		    		 EventFactory factory = new EventFactory();
		    		 for (int i = 1; i < data.length; i++) {
		    			 final int index = i - 1; // -1 to count for the time
		    			 Optional.ofNullable(factory.createEvent(
		    				PlannerUtils.EventType.toEventType(data[i].trim()))).ifPresent( event -> {
		    				    	 DayOfWeek day = days.size() > index ? days.get(index) : null;
		    						 // TODO endTime currently has no logic implemented
		    						 if(event.setData(day, startTime, startTime)) {
		    							 oracle.get(day).add(event);
		    						 }
		    					 });
		    		 }
		    	 }
		    	 line = reader.readLine();
		     }
	} catch (IOException  e) {
		    e.printStackTrace();
		}
	}
	
	/**
	 * Prints full schedule
	 */
	public String buildCurrentPlan() throws ParseException {
		StringBuilder finalTable = new StringBuilder();
		Map<Date, StringBuilder> contents = new HashMap<Date, StringBuilder>();
		
		
		// Insert the time definitions
		for (int i = 9; i < 21; i++) {
			contents.put(PlannerUtils.TIME_FORMAT.parse(i + ":00"), new StringBuilder());
		}

		// Print contents in their respective timeslots one by one
		contents.forEach((time, content) -> {  		// for each time slot
			Arrays.asList(DayOfWeek.values()).forEach(day -> {	// go through each day list and 
													// add the corresponding events
				if (oracle.get(day) != null) {
					Optional<Event> event = oracle.get(day).stream().filter(
									element -> element.getStartTime().equals(time)).findAny();
					if(event.isPresent()) {
						content.append(DELIMITER).append(event.get()).append("\t");
					} else {
						content.append(DELIMITER).append("\t\t");
					}
				}
			});
		});
		
		// Create title line in  the table
		StringBuilder title = new StringBuilder();
		Arrays.asList(DayOfWeek.values()).stream().filter(day -> 
			oracle.get(day) != null).forEachOrdered(day -> 
			title.append(DELIMITER).append(day).append("\t"));
		contents.put(PlannerUtils.TIME_FORMAT.parse("00:00"), title);
		
		// Use a sorted set to order the date keys
		SortedSet<Date> keys = new TreeSet<>(contents.keySet());

		// Collect all data registered and put it in one container
		// Filter out the times where there is no data
        keys.stream().filter(key -> !contents.get(key).toString().replace(
        		"\t", "").replace("|", "").isEmpty()).forEach(key -> finalTable.append(
        				PlannerUtils.TIME_FORMAT.format(key)).append(
        						contents.get(key)).append("\n"));
        return finalTable.toString();

	}
	
	
	public void addEvent(DayOfWeek day, Event event) {
		if (oracle.get(day) == null) {
			oracle.put(day, new LinkedList<Event>());
		}
		oracle.get(day).add(event);
	}

	/**
	 * Parses string data to extract details from event which
	 * will be deleted from the oracle
	 *
	 * @param deleteData string data to delete
	 */
	public void deleteEvent(String deleteData) {
		String[] data = deleteData.split(" ");
		Date startTime = null;
		DayOfWeek dayOfWeek = null;

		if (data.length >= 2) {
			if (PlannerUtils.isTime(data[0])) {
				startTime = PlannerUtils.getTime(data[0]);
				dayOfWeek = DayOfWeek.toDayOfWeek(data[1]);
			} else if (PlannerUtils.isTime(data[1])){
				startTime = PlannerUtils.getTime(data[1]);
				dayOfWeek = DayOfWeek.toDayOfWeek(data[0]);
			}
		}
		
		deleteEvent(dayOfWeek, startTime);
	}

	/**
	 * Deletes data from oracle
	 *
	 * @param dayOfWeek day of the event to be deleted
	 * @param startTime start time of the event to be deleted.
	 */
	public void deleteEvent(DayOfWeek dayOfWeek, Date startTime) {
		if (startTime == null || dayOfWeek == null) {
			System.out.println("Input data to delete is not valid.");
		} else {
			oracle.get(dayOfWeek).removeIf(b -> b.getStartTime().equals(startTime));
		}
	}

	/**
	 * Save schedule to a predetermined file.
	 */
	public void saveScheduleToFile() {
		File plannerFile = new File(PlannerUtils.WEEKLY_SCHEDULE_FILE_PATH);
	    if (plannerFile.exists() && plannerFile.isFile()) {
	    	plannerFile.delete();
	    }

		try {
			plannerFile.getParentFile().mkdirs();
			plannerFile.createNewFile();
		    FileWriter plannerWriter = new FileWriter(plannerFile, false);
		    String currentPlan = buildCurrentPlan();
		    plannerWriter.write(currentPlan);
		    plannerWriter.close();
		} catch (IOException | ParseException e) {
		    e.printStackTrace();
		}           
	}

	/**
	 * Takes all events and creates a total income for the week
	 */
	public int displayIncome() {
		return oracle.values().stream().mapToInt(eventList -> 
			eventList.stream().mapToInt(Event::getPrice).sum()).sum();
	}
}
