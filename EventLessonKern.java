package planner;

public class EventLessonKern extends EventLesson {
	private final int STANDARD_PRICE = 21;

	@Override
	public int getPrice() {
		return STANDARD_PRICE;
	}
	
	@Override
	public String toString() {
		return "Kern";
	}

}
