package com.UBERAPP.UBER_BACKEND_PROJECT.services;


import org.locationtech.jts.geom.Point;

public interface DistanceService {
    double calculatedDistance(Point src, Point dst);

}
