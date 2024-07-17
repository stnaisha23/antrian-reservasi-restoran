package com.sitinuraisha.reservasirestoran.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sitinuraisha.reservasirestoran.model.Reservation;
import com.sitinuraisha.reservasirestoran.services.ReservationService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/antiran-reservasi")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping("/add")
    public String addReservation(@RequestParam String customerName, @RequestParam String date) {
        return reservationService.addReservation(customerName, date);
    }

    @GetMapping("/perminggu")
    public Map<String, List<Reservation>> getReservationsForWeek() {
        return reservationService.getReservationsForWeek();
    }
}

