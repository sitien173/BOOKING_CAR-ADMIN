/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptit.webservice.model;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ngosi
 */
@Data
@NoArgsConstructor
public class ResponseModel<T> implements Serializable{
    private boolean success;
    private String message;
    private T data;
}
