package com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.repository;

import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.entity.QuantityMeasurementEntity;
import java.util.List;

public interface QuantityMeasurementReadRepository {
    List<QuantityMeasurementEntity> findAll();
    List<QuantityMeasurementEntity> getAllMeasurements();
    List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation);
    List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType);
    int getTotalCount();
    String getPoolStatistics();
}
