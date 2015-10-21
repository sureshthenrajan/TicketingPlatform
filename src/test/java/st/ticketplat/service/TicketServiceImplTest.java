package st.ticketplat.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import st.ticketplat.dao.SeatHoldDaoImpl;
import st.ticketplat.domain.Seat;
import st.ticketplat.domain.SeatHold;
import st.ticketplat.utils.UtilsTest;



public class TicketServiceImplTest {

	private TicketServiceImpl ticketService = new TicketServiceImpl();
	private SeatHoldDaoImpl seatHoldDao = mock(SeatHoldDaoImpl.class);

	@Before
	public void setUp(){
		ReflectionTestUtils.setField(ticketService,"seatHoldDao",seatHoldDao);
	}

	@Test
	public void testNumSeatsAvailable(){
		Optional<Integer> venueLevel = Optional.of(1);
		Optional<Integer> venueLevelEmpty = Optional.empty();
		when(seatHoldDao.numSeatsAvailable(venueLevel)).thenReturn(2);
		assertEquals(ticketService.numSeatsAvailable(venueLevel),2);
		when(seatHoldDao.numSeatsAvailable(venueLevelEmpty)).thenReturn(0);
		assertEquals(ticketService.numSeatsAvailable(venueLevelEmpty),0);
	}

	@Test
	public void testFindAndHoldSeats(){
		Optional<Integer> venueLevelMin = Optional.of(1);
		Optional<Integer> venueLevelMax = Optional.of(4);
		Optional<Integer> venueLevelEmpty = Optional.empty();
		SeatHold seatHold = null;
		String customerEmail = "test";
		Integer numOfReqSeats = 2;
		List<Seat> seats = UtilsTest.createSeats();
		SeatHold testSeatHold = createSeatHold(seats);
		//Scenario-1
		when(seatHoldDao.numSeatsAvailableInEachLevel(venueLevelMin, venueLevelMax)).thenReturn(seats);
		when(seatHoldDao.holdSeats(seats, customerEmail)).thenReturn(testSeatHold);
		seatHold = ticketService.findAndHoldSeats(numOfReqSeats, venueLevelMin, venueLevelMax, customerEmail);
		assertNotNull(seatHold);
		assertEquals("test",seatHold.getCustomer_email());
		assertEquals(2,seatHold.getSeats().size());
		assertEquals("test",seatHold.getCustomer_email());
		//Scenario-2 (Empty min & max levels)
		when(seatHoldDao.numSeatsAvailableInEachLevel(venueLevelEmpty, venueLevelEmpty)).thenReturn(seats);
		when(seatHoldDao.holdSeats(seats, customerEmail)).thenReturn(testSeatHold);
		seatHold = ticketService.findAndHoldSeats(numOfReqSeats, venueLevelEmpty, venueLevelEmpty, customerEmail);
		assertNotNull(seatHold);
		assertEquals("test",seatHold.getCustomer_email());
		assertEquals(2,seatHold.getSeats().size());
		//Scenario-3(No empty seats)
		when(seatHoldDao.numSeatsAvailableInEachLevel(venueLevelMin, venueLevelMax)).thenReturn(null);
		seatHold = ticketService.findAndHoldSeats(numOfReqSeats, venueLevelMin, venueLevelMax, customerEmail);
		assertNotNull(seatHold);
		assertEquals(false,seatHold.isHoldDone());

	}

	@Test
	public void testReserveSeats(){
		String reservationCode = "123xyz";
		Integer seatHoldId = 100;
		String customerEmail = "test";
		when(seatHoldDao.reserveSeats(seatHoldId, customerEmail)).thenReturn(reservationCode);
		String code = ticketService.reserveSeats(seatHoldId, customerEmail);
		assertNotNull(code);
		assertEquals(reservationCode,code);
	}

	private SeatHold createSeatHold(List<Seat> seats){
		SeatHold seatHold = new SeatHold();
		seatHold.setCustomer_email("test");
		seatHold.setErrorMessage("none");
		seatHold.setHoldDone(true);
		seatHold.setSeats(seats);
		return seatHold;
	}





}
