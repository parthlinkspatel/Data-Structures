package priorityqueue;

import java.util.Comparator;

public class Heap<T> implements PriorityQueueADT<T> {

  private int numElements;
  private T[] heap;
  private boolean isMaxHeap;
  private Comparator<T> comparator;
  private final static int INIT_SIZE = 2000;

  /**
   * Constructor for the heap.
   * @param comparator comparator object to define a sorting order for the heap elements.
   * @param isMaxHeap Flag to set if the heap should be a max heap or a min heap.
   */
  public Heap(Comparator<T> comparator, boolean isMaxHeap) {
      //TODO: Implement this method.
      this.isMaxHeap = isMaxHeap;
      this.comparator = comparator;
      numElements = 0;
      heap = (T[])new Object[INIT_SIZE];
  }

  /**
   * This results in the entry at the specified index "bubbling up" to a location
   * such that the property of the heap are maintained. This method should run in
   * O(log(size)) time.
   * Note: When enqueue is called, an entry is placed at the next available index in 
   * the array and then this method is called on that index. 
   *
   * @param index the index to bubble up
   */
  public void bubbleUp(int index) {
      //TODO: Implement this method.
      if( index==0){
        return;
      }
      else {
        int parentIndex = (index -1)/2;
        if( compare(heap[index], heap[parentIndex]) <= 0){
          return;
        }
        else{
          T temp = heap[index];
          heap[index] = heap[parentIndex];
          heap[parentIndex] = temp;
          bubbleUp(parentIndex);
        }
      }
  }

  /**
   * This method results in the entry at the specified index "bubbling down" to a
   * location such that the property of the heap are maintained. This method
   * should run in O(log(size)) time.
   * Note: When remove is called, if there are elements remaining in this
   *  the bottom most element of the heap is placed at
   * the 0th index and bubbleDown(0) is called.
   * 
   * @param index
   */
  public void bubbleDown(int index) {
      //TODO: Implement this method.
      int childIndex = 2*index +1;
      T value = heap[index];
      while(childIndex < size()){
        T maxValue = value;
        int maxIndex = -1;
        for(int i=0; i<2; i++){
          if(heap[i+childIndex] == null){

          } else if(compare(heap[i+childIndex], maxValue) > 0){
            maxValue = heap[i+childIndex];
            maxIndex = i + childIndex;
          }
        }
        if (maxValue == value){
          return;
        } else{
          int temp = index;
          index = maxIndex;
          maxIndex = temp;
          childIndex = 2*index +1;
        }
      }
  }

  /**
   * Test for if the queue is empty.
   * @return true if queue is empty, false otherwise.
   */
  public boolean isEmpty() {
    boolean isEmpty = false;
      //TODO: Implement this method.
      isEmpty = heap==null || heap[0]==null;
    return isEmpty;
  }

  /**
   * Number of data elements in the queue.
   * @return the size
   */
  public int size(){
    int size = numElements;
      //TODO: Implement this method.
        return size;
  }

  /**
   * Compare method to implement max/min heap behavior.  It calls the comparae method from the 
   * comparator object and multiply its output by 1 and -1 if max and min heap respectively.
   * TODO: implement the heap compare method
   * @param element1 first element to be compared
   * @param element2 second element to be compared
   * @return positive int if {@code element1 > element2}, 0 if {@code element1 == element2}, negative int otherwise
   */
  public int compare(T element1 , T element2) {
    int result = 0;
    int compareSign =  -1;
    if (isMaxHeap) {
      compareSign = 1;
    }
    result = compareSign * comparator.compare(element1, element2);
    return result;
  }

  /**
   * Return the element with highest (or lowest if min heap) priority in the heap 
   * without removing the element.
   * @return T, the top element
   * @throws QueueUnderflowException if empty
   */
  public T peek() throws QueueUnderflowException {
     T data = null;
      //TODO: Implement this method.
    if(isEmpty()){
      throw new QueueUnderflowException();
    }
    data = heap[0];
    return data;
  }  


  /**
   * Removes and returns the element with highest (or lowest if min heap) priority in the heap.
   * @return T, the top element
   * @throws QueueUnderflowException if empty
   */
  public T dequeue() throws QueueUnderflowException{
    T data = null;
      //TODO: Implement this method.
    data = peek();
    int temp=0;
    for(int i=0; i<numElements; i++){
      heap[i] = heap[i+1];
      temp = i;
    }
    numElements--;
    heap[temp] = null;
    for(int i=0; i<numElements; i++){
      bubbleUp(i);
      bubbleDown(i);
    }
    return data;
  }

  /**
   * Enqueue the element.
   * @param the new element
   */
  public void enqueue(T newElement) {
      //TODO: Implement this method.
    heap[numElements] = newElement;
    bubbleUp(numElements);
    numElements++;
  }


}