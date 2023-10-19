import java.time.LocalDate;

class Member {
    private String personalNumber;
    private String fullName;
    private LocalDate paymentDate;

    public Member(String personalNumber, String fullName, LocalDate paymentDate) {
        this.personalNumber = personalNumber;
        this.fullName = fullName;
        this.paymentDate = paymentDate;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    @Override
    public String toString() { // toString metod för att skriva ut objekt som string
        return personalNumber + ", " + fullName + "\n" + paymentDate;
    }
}
