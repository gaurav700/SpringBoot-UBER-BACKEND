package com.UBERAPP.UBER_BACKEND_PROJECT.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(indexes = {
        @Index(name = "index_rating_rider", columnList = "rider_id"),
        @Index(name = "index_rating_driver",columnList = "driver_id")
})
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @OneToOne
    private Ride ride;

    @ManyToOne
    private Rider rider;

    @ManyToOne
    private Driver driver;

    private Integer driverRating;

    private Integer riderRating;
}
