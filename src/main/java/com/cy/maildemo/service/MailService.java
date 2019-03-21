package com.cy.maildemo.service;

import com.cy.maildemo.util.LogAspect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.file.spi.FileSystemProvider;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailService {
    private Logger logger = LogManager.getLogger(MailService.class);
    @Value("${spring.mail.username}")
    public String from;

    @Autowired
    private JavaMailSender mailSender;

    /**
     * 发送普通邮件
     * @param to
     * @param subject
     * @param content
     */
    public void sendSimpleMail(String to,String subject,String content){
        Map<String,Object> map = new HashMap<String,Object>();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);
        mailMessage.setFrom(from);
        mailSender.send(mailMessage);
    }


    /**
     * 发送html邮件
     * @param to
     * @param subject
     * @param content
     */
    public void sendHtmlMail(String to,String subject,String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content,true);
        helper.setFrom(from);
        mailSender.send(message);
    }

    /**
     * 发送带附件得邮件
     * @param to
     * @param subject
     * @param content
     * @param filePath
     */
    public void sendFileMail(String to,String subject,String content,String filePath) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content);

        FileSystemResource file = new FileSystemResource(new File(filePath));
        String fileName = file.getFilename();
        helper.addAttachment(fileName,file);
        //如果发多个附件
//        helper.addAttachment(fileName+"_1",file);

        mailSender.send(message);
    }


    /**
     * 发带图片得邮件
     * @param to
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     * @throws MessagingException
     */
    public void sendImageMail(String to,String subject,String content,String rscPath,String rscId) throws MessagingException{
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setText(content);

        FileSystemResource res = new FileSystemResource(new File(rscPath));
        helper.addInline(rscId,res);
        mailSender.send(message);
    }
}
