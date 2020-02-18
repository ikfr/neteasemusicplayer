package dao;

import org.springframework.stereotype.Repository;
import pojo.Register;

import java.util.Date;

/**
 * @author super lollipop
 * @date 20-2-15
 */
@Repository(value = "registerDao")
public interface RegisterDao {
    int insert(Register register);
    Register query(Register register);
    int queryById(String id);
    Date queryDate();
    int updateDateAndCode(Register register);
}