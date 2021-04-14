package pageobjects;

import io.qameta.allure.Step;
import student.Student;

import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class StudentRegistrationFormPage {

    private static final String STUDENT_REGISTRATION_FORM_URL = "https://demoqa.com/automation-practice-form";

    @Step("Open students registration form")
    public final StudentRegistrationFormPage openPage() {
        open(STUDENT_REGISTRATION_FORM_URL);
        $(byClassName("main-header")).shouldHave(text("Practice Form"));
        return this;
    }

    @Step("Fill students registration form")
    public StudentRegistrationFormPage fillForm(Student student) {
        step("Fill common Data", () -> {
            $("#firstName").setValue(student.getFirstName());
            $("#lastName").setValue(student.getLastName());
            $("#userEmail").setValue(student.getEmail());
            $(byValue(student.getGender())).doubleClick();
            $("#userNumber").setValue(student.getMobileNumber());
        });
        step("Set date", () -> {
            $("#dateOfBirthInput").click();
            $(".react-datepicker__month-select").selectOptionByValue(String.valueOf(student.getBirthDate().getMonth().getValue() - 1));
            $(".react-datepicker__year-select").selectOptionByValue(String.valueOf(student.getBirthDate().getYear()));
            $(String.format(".react-datepicker__day--0%s", String.format("%02d", student.getBirthDate().getDayOfMonth()))).click();
        });
        step("Set subjects", () -> {
            $("#subjectsInput").setValue(student.getSubjects().get(0)).pressEnter();
        });
        step("Set hobbies", () -> $(byText(student.getHobbies().get(0))).click());
        step("Upload image", () -> {
            $("#uploadPicture").uploadFromClasspath("images/" + student.getFileName());
        });
        step("Set address", () -> {
            $("#currentAddress").setValue(student.getCurrentAddress());
            $("#react-select-3-input").setValue(student.getState()).pressEnter();
            $("#react-select-4-input").setValue(student.getCity()).pressEnter();
        });
        step("Submit form", () -> $("#submit").click());
        return this;
    }

    public final void checkData(Student student) {
        step("Verify successful form submit", () -> {
            $(byClassName("modal-content")).shouldBe(visible);
            $$(".table tr").shouldHave(size(11));
            $(".table tr", 0).shouldHave(text("Label")).shouldHave(text("Values"));
            $(".table tr", 1).shouldHave(text(student.getFirstName())).shouldHave(text(student.getLastName()));
            $(".table tr", 2).shouldHave(text(student.getEmail()));
            $(".table tr", 3).shouldHave(text(student.getGender()));
            $(".table tr", 4).shouldHave(text(student.getMobileNumber()));
            $(".table tr", 5).shouldHave(text(student.getBirthDate().format(DateTimeFormatter.ofPattern("dd MMMM,yyyy"))));
            $(".table tr", 6).shouldHave(text(String.join(", ", student.getSubjects())));
            $(".table tr", 7).shouldHave(text(String.join(", ", student.getHobbies())));
            $(".table tr", 8).shouldHave(text(student.getFileName()));
            $(".table tr", 9).shouldHave(text(student.getCurrentAddress()));
            $(".table tr", 10).shouldHave(text(student.getState())).shouldHave(text(student.getCity()));
        });
    }
}
