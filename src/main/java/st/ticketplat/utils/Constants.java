package st.ticketplat.utils;

public class Constants {
	public static final String SELECT_FREESEATS_QUERY = "SELECT seat_id, event_id FROM seat_hold WHERE level_id = ? and reserved = false and hold_ttl < now()";
	public static final String SELECT_FREESEATS_BETWEENLEVELS_QUERY = "SELECT seat_id, event_id, level_id, hold_id, hold_ttl, seat_row, "
			+ "seat_column, ticket_price, event_name, reserved FROM seat_hold WHERE level_id >= ? and  level_id <= ? "
			+ "and reserved = false and hold_ttl < now()";
	public static final String UPDATE_SEATHOLD_RESERVE_QUERY = "update seat_hold set reserved = true and customer_email = ? where hold_id = ?";
	public static final String INSERT_RESERVATION_QUERY = "insert into reservation (confirmation_code,customer_email,event_id,event_name,"
			+ "reserved_seats,lastupdated) values (?,?,?,?,?,now())";
	public static final String SELECT_HELDSEATS_QUERY = "SELECT seat_id, event_id, level_id, hold_id, hold_ttl, seat_row, "
			+ "seat_column, ticket_price, event_name,reserved FROM seat_hold WHERE hold_id = ? and reserved = false and hold_ttl > now()";
	public static final Integer RANDOM_INT_CEILING = 100000000;

}
