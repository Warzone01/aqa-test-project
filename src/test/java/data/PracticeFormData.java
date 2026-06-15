package data;

import java.util.List;

public record PracticeFormData(
        String firstName,
        String lastName,
        String email,
        String gender,
        String mobile,
        String birthDay,
        String birthMonth,
        String birthYear,
        List<String> subjects,
        List<String> hobbies,
        String picture,
        String address,
        String state,
        String city
) {

    public static PracticeFormData validStudent() {
        return new PracticeFormData(
                "Kirill",
                "Ivanov",
                "kirill.ivanov@example.com",
                "Male",
                "9991234567",
                "15",
                "June",
                "1995",
                List.of("Maths", "English"),
                List.of("Sports", "Reading"),
                "student-avatar.svg",
                "Saint Petersburg, Nevsky prospect, 1",
                "NCR",
                "Delhi"
        );
    }

    public String fullName() {
        return firstName + " " + lastName;
    }

    public String dateOfBirth() {
        return birthDay + " " + birthMonth + "," + birthYear;
    }
}
