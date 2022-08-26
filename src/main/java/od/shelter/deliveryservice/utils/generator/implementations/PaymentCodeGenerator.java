package od.shelter.deliveryservice.utils.generator.implementations;

import od.shelter.deliveryservice.utils.generator.Generator;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class PaymentCodeGenerator implements Generator {
    @Override
    public String generate() {
        return String.valueOf(new SecureRandom().nextInt(900000) + 100000);
    }
}
