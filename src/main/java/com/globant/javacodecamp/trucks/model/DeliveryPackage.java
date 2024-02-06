package com.globant.javacodecamp.trucks.model;

public record DeliveryPackage(
        String id,
        int weight,
        int volume,
        int district
) {
}
