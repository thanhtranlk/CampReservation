package Files;

import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

public class ListModel extends AbstractTableModel {

    private MySingleWithTailLinkedList listCampSites;
    private MySingleWithTailLinkedList fileredListCampSites;

    private ScreenDisplay display;

    private String[] columnNamesCurrentPark = {"Guest Name", "Est. Cost",
            "Check in Date", "EST. Check out Date ", "Max Power", "Num of Tenters"};

    private String[] columnNamesforCheckouts = {"Guest Name", "Est. Cost",
            "Check in Date", "ACTUAL Check out Date ", " Real Cost"};


    private DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    //private String date;

    public ListModel() {
        super();
        display = ScreenDisplay.CurrentParkStatus;
        listCampSites = new MySingleWithTailLinkedList();
        fileredListCampSites = new MySingleWithTailLinkedList();
        UpdateScreen();
        createList();
        UpdateScreen();
    }

    public void setDisplay(ScreenDisplay selected) {
        display = selected;
        UpdateScreen();
    }

    private void UpdateScreen() {
        switch (display) {
            case CurrentParkStatus:
                fileredListCampSites.clear();
                for (int i = 0; i < listCampSites.size(); i++)
                    if (listCampSites.get(i).actualCheckOut == null)
                        fileredListCampSites.add(listCampSites.get(i));
                break;

            case CheckOutGuest:
                fileredListCampSites.clear();
                for (int i = 0; i < listCampSites.size(); i++)
                    if (listCampSites.get(i).actualCheckOut != null)
                        fileredListCampSites.add(listCampSites.get(i));
                break;

            default:
                throw new RuntimeException("upDate is in undefined state: " + display);
        }
        fireTableStructureChanged();
        }

    @Override
    public String getColumnName(int col) {
        switch (display) {
            case CurrentParkStatus:
                return columnNamesCurrentPark[col];
            case CheckOutGuest:
                return columnNamesforCheckouts[col];
        }
        throw new RuntimeException("Undefined state for Col Names: " + display);
    }

    @Override
    public int getColumnCount() {
        switch (display) {
            case CurrentParkStatus:
                return columnNamesCurrentPark.length;
            case CheckOutGuest:
                return columnNamesforCheckouts.length;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public int getRowCount() {
        return fileredListCampSites.size();     // returns number of items in the MySingleWithOutTailLinkedList
    }

    @Override
    public Object getValueAt(int row, int col) {
        switch (display) {
            case CurrentParkStatus:
                return currentParkScreen(row, col);
            case CheckOutGuest:
                return checkOutScreen(row, col);
          }
        throw new IllegalArgumentException();
    }

    private Object currentParkScreen(int row, int col) {
        switch (col) {
            case 0:
                return (fileredListCampSites.get(row).guestName);

            case 1:
                return (fileredListCampSites.get(row).getCost());

            case 2:
                return (formatter.format(fileredListCampSites.get(row).checkIn.getTime()));

            case 3:
                if (fileredListCampSites.get(row).estimatedCheckOut == null)
                    return "-";

                return (formatter.format(fileredListCampSites.get(row).estimatedCheckOut.
                                getTime()));

            case 4:
            case 5:
                if (fileredListCampSites.get(row) instanceof RV)
                    if (col == 4)
                        return (((RV) fileredListCampSites.get(row)).getPower());
                    else
                        return "";

                else {
                    if (col == 5)
                        return (((Tent) fileredListCampSites.get(row)).
                                getNumberOfTenters());
                    else
                        return "";
                }
            default:
                throw new RuntimeException("Row,col out of range: " + row + " " + col);
        }
    }

    private Object checkOutScreen(int row, int col) {
        switch (col) {
            case 0:
                return (fileredListCampSites.get(row).guestName);

            case 1:
                return (fileredListCampSites.
                        get(row).getCost());
            case 2:
                return (formatter.format(fileredListCampSites.get(row).checkIn.
                                getTime()));

            case 3:
                return (formatter.format(fileredListCampSites.get(row).actualCheckOut.
                                getTime()));

            case 4:
                return (fileredListCampSites.get(row).getCost());

            default:
                throw new RuntimeException("Row,col out of range: " + row + " " + col);
        }
    }

    public void add(CampSite a) {
        listCampSites.add(a);
        //UpdateScreen();
    }

    public CampSite get(int i) {
        return fileredListCampSites.get(i);
    }

    public void upDate(int index, CampSite unit) {
        UpdateScreen();
    }

    public void saveDatabase(String filename) {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(listCampSites);
            os.close();
        } catch (IOException ex) {
            throw new RuntimeException("Saving problem! " + display);

        }
    }

    public void loadDatabase(String filename) {
        listCampSites.clear();

        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream is = new ObjectInputStream(fis);

            listCampSites = (MySingleWithTailLinkedList) is.readObject();
            UpdateScreen();
            is.close();
        } catch (Exception ex) {
            throw new RuntimeException("Loading problem: " + display);

        }
    }

    public void createList() {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        GregorianCalendar g1 = new GregorianCalendar();
        GregorianCalendar g2 = new GregorianCalendar();

        try {
            Date d1 = df.parse("1/1/2020");
            g1.setTime(d1);
            Date d2 = df.parse("1/4/2020");
            g2.setTime(d2);

            Tent tent1 = new Tent("T1", g1, g2,null,4);
            Tent tent2 = new Tent("T2", g1,g2,null, 8);

            RV RV1 = new RV("RV1",g1,g2,null, 1000);
            RV RV2 = new RV("RV2",g1,g2,null, 1000);

            add(tent1);
            add(tent2);
            add(RV1);
            add(RV2);

            // create a bunch of them.
            int count = 0;
            Random rand = new Random(13);
            String guest = null;

            // The count variable can be change to help with debugging.
            while (count < 100) {
                Date estimateChkOutdate = df.parse("2/" + (rand.nextInt(10) + 2) + "/2020");
                GregorianCalendar gEstmateChkOut = new GregorianCalendar();
                gEstmateChkOut.setTime(estimateChkOutdate);

                Date checkinDate = df.parse("1/" + (rand.nextInt(10) + 2) + "/2020");
                GregorianCalendar gChkIn = new GregorianCalendar();
                gChkIn.setTime(checkinDate);

                if (rand.nextBoolean()) {
                    guest = "T" + rand.nextInt(3);
                    Tent tent = new Tent(guest, gChkIn, gEstmateChkOut, null, rand.nextInt(10));
                    add(tent);
                } else {
                    guest = "RV" + rand.nextInt(3);
                    estimateChkOutdate = df.parse("2/" + (rand.nextInt(5) + 2) + "/2020");
                    gEstmateChkOut.setTime(estimateChkOutdate);
                    RV rv = new RV(guest, gChkIn, gEstmateChkOut, null, rand.nextInt(2000));
                    add(rv);
                }

                count++;
            }

        } catch (ParseException e) {
            throw new RuntimeException("Error in testing, creation of list");
        }

    }
}

