/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptit.webservice.model.DetailType;

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
public class DetailTypePostModel {
    private String name ;
    private String code ;
    private String icon ;
    private String description ;
    private int parentId;
    
    public String toJson() {
        StringBuilder jsonBuilder = new StringBuilder("{");
        jsonBuilder.append("\"name\"").append(":").append("\""+name+"\"").append(',');
        jsonBuilder.append("\"code\"").append(":").append("\""+code+"\"").append(',');
        jsonBuilder.append("\"icon\"").append(":").append("\""+icon+"\"").append(',');
        jsonBuilder.append("\"description\"").append(":").append("\""+description+"\"");
        if(parentId > 0) {
            jsonBuilder.append(',').append("\"parentId\"").append(":").append(parentId);
        }
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }
}
