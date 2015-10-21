package st.ticketplat.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import st.ticketplat.domain.Seat;

public class Utils {

	public static String getSeatHoldQuery(Integer holdTtlSeconds){
		return "update seat_hold set hold_ttl = TIMESTAMPADD('SECOND',"
				+ ""+holdTtlSeconds+" , CURRENT_TIMESTAMP), lastupdated = now(), hold_id = ?"
				+ "where seat_id = ? and level_id = ? and event_id = ?";
	}


	/**
	 * Pick seats to hold from the list of available
	 * seats by sorting the seats based on venueLevel.
	 *
	 *
	 * @param freeSeats
	 * @param numReqSeats
	 * @return
	 */
	public static List<Seat> pickSeatsToHold(List<Seat> freeSeats, Integer numReqSeats){
		List<Seat> holdSeats = null;
		if(!CollectionUtils.isEmpty(freeSeats)) {
			holdSeats = freeSeats.stream()
					.sorted((s1,s2) -> s1.getLevelId().compareTo(s2.getLevelId()))
					.limit(numReqSeats)
					.collect(Collectors.toList());
		}
		return holdSeats;
	}
}
