package com.kurtraschke.common;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UtilsTest {
    @Test
    void validateExtractionDestination() {
        final Path root = Paths.get("/some/where/");
        final Path safeDestination = Paths.get("directory/file.txt");
        final Path unsafeDestination = Paths.get("directory/../../file.txt");

        assertEquals(Paths.get("/some/where/directory/file.txt"), Utils.validateExtractionDestination(root, safeDestination));

        assertThrows(IllegalArgumentException.class, () -> {
            final Path destination = Utils.validateExtractionDestination(root, unsafeDestination);
        });
    }
}