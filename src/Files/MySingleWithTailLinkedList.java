package Files;

import java.io.Serializable;
import java.util.Random;

public class MySingleWithTailLinkedList implements Serializable {
	private Node top;
	private Node tail;

	/// Do not modify
	public MySingleWithTailLinkedList() {
		top = null;
		tail = null;
	}

	/// Do not modify
	public int size() {
		
		if (top == null)
			return 0;

		int size = 0;
		Node temp = top;
		while (temp != tail) {
			size++;
			temp = temp.getNext();
		}
		return size + 1;
	}

	/// Do not modify
	public void clear() {

		
		// remove method is functioning correctly.
		// normally just write: tail = top = null;

		Random r = new Random();

		while (size() != 0) {
			remove(r.nextInt(size()));
		}
	}

	/******************************************************************
	 * This add method add new data (s) to the LinkedList
	 *
	 * @param s the data to be added.
	 */
	public void add(CampSite s) {
		// no list
		if (top == null) {
			tail = top = new Node(s, null);
			return;
		}

		Node prev = null, curr = top;

		while (curr != null) {
			if (compare(s, curr.getData()) > 0) {
				prev = curr;
				curr = curr.getNext();
			} else {

				if (prev == null) {
					top = new Node(s, top);
				} else {
					Node node = new Node(s, curr);
					prev.setNext(node);
				}

				return;
			}

		}

		tail.setNext(new Node(s, null));
		tail = tail.getNext();

	}

	private int compare(CampSite campSite1, CampSite campSite2) {
		
		
		int diff = campSite1.estimatedCheckOut.compareTo(campSite2.estimatedCheckOut);
		if (diff == 0)
			diff = Double.compare(campSite1.getCost(), campSite2.getCost());
		if (diff == 0)
			diff = campSite1.getGuestName().compareTo(campSite2.getGuestName());
		
	
		return diff;
	}

	/******************************************************************
	 *
	 * This method removes a specific node at a specfic index starts at zero, if the
	 * index is out of bounds, the method returns a null, Otherwise the method
	 * returns the data that was removed.
	 *
	 * @param index is the index of the node to be removed
	 * @return the data of the node removed
	 */
	public CampSite remove(int index) {

		if (index < 0 || index >= size()) {
			return null;
		}

		Node previous = null;
		Node current = top;
		for (int i = 0; i < index; i++) {
			previous = current;
			current = current.getNext();
		}

		CampSite removed = current.getData();

		// remove the node
		if (index == 0) {
			top = top.getNext();
		} else {
			previous.setNext(current.getNext());
		}
		
		
		if(top == null)
			tail = null;

		if(current.getNext() == null)
			tail = previous;

		return removed;

	}

	/****************************************************************
	 * This method returns the data at a specific index. If the index is out of
	 * bounds, return null;
	 * 
	 * @param index is the index of the node to be returned
	 * @return the data at the index
	 */
	public CampSite get(int index) {
		if (index < 0 || index > size() - 1) {
			return null;
		}

		Node current = top;
		for (int i = 0; i < index; i++) {
			current = current.getNext();
		}

		return current.getData();

	}

	/// Do not modify
	@Override
	public String toString() {

		return "MySingleWithOutTailLinkedList{" + "top=" + top + ", size=" + size() + '}';
	}
}
