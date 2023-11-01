package com.example.atm.entity;

//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
import com.example.atm.validation.DivisibleByHundred;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
//    @Min(value = 100, message = "Total must be greater than 100")
    @DivisibleByHundred(message = "Total must be divisible by 100")
    private Integer total;

    @Column(name = "message")
    private String message;

    @Column(name = "date_bill", nullable = false)
    private java.sql.Date dateBill;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "money_bill", joinColumns = @JoinColumn(name = "bill_id"),
            inverseJoinColumns = @JoinColumn(name = "money_id"))
    private Set<Money> money = new HashSet<>();

    @Transient
    private UUID uuid;

    public UUID getUuid() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }


//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "money_bill",
//            joinColumns = @JoinColumn(name = "bill_id"),
//            inverseJoinColumns = @JoinColumn(name = "money_id", referencedColumnName = "id"))
//    private Set<Bill> bills = new HashSet<>();
}
