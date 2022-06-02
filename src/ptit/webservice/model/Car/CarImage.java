/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class CarImage {
    private String name;
    private String url;
}
