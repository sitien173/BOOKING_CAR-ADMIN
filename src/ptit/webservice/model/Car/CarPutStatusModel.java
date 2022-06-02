package ptit.webservice.model.Car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ngosi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarPutStatusModel {
    private int carId;
    private int status;
}
