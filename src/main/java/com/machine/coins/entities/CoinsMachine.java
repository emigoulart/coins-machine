package com.machine.coins.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_coins_machine")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoinsMachine implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer maxCoinsPerType;

    @OneToMany(targetEntity = Coin.class, mappedBy = "coinMachine", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Coin> coins;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CoinsMachine)) return false;
        CoinsMachine coinMachine = (CoinsMachine) o;
        return getId().equals(coinMachine.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
