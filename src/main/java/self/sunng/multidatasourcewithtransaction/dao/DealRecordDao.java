package self.sunng.multidatasourcewithtransaction.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import self.sunng.multidatasourcewithtransaction.dao.mapper.DealRecordMapper;

/**
 * Created by sunxiaodong on 2016/11/12.
 */
@Component
public class DealRecordDao {

    @Autowired
    DealRecordMapper dealRecordMapper;

    public int insert(DealRecord dealRecord) {
        return dealRecordMapper.insert(dealRecord.getUserId(), dealRecord.getAmount());
    }
}
