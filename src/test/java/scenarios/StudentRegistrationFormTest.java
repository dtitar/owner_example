package scenarios;

import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;
import pageobjects.StudentRegistrationFormPage;
import student.Student;
import student.StudentFactory;

public class StudentRegistrationFormTest extends TestBase {

    Student student = StudentFactory.getRandomStudent();
    StudentRegistrationFormPage studentRegistrationFormPage = new StudentRegistrationFormPage();

    @Test
    @Description("Успешное заполнение формы студента")
    final void successfulFillTest() {
        studentRegistrationFormPage
                .openPage()
                .fillForm(student)
                .checkData(student);
    }
}
