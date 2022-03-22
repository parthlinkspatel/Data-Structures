package structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.lang.Math;

public class BinarySearchTree<T extends Comparable<T>> implements BSTInterface<T> {
  protected BSTNode<T> root;

  public boolean isEmpty() {
    return root == null;
  }

  public int size() {
    return subtreeSize(root);
  }

  protected int subtreeSize(BSTNode<T> node) {
    if (node == null) {
      return 0;
    } else {
      return 1 + subtreeSize(node.getLeft()) + subtreeSize(node.getRight());
    }
  }

  public boolean contains(T t) throws NullPointerException {
    // TODO: Implement the contains() method
    if(t == null){
      throw new NullPointerException();
    }
    return containsHelper(t, root);
  }

  private boolean containsHelper(T t, BSTNode<T> node){
    if(node==null){
      return false;
    }
    if(node.getData().compareTo(t) == 0){
      return true;
    }
    boolean recur = containsHelper(t, node.getLeft());
    if(recur){
      return true;
    }
    return containsHelper(t, node.getRight());

  }

  public boolean remove(T t) {
    if (t == null) {
      throw new NullPointerException();
    }
    boolean result = contains(t);
    if (result) {
      root = removeFromSubtree(root, t);
    }
    return result;
  }

  private BSTNode<T> removeFromSubtree(BSTNode<T> node, T t) {
    // node must not be null
    int result = t.compareTo(node.getData());
    if (result < 0) {
      node.setLeft(removeFromSubtree(node.getLeft(), t));
      return node;
    } else if (result > 0) {
      node.setRight(removeFromSubtree(node.getRight(), t));
      return node;
    } else { // result == 0
      if (node.getLeft() == null) {
        return node.getRight();
      } else if (node.getRight() == null) {
        return node.getLeft();
      } else { // neither child is null
        T predecessorValue = getHighestValue(node.getLeft());
        node.setLeft(removeRightmost(node.getLeft()));
        node.setData(predecessorValue);
        return node;
      }
    }
  }

  private T getHighestValue(BSTNode<T> node) {
    // node must not be null
    if (node.getRight() == null) {
      return node.getData();
    } else {
      return getHighestValue(node.getRight());
    }
  }

  private BSTNode<T> removeRightmost(BSTNode<T> node) {
    // node must not be null
    if (node.getRight() == null) {
      return node.getLeft();
    } else {
      node.setRight(removeRightmost(node.getRight()));
      return node;
    }
  }

  public T get(T t) throws NullPointerException{
    // TODO: Implement the get() method
    if(t == null){
      throw new NullPointerException();
    }
    if(contains(t)){
      return t;
    }
    else{
      return null;
    }
  }


  public void add(T t) {
    if (t == null) {
      throw new NullPointerException();
    }
    root = addToSubtree(root, new BSTNode<T>(t, null, null));
  }

  public BSTNode<T> addReturn(T t) {
    if (t == null) {
      throw new NullPointerException();
    }
    BSTNode<T> newNode = new BSTNode<T>(t, null, null);
    root = addToSubtree(root, newNode);
    return newNode;
  }

  protected BSTNode<T> addToSubtree(BSTNode<T> node, BSTNode<T> toAdd) {
    if (node == null) {
      return toAdd;
    }
    int result = toAdd.getData().compareTo(node.getData());
    if (result <= 0) {
      node.setLeft(addToSubtree(node.getLeft(), toAdd));
    } else {
      node.setRight(addToSubtree(node.getRight(), toAdd));
    }
    return node;
  }

  @Override
  public T getMinimum() {
    // TODO: Implement the getMinimum() method
    if(isEmpty()){
      return null;
    }
    BSTNode<T> temp = root;
    T min;
    while(temp.getLeft() != null){
      temp = temp.getLeft();
    }
    min = temp.getData();
    return min;
  }


  @Override
  public T getMaximum() {
    // TODO: Implement the getMaximum() method
    if(isEmpty()){
      return null;
    }
    BSTNode<T> temp = root;
    T max;
    while(temp.getRight() != null){
      temp = temp.getRight();
    }
    max = temp.getData();
    return max;
  }


  @Override
  public int height() {
    // TODO: Implement the height() method
    if(isEmpty()){
      return -1;
    }
    return heightHelper(root);
  }

  private int heightHelper(BSTNode<T> node){   // Helper Method
    if (node == null) {
      return -1;
    }

    int leftHeight = heightHelper(node.getLeft());
    int rightHeight = heightHelper(node.getRight());

    if (leftHeight > rightHeight) {
      return leftHeight + 1;
    } else {
      return rightHeight + 1;
    }

  }


  public Iterator<T> preorderIterator() {
    // TODO: Implement the preorderIterator() method
    Queue<T> queue = new LinkedList<T>();
    preorderTraverse(queue, root);
    return queue.iterator();
  }

  private void preorderTraverse(Queue<T> queue, BSTNode<T> node) {
    if (node != null) {
      queue.add(node.getData());
      preorderTraverse(queue, node.getLeft());
      preorderTraverse(queue, node.getRight());
    }
  }


  public Iterator<T> inorderIterator() {
    Queue<T> queue = new LinkedList<T>();
    inorderTraverse(queue, root);
    return queue.iterator();
  }
  public Iterator<T> inorderIterator(BSTNode<T> root) {
    Queue<T> queue = new LinkedList<T>();
    inorderTraverse(queue, root);
    return queue.iterator();
  }

  private void inorderTraverse(Queue<T> queue, BSTNode<T> node) {
    if (node != null) {
      inorderTraverse(queue, node.getLeft());
      queue.add(node.getData());
      inorderTraverse(queue, node.getRight());
    }
  }

  public Iterator<T> postorderIterator() {
    // TODO: Implement the postorderIterator() method
    Queue<T> queue = new LinkedList<T>();
    postorderTraverse(queue, root);
    return queue.iterator();
  }

  private void postorderTraverse(Queue<T> queue, BSTNode<T> node) {
    if (node != null) {
      postorderTraverse(queue, node.getLeft());
      postorderTraverse(queue, node.getRight());
      queue.add(node.getData());
    }
  }


  @Override
  public boolean equals(BSTInterface<T> other) throws NullPointerException {
    // TODO: Implement the equals() method
    if(other==null){
      throw new NullPointerException();
    }
    return equalsHelper(root, other.getRoot());
  }

  private boolean equalsHelper(BSTNode<T> node, BSTNode<T> otherNode){
    if(otherNode == null && node == null) {
      return true;
    } else if(otherNode == null || node == null) {
      return false;
    }
    if(node.getData().compareTo(otherNode.getData()) != 0){
      return false;
    }
    else {
      return equalsHelper(node.getLeft(), otherNode.getLeft()) && equalsHelper(node.getRight(), otherNode.getRight());
    }
  }


  @Override
  public boolean sameValues(BSTInterface<T> other) throws NullPointerException {
    // TODO: Implement the sameValues() method
    if(other==null){
      throw new NullPointerException();
    }
    Iterator<T> iterator1 = inorderIterator();
    Iterator<T> iterator2 = other.inorderIterator();
    return sameValuesHelper(iterator1, iterator2);
  }

  private boolean sameValuesHelper(Iterator<T> tree1, Iterator<T> tree2){
    while (tree1.hasNext() || tree2.hasNext()){
      if(tree1.hasNext() && tree2.hasNext()){
        if(tree1.next().compareTo(tree2.next()) != 0 ){
          return false;
        }
      } else{
        return false;
      }
    } return true;
  }

  @Override
  public boolean isBalanced() {
    // TODO: Implement the isBalanced() method
    boolean bool = false;
    int height = height();
    int size = size();
    int int1 = (int) Math.pow(2, height);
    int int2 = (int) Math.pow(2, height + 1);
    if( (int1 <= size) && (size < int2) ){
      bool = true;
    }
    return bool;
  }

  @Override
  @SuppressWarnings("unchecked")
  public void balance() {
    // TODO: Implement the balanceHelper() method
    Iterator<T> inorderIter = inorderIterator();
    int size = size();
    T[] values = (T[]) new Comparable[size];
    for(int i=0; i<size; i++){
      values[i] = inorderIter.next();
    }
    root = null;
    balanceHelper(values, 0, size-1);
  }

  @SuppressWarnings("unchecked")
  public void balance(BSTNode<T> node) {
    // TODO: Implement the balanceHelper() method
    Iterator<T> inorderIter = inorderIterator(node);
    int size = subtreeSize(node);
    T[] values = (T[]) new Comparable[size];
    for(int i=0; i<size; i++){
      values[i] = inorderIter.next();
    }
    root = null;
    balanceHelper(values, 0, size-1);
  }

  private void balanceHelper(T[] values, int low, int high){
    if(low==high){
      add(values[low]);
    } else if((low + 1) == high){
      add(values[low]);
      add(values[high]);
    } 
    else {
      int mid = (low + high)/2;
      add(values[mid]);
      balanceHelper(values, low, mid-1);
      balanceHelper(values, mid + 1, high);
    }
  }


  @Override
  public BSTNode<T> getRoot() {
    // DO NOT MODIFY
    return root;
  }

  public static <T extends Comparable<T>> String toDotFormat(BSTNode<T> root) {
    // header
    int count = 0;
    String dot = "digraph G { \n";
    dot += "graph [ordering=\"out\"]; \n";
    // iterative traversal
    Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
    queue.add(root);
    BSTNode<T> cursor;
    while (!queue.isEmpty()) {
      cursor = queue.remove();
      if (cursor.getLeft() != null) {
        // add edge from cursor to left child
        dot += cursor.getData().toString() + " -> " + cursor.getLeft().getData().toString() + ";\n";
        queue.add(cursor.getLeft());
      } else {
        // add dummy node
        dot += "node" + count + " [shape=point];\n";
        dot += cursor.getData().toString() + " -> " + "node" + count + ";\n";
        count++;
      }
      if (cursor.getRight() != null) {
        // add edge from cursor to right child
        dot +=
            cursor.getData().toString() + " -> " + cursor.getRight().getData().toString() + ";\n";
        queue.add(cursor.getRight());
      } else {
        // add dummy node
        dot += "node" + count + " [shape=point];\n";
        dot += cursor.getData().toString() + " -> " + "node" + count + ";\n";
        count++;
      }
    }
    dot += "};";
    return dot;
  }

  public static void main(String[] args) {
    for (String r : new String[] {"a", "b", "c", "d", "e", "f", "g"}) {
      BSTInterface<String> tree = new BinarySearchTree<String>();
      for (String s : new String[] {"d", "b", "a", "c", "f", "e", "g"}) {
        tree.add(s);
      }
      Iterator<String> iterator = tree.inorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
      iterator = tree.preorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
      iterator = tree.postorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();

      System.out.println(tree.remove(r));

      iterator = tree.inorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
    }

    BSTInterface<String> tree = new BinarySearchTree<String>();
    for (String r : new String[] {"a", "b", "c", "d", "e", "f", "g"}) {
      tree.add(r);
    }
    System.out.println(toDotFormat(tree.getRoot()));
    System.out.println(tree.size());
    System.out.println(tree.height());
    System.out.println(tree.isBalanced());
    tree.balance();
    System.out.println(tree.size());
    System.out.println(tree.height());
    System.out.println(tree.isBalanced());
  }
}
