
//class created by Roy Wu - row64 
//cs445 assignment 1
//prof: John Ramirez
/* ABOUT:
* this class extends (from MyDeque class) a double-ended queue that is circular and resizing by using an array and modulo logic
* it feature methods that can access and alter data from a given relative location to the logical front and back of the queue
*/
public class IndexDeque<T> extends MyDeque<T> implements Indexable<T> {

    public IndexDeque(int initSize) {
		super(initSize);
	}
	
	public T getFront(int i) {
		if (isEmpty()) {
			return null;
		}
		if (i < 0 || size < (i + 1)) {
			throw new IndexOutOfBoundsException("Can't use " + i + " index lmfao");
		}
		if (i == 0) {
			return data[front];
		} else {
			return data[(front + i) % capacity()];
		}
	}
	
	public T getBack(int i) {
		if (isEmpty()) {
			return null;
		}
		if (i < 0 || size < (i + 1)) {
			throw new IndexOutOfBoundsException("Can't use " + i + " index lmfao");
		}
		if (i == 0) {
			return data[back];
		} else {
			return data[(back - i) % capacity()];
		}
	}

	public void setFront(int i, T item) {
		if (i < 0 || size < (i + 1)) {
			throw new IndexOutOfBoundsException("Can't use " + i + " index lmfao");
		}
		if (i == 0) {
			data[front] = item;
		} else {
			data[(front + i) % capacity()] = item;
		}
		
	}

	public void setBack(int i, T item) {
		if (i < 0 || size < (i + 1)) {
			throw new IndexOutOfBoundsException("Can't use " + i + " index lmfao");
		}
		if (i == 0) {
			data[back] = item;
		} else {
			data[(back - i) % capacity()] = item;
		}
	}
	
	public int size() {
		return size;
	}
}
