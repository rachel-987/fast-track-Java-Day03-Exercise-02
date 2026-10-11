
package java_practice.d03.ex02.logistics.application;

import java_practice.d03.ex02.domain.DomainException;
import java_practice.d03.ex02.logistics.application.port.ShippingCarrierPort;

import java.util.List;

public class ShippingCarrierFactory {

    private final List<ShippingCarrierPort> carriers;

    // Constructor Injection
    public ShippingCarrierFactory(
            List<ShippingCarrierPort> carriers) {
        this.carriers = List.copyOf(carriers);
    }

    public ShippingCarrierPort getCarrier(String city) {

        // 1. Kiểm tra thành phố
        if (city == null || city.isBlank()) {
            throw new DomainException(
                    "Thành phố không được để trống!"
            );
        }

        // 2. Xác định hãng vận chuyển theo thành phố
        CarrierCode selectedCarrier;

        String normalizedCity = city.trim();

        if ("Hà Nội".equalsIgnoreCase(normalizedCity)
                || "Hồ Chí Minh".equalsIgnoreCase(normalizedCity)) {

            selectedCarrier = CarrierCode.GHN;

        } else {
            selectedCarrier = CarrierCode.GHTK;
        }

        // 3. Tìm Adapter tương ứng thông qua Port
        return carriers.stream()
                .filter(carrier ->
                        selectedCarrier.name()
                                .equals(carrier.getCarrierCode())
                )
                .findFirst()
                .orElseThrow(() ->
                        new DomainException(
                                "Không tìm thấy hãng vận chuyển phù hợp!"
                        )
                );
    }
}
