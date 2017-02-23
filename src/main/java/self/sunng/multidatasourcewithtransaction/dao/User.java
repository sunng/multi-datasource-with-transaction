package self.sunng.multidatasourcewithtransaction.dao;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class User {

    private Long id;
    private String name;
    private Integer age;
    private BigDecimal balance;
    private Timestamp createTime;
    private Timestamp updateTime;
}
