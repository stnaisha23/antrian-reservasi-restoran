package com.sitinuraisha.reservasirestoran.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sitinuraisha.reservasirestoran.model.Reservation;
import com.sitinuraisha.reservasirestoran.repositories.ReservationRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public String addReservation(String customerName, String date) {
        LocalDate reservationDate = LocalDate.parse(date);
        DayOfWeek dayOfWeek = reservationDate.getDayOfWeek();

        if (dayOfWeek == DayOfWeek.WEDNESDAY || dayOfWeek == DayOfWeek.FRIDAY) {
            return "Reservasi tidak tersedia pada hari Rabu dan Jumat.";
        }

        List<Reservation> dayReservations = reservationRepository.findByDate(date);

        if (dayReservations.size() >= 2) {
            return "Reservasi penuh untuk tanggal " + date;
        }

        Reservation newReservation = new Reservation();
        newReservation.setCustomerName(customerName);
        newReservation.setDate(date);
        reservationRepository.save(newReservation);
        return "Reservasi berhasil untuk " + customerName + " pada tanggal " + date;
    }

    public Map<String, List<Reservation>> getReservationsForWeek() {
        Map<String, List<Reservation>> weeklyReservations = new HashMap<>();
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate sunday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (LocalDate date = monday; !date.isAfter(sunday); date = date.plusDays(1)) {
            String dateString = date.format(formatter);
            List<Reservation> dayReservations = reservationRepository.findByDate(dateString);

            if (!dayReservations.isEmpty()) {
                weeklyReservations.put(dateString, dayReservations);
            } else {
                System.out.println("No reservations found for date: " + dateString);
            }
        }

        System.out.println("Weekly reservations: " + weeklyReservations);

        return weeklyReservations;
    }
}

