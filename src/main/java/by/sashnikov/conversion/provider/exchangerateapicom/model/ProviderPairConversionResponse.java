package by.sashnikov.conversion.provider.exchangerateapicom.model;

import static java.util.Objects.isNull;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

import lombok.Data;
import lombok.NoArgsConstructor;

;

@Data
@NoArgsConstructor
@JsonDeserialize(using = PairConversionResponseDeserializer.class)
public abstract class ProviderPairConversionResponse {
    private String result;
}

class PairConversionResponseDeserializer extends StdDeserializer<ProviderPairConversionResponse> {

    public PairConversionResponseDeserializer() {
        super(ProviderPairConversionResponse.class);
    }

    @Override
    public ProviderPairConversionResponse deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectCodec codec = p.getCodec();
        TreeNode jsonNode = codec.readTree(p);
        TreeNode errorField = jsonNode.get(ProviderPairConversionRateErrorResponse.ERROR_FIELD_NAME);
        if (isNull(errorField)) {
            return codec.treeToValue(jsonNode, ProviderPairConversionRateSuccessResponse.class);
        } else {
            return codec.treeToValue(jsonNode, ProviderPairConversionRateErrorResponse.class);
        }
    }
}
