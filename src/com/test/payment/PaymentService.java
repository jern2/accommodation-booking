package com.test.payment;


public class PaymentService {
    public Payment processCardPayment(String cardComp, String cardNumber, String cardExp, String cardPw) throws Exception {
        
        if (cardNumber.startsWith("1234")) {
            throw new Exception("카드 결제 실패: 잘못된 카드 정보");
        }
        

        Payment payment = new Payment("Card", cardComp, 1000); // 임시 결제 금액
        return payment;
    }

}
