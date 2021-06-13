package by.sashnikov.conversion.util;

import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TestUtil {

    public static String readFileAsString(String path) throws IOException {
        Path testFilePath = ResourceUtils.getFile(path).toPath();
        return Files.readString(testFilePath);
    }
}

