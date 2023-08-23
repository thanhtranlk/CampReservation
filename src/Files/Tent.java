package Files;


import java.util.Calendar;
import java.util.GregorianCalendar;

public class Tent extends CampSite {

    private int numberOfTenters;

    public Tent() {
    }

    public Tent(String guestName,
                GregorianCalendar checkIn,
                GregorianCalendar estimatedCheckOut,
                GregorianCalendar actualCheckOut,
                int numberOfTenters) {
        super(guestName, checkIn, estimatedCheckOut, actualCheckOut);
        this.numberOfTenters = numberOfTenters;
    }

    public int getNumberOfTenters() {
        return numberOfTenters;
    }

    public void setNumberOfTenters(int numberOfTenters) {
        this.numberOfTenters = numberOfTenters;
    }

    @Override
    public double getCost() {
        double cost = 0;

        // You have not checked out, so use just
        // the estimatedCheckout date.
        if (actualCheckOut == null) {
            GregorianCalendar gTemp = (GregorianCalendar) estimatedCheckOut.clone();
            while (gTemp.after(checkIn)) {
                cost += 10;
                gTemp.add(Calendar.DATE, -1);
            }
            return cost;
        } else {

            // You have checked out early, use actualCheckOut date
            if (estimatedCheckOut.after(actualCheckOut)) {
                GregorianCalendar gTemp = (GregorianCalendar) actualCheckOut.clone();
                while (gTemp.after(checkIn)) {
                    cost += 10;
                    gTemp.add(Calendar.DATE, -1);
                }
                return cost;
            }
            // You have checked out late, use actualCheckOut date with charges
            else {
                GregorianCalendar gTemp = (GregorianCalendar) estimatedCheckOut.clone();
                while (gTemp.after(checkIn)) {
                    cost += 10;
                    gTemp.add(Calendar.DATE, -1);
                }

                gTemp = (GregorianCalendar) actualCheckOut.clone();
                while (gTemp.after(estimatedCheckOut)) {
                    cost += 30;
                    gTemp.add(Calendar.DATE, -1);
                }

                return cost;
            }
        }
    }

    @Override
    public String toString() {
        return "TentOnly{" +
                "numberOfTenters=" + numberOfTenters +
                super.toString() +
                '}';
    }

}
