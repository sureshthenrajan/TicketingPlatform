package st.ticketplat.domain;

import java.util.List;
import java.util.Map;

public class SeatHold {
	private Integer seatHoldId;
	private List<Seat> seats;
	private String customer_email;
	private String errorMessage;
	private boolean holdDone;

	public String getCustomer_email() {
		return customer_email;
	}

	public void setCustomer_email(String customer_email) {
		this.customer_email = customer_email;
	}

	public boolean isHoldDone() {
		return holdDone;
	}

	public void setHoldDone(boolean holdDone) {
		this.holdDone = holdDone;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public SeatHold(){

	}

	public Integer getSeatHoldId() {
		return seatHoldId;
	}
	public void setSeatHoldId(Integer seatHoldId) {
		this.seatHoldId = seatHoldId;
	}




}
