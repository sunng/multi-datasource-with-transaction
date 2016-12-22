package self.sunng.multidatasourcewithtransaction.ctr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import self.sunng.multidatasourcewithtransaction.srv.UserService;

/**
 * Created by sunxiaodong on 2016/11/12.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("list")
    public String list() {
        return userService.query("sunxiaodong");
    }

    @RequestMapping("add")
    public String add() {
        return String.valueOf(userService.add("AAA", 10));
    }

    @RequestMapping("io")
    public String io(@RequestParam String name) {
        return userService.io(name);
    }
}
