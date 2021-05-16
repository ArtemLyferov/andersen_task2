package by.andersen.intensive4.jdbc;

import by.andersen.intensive4.entities.Employee;
import by.andersen.intensive4.entities.Feedback;
import by.andersen.intensive4.entities.Project;
import by.andersen.intensive4.entities.Team;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
@ComponentScan("by.andersen.intensive4")
public class SpringConfigTest {

    @Bean
    public Team testTeam() {
        return new Team("Test team 1");
    }

    @Bean
    public LocalDate testDOB() {
        return LocalDate.of(1990, 12, 3);
    }

    @Bean
    public LocalDate testEmploymentDate() {
        return LocalDate.of(2018, 2, 3);
    }

    @Bean
    public Employee.DeveloperLevel testDeveloperLevel() {
        return Employee.DeveloperLevel.J3;
    }

    @Bean
    public Employee.EnglishLevel testEnglishLevel() {
        return Employee.EnglishLevel.A2;
    }

    @Bean
    public Employee testEmployee() {
        return new Employee("Petrov", "Anton", "Semenovich", testDOB(),
                "petrov@gmail.com", "live:petrov", "+375291112233", testEmploymentDate(),
                4, testDeveloperLevel(), testEnglishLevel(), testTeam());
    }

    @Bean
    public LocalDate testFeedbackDate() {
        return LocalDate.of(2020, 5, 12);
    }

    @Bean
    public Feedback testFeedback() {
        return new Feedback("Test feedback 1", testFeedbackDate(), testEmployee());
    }

    @Bean
    public Project.Methodology testMethodology() {
        return Project.Methodology.AGILE_MODEL;
    }

    @Bean
    public Project testProject() {
        return new Project("Test project 1", "Test customer", 200, testMethodology(),
                testEmployee(), testTeam());
    }
}
