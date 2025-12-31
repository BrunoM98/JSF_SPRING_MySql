package bm.fit_zone.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
// Create getter and setter.
@Data
// Create an empty constructor with no variables
@NoArgsConstructor
// Create a constructor with arguments/variables.
@AllArgsConstructor
// to string method
@ToString
@EqualsAndHashCode

public class Client {

    @Id
// Generate a variable for the ID.
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer idclient;
    private String name;
    private String surname;
    private Integer member;


}
