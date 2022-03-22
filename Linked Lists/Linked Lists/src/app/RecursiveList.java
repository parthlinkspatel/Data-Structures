package app;

import java.util.Iterator;

//import org.graalvm.compiler.graph.Node;

public class RecursiveList<T> implements ListInterface<T> {

  private int size;
  private Node<T> head = null;

  public RecursiveList() {
    this.head = null;
    this.size = 0;
  }

  public RecursiveList(Node<T> first) {
    this.head = first;
    this.size = 1;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public void insertFirst(T elem) throws NullPointerException { //Done
      //TODO: Implement this method.
    if(elem==null){
      throw new NullPointerException();
    }
    insertAt(0, elem);
  }

  @Override
  public void insertLast(T elem) throws NullPointerException { //Done
      //TODO: Implement this method.
    if(elem==null){
      throw new NullPointerException();
    }
    insertAt(size, elem);
  }

  @Override
  public void insertAt(int index, T elem) throws NullPointerException, IndexOutOfBoundsException { //Done
      //TODO: Implement this method.
    if(elem==null){
      throw new NullPointerException();
    }
    if(index<0 || index>size()){
      throw new IndexOutOfBoundsException();
    }
    if(index==0){   //Prepend or insertFirst
      if(head == null){   //Prepending an empty list
        Node<T> newNode = new Node<T>(elem, null);
        head = newNode;
      } else{   //Prepending a NONEMPTY list
        Node<T> newNode = new Node<T>(elem, head);
        head = newNode;
      }
    } else if(index == size + 1){   //Appending an NONempty list
      Node<T> newNode = new Node<T>(elem, null);
      returnLastNode(head).setNext(newNode);
    } else{ //insert middle of list
      Node<T> newNode = new Node<T>(elem, nodeAt(index, head));
      nodeAt(index-1, head).setNext(newNode);
    }
    size++;
  }

  private Node<T> nodeAt(int index, Node<T> curNode){ //helper method
    if(index==0){
      return curNode;
    }
    else {
      return nodeAt(index-1, curNode.getNext());
    }
  }

  private Node<T> returnLastNode(Node<T> curNode){ //helper method
    if(curNode.getNext()==null){
      return curNode;
    } else{
      return returnLastNode(curNode.getNext());
    }
  }

  @Override
  public T removeFirst() throws IllegalStateException {
    T removedItem = head.getData();
      //TODO: Implement this method.
    if(isEmpty()){
      throw new IllegalStateException();
    }
    head = head.getNext();
    size--;
    return removedItem;
  }

  @Override
  public T removeLast() throws IllegalStateException {
    T removedItem = null;
      //TODO: Implement this method.
    if(isEmpty()){
      throw new IllegalStateException();
    }
    removedItem = getLast();
    T temp = removeAt(size()-1);
    return removedItem;
  }

  @Override
  public T removeAt(int i) throws IndexOutOfBoundsException {
    T removedItem = null;
      //TODO: Implement this method.
    if(i<0 || i>size()){
      throw new IndexOutOfBoundsException();
    }
    removedItem = get(i);
    if(i==0){ //Head
      head = head.getNext();
    } else{
      removeAtHelper(i, head);
    }
    size--;
    return removedItem;
  }

  private void removeAtHelper(int i, Node<T> curNode){
    if(i==1){ //Removing anything besides head
      curNode.setNext(curNode.getNext().getNext());
    } else {
      removeAtHelper(i-1, curNode.getNext());
    }
  }

  @Override
  public T getFirst() throws IllegalStateException {
      //TODO: Implement this method.
    if(isEmpty()){
      throw new IllegalStateException();
    }
    T item = head.getData();
    return item;
  }

  @Override
  public T getLast() throws IllegalStateException {
    T item = null;
      //TODO: Implement this method.
    if(isEmpty()){
      throw new IllegalStateException();
    }
    item = get(size()-1);
    return item;
  }

  @Override
  public T get(int i) throws IndexOutOfBoundsException {
    T item = null;
      //TODO: Implement this method.
    if(i<0 || i>=size()){
      throw new IndexOutOfBoundsException();
    }  
    item = getHelper(i, head);
    return item;
  }

  private T getHelper(int i, Node<T> curNode){
    if(i==0){
      return curNode.getData();
    } else {
      return getHelper(i-1, curNode.getNext());
    }
  }

  @Override
  public void remove(T elem) throws NullPointerException,ItemNotFoundException {
      //TODO: Implement this method.
    if(elem==null){
      throw new NullPointerException();
    }
    if(notFoundHelper(elem, 0)){
      throw new ItemNotFoundException();
    }
    T temp = removeAt(indexOf(elem));
  }

  private boolean notFoundHelper(T elem, int count){
    if(elem == get(count)){
      return false;
    } else if(count == size()-1){
      return true;
    } else{
      return notFoundHelper(elem, count++);
    }
  }

  @Override
  public int indexOf(T elem) throws NullPointerException{ //Done
    int index = -1;
      //TODO: Implement this method.
    if(elem == null){
      throw new NullPointerException();
    } 
    index = indexOfHelper(elem, head, 0);
    return index;
  }

  private int indexOfHelper(T elem, Node<T> curNode, int curIndex){ //Helper method
    if(elem==curNode.getData() || elem.equals(curNode.getData())){
      return curIndex;
    } else{
      return indexOfHelper(elem, curNode.getNext(), curIndex++);
    }

  }

  @Override
  public boolean isEmpty() { //Done
    boolean empty = true;
      //TODO: Implement this method.
    if(size!=0){
      empty = false;
    }
    return empty;
  }


  public Iterator<T> iterator() { //Done
    Iterator<T> iter = null;
      //TODO: Implement this method.
    iter = new LinkedNodeIterator<T>(head);
   return iter;
  }
}
