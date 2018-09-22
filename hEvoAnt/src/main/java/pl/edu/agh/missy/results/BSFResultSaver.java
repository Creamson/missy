package pl.edu.agh.missy.results;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;

public class BSFResultSaver {
    private final String resultFileName;
    private long trackingStartTime;
    private StringBuilder unsavedResults = new StringBuilder();
    private int nextCheckpointIndex = 0;
    private File outputFile;

    public BSFResultSaver(String resultFileName) throws IOException {
        this.resultFileName = resultFileName;
        setupResultFile();
    }

    private void setupResultFile() throws IOException {
        outputFile = new File(format("Results/%s.csv", resultFileName));
        outputFile.getParentFile().mkdirs();
        outputFile.createNewFile();
    }

    public void startTracking() {
        this.nextCheckpointIndex = 0;
        this.trackingStartTime = currentTimeMillis();
    }

    public void flushResults() throws IOException {
        long flushStartTimestamp = currentTimeMillis();
        try (PrintWriter file = new PrintWriter(new FileOutputStream(outputFile, true))) {
            file.append(unsavedResults);
            unsavedResults = new StringBuilder();
        } finally {
            trackingStartTime += (currentTimeMillis() - flushStartTimestamp);
        }
    }

    public void recordCheckpoint(double currentSolution, String algorithmName) {
        unsavedResults.append(format("%s,%d,%s,%d\n", currentSolution, currentTimeMillis() - trackingStartTime, algorithmName, nextCheckpointIndex));
        nextCheckpointIndex++;
    }
}