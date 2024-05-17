package Modul4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Tugas2 {
    private HashMap<String, String> users; // Email -> Password
    private HashMap<String, ArrayList<String>> userDetails; 
    private HashMap<Integer, String> candidateMap;
    private HashMap<String, Integer> voteCounts; 
    private HashMap<String, Boolean> userVoteStatus; 
    private String loggedInUser; 

    public Tugas2() {
        users = new HashMap<>();
        userDetails = new HashMap<>();
        loggedInUser = null;
        candidateMap = new HashMap<>();
        voteCounts = new HashMap<>();
        userVoteStatus = new HashMap<>();
        initializeCandidates();
    }

    private void initializeCandidates() {
        candidateMap.put(1, "Kandidat A");
        candidateMap.put(2, "Kandidat B");
        candidateMap.put(3, "Kandidat C");
        candidateMap.values().forEach(candidateName -> voteCounts.put(candidateName, 0));
    }

    public void register() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n===== Registrasi =====");
        while (true) {
            try {
                System.out.print("Masukkan email (format: example@gmail.com): ");
                String email = scanner.nextLine();
                if (!email.endsWith("@gmail.com"))
                    throw new IllegalArgumentException("Format email tidak valid.");
                if (users.containsKey(email))
                    throw new IllegalArgumentException("Email sudah terdaftar.");
                System.out.print("-Masukkan password: ");
                String password = scanner.nextLine();
                System.out.print("-Masukkan nama lengkap: ");
                String nama = scanner.nextLine();
                String nik;
                while (true) {
                    System.out.print("-Masukkan NIK: ");
                    nik = scanner.nextLine();
                    if (!isDuplicateNIK(nik))
                        break;
                    System.out.println("\nNIK sudah terdaftar, silakan masukkan NIK yang berbeda.");
                }
                users.put(email, password);
                ArrayList<String> userInfo = new ArrayList<>();
                userInfo.add(nama);
                userInfo.add(nik);
                userDetails.put(email, userInfo);
                System.out.println("BERHASIL MENDAFTAR.");
                displayMenu();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Gagal Mendaftar: \n" + e.getMessage());
            }
        }
    }

    private boolean isDuplicateNIK(String nik) {
        return userDetails.values().stream().anyMatch(userInfo -> userInfo.get(1).equals(nik));
    }

    public void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("===== Login =====");
        while (true) {
            try {
                System.out.print("-Masukkan email: ");
                String email = scanner.nextLine();
                if (!users.containsKey(email))
                    throw new IllegalArgumentException("Email tidak terdaftar.");
                System.out.print("-Masukkan password: ");
                String password = scanner.nextLine();
                if (!users.get(email).equals(password))
                    throw new IllegalArgumentException("Password salah.");
                if (!email.endsWith("@gmail.com"))
                    throw new IllegalArgumentException("Format email tidak valid.\n");
                loggedInUser = email;
                userVoteStatus.putIfAbsent(email, false);
                System.out.println("Login Berhasil.");
                displayCandidates();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Gagal Login: " + e.getMessage());
                displayMenu();
                break;
            }
        }
    }

    public void vote(int candidateIndex) {
        String userEmail = loggedInUser;
        if (userEmail == null) {
            System.out.println("Silakan login terlebih dahulu.");
            return;
        }
        if (userVoteStatus.get(userEmail)) {
            System.out.println("\nAnda sudah memberikan suara. Anda hanya dapat memilih satu kandidat.");
            return;
        }
        String candidateName = candidateMap.get(candidateIndex);
        if (candidateName != null) {
            System.out.println("Terima kasih, suara Anda telah direkam untuk " + candidateName + ".");
            voteCounts.put(candidateName, voteCounts.get(candidateName) + 1);
            userVoteStatus.put(userEmail, true);
        } else {
            System.out.println("Kandidat tidak valid.");
        }
    }

    public void displayCandidates() {
        String userEmail = loggedInUser;
        if (userEmail == null) {
            System.out.println("Silakan login terlebih dahulu.");
            displayMenu();
            return;
        }
        if (userVoteStatus.get(userEmail)) {
            System.out.println("Anda sudah memberikan suara.");
            displayMenu();
            return;
        }
        System.out.println("Selamat datang, " + userDetails.get(userEmail).get(0) + ".");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nPilih kandidat yang ingin Anda dukung:");
            candidateMap.forEach((index, candidate) -> System.out.println(index + ". " + candidate));
            System.out.print("Masukkan nomor kandidat (1-3) atau ketik 'selesai' untuk keluar: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("selesai")) {
                displayMenu();
                break;
            }
            try {
                int candidateIndex = Integer.parseInt(input);
                if (candidateIndex >= 1 && candidateIndex <= 3) {
                    vote(candidateIndex);
                } else {
                    throw new IllegalArgumentException("Input tidak valid. Harap masukkan angka antara 1 hingga 3.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Harap masukkan angka antara 1 hingga 3.\n");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void displayResults() {
        System.out.println("Hasil Voting:");
        voteCounts.forEach((candidate, count) -> System.out.println(candidate + ": " + count + " suara"));
    }

    public void logout() {
        System.out.println("Logout berhasil.");
        loggedInUser = null;
        displayMenu();
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nSelamat datang di Sistem Voting Online");
        System.out.println("Pilih menu:");
        System.out.println("1. Login");
        System.out.println("2. Daftar");
        System.out.println("3. Hasil Vote");
        System.out.println("4. Keluar");
        System.out.print("Pilihan Anda: ");
        int choice = 0;
        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Input tidak valid.\n");
            displayMenu();
            return;
        }
        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            case 3:
                displayResults();
                displayMenu();
                break;
            case 4:
                System.out.println("Terima kasih. Sampai jumpa!");
                System.exit(0);
                break;
            default:
                System.out.println("Pilihan tidak valid.");
                displayMenu();
        }
    }

    public static void main(String[] args) {
        Tugas2 dataPemilih = new Tugas2();
        dataPemilih.displayMenu();
    }
}
