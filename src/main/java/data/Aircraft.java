package data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class Aircraft {
    private String model;
    private int passengerCapacity;
    private float range;

}