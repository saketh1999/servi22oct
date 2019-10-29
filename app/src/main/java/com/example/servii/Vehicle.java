package com.example.servii;

public class Vehicle {
    private String VehicleType;
    private String VehicleColor;
    private String VehicleBrand;
    private String VehicleModel;
    private String VehiclePlateNo;

    public Vehicle(){

    }

    public Vehicle(String vehicleType, String vehicleColor, String vehicleBrand, String vehicleModel, String vehiclePlateNo) {
        VehicleType = vehicleType;
        VehicleColor = vehicleColor;
        VehicleBrand = vehicleBrand;
        VehicleModel = vehicleModel;
        VehiclePlateNo = vehiclePlateNo;
    }

    public String getVehicleType() {
        return VehicleType;
    }

    public void setVehicleType(String vehicleType) {
        VehicleType = vehicleType;
    }

    public String getVehicleColor() {
        return VehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        VehicleColor = vehicleColor;
    }

    public String getVehicleBrand() {
        return VehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        VehicleBrand = vehicleBrand;
    }

    public String getVehicleModel() {
        return VehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        VehicleModel = vehicleModel;
    }

    public String getVehiclePlateNo() {
        return VehiclePlateNo;
    }

    public void setVehiclePlateNo(String vehiclePlateNo) {
        VehiclePlateNo = vehiclePlateNo;
    }
}

