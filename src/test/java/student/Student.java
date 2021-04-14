package student;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class Student {

    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String gender;
    private LocalDate birthDate;
    private List<String> subjects;
    private List<String> hobbies;
    private String fileName;
    private String currentAddress;
    private String state;
    private String city;
}
