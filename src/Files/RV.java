package Files;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class RV extends CampSite {

    private int power;

    public RV() {
    }

    public RV(String guestName, GregorianCalendar checkIn, GregorianCalendar estimatedCheckOut, GregorianCalendar actualCheckOut, int power) {
        super(guestName, checkIn, estimatedCheckOut, actualCheckOut);
        this.power = power;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    @Override
    public double getCost() {
        double cost = 0;

        // You have not checked out, so use just
        // the estimatedCheckout date.
        if (actualCheckOut == null) {
            GregorianCalendar gTemp = (GregorianCalendar) estimatedCheckOut.clone();
            while (gTemp.after(checkIn)) {
                cost += 20;
                gTemp.add(Calendar.DATE, -1);
            }
            return cost;
        } else {

            // You have checked out early, use actualCheckOut date
            if (estimatedCheckOut.after(actualCheckOut)) {
                GregorianCalendar gTemp = (GregorianCalendar) actualCheckOut.clone();
                while (gTemp.after(checkIn)) {
                    cost += 20;
                    gTemp.add(Calendar.DATE, -1);
                }
                return cost;
            }
            // You have checked out late, use actualCheckOut date with charges
            else {
                GregorianCalendar gTemp = (GregorianCalendar) estimatedCheckOut.clone();
                while (gTemp.after(checkIn)) {
                    cost += 20;
                    gTemp.add(Calendar.DATE, -1);
                }

                gTemp = (GregorianCalendar) actualCheckOut.clone();
                while (gTemp.after(estimatedCheckOut)) {
                    cost += 60;
                    gTemp.add(Calendar.DATE, -1);
                }

                return cost;
            }
        }
    }

    @Override
    public String toString() {
        return "RV{" +
                "power=" + power +
                super.toString() +
                '}';
    }
}