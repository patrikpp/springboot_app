package com.demo.demoapp.domain;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="exchange_rates")
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String shortName;

    private Timestamp validFrom;

    private String name;

    private String country;

    @Column(precision=19, scale=2)
    private BigDecimal move;

    @Column(precision=19, scale=1)
    private BigDecimal amount;

    @Column(precision=19, scale=3)
    private BigDecimal valBuy;

    @Column(precision=19, scale=3)
    private BigDecimal valSell;

    @Column(precision=19, scale=3)
    private BigDecimal valMid;

    @Column(precision=19, scale=3)
    private BigDecimal currBuy;

    @Column(precision=19, scale=3)
    private BigDecimal currSell;

    @Column(precision=19, scale=3)
    private BigDecimal currMid;

    private Integer version;

    @Column(precision=19, scale=3)
    private BigDecimal cnbMid;

    @Column(precision=19, scale=3)
    private BigDecimal ecbMid;
}
