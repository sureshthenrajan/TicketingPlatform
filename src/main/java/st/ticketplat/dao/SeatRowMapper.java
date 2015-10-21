package st.ticketplat.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import st.ticketplat.domain.Seat;

/**
 * Seat RowMapper
 *
 *
 * @author Suresh Thenrajan
 *
 */
public class SeatRowMapper implements RowMapper<Seat> {

	@Override
	public Seat mapRow(ResultSet rs, int arg1) throws SQLException {
		Seat seat = new Seat();
		seat.setSeatId(rs.getInt("seat_id"));
		seat.setEventId(rs.getInt("event_id"));
		seat.setEventName(rs.getString("event_name"));
		seat.setLevelId(rs.getInt("level_id"));
		seat.setHoldId(rs.getInt("hold_id"));
		seat.setHoldTtl(rs.getTimestamp("hold_ttl"));
		seat.setRowNumber(rs.getInt("seat_row"));
		seat.setColumnNumber(rs.getInt("seat_column"));
		seat.setPrice(rs.getDouble("ticket_price"));
		seat.setReserved(rs.getBoolean("reserved"));
		return seat;
	}



}
