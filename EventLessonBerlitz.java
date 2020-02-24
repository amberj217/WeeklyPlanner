package planner;

public class EventLessonBerlitz extends EventLesson{
	private final int STANDARD_PRICE = 13;
	private final int MAX_PRICE = 18;
	private final int MAX_PRICE_NO_LESSONS = 3;
	private static int NUMBER_LESSONS = 0;

	public EventLessonBerlitz() {
		NUMBER_LESSONS++;
	}
	
	@Override
	public int getPrice() {
		return (NUMBER_LESSONS < MAX_PRICE_NO_LESSONS) ? STANDARD_PRICE : MAX_PRICE;
	}

	@Override
	public String toString() {
		return "Berlitz";
	}


}
