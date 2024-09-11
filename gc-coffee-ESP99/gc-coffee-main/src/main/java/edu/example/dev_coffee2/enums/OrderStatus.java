package edu.example.dev_coffee2.enums;

public enum OrderStatus {
    ACCEPTED, // 주문 완료
    PAYMENT_CONFIRMED, // 결제 완료
    READY_FOR_DELIVERY, // 배송 준비 완료
    SHIPPED,            // 배송 중
    SETTLED,            // 배송 완료
    CANCELLED           // 주문 취소
}
