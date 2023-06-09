package cn.wolfcode.business.flowaudit.domain.qo;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class QueryObject {
    private Integer pageNum=1;
    private Integer pageSize=5;
    /** 请求参数 */
    private Map<String, Object> params;
    public Map<String, Object> getParams()
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        return params;
    }
}
