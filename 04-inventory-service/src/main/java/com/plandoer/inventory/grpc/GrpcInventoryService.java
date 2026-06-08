package com.plandoer.inventory.grpc;

import org.acme.inventory.model.InventoryService;

import io.quarkus.grpc.GrpcService;

@GrpcService
public class GrpcInventoryService implements InventoryService {
    @Inject
    CarInventory inventory;

    @Override
    public Uni<CarResponse> add(InsertCarRequest request) {
        Car car = new Car();
        car.licensePlateNumber = request.getLicensePlateNumber();
        car.manufacturer = request.getManufacturer();
        car.model = request.getModel();
        car.id = CarInventory.ids.incrementAndGet();
        Log.info("Persisting " + car);
        inventory.getCars().add(car);
        return Uni.createFrom().item(CarResponse.newBuilder()
                .setLicensePlateNumber(car.licensePlateNumber)
                .setManufacturer(car.manufacturer)
                .setModel(car.model)
                .setId(car.id)
                .build());
    }

    @Override
    public Uni<CarResponse> remove(RemoveCarRequest request) {
        Optional<Car> optionalCar = inventory.getCars().stream()
                .filter(car -> request.getLicensePlateNumber()
                        .equals(car.licensePlateNumber))
                .findFirst();
        if (optionalCar.isPresent()) {
            Car removedCar = optionalCar.get();
            inventory.getCars().remove(removedCar);
            return Uni.createFrom().item(CarResponse.newBuilder()
                    .setLicensePlateNumber(removedCar.licensePlateNumber)
                    .setManufacturer(removedCar.manufacturer)
                    .setModel(removedCar.model)
                    .setId(removedCar.id)
                    .build());
        }
        return Uni.createFrom().nullItem();
    }
}
