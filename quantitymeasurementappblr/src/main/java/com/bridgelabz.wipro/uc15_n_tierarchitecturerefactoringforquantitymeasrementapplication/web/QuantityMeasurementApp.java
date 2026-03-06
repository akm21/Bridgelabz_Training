package com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.web;

import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.dto.QuantityDTO;
import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.repository.IQuantityMeasurementRepository;
import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.repository.QuantityMeasurementCacheRepository;
import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.service.QuantityMeasurementService;
import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.service.QuantityMeasurementServiceImpl;

public class QuantityMeasurementApp {

    private static QuantityMeasurementApp instance;

    public QuantityMeasurementController controller;
    public IQuantityMeasurementRepository repository;

    private QuantityMeasurementApp() {
        this.repository = QuantityMeasurementCacheRepository.getInstance();
        QuantityMeasurementService service = new QuantityMeasurementServiceImpl(this.repository);
        this.controller = new QuantityMeasurementController(service);
    }

    public static synchronized QuantityMeasurementApp getInstance() {
        if (instance == null) instance = new QuantityMeasurementApp();
        return instance;
    }

    public static void main(String[] args) {
        var app = QuantityMeasurementApp.getInstance();

        var ft1 = QuantityDTO.of(1, QuantityDTO.LengthUnit.FEET);
        var in12 = QuantityDTO.of(12, QuantityDTO.LengthUnit.INCHES);

        System.out.println("Compare 1 FEET vs 12 INCHES -> " + (app.controller.performComparison(ft1, in12) ? "Equal" : "Not Equal"));
        System.out.println("Add (in first unit): " + app.controller.performAddition(ft1, in12));
        System.out.println("Add to inches: " + app.controller.performAddition(ft1, in12, QuantityDTO.of(0, QuantityDTO.LengthUnit.INCHES)));

        var f212 = QuantityDTO.of(212, QuantityDTO.TemperatureUnit.FAHRENHEIT);
        var toC = QuantityDTO.of(0, QuantityDTO.TemperatureUnit.CELSIUS);
        System.out.println("Convert 212 F -> " + app.controller.performConversion(f212, toC));

        var sub = app.controller.performSubtraction(QuantityDTO.of(3, QuantityDTO.LengthUnit.FEET),
                in12,
                QuantityDTO.of(0, QuantityDTO.LengthUnit.INCHES));
        System.out.println("Subtract 3 FEET - 12 INCHES -> " + sub);

        double q = app.controller.performDivision(QuantityDTO.of(2, QuantityDTO.LengthUnit.FEET), in12);
        System.out.println("Divide 2 FEET / 12 INCHES -> " + q);
    }
}

