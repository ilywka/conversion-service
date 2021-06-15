package by.sashnikov.conversion.provider;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Validated
@Configuration
@ConfigurationProperties("conversion.provider.exchangerate-api-com")
public class ProviderProperties {

    @NotEmpty
    private String apiUrl;
    @NotEmpty
    private String pairConversionPath;
    @NotEmpty
    private String apiKey;
}
