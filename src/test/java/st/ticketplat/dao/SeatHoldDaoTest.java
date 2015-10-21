package st.ticketplat.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import st.ticketplat.application.TicketingPlatformApplication;
import st.ticketplat.domain.Seat;
import st.ticketplat.domain.SeatHold;
import st.ticketplat.utils.UtilsTest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=TicketingPlatformApplication.class)
public class SeatHoldDaoTest {

	@Autowired
	private SeatHoldDao seatHoldDao;

	@Test
	public void testNumSeatsAvailable(){
		Integer freeSeats = seatHoldDao.numSeatsAvailable(Optional.of(2));
		assertNotNull(freeSeats);
		assertEquals(Integer.valueOf(3),Integer.valueOf(freeSeats));
		//scenario2:
		freeSeats = seatHoldDao.numSeatsAvailable(Optional.empty());
		assertNotNull(freeSeats);
		assertEquals(Integer.valueOf(0),Integer.valueOf(freeSeats));
	}

	@Test
	public void testNumSeatsAvailableInEachLevel(){
		List<Seat> seats = seatHoldDao.numSeatsAvailableInEachLevel
				(Optional.of(2), Optional.of(4));
		assertNotNull(seats);
		assertEquals(9,seats.size());

		seats = seatHoldDao.numSeatsAvailableInEachLevel
				(Optional.of(1), Optional.of(4));
		assertNotNull(seats);
		assertEquals(12,seats.size());

		seats = seatHoldDao.numSeatsAvailableInEachLevel
				(Optional.empty(), Optional.empty());
		assertNotNull(seats);
		assertEquals(0,seats.size());

	}

	@Test
	public void testHoldAndReserveSeats(){
		String customerEmail = "test";
		SeatHold seatHold = seatHoldDao.holdSeats(UtilsTest.createHeldSeats(), customerEmail);
		assertNotNull(seatHold);
		assertEquals(2,seatHold.getSeats().size());
		String reservationId = seatHoldDao.reserveSeats(seatHold.getSeatHoldId(), "test");
		assertNull(reservationId);
		//scenario 2:
		seatHold = seatHoldDao.holdSeats(null, customerEmail);
		assertNotNull(seatHold);
		assertEquals(false,seatHold.isHoldDone());
	}

	@Test
	public void testReserveSeats_Unknown(){
		String reservationId = seatHoldDao.reserveSeats(100, "test");
		assertNull(reservationId);
		//Unknown holdId
		reservationId = seatHoldDao.reserveSeats(100987, "test");
		assertNull(reservationId);
	}





}
