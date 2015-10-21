package st.ticketplat.domain;

import java.util.Date;

public class Seat {
	private Integer seatId;
	private Integer eventId;
	private String eventName;
	private Integer levelId;
	private Double price;
	private Integer rowNumber;
	private Integer columnNumber;
	private Integer holdId;
	private Date holdTtl;
	private boolean isReserved;

	public Integer getSeatId() {
		return seatId;
	}
	public void setSeatId(Integer seatId) {
		this.seatId = seatId;
	}
	public Integer getLevelId() {
		return levelId;
	}
	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}
	public Integer getColumnNumber() {
		return columnNumber;
	}
	public void setColumnNumber(Integer columnNumber) {
		this.columnNumber = columnNumber;
	}
	public Integer getHoldId() {
		return holdId;
	}
	public void setHoldId(Integer holdId) {
		this.holdId = holdId;
	}
	public Date getHoldTtl() {
		return holdTtl;
	}
	public void setHoldTtl(Date holdTtl) {
		this.holdTtl = holdTtl;
	}

	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public boolean isReserved() {
		return isReserved;
	}
	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}
}
