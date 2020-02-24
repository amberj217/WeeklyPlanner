package planner;


public class EventLessonPrivate extends EventLesson{
	private final int STANDARD_PRICE = 30;

	@Override
	public int getPrice() {
		return STANDARD_PRICE;
	}
	
	@Override
	public String toString() {
		return "Private";
	}
}
