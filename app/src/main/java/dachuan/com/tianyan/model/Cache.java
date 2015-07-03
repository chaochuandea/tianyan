package dachuan.com.tianyan.model;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by maibenben on 2015/7/3.
 */
@AllArgsConstructor
@Data
public class Cache {
    private String key;
    private Object data;
}
