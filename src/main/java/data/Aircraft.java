package data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class Aircraft {
    private String model;
    private int passengerCapacity;
    private float range;
}
