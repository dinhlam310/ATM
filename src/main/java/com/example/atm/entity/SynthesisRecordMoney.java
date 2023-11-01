package com.example.atm.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name ="synthesis_record_money")
public class SynthesisRecordMoney {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "value", nullable = false)
    private int value;

    @Column(name = "type", nullable = false)
    private String type;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
