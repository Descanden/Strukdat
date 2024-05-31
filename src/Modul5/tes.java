package Modul5;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

class Node {

  int data;
  Node left;
  Node right;

  public Node(int data) {
    this.data = data;
  }
}

public class tes {

  public Node root;
  static HashMap<Integer, String> inventarisBuku = new LinkedHashMap<>();
  static Scanner sc = new Scanner(System.in);

  private Node NewNode(Node root, Node newData) {
    if (root == null) {
      root = newData;
      return root;
    }
    if (newData.data < root.data) {
      root.left = NewNode(root.left, newData);
    } else {
      root.right = NewNode(root.right, newData);
    }
    return root;
  }

  public void NewNode(int data) {
    root = NewNode(root, new Node(data));
  }

  public void inOrder(Node node) {
    if (node != null) {
      inOrder(node.left);
      System.out.println(node.data + " " + inventarisBuku.get(node.data));
      inOrder(node.right);
    }
  }

  public void preOrder(Node node) {
    if (node != null) {
      System.out.println(node.data + " " + inventarisBuku.get(node.data));
      preOrder(node.left);
      preOrder(node.right);
    }
  }

  public void postOrder(Node node) {
    if (node != null) {
      postOrder(node.left);
      postOrder(node.right);
      System.out.println(node.data + " " + inventarisBuku.get(node.data));
    }
  }

  public static void main(String[] args) {
    tes obj = new tes();
    int isbn;
    String namaBuku;

    inventarisBuku.put(123, "Java Programming");
    inventarisBuku.put(21, "Phyton Programming");
    inventarisBuku.put(456, "Data Structures and Algorithms");
    inventarisBuku.put(143, "Statistics");
    inventarisBuku.put(789, "Computer Networks");

    obj.display();

    System.out.println("\nTambah data: ");
    System.out.print("ISBN: ");

    isbn = sc.nextInt();
    System.out.print("Nama Buku: ");
    namaBuku = sc.next();
    obj.tambahData(isbn, namaBuku);
    obj.display();
  }

  public void tambahData(Integer isbn, String namaBuku) {
    inventarisBuku.put(isbn, namaBuku);
  }

  public void display() {
    tes objDisplay = new tes();

    for (Integer key : inventarisBuku.keySet()) {
      objDisplay.NewNode(key);
    }

    System.out.println(
      "\nInventaris Buku (terurut berdasarkan ISBN - PreOrder): "
    );
    objDisplay.preOrder(objDisplay.root);

    System.out.println(
      "\nInventaris Buku (terurut berdasarkan ISBN - InOrder): "
    );
    objDisplay.inOrder(objDisplay.root);

    System.out.println(
      "\nInventaris Buku (terurut berdasarkan ISBN - PostOrder): "
    );
    objDisplay.postOrder(objDisplay.root);
  }
}