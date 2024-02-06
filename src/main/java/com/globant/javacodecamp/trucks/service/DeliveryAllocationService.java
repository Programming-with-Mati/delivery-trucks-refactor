package com.globant.javacodecamp.trucks.service;

import com.globant.javacodecamp.trucks.model.DeliveryPackage;
import com.globant.javacodecamp.trucks.model.District;
import com.globant.javacodecamp.trucks.model.Truck;
import com.globant.javacodecamp.trucks.model.PackageAllocation;
import com.globant.javacodecamp.trucks.repository.DeliveryTruckRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class DeliveryAllocationService {

  private final DeliveryTruckRepository truckRepository;

  public DeliveryAllocationService(DeliveryTruckRepository truckRepository) {
    this.truckRepository = truckRepository;
  }

  public List<PackageAllocation> allocatePackagesToTrucks(String packages) {

    List<Truck> trucks = truckRepository.findAll();

    var packagesLines = packages.split("\n");
    var deliveryPackages = new ArrayList<DeliveryPackage>();

    for (String packagesLine : packagesLines) {
      var columns = packagesLine.split(",");
      DeliveryPackage aPackage = new DeliveryPackage(columns[0], Integer.parseInt(columns[1]), Integer.parseInt(columns[2]), Integer.parseInt(columns[3]));
      deliveryPackages.add(aPackage);
    }

    if (deliveryPackages.isEmpty()) {
      throw new RuntimeException("Delivery Packages can't be null");
    }

    if (trucks == null || trucks.isEmpty()) {
      throw new RuntimeException("Delivery Trucks can't be null");
    }

    List<PackageAllocation> packageAllocations = new LinkedList<>();

    for (DeliveryPackage aPackage : deliveryPackages) {
      PackageAllocation packageAllocation = null;
      for (Truck truck : trucks) {
        if (aPackage.weight() < 0 || aPackage.weight() > 25) {
          throw new RuntimeException("Error in line %d. Invalid weight. Field 1");
        }
        if (aPackage.volume() < 0 || aPackage.volume() > 100) {
          throw new RuntimeException("Error in line %d. Invalid volume. Field 2");
        }
        if (
                (truck.getDistricts().stream().map(District::getDistrictNumber).anyMatch(d -> d == aPackage.district()) &&
                        (truck.getCurrentVolume() + aPackage.volume() <= truck.getMaxVolume()) &&
                        (truck.getCurrentWeight() + aPackage.weight() <= truck.getMaxWeight())
                )) {
          packageAllocation = PackageAllocation.allocated(aPackage, truck);
          truck.updateCurrentLoad(aPackage);
          break;
        }
      }
      if (packageAllocation == null) {
        throw new RuntimeException("Unable to allocate package id: %s. No truck available".formatted(aPackage.id()));
      }
      packageAllocations.add(packageAllocation);
    }

    return packageAllocations;
  }

}
