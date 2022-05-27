/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptit.webservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ngosi
 */
@Data
@NoArgsConstructor
public class ChangePasswordModel {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
