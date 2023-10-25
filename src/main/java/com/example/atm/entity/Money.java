package com.example.atm.entity;

//import jakarta.persistence.Entity;
//import jakarta.persistence.Table;
import lombok.*;

//import jakarta.persistence.*;
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
@Table(name = "money")
public class Money {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "quantity", nullable = false)
    @Min(value = 0, message = "Giá trị phải lớn hơn hoặc bằng 0")
    private int quantity;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "money_bill",
            joinColumns = @JoinColumn(name = "money_id"),
            inverseJoinColumns = @JoinColumn(name = "bill_id", referencedColumnName = "id"))
    private Set<Bill> bills = new HashSet<>();

    //    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "money_bill", joinColumns = @JoinColumn(name = "money_id"),
//            inverseJoinColumns = @JoinColumn(name = "bill_id"))
//    private Set<Bill> bill = new HashSet<>();

}
