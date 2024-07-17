package com.sitinuraisha.reservasirestoran.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sitinuraisha.reservasirestoran.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByDate(String date);
}
