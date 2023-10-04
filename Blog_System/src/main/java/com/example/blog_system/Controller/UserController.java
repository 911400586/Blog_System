package com.example.blog_system.Controller;

import cn.hutool.extra.mail.Mail;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.example.blog_system.Common.*;
import com.example.blog_system.Model.User;
import com.example.blog_system.Service.UserService;
import com.sun.mail.util.MailSSLSocketFactory;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
//@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
//    邮箱
    @Resource
    MailClient MailClient;
//    @Resource
//    TemplateEngine templateEngine;
//
    /**
     * 提供邮箱验证
     * @param email 用户的邮箱
     * @return 统一返回值
     */
    @RequestMapping("/email")
    public AjaxResult mail(String email)
    {
        if(email==null || email.equals(""))
        {
            return AjaxResult.fail(-1,"邮箱地址为空");
        }
        //1.邮箱格式验证
        if(!MailClient.validate(email))
        {
            return AjaxResult.fail(-1,"邮箱地址格式错误");
        }
        //判断是否已经发送过验证码
        String verifyCode = stringRedisTemplate.opsForValue().get(email);
        if(verifyCode!=null)
        {
            //先前已经发送过验证码
            return AjaxResult.fail(-2,"已经发送过验证码");
        }
        //发送验证码
        verifyCode = StringTools.generateVerificationCode(6);//生产六位验证码
        stringRedisTemplate.opsForValue().set(email,verifyCode,60,TimeUnit.SECONDS);
        MailUtil.send(email, "测试", verifyCode, false);
        return AjaxResult.success(1,"验证码发送成功");
    }
    @RequestMapping("/reg")

    public AjaxResult Register(User user,String code)
    {
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        //1.判空
        if(user==null || !StringUtils.hasLength(user.getUsername()) || !StringUtils.hasLength(user.getPassword()))
        {
            return AjaxResult.fail(-1,"参数异常");
        }
        //2.判断是否用户名重复
        User user1 = userService.getUserByName(user.getUsername());
        if(user1!=null && user1.getUsername().equals(user.getUsername()))
        {
            return AjaxResult.fail(-2,"该用户已注册!");
        }
        //判断验证码的正确性

        String verifyCode = stringRedisTemplate.opsForValue().get(user.getEmail());
        System.out.println(verifyCode);
        if(verifyCode==null)
        {
            return AjaxResult.fail(-3,"验证码失效!");
        }
        if(!code.equals(verifyCode))
        {
            return AjaxResult.fail(-4,"验证码错误!");
        }
        //验证通过

        //md5加盐
        user.setPassword(PasswordTools.Md5AndSalt(user.getPassword()));
       int num =  userService.register(user);
       if(num==1)
       {
           return AjaxResult.success(1);
       }
       return AjaxResult.fail(-1,"对不起,注册失败!");
    }

    @RequestMapping("/login")
    public AjaxResult Login(String username, String password, HttpServletRequest request)
    {
        //1.判空
        if(!StringUtils.hasLength(username) || !StringUtils.hasLength(password))
        {
            return AjaxResult.fail(-1,"参数异常");
        }
        //2.进行判断
        User user = userService.login(username);
        if(user==null || !StringUtils.hasLength(user.getUsername()))
        {
            if(user==null)
            {
                return AjaxResult.fail(-3,"参数异常");
            }
            if(!StringUtils.hasLength(user.getUsername()))
            {
                return AjaxResult.fail(-4,"参数异常");
            }

        }
        //判断密码是否正确 md5+salt
        if(!PasswordTools.verify(password,user.getPassword()))
        {

            return AjaxResult.fail(-1,"密码错误");
        }
        //3.创建session
//        HttpSession session = request.getSession();
//        session.setAttribute(ApplicationVariable.SESSION_KEY_USERINFO,user);
        HttpSession session = request.getSession();
        session.setAttribute(ApplicationVariable.SESSION_KEY_USERINFO,user);
        return AjaxResult.success(1);
    }
    @RequestMapping("/logout")
    public AjaxResult Logout(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        session.removeAttribute(ApplicationVariable.SESSION_KEY_USERINFO);
        return AjaxResult.success(1,"注销成功!");
    }
    @RequestMapping("/islogin")
    public AjaxResult islogin(HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);
        if(session!=null && session.getAttribute(ApplicationVariable.SESSION_KEY_USERINFO)!=null)
        {
            return AjaxResult.success(session.getAttribute(ApplicationVariable.SESSION_KEY_USERINFO));
        }
        //未登录
        return AjaxResult.success(-1);
    }
    @Value("${imgRoot.path}")
    private String myConfigValue;
    @SneakyThrows
    @PostMapping("/userImg")
    public AjaxResult uploadFile(@RequestParam("img") MultipartFile file,HttpServletRequest request) {

            // 这里你可以处理文件，例如保存到文件系统、数据库等
            // file.getOriginalFilename() 可以获取文件名
            // file.getBytes() 可以获取文件内容
            // file.getSize() 可以获取文件大小
        if(file==null || file.isEmpty())
        {
            return AjaxResult.fail(-1,"文件格式错误");
        }
        //随机数+.jpeg
        String name = UUID.randomUUID().toString().replaceAll("-","")+".jpeg";
        //保存头像

        FileTool.SaveFile(file.getBytes(),myConfigValue+name);
        //更新session
        User user  = UserSessionTools.getLoginUser(request);
        user.setPhoto(name);
        HttpSession session = UserSessionTools.session(request);
        session.setAttribute(ApplicationVariable.SESSION_KEY_USERINFO,user);
        //更新数据库
        int num = userService.Updata(user);
        if(num==1)
        {
            return AjaxResult.success(200);
        }
        return AjaxResult.fail(-1,"文件格式错误");
    }


        @SneakyThrows
        @RequestMapping("/getimg")
        public AjaxResult getImage(String img, HttpServletResponse response) {
            File file = new File(myConfigValue+img);
            InputStream inputStream = new FileInputStream(file);
            OutputStream outputStream = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int length = 0;
            while((length=inputStream.read(buffer))>0)
            {
                outputStream.write(buffer);
            }
            return AjaxResult.success(1);
        }
        @SneakyThrows
        @RequestMapping("/test")
    public AjaxResult test(String qq) {

            String a = MailUtil.send(qq, "测试", "邮件来自Hutool测试", false);
            System.out.println(a);
            return AjaxResult.success(1);
    }

    @RequestMapping("/name")
    public AjaxResult ModifyName(String name,HttpServletRequest request)
    {
        if(!StringUtils.hasLength(name))
        {
            return AjaxResult.fail(-1,"名字错误");
        }
        User user  = UserSessionTools.getLoginUser(request);
        int i = userService.UpdataForName(name,user.getId());
        if(i==1)
        {
            //更新session
            HttpSession session = UserSessionTools.session(request);
            user.setName(name);
            session.setAttribute(ApplicationVariable.SESSION_KEY_USERINFO,user);
            return AjaxResult.success(1);
        }
        return AjaxResult.fail(-1,"更新失败!");
    }
//@SneakyThrows
//@RequestMapping("/getimg")
//public AjaxResult getImage(String img, HttpServletResponse response) {
//         org.springframework.core.io.Resource resource = new ClassPathResource("static/img/"+img);
//         response.setContentType("image/jpeg");
//         InputStream inputStream = resource.getInputStream();
//        OutputStream outputStream = response.getOutputStream();
//        byte[] buffer = new byte[1024];
//        int length = 0;
//        while((length=inputStream.read(buffer))>0)
//        {
//            outputStream.write(buffer);
//        }
//        return AjaxResult.success(1);
//}

//    @RequestMapping("/test")
//    public AjaxResult test(String email)
//    {
////        //1.校验邮箱
//            if(!MailClient.validate(email))
//            {
//                return AjaxResult.fail(-1,"邮箱格式错误!");
//            }
////        //2.判读是否已经发送
//            String verifyCode = stringRedisTemplate.opsForValue().get(email);
//            if(verifyCode!=null)
//            {
//                return AjaxResult.fail(-1,"邮箱已经发送!");
//            }
//
////        //3.发送邮箱 存验证码于redis中
////        String verifyCode ="1212";
//        Context context = new Context();
//        String content=templateEngine.process("mail/mail.html", context);
//        verifyCode = StringTools.generateVerificationCode(6);
//        stringRedisTemplate.opsForValue().set(email,verifyCode,60, TimeUnit.SECONDS);
//        MailClient.sendMail("zhengzhang0217@163.com","验证码",content);
//        return AjaxResult.success(1,"邮箱发送成功!");
//
//    }

}
