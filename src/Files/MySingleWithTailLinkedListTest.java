package Files;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

public class MySingleWithTailLinkedListTest {
	@Test
	public void size() {
		MySingleWithTailLinkedList list = new MySingleWithTailLinkedList();
		assertEquals(0, list.size());
		
		list.add(createRV("A", "1/1/2020", "1/5/2020", null, 50));
		list.add(createRV("B", "1/1/2020", "1/5/2020", null, 50));
		assertEquals(2, list.size());
	}

	@Test
	public void clear() {
		MySingleWithTailLinkedList list = new MySingleWithTailLinkedList();
		list.add(createRV("A", "1/1/2020", "1/5/2020", null, 50));
		list.add(createRV("B", "1/1/2020", "1/5/2020", null, 50));
		list.clear();
		assertEquals(0, list.size());
	}

	@Test
	public void add() {
		MySingleWithTailLinkedList list = new MySingleWithTailLinkedList();

		RV rv1 = createRV("A", "1/1/2020", "1/5/2020", null, 50);
		RV rv2 = createRV("B", "1/1/2020", "2/5/2020", null, 150);
		RV rv3 = createRV("C", "1/1/2020", "1/15/2020", null, 250);
		RV rv4 = createRV("D", "1/1/2020", "1/5/2020", null, 350);
		Tent tent1 =  createTent("E", "1/1/2020", "1/5/2020", null, 1);
		Tent tent2 =  createTent("F", "2/1/2020", "3/5/2020", null, 1);
		
		
		list.add(rv1);
		list.add(rv2);
		list.add(rv3);
		list.add(rv4);
		list.add(tent1);
		list.add(tent2);
		
		
		assertEquals(6, list.size());
	}

	@Test
	public void remove() {
		MySingleWithTailLinkedList list = new MySingleWithTailLinkedList();

		RV rv1 = createRV("A", "1/1/2020", "1/5/2020", null, 50);
		RV rv2 = createRV("B", "1/1/2020", "2/5/2020", null, 150);
		RV rv3 = createRV("C", "1/1/2020", "1/15/2020", null, 250);
		RV rv4 = createRV("D", "1/1/2020", "1/5/2020", null, 350);
		Tent tent1 =  createTent("E", "1/1/2020", "1/5/2020", null, 1);
		Tent tent2 =  createTent("F", "2/1/2020", "3/5/2020", null, 1);
		
		
		list.add(rv1);
		list.add(rv2);
		list.add(rv3);
		list.add(rv4);
		list.add(tent1);
		list.add(tent2);
		
		
		assertEquals(tent1, list.remove(0));
		assertEquals(rv1, list.remove(0));
		assertEquals(tent2, list.remove(3));
		assertEquals(rv3, list.remove(1));
		
		
		assertEquals(2, list.size());
	}

	@Test
	public void get() {
		MySingleWithTailLinkedList list = new MySingleWithTailLinkedList();

		RV rv1 = createRV("A", "1/1/2020", "1/5/2020", null, 50);
		RV rv2 = createRV("B", "1/1/2020", "2/5/2020", null, 150);
		RV rv3 = createRV("C", "1/1/2020", "1/15/2020", null, 250);
		RV rv4 = createRV("D", "1/1/2020", "1/5/2020", null, 350);
		Tent tent1 =  createTent("E", "1/1/2020", "1/5/2020", null, 1);
		Tent tent2 =  createTent("F", "2/1/2020", "3/5/2020", null, 1);
		
		
		list.add(rv1);
		list.add(rv2);
		list.add(rv3);
		list.add(rv4);
		list.add(tent1);
		list.add(tent2);
		
		
		assertEquals(tent1, list.get(0));
		assertEquals(rv1, list.get(1));
		assertEquals(tent2, list.get(5));
		assertEquals(rv4, list.get(2));
		
		
		assertEquals(6, list.size());
	}

	// RV
	private static RV createRV(String guestName, String checkIn, String estimatedCheckOut, String actualCheckOut,
			int power) {
		return new RV(guestName, create(checkIn), create(estimatedCheckOut), create(actualCheckOut), power);
	}

	// Tent
	private static Tent createTent(String guestName, String checkIn, String estimatedCheckOut, String actualCheckOut,
			int numberOfTenters) {
		return new Tent(guestName, create(checkIn), create(estimatedCheckOut), create(actualCheckOut), numberOfTenters);
	}

	private static GregorianCalendar create(String date) {
		if (date == null)
			return null;

		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		GregorianCalendar g1 = new GregorianCalendar();

		try {
			g1.setTime(df.parse(date));
		} catch (ParseException e) {
			throw new RuntimeException("Error in testing, creation of list");
		}
		return g1;
	}

}