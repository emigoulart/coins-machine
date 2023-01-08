package dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoinsMachineDTO {
    private Double amount;

    private Integer numberOfCoins;

    private Integer maxCoinsPerType;

    private List<CoinDTO> coins;

    private Double change;
}
