package com.plandoer.reservation.rest;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.resteasy.reactive.RestQuery;

import com.plandoer.reservation.Reservation;
import com.plandoer.reservation.ReservationsRepository;
import com.plandoer.reservation.inventory.Car;
import com.plandoer.reservation.inventory.InventoryClient;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("reservation")
@Produces(MediaType.APPLICATION_JSON)
public class ReservationResource {
    private final ReservationsRepository reservationsRepository;
    private final InventoryClient inventoryClient;

    public ReservationResource(ReservationsRepository reservations, InventoryClient inventoryClient) {
        this.reservationsRepository = reservations;
        this.inventoryClient = inventoryClient;
    }

    @GET
    @Path("availability")
    public Collection<Car> availability(@RestQuery LocalDate startDate, @RestQuery LocalDate endDate) {
        List<Car> availableCars = inventoryClient.allCars();
        Map<Long, Car> carsById = new HashMap<>();

        for (Car car : availableCars) {
            carsById.put(car.id, car);
        }

        List<Reservation> reservations = reservationsRepository.findAll();

        for (Reservation reservation : reservations) {
            if (reservation.isReserved(startDate, endDate)) {
                carsById.remove(reservation.carId);
            }
        }
        return carsById.values();
    }
    
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public Reservation make(Reservation reservation) {
        return reservationsRepository.save(reservation);
    }
}
