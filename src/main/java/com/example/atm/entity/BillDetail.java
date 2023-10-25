package com.example.atm.entity;

import groovy.lang.Lazy;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "bill_detail")
public class BillDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @JoinColumn(name = "bill_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @Lazy
    private Bill bill;


    @JoinColumn(name = "money_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @Lazy
    private Money money;

    @Column(name = "quantity", nullable = false)
    @Min(value = 0, message = "Giá trị phải lớn hơn hoặc bằng 0")
    private int quantity;
}
