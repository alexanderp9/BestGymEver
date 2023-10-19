import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Gym gym = new Gym();
        gym.readDataFromFile("src/GymMembers.txt");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Skriv in namn eller personnummer, skriv avsluta för att stänga ner programmet. ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("avsluta")) {
                break;
            }
            Member foundMember = gym.lookUpMember(input.trim());

            if(foundMember != null && gym.isMemberActive(foundMember)) {
                gym.saveActiveMember(foundMember,"src/ActiveMembers.txt");
            }
        }
        scanner.close();
    }
}
