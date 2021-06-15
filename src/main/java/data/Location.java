package data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data @AllArgsConstructor @NoArgsConstructor
public class Location {
    private String country;
    private String city;

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Location)) return false;
        Location location = (Location) other;
        return getCountry().equals(location.getCountry()) && getCity().equals(location.getCity());
    }

}
