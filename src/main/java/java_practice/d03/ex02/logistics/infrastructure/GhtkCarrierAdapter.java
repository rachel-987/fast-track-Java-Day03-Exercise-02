
package java_practice.d03.ex02.logistics.infrastructure;

import java.math.BigDecimal;

import java_practice.d03.ex02.logistics.application.port.ShippingCarrierPort;

public class GhtkCarrierAdapter implements ShippingCarrierPort {

    @Override
    public String getCarrierCode() {
        return "GHTK";
    }

    @Override
    public BigDecimal calculateShippingFee(
            int weight,
            String city) {

        // 1. Kiểm tra trọng lượng
        if (weight <= 0) {
            throw new IllegalArgumentException(
                    "Trọng lượng phải lớn hơn 0!"
            );
        }

        // 2. Quy đổi gram sang kg, làm tròn lên
        int weightInKg = (weight - 1) / 1000 + 1;

        // 3. Công thức tính phí GHTK giả lập
        BigDecimal baseFee = new BigDecimal("10000");
        BigDecimal feePerKg = new BigDecimal("4000");

        // 4. Tính tổng phí vận chuyển
        return baseFee.add(
                feePerKg.multiply(
                        BigDecimal.valueOf(weightInKg)
                )
        );
    }
}
