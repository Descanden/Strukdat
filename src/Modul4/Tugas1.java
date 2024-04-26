package Modul4;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Map;


public class Tugas1 {
    private Map<Integer, String> candidateMap;
    private Map<String, Integer> voteCounts;

    public Tugas1() {
        this.candidateMap = new HashMap<>();
        this.voteCounts = new HashMap<>();
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

    public void recordVote(int candidateIndex) {
        String candidateName = candidateMap.get(candidateIndex);
        if (candidateName != null) {
            int currentVotes = voteCounts.get(candidateName);
            voteCounts.put(candidateName, currentVotes + 1);
            System.out.println("Terima kasih, suara Anda telah direkam untuk " + candidateName + ".");
        } else {
            System.out.println("Kandidat tidak valid.");
        }
    }

// public void recordVote(String candidateName) {
    //     if (this.candidates.containsKey(candidateName)) {
    //         int currentVotes = this.candidates.get(candidateName);
    //         this.candidates.put(candidateName, currentVotes + 1);
    //         System.out.println("Terima kasih, suara Anda telah direkam.");
    //     } else {
    //         System.out.println("Kandidat tidak valid.");
    //     }
    // }

    public void displayCandidates() {
        System.out.println("Pilih kandidat yang ingin Anda dukung:");
        for (Map.Entry<Integer, String> entry : candidateMap.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue());
        }
    }

    public void displayResults() {
        System.out.println("Hasil Voting:");
        for (Map.Entry<String, Integer> entry : voteCounts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " suara");
        }
    }

    public static void main(String[] args) {
        Tugas1 votingSystem = new Tugas1();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Selamat datang di Sistem Voting Online");

        while (true) {
            votingSystem.displayCandidates();
            System.out.print("Masukkan nomor kandidat (1-3) atau ketik 'selesai' untuk keluar: ");
            
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("selesai")) {
                break;
            }

            try {
                int candidateIndex = Integer.parseInt(input);
                if (candidateIndex >= 1 && candidateIndex <= 3) {
                    votingSystem.recordVote(candidateIndex);
                } else {
                    System.out.println("Input tidak valid. Harap masukkan angka antara 1 hingga 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Harap masukkan angka antara 1 hingga 3.");
            }
        }

        votingSystem.displayResults();
        scanner.close();
    }
}

//     public static void main(String[] args) {
//         Tugas1 votingSystem = new Tugas1();
//         votingSystem.addCandidate("Kandidat A");
//         votingSystem.addCandidate("Kandidat B");
//         votingSystem.addCandidate("Kandidat C");

//         Scanner scanner = new Scanner(System.in);
//         System.out.println("Selamat datang di Sistem Voting Online");

//         while (true) {
//             votingSystem.displayCandidates();
//             System.out.print("Masukkan nama kandidat (atau ketik 'selesai' untuk keluar): ");
//             String input = scanner.nextLine();

//             if (input.equalsIgnoreCase("selesai")) {
//                 break;
//             }

//             votingSystem.recordVote(input);
//         }

//         votingSystem.displayResults();
//         scanner.close();
//     }
// }




