package st.ticketplat.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import st.ticketplat.TicketingPlatformApplication;
import st.ticketplat.domain.SeatHold;
import st.ticketplat.domain.VenueLevel;
import st.ticketplat.service.TicketService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=TicketingPlatformApplication.class)
public class TicketServiceIntegrationTest {

	@Autowired
	private TicketService ticketService;

	@Test
	public void harness(){
		String customerEmail= "test";
		findAndReserveTickets(Optional.of(VenueLevel.ORCHESTRA.getValue()),
				Optional.of(VenueLevel.MAIN.getValue()),customerEmail,2);
		findAndReserveTickets(Optional.of(VenueLevel.BALCONY1.getValue()),
				Optional.of(VenueLevel.BALCONY2.getValue()),customerEmail,2);
		holdTickets(Optional.of(VenueLevel.ORCHESTRA.getValue()),
				Optional.of(VenueLevel.MAIN.getValue()),customerEmail,2);
		holdTickets(Optional.of(VenueLevel.ORCHESTRA.getValue()),
				Optional.of(VenueLevel.MAIN.getValue()),customerEmail,2);
	}

	public void findSeats(Optional<Integer> level){
		int numSeats = ticketService.numSeatsAvailable(level);
		assertNotNull(numSeats);
		assertEquals(3,numSeats);
	}

	public void findAndReserveTickets(Optional<Integer> minLevel, Optional<Integer> maxLevel, String customerEmail,int numOfSeats){
		//hold
		SeatHold seatHold = ticketService.findAndHoldSeats(numOfSeats, minLevel,
				maxLevel, customerEmail);
		assertNotNull(seatHold);
		assertEquals(numOfSeats,seatHold.getSeats().size());
		//reserve
		String reservationCode = ticketService.reserveSeats(seatHold.getSeatHoldId(), customerEmail);
		assertNotNull(reservationCode);
	}

	public void holdTickets(Optional<Integer> minLevel, Optional<Integer> maxLevel, String customerEmail,int numOfSeats){
		SeatHold seatHold = ticketService.findAndHoldSeats(numOfSeats, minLevel,
				maxLevel, customerEmail);
		assertNotNull(seatHold);
		assertEquals(numOfSeats,seatHold.getSeats().size());
	}

}
