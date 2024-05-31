package Modul5;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

class Node {
  int data;
  Node left, right;

  public Node(int data) {
    this.data = data;
    left = right = null;
  }
}

public class Tugas2 {
  private Node root;
  static Map<Integer, String> inventarisBuku = new LinkedHashMap<>();
  static Scanner sc = new Scanner(System.in);

  private Node addNode(Node root, int data) {
    if (root == null) {
      root = new Node(data);
      return root;
    }
    if (data < root.data) {
      root.left = addNode(root.left, data);
    } else if (data > root.data) {
      root.right = addNode(root.right, data);
    }
    return root;
  }

  public void addNode(int data) {
    root = addNode(root, data);
  }

  public void preOrder(Node node) {
    if (node != null) {
      System.out.println(node.data + " " + inventarisBuku.get(node.data));
      preOrder(node.left);
      preOrder(node.right);
    }
  }

  public void inOrder(Node node) {
    if (node != null) {
      inOrder(node.left);
      System.out.println(node.data + " " + inventarisBuku.get(node.data));
      inOrder(node.right);
    }
  }

  public void postOrder(Node node) {
    if (node != null) {
      postOrder(node.left);
      postOrder(node.right);
      System.out.println(node.data + " " + inventarisBuku.get(node.data));
    }
  }

  public Node searchNode(Node root, int data) {
    if (root == null || root.data == data) {
      return root;
    }
    if (data < root.data) {
      return searchNode(root.left, data);
    }
    return searchNode(root.right, data);
  }

  public void searchBook(int data) {
    Node node = searchNode(root, data);
    if (node != null) {
      System.out.println("Buku ditemukan: data = " + data + ", Judul = " + inventarisBuku.get(data));
    } else {
      System.out.println("Buku tidak ditemukan.");
    }
  }

  public void addBook(int data, String namaBuku) {
    inventarisBuku.put(data, namaBuku);
    addNode(data);
  }

  public void displayPreOrder(Node node) {
    System.out.println("\nInventaris Buku (PreOrder): ");
    preOrder(node);
  }

  public void displayInOrder(Node node) {
    System.out.println("\nInventaris Buku (InOrder): ");
    inOrder(node);
  }

  public void displayPostOrder(Node node) {
    System.out.println("\nInventaris Buku (PostOrder): ");
    postOrder(node);
  }

  public static void main(String[] args) {
    Tugas2 obj = new Tugas2();

    // Initial Data
    obj.addBook(123, "Java Programming");
    obj.addBook(21, "Phyton Programming");
    obj.addBook(143, "Statistics");
    obj.addBook(456, "Data Structures and Algorithms");
    obj.addBook(789, "Computer Networks");

    obj.displayPreOrder(obj.root);
    obj.displayInOrder(obj.root);
    obj.displayPostOrder(obj.root);

    while (true) {
      try {
        System.out.println("\nSilahkan dipilih:");
        System.out.println("1. Menambahkan Buku");
        System.out.println("2. Mencari Buku");
        System.out.print("Pilihan: ");
        int pilihan = sc.nextInt();
        sc.nextLine(); // consume newline

        switch (pilihan) {
          case 1:
            System.out.println("\nSilahkan menambahkan buku:");
            System.out.print("data: ");
            int data = sc.nextInt();
            sc.nextLine(); // consume newline
            System.out.print("Nama Buku: ");
            String namaBuku = sc.nextLine();
            obj.addBook(data, namaBuku);
            obj.displayPreOrder(obj.root);
            obj.displayInOrder(obj.root);
            obj.displayPostOrder(obj.root);
            break;
          case 2:
            System.out.println("\nPencarian Buku:");
            System.out.print("Masukkan data: ");
            data = sc.nextInt();
            obj.searchBook(data);
            break;
          default:
            System.out.println("Pilihan tidak valid. Silakan coba lagi.");
        }
      } catch (java.util.InputMismatchException e) {
        System.out.println("Input tidak valid. Mohon masukkan angka.");
        sc.nextLine();
      }
    }
  }
}
