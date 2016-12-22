package self.sunng.multidatasourcewithtransaction.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import self.sunng.multidatasourcewithtransaction.common.ReadOnlyConnection;

/**
 * Created by sunxiaodong on 2016/11/12.
 */
@Repository
public class UserDao {

    @Autowired
    UserMapper userMapper;

//    @ReadOnlyConnection
    public User findByName(String name) {
        return userMapper.findByName(name);
    }

    @ReadOnlyConnection
    public int insert(String name, int age) {
        return userMapper.insert(name, age);
    }

    public User findByID(int id) {
        return userMapper.findByID(id);
    }
}
