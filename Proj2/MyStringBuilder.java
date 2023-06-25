//Class created by Roy Wu - row64
//cs445 assignment 2
//prof: John Ramirez
/*ABOUT:
 * this class recreates the functionality of Java's built in StringBuilder class by 
 * utilizing a doubly-linked, circular list of nodes as its implementation.
 * it features overloaded constructors to initialize the String Builder and it has
 * methods that appends to, deletes from, and inserts to the String Builder--among others
 */

  
public class MyStringBuilder
{
	private CNode firstC;	
	private int length;  

	
	public MyStringBuilder(String s)
	{
		if (s == null || s.length() == 0)  // special case for empty String
		{
			firstC = null;
			length = 0;
		}
		else
		{
			firstC = new CNode(s.charAt(0));  // create first node
			length = 1;
			CNode currNode = firstC;
			// Iterate through remainder of the String, creating a new
			// node at the end of the list for each character.  Note
			// how the nodes are being linked and the current reference
			// being moved down the list.
			for (int i = 1; i < s.length(); i++)
			{
				CNode newNode = new CNode(s.charAt(i));  // create Node
				currNode.next = newNode;  	// link new node after current
				newNode.prev = currNode;	// line current before new node
				currNode = newNode;			// move down the list
				length++;
			}
			// After all nodes are created, connect end back to front to make
			// list circular
			currNode.next = firstC;
			firstC.prev = currNode;
		}
	}

	// Return the entire contents of the current MyStringBuilder as a String
	// For this method you should do the following:
	// 1) Create a character array of the appropriate length
	// 2) Fill the array with the proper characters from your MyStringBuilder
	// 3) Return a new String using the array as an argument, or
	//    return new String(charArray);
	// Note: This method is implemented for you.  See code below.
	public String toString()
	{
		char [] c = new char[length];
		int i = 0;
		CNode currNode = firstC;
		
		// Since list is circular, we cannot look for null in our loop.
		// Instead we count within our while loop to access each node.
		// Note that in this code we don't even access the prev references
		// since we are simply moving from front to back in the list.
		while (i < length)
		{
			c[i] = currNode.data;
			i++;
			currNode = currNode.next;
		}
		return new String(c);
	}

	// Create a new MyStringBuilder initialized with the chars in array s. 
	// You may NOT create a String from the parameter and call the first
	// constructor above.  Rather, you must build your MyStringBuilder by
	// accessing the characters in the char array directly.  However, you
	// can approach this in a way similar to the other constructor.
	public MyStringBuilder(char [] s)
	{
		if (s == null || s.length == 0) { //empty array case
			firstC = null;
			length = 0;
		} else { //first node
			firstC = new CNode(s[0]);
			CNode currNode = firstC;  
			length = 1;
			//iterate for rest of nodes
			for (int i = 1; i < s.length; i++) {
				CNode newNode = new CNode(s[i]);
				currNode.next = newNode;
				newNode.prev = currNode;
				currNode = newNode;
				length++;
			}
			//link ends for circular list
			currNode.next = firstC;
			firstC.prev = currNode;
		}
	}
	
	// Copy constructor -- make a new MyStringBuilder from an old one.  Be sure
	// that you make new nodes for the copy (traversing the nodes in the original
	// MyStringBuilder as you do)
	public MyStringBuilder(MyStringBuilder old)
	{
		//empty array case
		if (old == null || old.length == 0) {
			firstC = null;
			length = 0;
		} else {
			CNode currNode = old.firstC;
			firstC = old.firstC;
			length = old.length;
			CNode thisCurr = firstC;
			currNode = currNode.next;
			
			for (int i = 1; i < old.length; i++) {
				CNode newNode = new CNode(currNode.data);
				thisCurr.next = newNode;
				newNode.prev = thisCurr;
				thisCurr = newNode;
				currNode = currNode.next;
			}
			thisCurr.next = firstC;
			firstC.prev = thisCurr;
		}
	}
			
	
	// Create a new empty MyStringBuilder
	public MyStringBuilder()
	{
		firstC = null;
		length = 0;
	}
	
	// Append MyStringBuilder b to the end of the current MyStringBuilder, and
	// return the current MyStringBuilder.  Be careful for special cases!  Note
	// that you cannot simply link the two MyStringBuilders together -- that is
	// very simple but it will intermingle the two objects, which you do not want.
	// Thus, you should copy the data in argument b to the end of the current
	// MyStringBuilder.
	public MyStringBuilder append(MyStringBuilder b)
	{
		if (b == null || b.length() == 0) {
			return this;
		} else {
			for (int i = 0; i < b.length(); i++) { //if list has elements
				if (length > 0) {
					CNode newNode = new CNode(b.charAt(i)); 
					CNode last = firstC.prev;
					last.next = newNode;
					last = newNode;
					last.next = firstC;
					firstC.prev = last;
					length++;
				} else { //if list is empty
					firstC = new CNode(b.charAt(0));
					firstC.prev = firstC; 
					firstC.next = firstC; 
					length++;
				}
		}
		}
		return this;
	}

	// Append String s to the end of the current MyStringBuilder, and return
	// the current MyStringBuilder.  Be careful for special cases!
	public MyStringBuilder append(String s)
	{
		if (s.length() == 0 || s == null) {
			return this;
		} 
		if (this.length() == 0 && s.length() == 1) {
			CNode newFirst = new CNode(s.charAt(0));
			firstC = newFirst;
			length++;
		}
		if (this.length() == 0) { //special case: empty MyStringBuilder
			CNode newFirst = new CNode(s.charAt(0));
			firstC = newFirst;
			length++;
			CNode newSecond = new CNode(s.charAt(1));
			length++;
			firstC.next = newSecond;
			firstC.prev = newSecond;
			newSecond.prev = firstC;
			for (int i = 2; i < s.length(); i++) {
				CNode newNode = new CNode(s.charAt(i));
				CNode last = firstC.prev;
				last.next = newNode;
				last = newNode;
				last.next = firstC;
				firstC.prev = last;
				length++;
			}
		} else {
			for (int i = 0; i < s.length(); i++) {
				if (length > 0) {
					CNode newNode = new CNode(s.charAt(i));
					CNode last = firstC.prev;
					last.next = newNode;
					last = newNode;
					last.next = firstC;
					firstC.prev = last;
					length++;
				} else {
					firstC = new CNode(s.charAt(0));
					length++;
					
				}
			}
		}
		return this;
	}

	// Append char array c to the end of the current MyStringBuilder, and
	// return the current MyStringBuilder.  Be careful for special cases!
	public MyStringBuilder append(char [] c)
	{
		if (c== null || c.length == 0) {
			return this;
		} else {
			for (int i = 0; i < c.length; i++) {
				if (length > 0) {
					CNode newNode = new CNode(c[i]);
					CNode last = firstC.prev;
					last.next = newNode;
					last = newNode;
					last.next = firstC;
					firstC.prev = last;
					length++;
				} else {
					firstC = new CNode(c[i]);
					length++;
				}
			}
		}
		return this;
	}

	// Append char c to the end of the current MyStringBuilder, and
	// return the current MyStringBuilder.  Be careful for special cases!
	public MyStringBuilder append(char c)
	{
		if (length > 0) {
			CNode newNode = new CNode(c);
			CNode last = firstC.prev;
			last.next = newNode;
			last = newNode;
			last.next = firstC;
			firstC.prev = last;
			length++;
		} else {
			firstC = new CNode(c);
			length++;
		}
		return this;
	}

	// Return the character at location "index" in the current MyStringBuilder.
	// If index is invalid, throw an IndexOutOfBoundsException.
	public char charAt(int index)
	{
		CNode curr = firstC;
		if (index < 0 || index >= length) {
			throw new IndexOutOfBoundsException();
		} else {
			for (int i = 0; i < length; i++) {
				if (i == index) {
					return curr.data;
				} else {
					curr = curr.next;
				}
			}
		}
		return '\n';
	}

	// Delete the characters from index "start" to index "end" - 1 in the
	// current MyStringBuilder, and return the current MyStringBuilder.
	// If "start" is invalid or "end" <= "start" do nothing (just return the
	// MyStringBuilder as is).  If "end" is past the end of the MyStringBuilder, 
	// only remove up until the end of the MyStringBuilder. Be careful for 
	// special cases!
	public MyStringBuilder delete(int start, int end)
	{
		CNode curr = firstC;
		CNode begin = null;
		CNode fin = null;
		if (start < 0 || end <= start) {return this;} //special case: invalid parameters
		if (start == 0 && end == length) { //special case: delete everything
			firstC = null;
			firstC.prev = null;
			length = 0;
			return this;
		}
		if (end >= length - 1) { //special case: end is higher than length
			for (int i = 0; i < length; i++) {
				if (i == start - 1) {
					firstC.prev = curr;
					(firstC.prev).next = null;
					length = start;
					return this;
				}
				curr = curr.next;
			}
		}
		if (start == 0) { //special case: from start to end
			for (int i = 0; i <= end; i++) {
				if (i == end) {
					firstC = curr;
					firstC.next = curr.next;
				}
				curr = curr.next;
			}
			length = (length - (end - start));
			return this;
		}
		if (start > 0 && end < length - 1) { //normal case: parameters within StringBuilder
			for (int i = 0; i <= end; i++) {
				if (i == start - 1) {
					begin = curr;
				}
				if (i == end) {
					fin = curr;
				}
				curr = curr.next;
			}
			begin.next = fin;
			length = (length - (end - start));
			return this;
		}
		return this; 
		
	}

	// Delete the character at location "index" from the current
	// MyStringBuilder and return the current MyStringBuilder.  If "index" is
	// invalid, do nothing (just return the MyStringBuilder as is).  If "index"
	// is the last character in the MyStringBuilder, go backward in the list in
	// order to make this operation faster (since the last character is simply
	// the previous of the first character)
	// Be careful for special cases!
	public MyStringBuilder deleteCharAt(int index)
	{
		CNode curr = firstC;
		CNode begin = null;
		CNode fin = null;
		if (index < 0 || index > length - 1) {//case: invalid parameters
			return this;
		}
		if (index == 0) { //case: delete first char
			firstC = firstC.next;
			length--;
			return this;
		}
		if (index == length - 1) { //case: delete last char
			firstC.prev = (firstC.prev).prev;
			(firstC.prev).next = firstC;
			length--;
			return this;
		} else { //normal case
			for (int i = 0; i < index + 2; i++) {
				if (i == index - 1) {
					begin = curr;
				}
				if (i == index) {
					fin = curr.next;
				}
				curr = curr.next;
				if (begin != null && fin != null) {
					begin.next = fin;
					length--;
					return this;
				}
			}
		}
		return this;
	}

	// Find and return the index within the current MyStringBuilder where
	// String str first matches a sequence of characters within the current
	// MyStringBuilder.  If str does not match any sequence of characters
	// within the current MyStringBuilder, return -1.  Think carefully about
	// what you need to do for this method before implementing it.
	public int indexOf(String str)
	{
		String converted = this.toString();
		if (converted.contains(str)) { //case: if first word is str
			if (converted.contains(" ")) {
				String [] pieces = converted.split(" ");
				if (pieces[0].equals(str)) {
					return 0;
				}
			}
		}
		CNode curr = firstC;
		int count = 0;
		if (str == null) {
			return -1;
		}
		for (int i = 0; i < length; i++) {
			if (curr.data == str.charAt(0)) {
				CNode temp = curr.next;
				count++;
				for (int j = 1; j < str.length(); j++) {
					if (temp.data == str.charAt(j)) {
						count++;
					} else {
						break;
					}
					if (temp.next != null) {
						temp = temp.next;
					}
				}
				if (count == str.length()) {
					return i;
			}
				count = 0;
			}
			curr = curr.next;
		}
		return -1;
	}

	// Insert String str into the current MyStringBuilder starting at index
	// "offset" and return the current MyStringBuilder.  if "offset" == 
	// length, this is the same as append.  If "offset" is invalid
	// do nothing.
	public MyStringBuilder insert(int offset, String str)
	{
		CNode curr = null;
		CNode begin = null;
		CNode fin = null;
		CNode temp = firstC;
		if (offset < 0 || offset > length) {
			return this;
		}
		/*if (offset == length) {
			append(str);
		} */
		if (offset == 0) {
			fin = firstC;
			firstC = new CNode(str.charAt(0));
			temp = firstC;
			
			for (int i = 1; i < str.length(); i++) {
				CNode newNode = new CNode(str.charAt(i));
				temp.next = newNode;
				temp = newNode;
				length++;
			}
			temp.next = fin;
			length++;
			return this;
		} else {
			for (int i = 0; i < length; i++) {
				if (i == offset - 1) {
					begin = temp;
					fin = begin.next;
					CNode newNode = new CNode(str.charAt(0));
					begin.next = newNode;
					curr = newNode;
				} else if (i == offset) {
					for (int j = 1; j < str.length(); j++) {
						CNode newNode = new CNode(str.charAt(j));
						curr.next = newNode;
						curr = newNode;
						length++;
					}
					curr.next = fin;
					length++;
				}
				temp = temp.next;
			}
		return this;
		}
	}

	// Insert character c into the current MyStringBuilder at index
	// "offset" and return the current MyStringBuilder.  If "offset" ==
	// length, this is the same as append.  If "offset" is invalid, 
	// do nothing.
	public MyStringBuilder insert(int offset, char c)
	{
		if (offset < 0 || offset > length) {
			return this;
		}
		if (offset == length) {
			append(c);
			return this;
		}
		if (offset == 0) {
			CNode last = firstC.prev;
			CNode inserted = firstC;
			firstC = new CNode(c);
			firstC.next = inserted;
			last.next = firstC;
			firstC.prev = last;
			length++;
			return this;
		} else {
			CNode atEnd = null;
			CNode temp = firstC;
			for (int i = 0; i < offset + 1; i++) {
				if (i == offset) {
					CNode inserted = new CNode(c);
					atEnd = temp.next;
					temp.next = inserted;
					temp = inserted;
					temp.next = atEnd;
				} else {
					temp = temp.next;
				}
			}
			temp.next = atEnd;
			length++;
			return this;
		}
	}

	// Return the length of the current MyStringBuilder
	public int length()
	{
		return length;
	}

	// Delete the substring from "start" to "end" - 1 in the current
	// MyStringBuilder, then insert String "str" into the current
	// MyStringBuilder starting at index "start", then return the current
	// MyStringBuilder.  If "start" is invalid or "end" <= "start", do nothing.
	// If "end" is past the end of the MyStringBuilder, only delete until the
	// end of the MyStringBuilder, then insert.  This method should be done
	// as efficiently as possible.  In particular, you may NOT simply call
	// the delete() method followed by the insert() method, since that will
	// require an extra traversal of the linked list.
	public MyStringBuilder replace(int start, int end, String str)
	{
		delete(start, end);
		insert(start, str);
		return this;
	}

	// Return as a String the substring of characters from index "start" to
	// index "end" - 1 within the current MyStringBuilder.  For this method
	// you should do the following:
	// 1) Create a character array of the appropriate length
	// 2) Fill the array with the proper characters from your MyStringBuilder
	// 3) Return a new String using the array as an argument, or
	//    return new String(charArray);
	public String substring(int start, int end)
	{
			String converted = this.toString();
			String[] pieces = converted.split("(?!^)");
			String builtSubstring = "";
			for (int i = start; i < end; i++) { //for loop <=
				builtSubstring = builtSubstring + pieces[i];
			}
			return builtSubstring;
	}

	// Return as a String the reverse of the contents of the MyStringBuilder.  Note
	// that this does NOT change the MyStringBuilder in any way.  See substring()
	// above for the basic approach.
	public String revString()
	{
		String converted = this.toString();
		String[] pieces = converted.split("(?!^)");
		String builtReversed = "";
		for (int i = pieces.length - 1; i >= 0; i--) {
			builtReversed = builtReversed + pieces[i];
		}
		return builtReversed;
	}
	
	// You must use this inner class exactly as specified below.  Note that
	// since it is an inner class, the MyStringBuilder class MAY access the
	// data, next and prev fields directly.
	private class CNode
	{
		private char data;
		private CNode next, prev;

		public CNode(char c)
		{
			data = c;
			next = null;
			prev = null;
		}

		public CNode(char c, CNode n, CNode p)
		{
			data = c;
			next = n;
			prev = p;
		}
	}
}