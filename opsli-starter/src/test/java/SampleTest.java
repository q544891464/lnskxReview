import org.junit.Test;
import org.junit.runner.RunWith;
import org.opsli.OpsliApplication;
import org.opsli.modulars.system.apply.service.ISysApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = OpsliApplication.class)
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
