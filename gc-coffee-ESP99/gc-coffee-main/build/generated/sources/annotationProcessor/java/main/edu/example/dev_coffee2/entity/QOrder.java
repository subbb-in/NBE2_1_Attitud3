package edu.example.dev_coffee2.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = -335688303L;

    public static final QOrder order = new QOrder("order1");

    public final StringPath address = createString("address");

    public final StringPath email = createString("email");

    public final DateTimePath<java.time.LocalDateTime> modDate = createDateTime("modDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final ListPath<OrderItem, QOrderItem> orderItems = this.<OrderItem, QOrderItem>createList("orderItems", OrderItem.class, QOrderItem.class, PathInits.DIRECT2);

    public final EnumPath<edu.example.dev_coffee2.enums.OrderStatus> orderStatus = createEnum("orderStatus", edu.example.dev_coffee2.enums.OrderStatus.class);

    public final StringPath postcode = createString("postcode");

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public QOrder(String variable) {
        super(Order.class, forVariable(variable));
    }

    public QOrder(Path<? extends Order> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrder(PathMetadata metadata) {
        super(Order.class, metadata);
    }

}

