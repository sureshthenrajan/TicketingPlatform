package st.ticketplat.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import st.ticketplat.domain.Seat;
import st.ticketplat.domain.SeatHold;
import st.ticketplat.domain.VenueLevel;
import st.ticketplat.utils.Constants;
import st.ticketplat.utils.Utils;

/**
 * SeatHolDao impl
 *
 *
 * @author Suresh Thenrajan
 *
 */
@Component
public class SeatHoldDaoImpl implements SeatHoldDao {

	private static final Logger log = LoggerFactory.getLogger(SeatHoldDaoImpl.class);

	@Autowired
	private JdbcTemplate h2JdbcTemplate;

	@Value("${hold.ttl.seconds}")
	private Integer holdTtlSeconds;

	private Random randomGenerator = new Random();

	@Override
	public Integer numSeatsAvailable(Optional<Integer> venueLevel) {
		log.info("Querying for free seats given venue level = 'venueLevel':"+venueLevel.orElse(0));
		List<Map<String, Object>> rows = h2JdbcTemplate.queryForList( Constants.SELECT_FREESEATS_QUERY,
				new Object[] {venueLevel.orElse(0)}
				);
		return rows.size();
	}

	@Override
	public List<Seat> numSeatsAvailableInEachLevel(Optional<Integer> minLevel,
			Optional<Integer> maxLevel){
		String numSeatsQuery = Constants.SELECT_FREESEATS_BETWEENLEVELS_QUERY;
		List<Seat> seats = h2JdbcTemplate.query(
				numSeatsQuery, new Object[] { minLevel.orElse(VenueLevel.ORCHESTRA.getValue()),
						maxLevel.orElse(VenueLevel.BALCONY2.getValue())},new SeatRowMapper()
				);
		return seats;
	}

	@Override
	public SeatHold holdSeats(final List<Seat> seats, String customerEmail){
		log.info("ttl from props: "+holdTtlSeconds);
		SeatHold seatHold = new SeatHold();
		if(!CollectionUtils.isEmpty(seats)){
			final String holdSeatsSql = Utils.getSeatHoldQuery(holdTtlSeconds);
			final Integer holdId = randomGenerator.nextInt(Constants.RANDOM_INT_CEILING);
			log.info("holdId: "+holdId);
			seats.parallelStream().forEach(
					(seat) -> {
						//Add to batch
						h2JdbcTemplate.batchUpdate(holdSeatsSql, new BatchPreparedStatementSetter() {
							@Override
							public void setValues(PreparedStatement ps, int i) throws SQLException {
								ps.setInt(1, holdId);
								ps.setInt(2, seat.getSeatId());
								ps.setInt(3, seat.getLevelId());
								ps.setInt(4, seat.getEventId() );
							}
							@Override
							public int getBatchSize() {
								return seats.size();
							}

						});
					});
			seatHold.setHoldDone(true);
			seatHold.setSeatHoldId(holdId);
			seatHold.setSeats(seats);
			seatHold.setCustomer_email(customerEmail);
		}else{
			seatHold.setHoldDone(false);
			seatHold.setErrorMessage("Error in holding, no seats provided for hold");
			seatHold.setSeats(null);
		}
		return seatHold;
	}



	@Transactional
	@Override
	public String reserveSeats(int seatHoldId, String customerEmail)  {
		String reservationCode =  null;
		//Get all seats that belongs to seatHoldId
		try {
			reservationCode =  UUID.randomUUID().toString();
			log.info("reservationCode: "+reservationCode);
			List<Seat> seats = h2JdbcTemplate.query(
					Constants.SELECT_HELDSEATS_QUERY, new Object[] { seatHoldId },new SeatRowMapper()
					);
			if(CollectionUtils.isEmpty(seats)){
				log.error("No seats held for this user :"+customerEmail);
				return null;
			}
			final Seat seat  = seats.get(0);
			//Update seat_hold
			h2JdbcTemplate.update(Constants.UPDATE_SEATHOLD_RESERVE_QUERY, new Object[] { customerEmail, seatHoldId});
			//insert into reservation
			h2JdbcTemplate.update(Constants.INSERT_RESERVATION_QUERY, new Object[]
					{ reservationCode, customerEmail,seat.getEventId(),seat.getEventName(),seats.size()});
		} catch (Exception e) {
			log.error("reserveSeats error: ",e);
		}

		return reservationCode;
	}

}
