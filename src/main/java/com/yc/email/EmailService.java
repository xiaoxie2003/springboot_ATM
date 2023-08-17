package com.yc.email;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Autowired
    private FreeMarkerConfigurer configurer;

    /**
     * 发送复杂的邮件 用来发送html邮件、带附件的邮件、有静态资源（图片）的邮件
     * @param email
     * @return
     */
    public boolean sendSimpleEmail(Email email,String templateName, Map<String,Object> data){
        log.info("发送复杂邮件信息：{}",email.toJsonString());

        //实例化MimeMessage
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            //设置邮件发件人
            mimeMessageHelper.setFrom(email.getSender());
            //设置邮件接收人（可以多人）
            mimeMessageHelper.setTo(email.getReceiverArray());
            //设置邮件抄送
            String[] ccArray = email.getCcArray();
            if(ccArray.length > 0){
                mimeMessageHelper.setCc(ccArray);//普通抄送，邮件接收人可以看见发件人
            }
            //设置邮件主题
            mimeMessageHelper.setSubject(email.getSubject());

            //通过模板获取文件
            String content = getContent(templateName,data);

            //设置邮件内容
            mimeMessageHelper.setText(content,true);
            //批量处理嵌入式资源
            Map<String,String> inlineMap = email.getInlineMap();
            for(Map.Entry<String,String> entry : inlineMap.entrySet()){
                String contentId = entry.getKey();
                String filePath = entry.getValue();
                //设置嵌入式资源
                mimeMessageHelper.addInline(contentId,new File(filePath));
            }
            //批量处理附件
            List<String> attachmentList = email.getAttachmentList();
            for(int i = 0;i < attachmentList.size(); i++){
                String filePath = attachmentList.get(i);
                String fileName = i + "-" + filePath.substring(filePath.lastIndexOf(File.separator));
                //设置附件
                mimeMessageHelper.addAttachment(fileName,new File(filePath));
            }
            //发送复杂邮件
            mailSender.send(mimeMessage);
            return true;
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return false;
    }

    public String getContent(String templateName,Map<String,Object> data) throws IOException, TemplateException {
        //获取模板
        Template template = configurer.getConfiguration().getTemplate(templateName,"UTF-8");
        //获取数据并把模板转为字符串
        return FreeMarkerTemplateUtils.processTemplateIntoString(template,data);
    }
}
