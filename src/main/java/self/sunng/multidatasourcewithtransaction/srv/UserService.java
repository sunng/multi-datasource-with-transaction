package self.sunng.multidatasourcewithtransaction.srv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import self.sunng.multidatasourcewithtransaction.common.ReadOnlyConnection;
import self.sunng.multidatasourcewithtransaction.dao.DealRecord;
import self.sunng.multidatasourcewithtransaction.dao.DealRecordDao;
import self.sunng.multidatasourcewithtransaction.dao.User;
import self.sunng.multidatasourcewithtransaction.dao.UserDao;

import java.math.BigDecimal;

/**
 * Created by sunxiaodong on 2016/11/12.
 */
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    DealRecordDao dealRecordDao;

    @ReadOnlyConnection
    public User query(String name) {
        return userDao.findByName(name);
    }

    public long add(String name, int age) {
        User user = new User();
        user.setName(name);
        user.setAge(age);
        return userDao.insert(user);
    }

    @Transactional
    public BigDecimal deal(long id, BigDecimal amount) {
        User user = userDao.findByID(id);
        user.setBalance(user.getBalance().add(amount));
        userDao.updateBalance(user);
        DealRecord dealRecord = new DealRecord();
        dealRecord.setUserId(id);
        dealRecord.setAmount(amount);
        dealRecordDao.insert(dealRecord);

        return user.getBalance();
    }
}
