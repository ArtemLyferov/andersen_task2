package by.andersen.intensive4.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends Entity {
    private String surname;
    private String name;
    private String patronymic;
    private LocalDate DOB;
    private String email;
    private String skype;
    private String phoneNumber;
    private LocalDate employmentDate;
    private int experience;
    private DeveloperLevel developerLevel;
    private EnglishLevel englishLevel;
    private Team team;

    public enum DeveloperLevel {
        J1, J2, J3, M1, M2, M3, S1, S2, S3;
    }

    public enum EnglishLevel {
        A1, A2, B1, B2, C1, C2;
    }
}
