package self.sunng.multidatasourcewithtransaction.dao.mapper;

import org.apache.ibatis.annotations.*;
import self.sunng.multidatasourcewithtransaction.dao.User;

import java.math.BigDecimal;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM USER WHERE ID = #{id}")
    User findByID(@Param("id") long id);

    @Select("SELECT * FROM USER WHERE NAME = #{name}")
    User findByName(@Param("name") String name);

    @SelectKey(statement = " SELECT LAST_INSERT_ID() AS id", keyProperty = "user.id", before = false, resultType = long.class)
    @Insert("INSERT INTO USER(NAME, AGE) VALUES(#{user.name}, #{user.age})")
    int insert(@Param("user") User user);

    @Update("UPDATE USER SET balance = #{balance} where id = #{id}")
    int update(@Param("id") Long id, @Param("balance") BigDecimal balance);
}