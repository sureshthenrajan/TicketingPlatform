package st.ticketplat.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import st.ticketplat.domain.Seat;
import st.ticketplat.domain.SeatHold;

/**
 * SeatHold Dao
 *
 *
 * @author Suresh Thenrajan
 *
 */
@Component
public interface SeatHoldDao {

	/**
	 * Get number of seats available for a venue
	 *
	 *
	 * @param venueLevel
	 * @return
	 */
	public Integer numSeatsAvailable(Optional<Integer> venueLevel);

	/**
	 * Hold the seats (using db ttl) for a customer
	 *
	 * @param seats
	 * @param customerEmail
	 * @return
	 */
	public SeatHold holdSeats(final List<Seat> seats, String customerEmail);

	/**
	 * Reserve seats for a customer, given a holdId
	 *
	 *
	 * @param seatHoldId
	 * @param customerEmail
	 * @return
	 */
	public String reserveSeats(int seatHoldId, String customerEmail);

	/**
	 *
	 *
	 *
	 * @param minLevel
	 * @param maxLevel
	 * @return
	 */
	public List<Seat> numSeatsAvailableInEachLevel(Optional<Integer> minLevel,
			Optional<Integer> maxLevel);

}
