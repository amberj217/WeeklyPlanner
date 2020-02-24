package planner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import planner.PlannerUtils.DayOfWeek;
import planner.PlannerUtils.EventType;

/**
 * Parses user input into an event 
 */
public class EventFactory {
	public Event getEvent(String eventData) {
		List<String> data = Arrays.asList(eventData.split(" "));
		Date startTime = null;
		Date endTime = null;
		DayOfWeek dayOfWeek = null;
		EventType eventType = null;

		for (String element : data) {
			if (PlannerUtils.isTime(element)) {
				if (startTime == null) {
					startTime = PlannerUtils.getTime(element);
				} else if (endTime == null) {
					endTime = PlannerUtils.getTime(element);
				}
				continue;
			}
			// TODO use optional
			if(DayOfWeek.toDayOfWeek(element) != null) {
				dayOfWeek = DayOfWeek.toDayOfWeek(element);
				continue;
			}
		//	Optional.of(DayOfWeek.toDayOfWeek(element)).ifPresent(value -> {dayOfWeek = value;});
			if (EventType.toEventType(element) != null) {
				eventType = EventType.toEventType(element);
				continue;
			} 
		}
//		Optional<Event> event = Optional.ofNullable(createEvent(eventType));
//		event.ifPresent(eventResult -> eventResult.setData(dayOfWeek, startTime, endTime));
		Event event = createEvent(eventType);
		if (event != null) {
			event.setData(dayOfWeek, startTime, endTime);
		}
		
		
		return event;
	}
	
	public Event createEvent(EventType eventType) {
		if (eventType == null) {
			return null;
		}
		switch (eventType) {
		case APPOINTMENT:
			return new EventAppointment();
		case LESSON_BERLITZ:
			return new EventLessonBerlitz();
		case LESSON_PRIVATE:
			return new EventLessonPrivate();
		case LESSON_KERN:
			return new EventLessonKern();
		case MEETING:
			return new EventMeeting();
		default:
			return null;
		}
	}
	
}
