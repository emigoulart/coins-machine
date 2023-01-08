package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@Builder
public class CoinDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Double coinValue;

    private Integer quantity;


}
