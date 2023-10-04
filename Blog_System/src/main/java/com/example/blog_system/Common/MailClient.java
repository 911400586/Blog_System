package com.example.blog_system.Common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MailClient {


    private Pattern pattern;
    private Matcher matcher;
    // 电子邮件验证模式
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public MailClient() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    /**
     * 对电子邮件地址进行验证
     *
     * @param email 电子邮件地址
     * @return true 如果电子邮件地址有效，否则返回false
     */
    public boolean validate(final String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
