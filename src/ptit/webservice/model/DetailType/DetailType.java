/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptit.webservice.model.DetailType;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ngosi
 */
@Data
@NoArgsConstructor
public class DetailType {
    private int id ;
    private String name ;
    private String code ;
    private String icon ;
    private String description ;
    private DetailType parent ;

    public DetailType(int id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }
    
    
    public void setParentId(int parentId) {
        parent = new DetailType();
        parent.setId(parentId);
    }
}
