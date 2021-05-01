package by.andersen.intensive4.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback extends Entity {
    private String description;
    private LocalDate feedbackDate;
    private Employee employee;
}
