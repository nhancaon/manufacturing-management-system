package com.manufacturing.manufacturingmanagementsystem.dtos.SaleForecast;

import com.manufacturing.manufacturingmanagementsystem.models.SaleForecastsEntity;

import java.util.List;

public interface ISaleForecast {
    public List<SaleForecastsEntity> getAllSaleForecast();
}
