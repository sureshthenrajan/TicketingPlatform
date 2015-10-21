package st.ticketplat.resource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import st.ticketplat.domain.SeatHold;
import st.ticketplat.service.TicketService;


/***
 * Rest resource for ticketing service
 *
 *
 *
 * @author Suresh Thenrajan
 *
 */
@RequestMapping("/tickets")
@EnableAutoConfiguration
@RestController
public class TicketResource {

	@Autowired
	private TicketService ticketService;

	/**
	 * Heart Beat for availability check
	 *
	 *
	 * @return
	 */
	@RequestMapping(value = "/heartBeat", method = RequestMethod.GET)
	public @ResponseBody Map<String,String> heartBeat(){
		Map<String,String> response = new HashMap<String,String>();
		response.put("heartBeat","Ok");
		response.put("Code","200");
		return response;
	}


	/**
	 * Get number of available seats for a venue
	 *
	 * @param venueLevel
	 * @return
	 */
	@RequestMapping(value = "/numSeatsAvailable", method = RequestMethod.GET)
	public @ResponseBody int numSeatsAvailable(@RequestParam(value = "level", required = true) int venueLevel){
		return ticketService.numSeatsAvailable(Optional.of(venueLevel));
	}

	/**
	 * Find availability for venues and hold the seats
	 *
	 *
	 * @param numReqSeats
	 * @param minLevel
	 * @param maxLevel
	 * @param customerEmail
	 * @return
	 */
	@RequestMapping(value = "/findAndHoldSeats", method = RequestMethod.POST)
	public @ResponseBody SeatHold findAndHoldSeats(@RequestParam(value = "numReqSeats", required = true) int numReqSeats,
			@RequestParam(value = "minLevel", required = false) int minLevel,
			@RequestParam(value = "maxLevel", required = false) int maxLevel,
			@RequestParam(value = "customerEmail", required = true) String customerEmail){
		return ticketService.findAndHoldSeats(numReqSeats, Optional.of(minLevel), Optional.of(maxLevel), customerEmail);
	}


	/**
	 * Reserve seats for a customer
	 *
	 *
	 * @param seatHoldId
	 * @param customerEmail
	 * @return
	 */
	@RequestMapping(value = "/reserveSeats" , method = RequestMethod.POST)
	public @ResponseBody Map<String,String> reserveSeats(@RequestParam(value = "seatHoldId", required = true) int seatHoldId,
			@RequestParam(value = "customerEmail", required = true) String customerEmail){
		Map<String,String> response = new HashMap<String,String>();
		String reservationCode = ticketService.reserveSeats(seatHoldId, customerEmail);
		if(null == reservationCode){
			response.put("Error","Reservation unsuccessful:No seats held for this user");
			response.put("statusCode","406");
			response.put("reservationCode","");
		}else{
			response.put("statusCode","200");
			response.put("reservationCode",reservationCode);
		}

		return response;
	}

	@ExceptionHandler({IllegalStateException.class,IllegalArgumentException.class})
	void handleIllegalArgumentException(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}
}
