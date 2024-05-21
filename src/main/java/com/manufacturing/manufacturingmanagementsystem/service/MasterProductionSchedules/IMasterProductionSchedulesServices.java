package com.manufacturing.manufacturingmanagementsystem.service.MasterProductionSchedules;

import com.manufacturing.manufacturingmanagementsystem.dtos.MasterProductionSchedulesDTO;
import com.manufacturing.manufacturingmanagementsystem.dtos.requests.MPS.MPSRequest;
import com.manufacturing.manufacturingmanagementsystem.dtos.requests.MPS.MPSUpdateRequest;
import com.manufacturing.manufacturingmanagementsystem.dtos.responses.MPS.MPSSuggestionMonthlyResponse;
import com.manufacturing.manufacturingmanagementsystem.models.MasterProductionSchedulesEntity;

import java.sql.Date;
import java.util.List;

public interface IMasterProductionSchedulesServices {
    void createMPS(MPSRequest mpsRequest);

    void updateMPS(MPSUpdateRequest mpsRequest);

    List<MasterProductionSchedulesEntity> getAllMPSofPM(Long pmID);

    List<MasterProductionSchedulesEntity> getALl();

    void deleteMPS(Long id);

    MPSSuggestionMonthlyResponse suggestMPSMonthly(Long productId, Date month);
}

