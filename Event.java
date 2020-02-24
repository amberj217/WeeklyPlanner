package planner;

import java.util.Date;

import planner.PlannerUtils.DayOfWeek;

abstract class Event {
	private DayOfWeek dayOfWeek;
	private Date startTime;
	private Date endTime;

	public boolean setData(DayOfWeek dayOfWeek, Date startTime, Date endTime) {
		if (dayOfWeek == null || startTime == null || endTime == null) {
			return false;
		}
		
		this.dayOfWeek = dayOfWeek;
		this.startTime = startTime;
		this.endTime = endTime;
		return true;
	}

	public abstract int getPrice();
	
	
	public DayOfWeek getDay() {
		return dayOfWeek;
	}
	
	public void setDay(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	
	
	public Date getStartTime() {
		return startTime;
	}
	
	public void setDay(Date startTime) {
		this.startTime = startTime;
	}
	
	
	public Date getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Date endTime) {
		 this.endTime = endTime;
	}
}
