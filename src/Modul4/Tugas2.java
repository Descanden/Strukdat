package Modul4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Tugas2 {
    private HashMap<String, String> users; // Email -> Password
    private HashMap<String, ArrayList<String>> userDetails; // Email -> [Nama, NIK]
    private HashMap<Integer, String> candidateMap;
    private HashMap<String, Integer> voteCounts;
    private HashMap<String, Boolean> userVoteStatus; // Email -> Status suara
    private String loggedInUser; // Menyimpan email pengguna yang sedang login

    public Tugas2() {
        this.users = new HashMap<>();
        this.userDetails = new HashMap<>();
        this.loggedInUser = null;
        this.candidateMap = new HashMap<>();
        this.voteCounts = new HashMap<>();
        this.userVoteStatus = new HashMap<>();
        initializeCandidates();
    }

    private void initializeCandidates() {
        candidateMap.put(1, "Kandidat A");
        candidateMap.put(2, "Kandidat B");
        candidateMap.put(3, "Kandidat C");

        for (String candidateName : candidateMap.values()) {
            voteCounts.put(candidateName, 0);
        }
    }

    public void register() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("===== Registrasi =====");

        while (true) {
            try {
                System.out.print("Masukkan email (format: example@gmail.com): ");
                String email = scanner.nextLine();

                if (!email.endsWith("@gmail.com")) throw new IllegalArgumentException("Format email tidak valid.");
                if (users.containsKey(email)) throw new IllegalArgumentException("Email sudah terdaftar.");

                System.out.print("Masukkan password: ");
                String password = scanner.nextLine();

                System.out.print("Masukkan nama lengkap: ");
                String nama = scanner.nextLine();

                String nik;
                while (true) {
                    System.out.print("Masukkan NIK: ");
                    nik = scanner.nextLine();
                    if (!isDuplicateNIK(nik)) {
                        break;
                    } else {
                        System.out.println("NIK sudah terdaftar, silakan masukkan NIK yang berbeda.");
                    }
                }

                users.put(email, password);
                ArrayList<String> userInfo = new ArrayList<>();
                userInfo.add(nama);
                userInfo.add(nik);
                userDetails.put(email, userInfo);

                System.out.println("Berhasil Mendaftar.");
                displayMenu();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Gagal Mendaftar: " + e.getMessage());
            }
        }
    }

    private boolean isDuplicateNIK(String nik) {
        for (ArrayList<String> userInfo : userDetails.values()) {
            if (userInfo.get(1).equals(nik)) {
                return true;
            }
        }
        return false;
    }

    public void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("===== Login =====");

        while (true) {
            try {
                System.out.print("Masukkan email: ");
                String email = scanner.nextLine();

                if (!users.containsKey(email)) {
                    throw new IllegalArgumentException("Email tidak terdaftar.");
                }

                System.out.print("Masukkan password: ");
                String password = scanner.nextLine();

                if (!users.get(email).equals(password)) {
                    throw new IllegalArgumentException("Password salah.");
                }

                if (!email.endsWith("@gmail.com")) {
                    throw new IllegalArgumentException("Format email tidak valid.");
                }

                // Simpan email pengguna yang sedang login
                loggedInUser = email;

                if (!userVoteStatus.containsKey(email)) {
                    userVoteStatus.put(email, false); // Set status suara menjadi false jika belum ada
                }

                System.out.println("Login Berhasil.");
                displayCandidates();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Gagal Login: " + e.getMessage());
                displayMenu(); // Kembali ke menu utama setelah gagal login
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
            System.out.println("Anda sudah memberikan suara. Anda hanya dapat memilih satu kandidat.");
            return;
        }

        String candidateName = candidateMap.get(candidateIndex);
        if (candidateName != null) {
            System.out.println("Terima kasih, suara Anda telah direkam untuk " + candidateName + ".");
            int currentVotes = voteCounts.get(candidateName);
            voteCounts.put(candidateName, currentVotes + 1);
            userVoteStatus.put(userEmail, true); // Set status suara menjadi true setelah memberikan suara
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
            System.out.println("Pilih kandidat yang ingin Anda dukung:");
            for (Map.Entry<Integer, String> entry : candidateMap.entrySet()) {
                System.out.println(entry.getKey() + ". " + entry.getValue());
            }

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
                    throw new IllegalArgumentException("Input tidak valid. Harap masukkan angka antara 1 hingga 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Harap masukkan angka antara 1 hingga 3.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void displayResults() {
        System.out.println("Hasil Voting:");
        for (Map.Entry<String, Integer> entry : voteCounts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " suara");
        }
    }

    public void logout() {
        System.out.println("Logout berhasil.");
        loggedInUser = null;
        displayMenu(); // Kembali ke menu utama setelah logout
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Selamat datang di Sistem Voting Online");
        System.out.println("Pilih menu:");
        System.out.println("1. Login");
        System.out.println("2. Daftar");
        System.out.println("3. Hasil Vote");
        System.out.println("4. Keluar");
        System.out.print("Pilihan Anda: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            case 3:
                displayResults();
                displayMenu(); // Kembali ke menu utama setelah melihat hasil vote
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
