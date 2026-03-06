package com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.service;

import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.entity.QuantityDTO;

public interface IComparisonService {
    boolean compare(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
}
