package org.opsli.modulars.tools.word;


import com.deepoove.poi.data.PictureRenderData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 导出Word
 * @author Administrator
 *
 */
@RequestMapping("/auth/exportWord/")
@RestController
public class ExportWordController {



    /**
     * 用户信息导出word
     * @throws IOException
     */
    @RequestMapping("/exportUserWordTest")
    public void exportUserWordTest(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" +new SimpleDateFormat("yyyyMMddHHmm").format(new Date())+".docx");
        Map<String, Object> params = new HashMap<>();
        // 渲染文本
        params.put("name", "张三");
        params.put("position", "开发工程师");
        params.put("entry_time", "2020-07-30");
        params.put("province", "江苏省");
        params.put("city", "南京市");

        // TODO 渲染其他类型的数据请参考官方文档
        String templatePath = "D:\\user.docx";

        WordUtil.generateWord(response.getOutputStream(), templatePath, params);
    }
}