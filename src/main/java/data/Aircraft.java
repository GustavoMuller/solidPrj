package data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

<<<<<<< HEAD:src/main/java/Data/Aircraft.java
@Data
@AllArgsConstructor
public class Aircraft {
    private String model;
    private int passengerCapacity;
    private int range;

=======
@Data @AllArgsConstructor @NoArgsConstructor
public class Aircraft {
    private String model;
    private int passengerCapacity;
    private float range;
>>>>>>> master:src/main/java/data/Aircraft.java
}
