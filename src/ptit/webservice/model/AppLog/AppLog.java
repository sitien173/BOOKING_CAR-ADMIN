/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptit.webservice.model.AppLog;
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
public class AppLog {
    private int id;
    private String exception;
    private String message;
    private String method;
    private String params;
    private String request_url;
    private int status;
    private String created_at;
    private String created_by;
    private String query;
    private String header;
    private String host;
}
