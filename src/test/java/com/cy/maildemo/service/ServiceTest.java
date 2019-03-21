package com.cy.maildemo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

    @Resource
    MailService mailService;

    @Test
    public void mailTest(){
        mailService.sendSimpleMail("troub_cy@163.com","第一封邮件","你好，这是我得第一封邮件");
    }

    @Test
    public  void htmlMailTest(){
        String content = "<html>\n"+
                "<body>\n"+
                "<h3> hello world,这是一封html邮件 </h3>\n"+
                "</body>\n"+
                "</html>";
        mailService.sendSimpleMail("troub_cy@163.com","第一封html邮件",content);
    }

    @Test
    public void fileMailTest() throws MessagingException {
        String filePath = "d://gzkqgl.war";
        mailService.sendFileMail("1662421864@qq.com","附件邮件","这是一封带附件得邮件",filePath);
    }

    @Test
    public void imgMailTest() throws MessagingException {
        String imgPath = "d://nophoto.png";
        String rscid="0001";
        String content = "<html><body>这是图片邮件 :<img src=\'cid:"+rscid
                + "\'></img></body></html>";
        mailService.sendImageMail("1662421864@qq.com","图片邮件",content,imgPath,rscid);
    }



}
