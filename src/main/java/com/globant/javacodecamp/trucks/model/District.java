package com.globant.javacodecamp.trucks.model;

public enum District {

    DISTRICT_1(1),
    DISTRICT_2(2),
    DISTRICT_3(3),
    DISTRICT_4(4),
    DISTRICT_5(5);

    private final int districtNumber;

    District(int districtNumber) {
        this.districtNumber = districtNumber;
    }

    public int getDistrictNumber() {
        return districtNumber;
    }
}
