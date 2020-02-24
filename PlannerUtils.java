package planner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlannerUtils {
	public final static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
	
	public static final String WEEKLY_SCHEDULE_FILE_PATH = "schedule/weeklySchedule.txt";

	enum DayOfWeek {
		MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
		
		public String toString() {
			switch(this) {
			case MONDAY:
				return "Monday";
			case TUESDAY:
				return "Tuesday";
			case WEDNESDAY:
				return "Wednesday";
			case THURSDAY:
				return "Thursday";
			case FRIDAY:
				return "Friday";
			case SATURDAY:
				return "Saturday";
			case SUNDAY:
				return "Sunday";
			}
			return null;
		}
		
		public static DayOfWeek toDayOfWeek(String dayOfWeek) {
			switch(dayOfWeek.toLowerCase()) {
			case "monday": case "mon":
				return MONDAY;
			case "tuesday":  case "tue":
				return TUESDAY;
			case "wednesday": case "wed":
				return WEDNESDAY;
			case "thursday": case "thu":
				return THURSDAY;
			case "friday": case "fri":
				return FRIDAY;
			case "saturday": case "sat":
				return SATURDAY;
			case "sunday": case "sun":
				return SUNDAY;
			}
			return null;
			}
		

	};
	
	enum EventType {
		LESSON_BERLITZ, LESSON_PRIVATE, LESSON_KERN, APPOINTMENT, MEETING;
		
		public static EventType toEventType(String eventType) {
			switch(eventType.toLowerCase()) {
			case "lesson berlitz" :
			case "berlitz":
				return LESSON_BERLITZ;
			case "lesson private":
			case "private" :
				return LESSON_PRIVATE;
			case "lesson kern":
			case "kern" :
				return LESSON_KERN;
			case "appointment":
				return APPOINTMENT;
			case "meeting":
				return MEETING;
			}
			return null;
		}
	};
	
	
	/**
	 * Verify a given string represents a valid time
	 * @param time string to be analysed
	 * @return true if complies with expected format
	 */
	public static boolean isTime(String time) {
		try {
			TIME_FORMAT.parse(time);
			return true;
		} catch (ParseException ex) {
			return false;
		}
	}

	/**
	 * @param time string to be converted in a Date format
	 * @return null if string cannot be converted
	 */
	public static Date getTime(String time) {
		Date temp = null;
		try {
			temp =(java.util.Date)TIME_FORMAT.parse(time);
		} catch (ParseException ex) {
			// Ignore exception
			temp = null;
		}
		return temp;
	}
}
