import java.sql.Struct;
import java.util.HashMap;
import java.util.Map;

public class Huffman {

  private HuffmanData[] leafEntries;
  private final static int SIZE = 50;
  private PriorityQueueInterface<BinaryTreeInterface<HuffmanData>> pq;
  private BinaryTreeInterface<HuffmanData> huffmanTree;

  public Huffman() {
    leafEntries = new HuffmanData[SIZE];
    pq = new PriorityQueue<>();
    huffmanTree = new BinaryTree<>();
  }

  public void setFrequencies() {
    // Create test data and store as an array of HuffData
    leafEntries[0] = new HuffmanData(5000, 'a');
    leafEntries[1] = new HuffmanData(2000, 'b');
    leafEntries[2] = new HuffmanData(10000, 'c');
    leafEntries[3] = new HuffmanData(8000, 'd');
    leafEntries[4] = new HuffmanData(22000, 'e');
    leafEntries[5] = new HuffmanData(49000, 'f');
    leafEntries[6] = new HuffmanData(4000, 'g');
  }

  public void setPriorityQueue() {
    // Copy test data from array LeafEntries of HuffData
    // into a PriorityQueue of HuffmanTree
    for (int i = 0; i < 7; i++) {
      if (leafEntries[i].getFrequency() > 0)
        pq.add(new BinaryTree<>(leafEntries[i]));
    }
  }

  public void createHuffmanTree() {
    while (pq.getSize() > 1) {
      BinaryTreeInterface<HuffmanData> left = pq.removeMin();
      BinaryTreeInterface<HuffmanData> right = pq.removeMin();
      HuffmanData sumOfLeftAndRight =
              new HuffmanData(
                      left.getRootData().getFrequency() + right.getRootData().getFrequency(),
                      ' ');

      BinaryTree<HuffmanData> newNode = new BinaryTree<>(
              sumOfLeftAndRight,
              (BinaryTree<HuffmanData>) left,
              (BinaryTree<HuffmanData>) right
      );
      pq.add(newNode);
    }
    huffmanTree = pq.removeMin();
  }

  public void printCode() {
    Map<Character, String> character2ByteSequence = new HashMap<>();
    printCodeProcedure("", (BinaryTree<HuffmanData>) huffmanTree, character2ByteSequence);
    for (Map.Entry<Character, String> entry : character2ByteSequence.entrySet()) {
      System.out.println(entry.getKey() + ": " + entry.getValue());
    }
  }

  private void printCodeProcedure(String code, BinaryTree<HuffmanData> tree,
                                  Map<Character, String> character2ByteSequence) {
    if (contains(leafEntries, tree.getRootData())) {
      character2ByteSequence.put(tree.getRootData().getSymbol(), code);
    } else {
      printCodeProcedure(code + "0", tree.getLeftSubtree(), character2ByteSequence);
      printCodeProcedure(code + "1", tree.getRightSubtree(), character2ByteSequence);
    }
  }

  private <T> boolean contains(T[] array, T data) {
    for (T element : array) {
      if (element != null && element.equals(data)) {
        return true;
      }
    }
    return false;
  }
}
