package by.andersen.intensive4.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback extends Entity {
    private String description;
    private Date feedbackDate;
    private Employee employee;
}
