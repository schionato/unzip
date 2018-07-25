package com.github.schionato;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ZipTest {

    private InputStream file;

    @BeforeEach
    void setUp() {
        this.file = ZipTest.class.getResourceAsStream("/some-compressed-file.zip");
    }

    @Test
    void unzip() throws IOException {
        Zip.unzip(file).to("/tmp").build();

        String actual = Files.readAllLines(Paths.get("/tmp/some-file.txt")).get(0);
        assertEquals("unzipped with success!", actual);
    }

}