Java v- 17   
Spring boot v- 3.3.3

# Products DB에 데이터가 없을 경우

`test / java / dev_coffee2 / repository / ProductRepository.Tests`에서 `testInsert()` 클래스 실행하여 생성

## 등록
### add() 메서드 테스트 시

POST > http://localhost:8080/api/v1/orders

```json
JSON 예시:
{
  "productId": "3",   // products에 등록된 제품 1 ~ 4번 중 하나
  "address": "의정부",
  "postcode": "35262",
  "orderStatus": "CANCELLED", // Enum OrderStatus class 1 ~ 4가지 중 하나
  "quantity": 10,
  "category": "BrazilSerraDoCoffee" // Enum Category class 등록된 제품 1 ~ 4번 중 하나
}
productId에서 "category"를 가져오기 때문에 누락시키면 안됨.
productId 순서에 맞는 category 순서를 가져옴.

예를 들어:
productId = 3 은 Category 3번째인 BrazilSerraDoCoffee 임.
하지만 JSON에 "category" : "BrazilSerraDoCoffee"가 아닌, "category" : "EthiopiaSidam"이라고
데이터를 다르게 전송해도 productId에 맞는 Category를 가져온다.

그러면 Category란은 기입하지 않아도 되는 줄 알지만,
서비스단에서 가져오게 해놓았기 때문에 반드시 기입해야 한다.
만약 서비스단에 있는 add() 의 .category(foundProduct.getCategory())를
비활성화 한다면 orderitems 테이블에 category 컬럼이 Null로 된다.
```
## 조회
### getAllOrders() 메서드 테스트 시
GET > http://localhost:8080/api/v1/orders/ email
```json

예상 출력:

json
코드 복사
[
  {
    "email": "hong@example.com",
    "address": "서울시 강남구",
    "postcode": "15888",
    "orderItems": [
      {
        "productId": 3,
        "category": "BrazilSerraDoCoffee",
        "price": 120000,
        "quantity": 6
      }
    ]
  }
]
Order 안에 OrderItems 데이터를 조회.
즉, 주문자의 정보와 주문 목록을 엮어서 조회.
getAllItems() 메서드는 Order 정보를 제외하고 email을 기준으로 OrderItems만 조회해줌.
```
## 수정  
### modify() 메서드 테스트 시
PUT > http://localhost:8080/api/v1/orders/{orderId}
```json

JSON 예시:

json
코드 복사
{
  "address": "강원도",
  "postcode": "2215",
  "orderStatus": "SETTLED",
  "quantity": 7,
  "category": "ColumbiaCoffee"
}
price는 수량 변경 시 setPrice 메서드로 변경된 quantity * price 계산되어 저장됨.
```
## 삭제
### deleteOrderItem() 메서드 테스트 시
DELETE > http://localhost:8080/api/v1/orders/orderItem/{orderItemId}

### deleteOrder() 메서드 테스트 시
DELETE > http://localhost:8080/api/v1/orders/{orderItemId}

```josn
OrderItem이 Order_Id를 참조(FK)하고 있기 때문에,
OrderItems를 먼저 삭제해야 Orders 테이블의 컬럼들을 삭제할 수 있다.
```
