package self.sunng.multidatasourcewithtransaction.ctr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import self.sunng.multidatasourcewithtransaction.dao.User;
import self.sunng.multidatasourcewithtransaction.srv.UserService;

import java.math.BigDecimal;

/**
 * Created by sunxiaodong on 2016/11/12.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("list")
    public User list(@RequestParam String name) {
        return userService.query(name);
    }

    @RequestMapping("add")
    public long add(@RequestParam String name, @RequestParam int age) {
        return userService.add(name, age);
    }

    @RequestMapping("deal")
    public BigDecimal deal(@RequestParam long userId, @RequestParam BigDecimal amount) {
        return userService.deal(userId, amount);
    }
}
