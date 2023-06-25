

//class created by Roy Wu - row64 
//cs445 assignment 1
//prof: John Ramirez
/* ABOUT:
* this class extends (from IndexDeque class) a double-ended queue that is circular and resizing by using an array and modulo logic
* it feature methods that can access and alter data from a given relative location to the logical front and back of the queue
*/
//NOTE: incomplete and erroneous output, faulty remove methods

public class IndexAddRemoveDeque<T> extends IndexDeque<T> implements IndexableAddRemove<T> {

	public IndexAddRemoveDeque(int initSize) {
		super(initSize);
	}

	public void addToFront(int i, T item) {
		if (i < 0 || size < (i + 1)) {
			throw new IndexOutOfBoundsException("Can't use " + i + " index lmfao");
		}
		if (!(size < capacity())) {
			resize();
		}
		int tracker = (i + front) % capacity();
		for (int index = size; index > tracker; index--) {
			data[(index + front) % capacity()] = data[((index + front) - 1) % capacity()];
		}
		data[(tracker + front) % capacity()] = item;
		back = ((back++) % capacity());
		size++;
	}

	public void addToBack(int i, T item) {
		if (i < 0 || size < (i + 1)) {
			throw new IndexOutOfBoundsException("Can't use " + i + " index lmfao");
		}
		if (!(size < capacity())) {
			resize();
		}
		int tracker = ((back - i) + 1) % capacity();
		for (int index = size; index > tracker; index--) {
			data[(tracker + front) % capacity()] = data[((index + front) - 1) % capacity()];
		}
		data[(tracker + front) % capacity()] = item;
		back = ((back++) % capacity());
		size++;
	}

	public T removeFront(int i) {
		if (isEmpty()) {
			return null;
		} 
		if (i < 0 || size < (i + 1)) {
			throw new IndexOutOfBoundsException("Can't use " + i + " index lmfao");
		}
		int tracker = (i + front) % capacity();
		T removed = data[tracker];
		for (int index = tracker; index < size; index++) {
			data[(index + front) % capacity()] = data[((index + front) + 1) % capacity()];
		}
		back = ((back--) % capacity());
		size--;
		return removed;
	}

	public T removeBack(int i) {
		if (isEmpty()) {
			return null;
		}
		if (i < 0 || size < (i + 1)) {
			throw new IndexOutOfBoundsException("Can't use " + i + " index lmfao");
		}
		int tracker = (back - i) % capacity();
		T removed = data[tracker];
		for (int index = tracker; index < size; index++) {
			data[(index + front) % capacity()] = data[((index + front) + 1) % capacity()];
		}
		back = (back--) % capacity();
		size--;
		return removed;
	}

}
