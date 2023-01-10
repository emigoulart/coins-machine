package dto;

import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoinDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Double coinValue;

    private Integer quantity;

}
