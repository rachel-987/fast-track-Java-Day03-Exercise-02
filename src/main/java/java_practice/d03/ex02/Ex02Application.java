
package java_practice.d03.ex02;

import java_practice.d03.ex02.domain.DomainException;

import java_practice.d03.ex02.logistics.application.CalculateShippingFeeUseCase;
import java_practice.d03.ex02.logistics.application.ShippingCarrierFactory;
import java_practice.d03.ex02.logistics.application.port.ShippingCarrierPort;

import java_practice.d03.ex02.logistics.infrastructure.GhnCarrierAdapter;
import java_practice.d03.ex02.logistics.infrastructure.GhtkCarrierAdapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class Ex02Application {

	public static void main(String[] args) {

		ConfigurableApplicationContext context =
				SpringApplication.run(Ex02Application.class, args);

		try (Scanner scanner = new Scanner(System.in)) {

			// 1. Khởi tạo các Adapter
			ShippingCarrierPort ghn = new GhnCarrierAdapter();
			ShippingCarrierPort ghtk = new GhtkCarrierAdapter();

			// 2. Truyền Adapter vào Factory
			ShippingCarrierFactory factory =
					new ShippingCarrierFactory(
							List.of(ghn, ghtk)
					);

			// 3. Inject Factory vào Use Case
			CalculateShippingFeeUseCase useCase =
					new CalculateShippingFeeUseCase(factory);

			// 4. Menu nhập dữ liệu
			while (true) {

				System.out.println("\n===== LOGISTICS APPLICATION =====");
				System.out.println("1. Tính phí vận chuyển");
				System.out.println("0. Thoát");
				System.out.print("Nhập lựa chọn: ");

				String choice = scanner.nextLine().trim();

				switch (choice) {

					case "1":

						System.out.print("Nhập thành phố: ");
						String city = scanner.nextLine().trim();

						System.out.print(
								"Nhập trọng lượng hàng hóa (gram): "
						);
						String weightInput =
								scanner.nextLine().trim();

						try {
							int weight =
									Integer.parseInt(weightInput);

							// Gọi Use Case để tính phí
							BigDecimal shippingFee =
									useCase.execute(weight, city);

							// Lấy tên hãng đã được Factory chọn
							ShippingCarrierPort carrier =
									factory.getCarrier(city);

							System.out.println(
									"\n===== KẾT QUẢ ====="
							);

							System.out.println(
									"Thành phố: " + city
							);

							System.out.println(
									"Trọng lượng: " + weight + " gram"
							);

							System.out.println(
									"Đơn vị vận chuyển: "
											+ carrier.getCarrierCode()
							);

							System.out.println(
									"Phí vận chuyển: "
											+ shippingFee + " VND"
							);

						} catch (NumberFormatException e) {
							System.out.println(
									"Lỗi: Trọng lượng phải là số nguyên hợp lệ!"
							);

						} catch (DomainException e) {
							System.out.println(
									"Lỗi nghiệp vụ: " + e.getMessage()
							);
						}

						break;

					case "0":
						System.out.println("Đã thoát chương trình!");
						return;

					default:
						System.out.println(
								"Lựa chọn không hợp lệ!"
						);
				}
			}

		} finally {
			context.close();
		}
	}
}
