package structures;
import java.lang.Math;

public class ScapegoatTree<T extends Comparable<T>> extends BinarySearchTree<T> {
  private int upperBound = 0;


  @Override
  public void add(T t) {
    // TODO: Implement the add() method
    upperBound++;
    BSTNode<T> newNode = super.addReturn(t);
    int height = height();
    int size = size();
    if(!(height <= (Math.log(upperBound)/Math.log(1.5))) ){
      BSTNode<T> w = findScapeGoat(newNode);
      balance(w);
      upperBound = size();
    }

  }

  private BSTNode<T> findScapeGoat(BSTNode<T> newNode){
    double fraction = 2.0/3;
    double fraction2 = ((double) subtreeSize(newNode))/subtreeSize(newNode.parent);
    if(fraction2 > fraction){
      return newNode.parent;
    } else {
      return findScapeGoat(newNode.parent);
    }
  }

  @Override
  public boolean remove(T element) {
    // TODO: Implement the remove() method
    boolean removed = super.remove(element);
    if(upperBound > 2*size()){
      balance();
      upperBound = size();
    }
    return removed;
  }

  public static void main(String[] args) {
    BSTInterface<String> tree = new ScapegoatTree<String>();
    /*
    You can test your Scapegoat tree here.
    for (String r : new String[] {"0", "1", "2", "3", "4"}) {
      tree.add(r);
      System.out.println(toDotFormat(tree.getRoot()));
    }
    */
  }
}
/*height <= (Math.log(upperBound)/Math.log(1.5)) &&
Math.log(upperBound)/Math.log(1.5) < Math.log(2*size)/Math.log(1.5) &&
Math.log(2*size)/Math.log(1.5) < Math.log(size)+2 */