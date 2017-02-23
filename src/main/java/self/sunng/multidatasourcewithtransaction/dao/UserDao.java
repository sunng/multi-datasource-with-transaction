package self.sunng.multidatasourcewithtransaction.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import self.sunng.multidatasourcewithtransaction.dao.mapper.UserMapper;

/**
 * Created by sunxiaodong on 2016/11/12.
 */
@Component
public class UserDao {

    @Autowired
    UserMapper userMapper;

    public User findByName(String name) {
        return userMapper.findByName(name);
    }

    public User findByID(long id) {
        return userMapper.findByID(id);
    }

    public long insert(User user) {
        userMapper.insert(user);
        return user.getId();
    }

    public int updateBalance(User user) {
        return userMapper.update(user.getId(), user.getBalance());
    }
}
