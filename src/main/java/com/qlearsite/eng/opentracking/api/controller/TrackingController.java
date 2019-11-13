package com.qlearsite.eng.opentracking.api.controller;

import com.qlearsite.eng.opentracking.service.TrackingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/tracking")
public class TrackingController {

    @Autowired
    private TrackingService trackingService;

    @GetMapping(value = "/img/transparent.png/{id}",
            produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getDistributions(@PathVariable("id") String id) {
        return trackingService.track(id);
    }
}