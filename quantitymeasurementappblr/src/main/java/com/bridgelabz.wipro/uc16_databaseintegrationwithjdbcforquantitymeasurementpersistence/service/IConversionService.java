package com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.service;


import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.entity.QuantityDTO;

public interface IConversionService {
    QuantityDTO convert(QuantityDTO thisQuantityDTO, QuantityDTO targetUnitDTO);
}
