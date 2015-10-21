package st.ticketplat.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import st.ticketplat.dao.SeatHoldDao;
import st.ticketplat.domain.Seat;
import st.ticketplat.domain.SeatHold;
import st.ticketplat.utils.Utils;


@Component
public class TicketServiceImpl implements TicketService {

	private static final Logger log = LoggerFactory.getLogger(TicketServiceImpl.class);

	@Autowired
	private SeatHoldDao seatHoldDao;

	@Override
	public int numSeatsAvailable(Optional<Integer> venueLevel) {
		return seatHoldDao.numSeatsAvailable(venueLevel);
	}

	@Override
	public SeatHold findAndHoldSeats(int numReqSeats, Optional<Integer> minLevel,
			Optional<Integer> maxLevel, String customerEmail) {
		SeatHold seatHold = new SeatHold();
		//Check if the user is already holding seats for the same event
		//if not, get free seats in levels
		List<Seat> freeSeats = seatHoldDao.numSeatsAvailableInEachLevel(minLevel, maxLevel);
		if(!CollectionUtils.isEmpty(freeSeats) && freeSeats.size() >= numReqSeats){
			//Pick seats to hold
			//Sort descending based on venueLevel, pick 'numReqSeats'
			List<Seat> holdSeats = Utils.pickSeatsToHold(freeSeats,numReqSeats);
			seatHold = seatHoldDao.holdSeats(holdSeats, customerEmail);
		}else{
			log.error("Not enough seats available to complete the hold, please choose lesser number of seats");
			seatHold.setHoldDone(false);
			seatHold.setErrorMessage("Not enough seats available to complete the hold, please choose lesser number of seats");
		}
		return seatHold;
	}

	@Override
	public String reserveSeats(int seatHoldId, String customerEmail) {
		String reservationCode = null;
		reservationCode = seatHoldDao.reserveSeats(seatHoldId, customerEmail);
		return reservationCode;
	}

}
