package DB;
public class Trip {

        private String destination;
        private String airline;
        private String flightNo;
        private double price;
        private String departureDate;
        private String departureTime;
        private String arrivalDate;
        private String arrivalTime;
        private String hotelName;
        private double hotelPrice;
        private String hotelAddress;
        private double backPrice;
        private String backDepartureDate;
        private String backDepartureTime;
        private String backArrivalDate;
        private String backArrivalTime;
        private String backAirline;
        private String backFlightNo;
        private String origin;

//    Object[] values = {
//            destination, airline, flightNo, price, departureDate, departureTime,
//            arrivalDate, arrivalTime, hotelName, hotelPrice, hotelAddress,
//            backPrice, backDepartureDate, backDepartureTime, backArrivalDate,
//            backArrivalTime,backAirline,backFlightNo, origin
//    };


    public Trip(Object[] values) {
        this.destination = (String) values[0];
        this.airline = (String) values[1];
        this.flightNo = (String) values[2];
        this.price = (double) values[3];
        this.departureDate = (String) values[4];
        this.departureTime = (String) values[5];
        this.arrivalDate = (String) values[6];
        this.arrivalTime = (String) values[7];
        this.hotelName = (String) values[8];
        this.hotelPrice = (double) values[9];
        this.hotelAddress = (String) values[10];
        this.backPrice = (double) values[11];
        this.backDepartureDate = (String) values[12];
        this.backDepartureTime = (String) values[13];
        this.backArrivalDate = (String) values[14];
        this.backArrivalTime = (String) values[15];
        this.backAirline = (String) values[16];
        this.backFlightNo = (String) values[17];
        this.origin = (String) values[18];
    }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public String getAirline() {
            return airline;
        }

        public void setAirline(String airline) {
            this.airline = airline;
        }

        public String getFlightNo() {
            return flightNo;
        }

        public void setFlightNo(String flightNo) {
            this.flightNo = flightNo;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getDepartureDate() {
            return departureDate;
        }

        public void setDepartureDate(String departureDate) {
            this.departureDate = departureDate;
        }

        public String getDepartureTime() {
            return departureTime;
        }

        public void setDepartureTime(String departureTime) {
            this.departureTime = departureTime;
        }

        public String getArrivalDate() {
            return arrivalDate;
        }

        public void setArrivalDate(String arrivalDate) {
            this.arrivalDate = arrivalDate;
        }

        public String getArrivalTime() {
            return arrivalTime;
        }

        public void setArrivalTime(String arrivalTime) {
            this.arrivalTime = arrivalTime;
        }

        public String getHotelName() {
            return hotelName;
        }

        public void setHotelName(String hotelName) {
            this.hotelName = hotelName;
        }

        public double getHotelPrice() {
            return hotelPrice;
        }

        public void setHotelPrice(double hotelPrice) {
            this.hotelPrice = hotelPrice;
        }

        public String getHotelAddress() {
            return hotelAddress;
        }

        public void setHotelAddress(String hotelAddress) {
            this.hotelAddress = hotelAddress;
        }

        public double getBackPrice() {
            return backPrice;
        }

        public void setBackPrice(double backPrice) {
            this.backPrice = backPrice;
        }

        public String getBackDepartureDate() {
            return backDepartureDate;
        }

        public void setBackDepartureDate(String backDepartureDate) {
            this.backDepartureDate = backDepartureDate;
        }

        public String getBackDepartureTime() {
            return backDepartureTime;
        }

        public void setBackDepartureTime(String backDepartureTime) {
            this.backDepartureTime = backDepartureTime;
        }

        public String getBackArrivalDate() {
            return backArrivalDate;
        }

        public void setBackArrivalDate(String backArrivalDate) {
            this.backArrivalDate = backArrivalDate;
        }

        public String getBackArrivalTime() {
            return backArrivalTime;
        }

        public void setBackArrivalTime(String backArrivalTime) {
            this.backArrivalTime = backArrivalTime;
        }

        public String getBackAirline() {
            return backAirline;
        }

        public void setBackAirline(String backAirline) {
            this.backAirline = backAirline;
        }

        public String getBackFlightNo() {
            return backFlightNo;
        }

        public void setbackFlightNo(String backFlightNo) {
            this.backFlightNo = backFlightNo;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }
    }
