package st.ticketplat.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import st.ticketplat.domain.Seat;
import st.ticketplat.domain.VenueLevel;

public class UtilsTest {

	@Test
	public void testpickSeatsToHold(){
		List<Seat> seatsPicked = Utils.pickSeatsToHold(createSeats(),2);
		assertNotNull(seatsPicked);
		assertEquals(2,seatsPicked.size());
		assertEquals(VenueLevel.ORCHESTRA.getValue(),seatsPicked.get(0).getLevelId());
		assertEquals(VenueLevel.MAIN.getValue(),seatsPicked.get(1).getLevelId());
		assertNull(Utils.pickSeatsToHold(null,2));
	}

	@Test
	public void testGetSeatHoldQuery(){
		assertNotNull(Utils.getSeatHoldQuery(100));
		assertNotNull(Utils.getSeatHoldQuery(null));
	}

	public static List<Seat> createSeats(){
		List<Seat> seats = new ArrayList<Seat>();
		Seat seat1L1 = new Seat();
		seat1L1.setSeatId(10);
		seat1L1.setEventId(100);
		seat1L1.setColumnNumber(10);
		seat1L1.setRowNumber(1);
		seat1L1.setEventName("beethoven");
		seat1L1.setHoldId(1000);
		seat1L1.setHoldTtl(new Date());
		seat1L1.setReserved(false);
		seat1L1.setPrice(100.00);
		seat1L1.setLevelId(VenueLevel.ORCHESTRA.getValue());
		seats.add(seat1L1);
		Seat seat2L1 = new Seat();
		seat2L1.setSeatId(11);
		seat2L1.setEventId(100);
		seat2L1.setColumnNumber(11);
		seat2L1.setRowNumber(1);
		seat2L1.setEventName("beethoven");
		seat2L1.setHoldId(1001);
		seat2L1.setHoldTtl(new Date());
		seat2L1.setReserved(false);
		seat2L1.setPrice(100.00);
		seat2L1.setLevelId(VenueLevel.MAIN.getValue());
		seats.add(seat2L1);

		return seats;

	}

	public static List<Seat> createHeldSeats(){
		// gets a calendar using the default time zone and locale.
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, 300);
		Date holdTtl = calendar.getTime();

		List<Seat> seats = new ArrayList<Seat>();
		Seat seat1L1 = new Seat();
		seat1L1.setSeatId(10);
		seat1L1.setEventId(100);
		seat1L1.setColumnNumber(10);
		seat1L1.setRowNumber(1);
		seat1L1.setEventName("beethoven");
		seat1L1.setHoldId(1000);
		seat1L1.setHoldTtl(holdTtl);
		seat1L1.setReserved(false);
		seat1L1.setPrice(100.00);
		seat1L1.setLevelId(VenueLevel.ORCHESTRA.getValue());
		seats.add(seat1L1);
		Seat seat2L1 = new Seat();
		seat2L1.setSeatId(11);
		seat2L1.setEventId(100);
		seat2L1.setColumnNumber(11);
		seat2L1.setRowNumber(1);
		seat2L1.setEventName("beethoven");
		seat2L1.setHoldId(1001);
		seat2L1.setHoldTtl(holdTtl);
		seat2L1.setReserved(false);
		seat2L1.setPrice(100.00);
		seat2L1.setLevelId(VenueLevel.MAIN.getValue());
		seats.add(seat2L1);

		return seats;

	}

}
