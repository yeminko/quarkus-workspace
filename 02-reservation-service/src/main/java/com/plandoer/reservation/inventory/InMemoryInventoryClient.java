package com.plandoer.reservation.inventory;

import java.util.List;

import jakarta.inject.Singleton;

@Singleton
public class InMemoryInventoryClient implements InventoryClient {

    private static final List<Car> ALL_CARS = List.of(
            new Car(1L, "34 ABC 123", "Toyota", "Corolla"),
            new Car(2L, "35 DEF 456", "Honda", "Civic"),
            new Car(3L, "36 GHI 789", "Ford", "Focus")
    );

    @Override
    public List<Car> allCars() {
        return ALL_CARS;
    }
}