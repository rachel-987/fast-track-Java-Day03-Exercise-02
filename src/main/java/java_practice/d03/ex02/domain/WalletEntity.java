package java_practice.d03.ex02.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class WalletEntity {

    // 1. Các thuộc tính được đóng gói
    private final UUID id;
    private BigDecimal balance;
    private WalletStatus status;

    // 2. Constructor khởi tạo ví
    public WalletEntity(UUID id, BigDecimal balance) {

        if (id == null) {
            throw new DomainException(
                    "Wallet ID không được null!"
            );
        }

        if (balance == null ||
                balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainException(
                    "Số dư ban đầu không hợp lệ!"
            );
        }

        this.id = id;
        this.balance = balance;
        this.status = WalletStatus.ACTIVE;
    }

    // 3. Nghiệp vụ rút tiền
    public void withdrawMoney(BigDecimal amount) {

        // Không cho rút tiền khi ví bị khóa
        if (status == WalletStatus.LOCKED) {
            throw new DomainException(
                    "Ví điện tử đang bị khóa!"
            );
        }

        // Số tiền rút phải lớn hơn 0
        if (amount == null ||
                amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DomainException(
                    "Số tiền rút phải lớn hơn 0!"
            );
        }

        // Không cho phép rút quá số dư
        if (balance.compareTo(amount) < 0) {
            throw new DomainException(
                    "Số dư không đủ để rút tiền!"
            );
        }

        // Cập nhật số dư
        this.balance = this.balance.subtract(amount);
    }

    // 4. Nghiệp vụ khóa ví
    public void lockWallet() {
        this.status = WalletStatus.LOCKED;
    }

    // 5. Getter - không tạo Setter
    public UUID getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public WalletStatus getStatus() {
        return status;
    }
}