import org.junit.Test;
import org.junit.runner.RunWith;
import org.opsli.OpsliApplication;
import org.opsli.api.wrapper.system.apply.SysApplyModel;
import org.opsli.modulars.tools.word.WordUtil;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@SpringBootTest(classes = OpsliApplication.class)
@RunWith(SpringRunner.class)
public class WordTest {

    @Test
    public void test() throws IOException {
        SysApplyModel model = new SysApplyModel();
        model.setApplyName("test");
    }

}
