package by.sashnikov.conversion.provider.exchangeratesapiio.model;

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

@Data
@NoArgsConstructor
@JsonDeserialize(using = PairConversionResponseDeserializer.class)
public class ExchangeRatesApiIoPairConversionResponse {
    private Boolean success;
}

class PairConversionResponseDeserializer extends StdDeserializer<ExchangeRatesApiIoPairConversionResponse> {
    private static final String ERROR_FIELD_NAME = "error";

    public PairConversionResponseDeserializer() {
        super(ExchangeRatesApiIoPairConversionResponse.class);
    }

    @Override
    public ExchangeRatesApiIoPairConversionResponse deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectCodec codec = p.getCodec();
        TreeNode jsonNode = codec.readTree(p);
        TreeNode errorField = jsonNode.get(ERROR_FIELD_NAME);
        if (isNull(errorField)) {
            return codec.treeToValue(jsonNode, ExchangeRatesApiIoPairConversionSuccessResponse.class);
        } else {
            return codec.treeToValue(jsonNode, ExchangeRatesApiIoPairConversionErrorResponse.class);
        }
    }
}

