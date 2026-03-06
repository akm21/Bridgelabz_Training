package com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.repository;

import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.exception.PersistenceException;
import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.entity.QuantityMeasurementEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class QuantityMeasurementCacheRepository implements IQuantityMeasurementRepository {

    private static final String FILE_NAME = "quantity_measurement_repo.ser";
    private final List<QuantityMeasurementEntity> cache = new ArrayList<>();

    private static QuantityMeasurementCacheRepository instance;

    private QuantityMeasurementCacheRepository() {
        loadFromDisk();
    }

    public static synchronized QuantityMeasurementCacheRepository getInstance() {
        if (instance == null) instance = new QuantityMeasurementCacheRepository();
        return instance;
    }

    @Override
    public synchronized void save(QuantityMeasurementEntity entity) {
        cache.add(entity);
        try (FileOutputStream fos = new FileOutputStream(FILE_NAME, true);
             AppendableObjectOutputStream oos = new AppendableObjectOutputStream(fos)) {
            oos.writeObject(entity);
            oos.flush();
        } catch (IOException e) {
            throw new PersistenceException("Failed to persist entity", e);
        }
    }

    @Override
    public synchronized List<QuantityMeasurementEntity> findAll() {
        return new ArrayList<>(cache);
    }

    private void loadFromDisk() {
        File file = new File(FILE_NAME);
        if (!file.exists() || file.length() == 0) return;

        try (FileInputStream fis = new FileInputStream(FILE_NAME);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            while (true) {
                try {
                    QuantityMeasurementEntity entity = (QuantityMeasurementEntity) ois.readObject();
                    cache.add(entity);
                } catch (EOFException eof) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            // Keep cache usable even if disk file corrupted
            System.err.println("Warning: Failed to load repo file: " + e.getMessage());
        }
    }

    /**
     * Skips header when appending, prevents stream corruption.
     */
    static class AppendableObjectOutputStream extends ObjectOutputStream {
        public AppendableObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            File file = new File(FILE_NAME);
            if (!file.exists() || file.length() == 0) super.writeStreamHeader();
            else reset();
        }
    }
}
