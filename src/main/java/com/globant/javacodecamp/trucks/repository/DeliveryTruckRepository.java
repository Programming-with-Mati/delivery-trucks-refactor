package com.globant.javacodecamp.trucks.repository;

import com.globant.javacodecamp.trucks.model.Truck;

import java.util.List;

public interface DeliveryTruckRepository {
  List<Truck> findAll();
}
