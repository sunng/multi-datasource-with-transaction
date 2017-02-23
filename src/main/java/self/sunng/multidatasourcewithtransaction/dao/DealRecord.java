package self.sunng.multidatasourcewithtransaction.dao;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class DealRecord {

    private Long id;
    private Long userId;
    private BigDecimal amount;
    private Timestamp create_time;
}
