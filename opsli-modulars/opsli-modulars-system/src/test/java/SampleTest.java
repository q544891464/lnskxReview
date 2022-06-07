import com.baidubce.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opsli.modulars.system.apply.entity.SysApply;
import org.opsli.modulars.system.apply.mapper.SysApplyMapper;
import org.opsli.modulars.system.apply.service.ISysApplyService;
import org.opsli.modulars.system.user.entity.SysUser;
import org.opsli.modulars.system.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SampleTest {

    @Autowired
    private ISysApplyService iSysApplyService;



    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        boolean result = iSysApplyService.IsPassApply("1527197250850140162","1");

//        Assert.assertEquals(5, applyList.size());
        System.out.println(result);
    }
}
