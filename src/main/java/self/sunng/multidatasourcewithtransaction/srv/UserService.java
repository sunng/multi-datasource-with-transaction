package self.sunng.multidatasourcewithtransaction.srv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import self.sunng.multidatasourcewithtransaction.dao.UserDao;

/**
 * Created by sunxiaodong on 2016/11/12.
 */
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public String query(String name) {
        return userDao.findByName(name).getAge().toString();
    }

    public int add(String name, int age) {
        return userDao.insert(name, age);
    }

    @Transactional
    public String io(String name) {
        int id = userDao.insert(name, 1);
        return userDao.findByID(5).getName();
    }
}
