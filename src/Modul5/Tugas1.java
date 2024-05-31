package Modul5;

import java.util.Scanner;

class Nodes {
  String data;
  Nodes left;
  Nodes right;

  public Nodes(String data) {
    this.data = data;
    this.left = null;
    this.right = null;
  }
}

public class Tugas1 {
  public Nodes root;

  public Tugas1() {
    root = null;
  }

  public void addRoot(String data) {
    root = new Nodes(data);
  }

  public void addLeft(Nodes parent, String data) {
    parent.left = new Nodes(data);
  }

  public void addRight(Nodes parent, String data) {
    parent.right = new Nodes(data);
  }

  public void inOrder(Nodes node) {
    if (node != null) {
      inOrder(node.left);
      System.out.print(node.data + " ");
      inOrder(node.right);
    }
  }

  public void preOrder(Nodes node) {
    if (node != null) {
      System.out.print(node.data + " ");
      preOrder(node.left);
      preOrder(node.right);
    }
  }

  public void postOrder(Nodes node) {
    if (node != null) {
      postOrder(node.left);
      postOrder(node.right);
      System.out.print(node.data + " ");
    }
  }

  public static void main(String[] args) {
    Tugas1 obj = new Tugas1();
    Scanner sc = new Scanner(System.in);

    System.out.println("Tambahkan 6 data");
    for (int i = 1; i <= 6; i++) {
      System.out.print("Data ke " + i + ": ");
      String input = sc.next();
      if (i == 1) {
        obj.addRoot(input);
      } else if (i == 2) {
        obj.addLeft(obj.root, input);
      } else if (i == 3) {
        obj.addRight(obj.root, input);
      } else if (i == 4) {
        obj.addLeft(obj.root.left, input);
      } else if (i == 5) {
        obj.addRight(obj.root.left, input);
      } else if (i == 6) {
        obj.addLeft(obj.root.right, input);
      }
    }

    System.out.println("\nPre Order: ");
    obj.preOrder(obj.root);

    System.out.println("\nIn Order: ");
    obj.inOrder(obj.root);

    System.out.println("\nPost Order: ");
    obj.postOrder(obj.root);
  }
}
