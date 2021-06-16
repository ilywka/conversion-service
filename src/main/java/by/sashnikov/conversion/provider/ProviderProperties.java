package by.sashnikov.conversion.provider;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Validated
@Data
@NoArgsConstructor
public class ProviderProperties {

    @NotEmpty
    private String apiUrl;
    @NotEmpty
    private String pairConversionPath;
    @NotEmpty
    private String apiKey;
}
