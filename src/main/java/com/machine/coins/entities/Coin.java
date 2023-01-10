package com.machine.coins.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_coin")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coin implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double coinValue;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "coin_machine_id", insertable = false, updatable = false)
    private CoinsMachine coinMachine;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coin)) return false;
        Coin coin = (Coin) o;
        return getId().equals(coin.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
