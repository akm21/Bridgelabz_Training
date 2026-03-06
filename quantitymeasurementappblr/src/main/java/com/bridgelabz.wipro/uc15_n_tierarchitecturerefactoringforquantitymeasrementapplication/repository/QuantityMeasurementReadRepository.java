package com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.repository;

import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.entity.QuantityMeasurementEntity;

import java.util.List;

public interface QuantityMeasurementReadRepository {
    List<QuantityMeasurementEntity> findAll();
}
