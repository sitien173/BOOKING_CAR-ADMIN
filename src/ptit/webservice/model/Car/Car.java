package ptit.webservice.model.Car;

import java.math.BigDecimal;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ptit.webservice.model.AppUser.AppUser;
import ptit.webservice.model.Brand.Brand;
import ptit.webservice.model.Detail.Detail;

/**
 *
 * @author ngosi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private int totalBooking;
    private String rules;
    private int provinceId;
    private int districtId;
    private int wardId;
    private int status;
    private String address;
    private Brand brand;
    private AppUser ownerCar;
    private Collection<Detail> details;
    private Collection<CarImage> images;
}
