
package java_practice.d03.ex02.logistics.application;

import java.math.BigDecimal;

import java_practice.d03.ex02.domain.DomainException;
import java_practice.d03.ex02.logistics.application.port.ShippingCarrierPort;

public class CalculateShippingFeeUseCase {

    private final ShippingCarrierFactory shippingCarrierFactory;

    // Constructor Injection
    public CalculateShippingFeeUseCase(
            ShippingCarrierFactory shippingCarrierFactory) {
        this.shippingCarrierFactory = shippingCarrierFactory;
    }

    public BigDecimal execute(int weight, String city) {

        // 1. Kiểm tra trọng lượng
        if (weight <= 0) {
            throw new DomainException(
                    "Trọng lượng phải lớn hơn 0!"
            );
        }

        // 2. Kiểm tra thành phố
        if (city == null || city.isBlank()) {
            throw new DomainException(
                    "Thành phố không được để trống!"
            );
        }

        // 3. Factory lựa chọn hãng vận chuyển
        ShippingCarrierPort carrier =
                shippingCarrierFactory.getCarrier(city);

        // 4. Gọi Adapter tính phí thông qua Port
        return carrier.calculateShippingFee(weight, city);
    }
}
