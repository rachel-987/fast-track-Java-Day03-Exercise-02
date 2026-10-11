
package java_practice.d03.ex02.logistics.application.port;

import java.math.BigDecimal;

public interface ShippingCarrierPort {

    BigDecimal calculateShippingFee(int weight, String city);

    String getCarrierCode();
}
