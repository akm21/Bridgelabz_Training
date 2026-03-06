package com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.repository;

import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.entity.QuantityMeasurementEntity;

public interface QuantityMeasurementWriteRepository {
    void save(QuantityMeasurementEntity entity);
    void deleteAll();
    void releaseResources();
}
