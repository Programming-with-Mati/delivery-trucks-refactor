package com.globant.javacodecamp.trucks.model;


import java.util.function.BiConsumer;

public record PackageAllocation(
        DeliveryPackage deliveryPackage,
        Truck truck,
        DeliveryPackageAllocationStatus deliveryAllocationStatus
) {

    public static PackageAllocation allocated(DeliveryPackage deliveryPackage, Truck truck) {
        return new PackageAllocation(deliveryPackage, truck, DeliveryPackageAllocationStatus.ALLOCATED);
    }

    public static PackageAllocation notAllocated(DeliveryPackage deliveryPackage) {
        return new PackageAllocation(deliveryPackage, null, DeliveryPackageAllocationStatus.NOT_ALLOCATED);
    }

    public void updateTruckLoad() {
        deliveryAllocationStatus.updateTruckCurrentLoad(truck, deliveryPackage);
    }

    public enum DeliveryPackageAllocationStatus {
        ALLOCATED(Truck::updateCurrentLoad),
        NOT_ALLOCATED((truck, deliveryPackage) -> {});

        private final BiConsumer<Truck, DeliveryPackage> updateCurrentLoad;

        DeliveryPackageAllocationStatus(BiConsumer<Truck, DeliveryPackage> updateCurrentLoad) {
            this.updateCurrentLoad = updateCurrentLoad;
        }

        public void updateTruckCurrentLoad(Truck truck, DeliveryPackage deliveryPackage) {
            updateCurrentLoad.accept(truck, deliveryPackage);
        }
    }
}
