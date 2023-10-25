package com.example.atm.entity;

//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "bill")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "total", nullable = false)
    @Min(value = 99, message = "Total must be greater than 99")
    private Integer total;

    @Column(name = "date_bill", nullable = false)
    private java.sql.Date dateBill;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "money_bill", joinColumns = @JoinColumn(name = "bill_id"),
            inverseJoinColumns = @JoinColumn(name = "money_id"))
    private Set<Money> money = new HashSet<>();

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "money_bill",
//            joinColumns = @JoinColumn(name = "bill_id"),
//            inverseJoinColumns = @JoinColumn(name = "money_id", referencedColumnName = "id"))
//    private Set<Bill> bills = new HashSet<>();
}
