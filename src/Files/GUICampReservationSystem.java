package Files;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

/*****************************************************************
 *
 *  Campers Reservation System
 *
 *****************************************************************/
public class GUICampReservationSystem extends JFrame implements ActionListener{
    /** Holds menu bar */
    private JMenuBar menus;

    /** menus in the menu bar */
    private JMenu fileMenu;
    private JMenu actionMenu;

    /** menu items in each of the menus */
    private JMenuItem openSerItem;
    private JMenuItem exitItem;
    private JMenuItem saveSerItem;
    private JMenuItem reserveRVItem;
    private JMenuItem reserveTentOnlyItem;
    private JMenuItem checkOutItem;


    private JMenuItem currentParkItemScn;
    private JMenuItem checkOUtItemScn;

    private JPanel panel;

    /** Holds the list engine */
    private ListModel DList;

    /** Holds jTable */
    private JTable jTable;

    /** Scroll pane */
    private JScrollPane scrollList;

    /*****************************************************************
     *
     * A constructor that starts a new GUI1024 for the rental store
     *
     *****************************************************************/
    public GUICampReservationSystem(){
        //adding menu bar and menu items
        menus = new JMenuBar();
        fileMenu = new JMenu("File");
        actionMenu = new JMenu("Action");
        openSerItem = new JMenuItem("Open File");
        exitItem = new JMenuItem("Exit");
        saveSerItem = new JMenuItem("Save File");
         reserveRVItem = new JMenuItem("Reserve a RV Site");
        reserveTentOnlyItem = new JMenuItem("Reserve a TentOnly site");
        checkOutItem = new JMenuItem("CheckOut of TentOnly or RV");

        currentParkItemScn = new JMenuItem("Current Park Screen");
        checkOUtItemScn = new JMenuItem("Check out screen");

        //adding items to bar
        fileMenu.add(openSerItem);
        fileMenu.add(saveSerItem);
        fileMenu.addSeparator();
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        fileMenu.addSeparator();
        fileMenu.add(currentParkItemScn);
        fileMenu.add(checkOUtItemScn);

        actionMenu.add(reserveRVItem);
        actionMenu.add(reserveTentOnlyItem);
        actionMenu.addSeparator();
        actionMenu.add(checkOutItem);

        menus.add(fileMenu);
        menus.add(actionMenu);

        //adding actionListener
        openSerItem.addActionListener(this);
        saveSerItem.addActionListener(this);
        exitItem.addActionListener(this);
        reserveRVItem.addActionListener(this);
        reserveTentOnlyItem.addActionListener(this);
        checkOutItem.addActionListener(this);


        currentParkItemScn.addActionListener(this);
        checkOUtItemScn.addActionListener(this);

        setJMenuBar(menus);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        DList = new ListModel();
        jTable = new JTable(DList);
        scrollList = new JScrollPane(jTable);
        panel.add(scrollList);
        add(panel);
        scrollList.setPreferredSize(new Dimension(800,650));

        setVisible(true);
        setSize(950,650);
    }

    public void actionPerformed(ActionEvent e) {

        Object comp = e.getSource();

        if (currentParkItemScn == comp)
            DList.setDisplay(ScreenDisplay.CurrentParkStatus);

        if (checkOUtItemScn == comp)
            DList.setDisplay(ScreenDisplay.CheckOutGuest);

        if (openSerItem == comp) {
            JFileChooser chooser = new JFileChooser();
            int status = chooser.showOpenDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                String filename = chooser.getSelectedFile().getAbsolutePath();
                if (openSerItem == comp)
                    DList.loadDatabase(filename);
            }
        }

        if (saveSerItem == comp) {
            JFileChooser chooser = new JFileChooser();
            int status = chooser.showSaveDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                String filename = chooser.getSelectedFile().getAbsolutePath();
                if (saveSerItem == e.getSource())
                    DList.saveDatabase(filename);
            }
        }

        //MenuBar options
        if(e.getSource() == exitItem){
            System.exit(1);
        }
        if(e.getSource() == reserveRVItem){
            RV RV = new RV();
            ReservationRVDialog dialog = new ReservationRVDialog(this, RV);
            if(dialog.getCloseStatus() == ReservationRVDialog.OK){
                DList.add(RV);
            }
        }
        if(e.getSource() == reserveTentOnlyItem){
            Tent tent = new Tent();
            ReservationTentDialog dialog = new ReservationTentDialog(this, tent);
            if(dialog.getCloseStatus() == ReservationTentDialog.OK){
                DList.add(tent);
            }
        }

        if (checkOutItem == e.getSource()) {
            int index = jTable.getSelectedRow();
            if (index != -1) {
                GregorianCalendar dat = new GregorianCalendar();

                CampSite unit = DList.get(index);
                CheckOutOnDialog dialog = new CheckOutOnDialog(this, unit);

                JOptionPane.showMessageDialog(null,
                        "  Be sure to thank " + unit.getGuestName() +
                                "\n for camping with us and the price is:  " +
                                unit.getCost() +
                                " dollars");
                DList.upDate(index, unit);
            }
        }
    }

    public static void main(String[] args) {
        new GUICampReservationSystem();
    }
}
