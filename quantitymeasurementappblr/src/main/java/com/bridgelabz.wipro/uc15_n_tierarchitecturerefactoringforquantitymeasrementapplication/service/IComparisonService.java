package com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.service;

import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.dto.QuantityDTO;

public interface IComparisonService {
    boolean compare(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
}
