package com.github.schionato;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

class Zip {

    private final InputStream is;
    private final String targetLocation;

    private Zip(InputStream is, String targetLocation) {
        this.is = is;
        this.targetLocation = targetLocation;
    }

    private void uncompress() {
        byte[] buffer = new byte[2048];

        try (ZipInputStream zip = new ZipInputStream(is)) {

            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                File newFile = new File(targetLocation + File.separator + entry.getName());
                if (entry.isDirectory()) {
                    newFile.mkdirs();
                } else {
                    try (OutputStream fos = new FileOutputStream(newFile)) {
                        int len;
                        while ((len = zip.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static UnzipBuilder unzip(InputStream is) {
        return new UnzipBuilder(is);
    }

    static class UnzipBuilder {

        private final InputStream is;
        private String targetLocation;

        private UnzipBuilder(InputStream is) {
            this.is = is;
        }

        UnzipBuilder to(String targetLocation) {
            this.targetLocation = targetLocation;
            return this;
        }

        void build() {
            new Zip(is, targetLocation).uncompress();
        }
    }

}
