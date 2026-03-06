package com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.repository;

import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.entity.QuantityMeasurementEntity;
import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.exception.PersistenceException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class QuantityMeasurementCacheRepository implements IQuantityMeasurementRepository {

    private static final Logger logger = Logger.getLogger(QuantityMeasurementCacheRepository.class.getName());

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

    // ----------------------------------------------------------------
    // Write operations
    // ----------------------------------------------------------------

    @Override
    public synchronized void save(QuantityMeasurementEntity entity) {
        cache.add(entity);
        File file = new File(FILE_NAME);
        boolean fileExists = file.exists() && file.length() > 0;
        try (FileOutputStream fos = new FileOutputStream(file, true);
             ObjectOutputStream oos = fileExists
                     ? new AppendableObjectOutputStream(fos)
                     : new ObjectOutputStream(fos)) {
            oos.writeObject(entity);
            oos.flush();
        } catch (IOException e) {
            throw new PersistenceException("Failed to persist entity", e);
        }
    }

    @Override
    public synchronized void deleteAll() {
        cache.clear();
        File file = new File(FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
        logger.info("Cache and serialized file cleared.");
    }

    @Override
    public void releaseResources() {
        // No external resources to release for the cache-based repository
        logger.info("No external resources to release for cache repository.");
    }

    // ----------------------------------------------------------------
    // Read operations
    // ----------------------------------------------------------------

    @Override
    public synchronized List<QuantityMeasurementEntity> findAll() {
        return new ArrayList<>(cache);
    }

    @Override
    public synchronized List<QuantityMeasurementEntity> getAllMeasurements() {
        return findAll();
    }

    @Override
    public synchronized List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {
        return cache.stream()
                .filter(e -> operation != null && operation.equalsIgnoreCase(e.getOperation()))
                .collect(Collectors.toList());
    }

    @Override
    public synchronized List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType) {
        return cache.stream()
                .filter(e -> measurementType != null
                        && measurementType.equalsIgnoreCase(e.getThisMeasurementType()))
                .collect(Collectors.toList());
    }

    @Override
    public synchronized int getTotalCount() {
        return cache.size();
    }

    @Override
    public String getPoolStatistics() {
        return "Cache repository — no connection pool. Cached entries: " + cache.size();
    }

    // ----------------------------------------------------------------
    // Disk loading
    // ----------------------------------------------------------------

    private void loadFromDisk() {
        File file = new File(FILE_NAME);
        if (!file.exists() || file.length() == 0) return;

        try (FileInputStream fis = new FileInputStream(file);
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
            // Keep cache usable even if disk file is corrupted
            System.err.println("Warning: Failed to load repo file: " + e.getMessage());
        }
    }

    // ----------------------------------------------------------------
    // Appends to an existing serialization file without re-writing header
    // ----------------------------------------------------------------

    /**
     * Skips writing the stream header when appending to an existing file,
     * preventing stream corruption on subsequent writes.
     */
    private static class AppendableObjectOutputStream extends ObjectOutputStream {
        public AppendableObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            // Suppress header — the file already has one from the first write
            reset();
        }
    }
}
