package self.sunng.multidatasourcewithtransaction.dao.mapper;

import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;

@Mapper
public interface DealRecordMapper {

    @Insert("INSERT INTO DEAL_RECORD(user_id, amount) VALUES(#{userId}, #{amount})")
    int insert(@Param("userId") long userId, @Param("amount") BigDecimal amount);

}