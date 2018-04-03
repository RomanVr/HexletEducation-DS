package DS.m2e2;

import java.util.List;
import java.util.Collection;
import java.util.NoSuchElementException;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ListIterator;
import java.util.Iterator;

public class LinkedList<T> implements List<T> {
	
	private Item<T> firstInList = null;

    private Item<T> lastInList = null;

    private int size;

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean contains(final Object o) {
	    // BEGIN (write your solution here)
		return indexOf(o) != -1;
	    // END
    }

    @Override
    public Iterator<T> iterator() {
        return new ElementsIterator(0);
    }

    @Override
    public Object[] toArray() {
 	    // BEGIN (write your solution here)
    	final Object[] objectsList = new Object[this.size];
    	int i = 0;
    	for (Item<T> item = this.firstInList; 
   			 item != null; 
   			 item = item.getNextItem()) {
   			objectsList[i++] = item.element;
   		}
    	return objectsList;
	    // END
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
	    // BEGIN (write your solution here)
    	if (a.length < size) {
            a = (T1[])java.lang.reflect.Array.newInstance(
                                a.getClass().getComponentType(), size);}
    	int i = 0;
        Object[] result = a;
        for (Item<T> item = this.firstInList; 
      			 item != null; 
      			 item = item.getNextItem()) {
        	
            result[i++] = item.element;
        }
        if (a.length > size) { a[size] = null; }
        return a;
	    // END
    }

    @Override
    public boolean add(final T newElement) {
	    // BEGIN (write your solution here)
    	final Item<T> lastNode = this.lastInList;
    	
	    final Item<T> newItem = new Item<T>(newElement, lastInList, null);
	    this.lastInList = newItem;//указатель на хвост
	    if(lastNode == null) {
	    	this.firstInList = newItem;// указатель на голову
	    } else {
			lastNode.nextItem = newItem;
		}
	    size++;
	    return true;
	    // END
    }

    @Override
    public boolean remove(final Object o) {
	    // BEGIN (write your solution here)	   
    	int i = 0;
    	for (Item<T> item = this.firstInList; 
   			 item != null; 
   			 item = item.getNextItem()) {
    		if (o.equals(item.element)) { 
    			remove(item);    			
    			return true; 
    		}
			i++;
   		}    	
    	return false;
	    // END
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        for (final Object item : c) {
            if (!this.contains(item)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(final Collection<? extends T> c) {
        for (final T item : c) {
            add(item);
        }
        return true;
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        for (final Object item : c) {
            remove(item);
        }
        return true;
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        for (final Object item : this) {
            if (!c.contains(item)) this.remove(item);
        }
        return true;
    }

    @Override
    public void clear() {
        // BEGIN (write your solution here)    	
    	for (Item<T> item = this.firstInList; 
      			 item != null; 
      			 item = item.getNextItem()) {
    		
    		if (item.prevItem != null) {
    			item.getPrevItem().nextItem = null;
    			item.prevItem = null;
    		} 
    	}   
    	size = 0;	
    	this.firstInList = this.lastInList = null;
        // END
    }

    @Override
    public T remove(final int index) throws IndexOutOfBoundsException{
        // BEGIN (write your solution here)
        if (index > size || index < 0) { throw new IndexOutOfBoundsException(); }
        Item<T> itemRemove = null;
        int i = 0;
    	for (Item<T> item = this.firstInList; 
   			 item != null; 
   			 item = item.getNextItem()) {
    		if(index == i) {
    			itemRemove = item;
    			remove(item);
    			break;
    		}
   			i++;
   		}
        if (itemRemove == null) { return null; }
        return itemRemove.element;
        // END
    }


    private void remove(final Item current) {
        // BEGIN (write your solution here)    	
    	if(this.firstInList == this.lastInList) { // once element
    		this.firstInList = this.lastInList = null;
    	} else if (this.firstInList == current) {
			current.getNextItem().prevItem = null;
		} else if (this.lastInList == current) {
			current.getPrevItem().nextItem = null;
		} else {
			current.getNextItem().prevItem = current.getPrevItem();
			current.getPrevItem().nextItem = current.getNextItem();
		}
    	size--;
        // END
    }

    @Override
    public List<T> subList(final int start, final int end) {
	    return null;
    }

    @Override
    public ListIterator listIterator() {
	    return new ElementsIterator(0);
    }

    @Override
    public ListIterator listIterator(final int index) {
	    return new ElementsIterator(index);
    }

    @Override
    public int lastIndexOf(final Object target) {
	    throw new UnsupportedOperationException();
    } 

    @Override
    public int indexOf(final Object o) {
	    // BEGIN (write your solution here)
        // проверяем если объект == null
    	int index = 0;
    	if(o == null) {
    		for (Item<T> item = this.firstInList; item != null; item = item.getNextItem()) {
    			if(item.element == null) { return index; }
    			index++;
    		}
    	} else {
    		for (Item<T> item = this.firstInList; 
    			 item != null; 
    			 item = item.getNextItem()) {
    			if (o.equals(item.element)) { return index; }
    			index++;
    		}
    	}
    	return -1;    	
	    // END
    } 

    @Override
    public void add(final int index, final T element) {
	    throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(final int index, final Collection elements) {
	    throw new UnsupportedOperationException();
    }

    @Override
    public T set(final int index, final T element) {
        // BEGIN (write your solution here)
        final Item<T> itemChange = getItemByIndex(index);
        final T elementOld = itemChange.element;
        itemChange.element = element;
        return elementOld;        		
        // END
    }

    @Override
    public T get(final int index) {
        // BEGIN (write your solution here)    	
    	return getItemByIndex(index).element;
        // END
    }

    private Item<T> getItemByIndex(final int index) {
        // BEGIN (write your solution here) An auxiliary method for searching for node by index.
    	if (index > size || index < 0) { throw new IndexOutOfBoundsException(); }        
        int i = 0;
    	for (Item<T> item = this.firstInList; 
   			 item != null; 
   			 item = item.getNextItem()) {
    		if(index == i) {    			
    			return item;    			
    		}
   			i++;
   		}
    	return null;
        // END
    }

    private class ElementsIterator implements ListIterator<T> {

        private Item<T> currentItemInIterator;

        private Item<T> lastReturnedItemFromIterator;

        private int index;

        public ElementsIterator(final int index) {
            // BEGIN (write your solution here)
        	this.currentItemInIterator = (index == size) ? null : getItemByIndex(index);
            this.index = index;
            // END
        }

        @Override
        public boolean hasNext() {
            // BEGIN (write your solution here)
            return index != LinkedList.this.size;
            // END
        }

        @Override
        public T next() {
            // BEGIN (write your solution here)
            lastReturnedItemFromIterator = currentItemInIterator;
            currentItemInIterator = currentItemInIterator.getNextItem();
            index++;
            return lastReturnedItemFromIterator.element;
            // END
        }

        @Override
        public boolean hasPrevious() {
            // BEGIN (write your solution here)
            return index > 0;
            // END
        }

        @Override
        public T previous() {
            // BEGIN (write your solution here)
        	lastReturnedItemFromIterator = (currentItemInIterator == null) ? LinkedList.this.lastInList : currentItemInIterator.getPrevItem();
            currentItemInIterator = lastReturnedItemFromIterator;
            index--;
            return lastReturnedItemFromIterator.element;
            // END
        }

        @Override
        public void add(final T element) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(final T element) {
            // BEGIN (write your solution here)
        	LinkedList.this.set(index, element);
            // END
        }

        @Override
        public int previousIndex(){
            // BEGIN (write your solution here)
        	return index - 1;
            // END
        }

        @Override
        public int nextIndex() {
            // BEGIN (write your solution here)
            return index;
            // END
        }


        @Override
        public void remove() {
            // BEGIN (write your solution here)
        	if (lastReturnedItemFromIterator == null)
                throw new IllegalStateException();
        	
        	Item<T> lastNext = lastReturnedItemFromIterator.nextItem;        	
        	LinkedList.this.remove(lastReturnedItemFromIterator);
        	if(currentItemInIterator == lastReturnedItemFromIterator) {
        		currentItemInIterator = lastNext;
        	}
        	index--;
        	lastReturnedItemFromIterator = null;
            // END
        }
    }

    private static class Item<T> {
    
    	private T element;

        private Item<T> nextItem;

        private Item<T> prevItem;

        public Item(final T element, final Item<T> prevItem, final Item<T> nextItem) {
            this.element = element;
            this.nextItem = nextItem;
            this.prevItem = prevItem;
        }


        public Item<T> getNextItem() {
            return nextItem;
        }

        public Item<T> getPrevItem() {
            return prevItem;
        }
    }
}
