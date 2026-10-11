
package java_practice.d03.ex02.logistics.infrastructure;

import java.math.BigDecimal;

import java_practice.d03.ex02.logistics.application.port.ShippingCarrierPort;

public class GhnCarrierAdapter implements ShippingCarrierPort {

    @Override
    public String getCarrierCode() {
        return "GHN";
    }

    @Override
    public BigDecimal calculateShippingFee(
            int weight,
            String city) {

        // Kiểm tra trọng lượng hàng hóa
        if (weight <= 0) {
            throw new IllegalArgumentException(
                    "Trọng lượng phải lớn hơn 0!"
            );
        }

        // Quy đổi gram sang số kg, làm tròn lên
        int weightInKg = (weight - 1) / 1000 + 1;

        // Công thức tính phí GHN giả lập
        BigDecimal baseFee = new BigDecimal("15000");
        BigDecimal feePerKg = new BigDecimal("5000");

        return baseFee.add(
                feePerKg.multiply(
                        BigDecimal.valueOf(weightInKg)
                )
        );
    }
}
