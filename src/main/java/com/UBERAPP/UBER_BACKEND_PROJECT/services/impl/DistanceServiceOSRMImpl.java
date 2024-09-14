package com.UBERAPP.UBER_BACKEND_PROJECT.services.impl;

import com.UBERAPP.UBER_BACKEND_PROJECT.services.DistanceService;
import lombok.Data;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class DistanceServiceOSRMImpl implements DistanceService {

    private static final String OSRM_API = "http://router.project-osrm.org/route/v1/driving/";
    @Override
    public double calculatedDistance(Point src, Point dst) {
        // call the third party api called OSRM to fetch the distance
        try{
            String uri = src.getX()+","+src.getY()+";"+dst.getX()+","+dst.getY();
            OSRMResponseDTO responseDTO = RestClient.builder()
                .baseUrl(OSRM_API)
                .build()
                .get()
                .uri(uri)
                .retrieve()
                .body(OSRMResponseDTO.class);
            return responseDTO.getRoutes().get(0).getDistance() / 1000.0;
        }
        catch (Exception e){
            throw new RuntimeException("Error getting data from OSRM"+ e.getMessage());
        }

    }
}

@Data
class OSRMResponseDTO {
    private List<OSRMRoutes> routes;
}

@Data
class OSRMRoutes {
    private Double distance;
}

