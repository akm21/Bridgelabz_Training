package com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.service;

import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.dto.QuantityDTO;

public interface IArithmeticService {
    QuantityDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
    QuantityDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO);

    QuantityDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
    QuantityDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO);

    double divide(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
}
