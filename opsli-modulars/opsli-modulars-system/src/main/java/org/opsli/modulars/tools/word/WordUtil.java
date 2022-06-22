package org.opsli.modulars.tools.word;

import com.deepoove.poi.XWPFTemplate;
import org.opsli.api.base.result.ResultVo;
import org.opsli.api.wrapper.system.apply.SysApplyModel;
import org.opsli.api.wrapper.system.options.OptionsModel;
import org.opsli.core.utils.OptionsUtil;
import org.opsli.modulars.system.apply.entity.SysApply;
import org.opsli.plugins.oss.OssStorageFactory;
import org.opsli.plugins.oss.service.BaseOssStorageService;
import org.opsli.plugins.oss.service.OssStorageService;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * word工具类
 * Poi-tl模板引擎官方文档：http://deepoove.com/poi-tl/
 */
public class WordUtil {

    public static void wordTest() throws IOException {
        Map<String, Object> data = new HashMap<>();
        SysApplyModel model = new SysApplyModel();

        data.put("title", "Hello world");
        XWPFTemplate template = XWPFTemplate.compile("D:/template.docx")
                .render(data);
        FileOutputStream out;
        out = new FileOutputStream("D:/template2.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }

    /**根据模板创建申请表的word文件
     *
     *  */
    public static String createApplyWord(SysApplyModel model) throws IOException {
        Map<String, Object> data = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);

        data.put("year",year);
        data.put("month",month);
        data.put("day",day);
        data.put("applyName", model.getApplyName());
        data.put("publicationDate", model.getPublicationDate());
        data.put("firstAuthor", model.getFirstAuthor());
        data.put("firstAuthorWorkplace", model.getFirstAuthorWorkplace());
        data.put("firstAuthorSex", model.getFirstAuthorSex());
        data.put("firstAuthorBirth", model.getFirstAuthorBirth());
        data.put("firstAuthorId", model.getFirstAuthorId());
        data.put("firstAuthorProfess",model.getFirstAuthorProfess());
        data.put("firstAuthorEdu",model.getFirstAuthorEdu());
        data.put("firstAuthorDegree",model.getFirstAuthorDegree());
        data.put("firstAuthorSchool",model.getFirstAuthorSchool());
        data.put("firstAuthorTitle", model.getFirstAuthorTitle());
        data.put("firstAuthorPost", model.getFirstAuthorPost());
        data.put("firstAuthorMail", model.getFirstAuthorMail());
        data.put("firstAuthorTel", model.getFirstAuthorTel());
        data.put("firstAuthorAddress",model.getFirstAuthorAddress());
        data.put("firstAuthorIntro", model.getFirstAuthorIntro());
        data.put("introduction", model.getIntroduction());
        data.put("publicationName", model.getPublicationName());
        data.put("impactFactor", model.getImpactFactor());
        data.put("retrieval", model.getRetrieval());
        data.put("citations", model.getCitations());
        data.put("paperType", model.getPaperType());
        data.put("relatedAchievements", model.getRelatedAchievements());
        data.put("projectName", model.getProjectName());
        data.put("projectLevel", model.getProjectLevel());
        data.put("projectInnovation", model.getProjectInnovation());



        OptionsModel option = OptionsUtil.getOptionByCode("storage_local_domain");


        XWPFTemplate template = XWPFTemplate.compile("opsli-starter/src/main/resources/resources/论文评奖模板.docx")
                .render(data);
        FileOutputStream out;
        String filename = model.getApplyName() + ".docx";
        out = new FileOutputStream("opsli-starter/src/main/resources/resources/"+filename);
        template.write(out);
        out.flush();

        out.close();
        template.close();
        try {
            File file = new File("opsli-starter/src/main/resources/resources/"+filename);
            InputStream in = new FileInputStream(file);

            OssStorageService ossStorageService = OssStorageFactory.INSTANCE.getHandle();
            BaseOssStorageService.FileAttr fileAttr = ossStorageService.upload(in,"docx");

            String storagePath = fileAttr.getFileStoragePath();
            System.out.println("storagePath:"+storagePath);
            return storagePath;
        }catch (IOException e){
            System.out.println(e+"上传失败");
//            return ResultVo.error("上传文件失败，请稍后再试");
            return "error";

        }


    }

    /**
     * 根据模板填充内容生成word，并下载
     * @param templatePath word模板文件路径
     * @param paramMap     替换的参数集合
     */
    public static void generateWord(OutputStream out,String templatePath, Map<String, Object> paramMap) {

        Long time = new Date().getTime();
        // 生成的word格式
        String formatSuffix = ".docx";
        // 拼接后的文件名
        String fileName = time + formatSuffix;

        //设置生成的文件存放路径，可以存放在你想要指定的路径里面
        String rootPath="D:/code/"+File.separator+"file/word/";

        String filePath = rootPath+fileName;
        File newFile = new File(filePath);
        //判断目标文件所在目录是否存在
        if(!newFile.getParentFile().exists()){
            //如果目标文件所在的目录不存在，则创建父目录
            newFile.getParentFile().mkdirs();
        }

        // 读取模板templatePath并将paramMap的内容填充进模板，即编辑模板(compile)+渲染数据(render)
        XWPFTemplate template = XWPFTemplate.compile(templatePath).render(paramMap);
        try {
            //out = new FileOutputStream(filePath);//输出路径(下载到指定路径)
            // 将填充之后的模板写入filePath
            template.write(out);//将template写到OutputStream中
            out.flush();
            out.close();
            template.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}