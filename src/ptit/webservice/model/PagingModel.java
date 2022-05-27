/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptit.webservice.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ngosi
 */
@Data
@NoArgsConstructor
public class PagingModel<T> implements Serializable{
    private int pageIndex;
    private int limit;
    private int totalPage;
    private List<T> items;
}
