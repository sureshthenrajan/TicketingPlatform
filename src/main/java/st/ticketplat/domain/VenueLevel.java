package st.ticketplat.domain;

public enum VenueLevel {

	ORCHESTRA(1),
	MAIN(2),
	BALCONY1(3),
	BALCONY2(4);

	private Integer value;

	private VenueLevel(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value.toString();
	}

	public static VenueLevel fromId(Integer id) {
        for (VenueLevel level : VenueLevel.values()) {
            if (level.value.equals(id)) {
                return level;
            }
        }
        return null;
    }


}
