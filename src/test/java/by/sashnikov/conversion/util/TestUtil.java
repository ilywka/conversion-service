package by.sashnikov.conversion.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.util.ResourceUtils;

import java.nio.file.Files;
import java.nio.file.Path;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @SneakyThrows
    public static String readFileAsString(String path) {
        Path testFilePath = ResourceUtils.getFile(path).toPath();
        return Files.readString(testFilePath);
    }

    @SneakyThrows
    public static <T> T readFileAsObject(String path, TypeReference<T> typeReference) {
        String fileData = readFileAsString(path);
        return OBJECT_MAPPER.readValue(fileData, typeReference);
    }

    @SneakyThrows
    public static String objectAsJsonString(Object object) {
        return OBJECT_MAPPER.writeValueAsString(object);
    }
}

