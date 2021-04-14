package student;

import com.github.javafaker.Faker;
import enums.Hobbies;
import enums.States;
import enums.Subjects;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Factory for creating different types of students
 */
public class StudentFactory {

    private static final String FILENAME = "screwYouGuys";
    private static final String EXTENSION = "png";

    public static Student getRandomStudent() {
        Faker fake = new Faker();
        States state = fake.options().option(States.class);
        return Student.builder()
                .firstName(fake.name().firstName())
                .lastName(fake.name().lastName())
                .email(fake.internet().emailAddress())
                .gender(fake.demographic().sex())
                .mobileNumber(fake.phoneNumber().subscriberNumber(10))
                .birthDate(getLocalDateBirthDate(fake.date().birthday()))
                .subjects(List.of(fake.options().option(Subjects.class).name()))
                .hobbies(List.of(fake.options().option(Hobbies.class).getValue()))
                .fileName(fake.file().fileName("", FILENAME, EXTENSION, ""))
                .currentAddress(fake.address().fullAddress())
                .state(state.getValue())
                .city(fake.options().nextElement(state.getCities()))
                .build();
    }

    private static LocalDate getLocalDateBirthDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
