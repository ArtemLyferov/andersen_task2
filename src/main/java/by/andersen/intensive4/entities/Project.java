package by.andersen.intensive4.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project extends Entity {
    private String nameProject;
    private String customer;
    private int duration;
    private Methodology methodology;
    private Employee projectManager;
    private Team team;

    public enum Methodology{
        WATERFALL_MODEL, AGILE_MODEL, V_MODEL, RAD, SPIRAL, EXTREME_PROGRAMMING, LEAN;
    }


}