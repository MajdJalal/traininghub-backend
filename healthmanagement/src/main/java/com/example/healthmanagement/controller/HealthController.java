package com.example.healthmanagement.controller;


import com.example.healthmanagement.model.BookAppointmentModel;
import com.example.healthmanagement.service.HealthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/health/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class HealthController {

    private HealthService healthService;

    public HealthController(HealthService iAccountService) {
        this.healthService = iAccountService;
    }
    @PostMapping("/appointment")
    public ResponseEntity<> bookAppointment(@Valid @RequestBody BookAppointmentModel bookAppointmentModel) {
        healthService.bookAppointment(bookAppointmentModel);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.builder().statusMsg("created a new appointment").build());
    }



}
