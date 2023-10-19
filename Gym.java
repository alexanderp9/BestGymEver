import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Gym {
    public List<Member> membersList = new ArrayList<>();
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate oneYearBack = LocalDate.now().minusYears(1);
    Member foundMember = null;

    public void readDataFromFile(String filename) { // läser data från fil som angetts, "filtrerar" datan om vad som är vad och lägger in person som member i listan.
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String personalNumber = null;
            String fullName = null;
            String line;


            while ((line = reader.readLine()) != null) { // while loop som läser tills det inte finns någon mer rad.
                if (personalNumber == null) { // lägger if null för att vi vet och förväntar oss att den ska fortf va det då den börjar med personalnumber och name i filen så vi vill köra den här koden
                    personalNumber = line.split(", ")[0];
                    fullName = line.split(", ")[1];
                } else {
                    LocalDate paymentDate = LocalDate.parse(line, dateFormat);
                    Member member = new Member(personalNumber, fullName, paymentDate);
                    membersList.add(member);
                    personalNumber = null; // sätter värdena till null igen för att förbereda för nästa inläsning
                    fullName = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean isMemberActive(Member member) { //inkapslar metoden för att kolla om medlemskapet är aktivt och kanske behöva för andra metoder senare.
        LocalDate paymentDate = member.getPaymentDate();
        return paymentDate.isAfter(oneYearBack);


    }

    public Member lookUpMember(String userInput) { // kollar upp en potentiell medlem i listan med hjälp av userinput, ger info om medlem hittad och om medlemskapet är aktivt.
        for (Member member : membersList) {
            if (member.getPersonalNumber().equals(userInput) || member.getFullName().equals(userInput)) {
                foundMember = member;
                break;
            }
        }

        if (foundMember != null) {
            System.out.println("Söker efter: " + userInput);
            System.out.println("Medlem: " + foundMember.getFullName());
            System.out.println("Datum då medlemskapet betalades: " + foundMember.getPaymentDate());


            if (isMemberActive(foundMember)) {
                System.out.println("Aktiv medlem.");
            } else {
                System.out.println("Medlemskapet har löpt ut.");
            }
        } else {
            System.out.println(userInput + " finns inte i systemet.");
        }
        return foundMember;
    }
    public void saveActiveMember(Member foundMember, String filename) { // sparar en medlem i en txt fil om dom har ett aktivt medlemskap med dagens datum.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            LocalDate currentDate = LocalDate.now();
            LocalDate paymentDate = foundMember.getPaymentDate();

            if (paymentDate.isAfter(oneYearBack)) {
                writer.write(foundMember.getPersonalNumber() + ", " + foundMember.getFullName() + "\n" + dateFormat.format(currentDate));
                writer.newLine();
                writer.flush(); // för att skicka ut datan till filen direkt istället för att efter man stängt programmet.
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
