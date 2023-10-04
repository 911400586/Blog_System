package com.example.blog_system.Common;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTools {
   public static String removeMarkdownKeywords(String markdownText) {

      String[] regex = {"#"};
      String text = markdownText;
      for(String i:regex)
      {
         text = text.replaceAll(i,"");
      }
      return text;
   }
   //生成指定位数的验证码
   public static String generateVerificationCode(int codeLength) {
      String base = "0123456789";
      StringBuilder code = new StringBuilder();
      Random random = new Random();
      for (int i = 0; i < codeLength; i++) {
         code.append(base.charAt(random.nextInt(base.length())));
      }
      return code.toString();
   }
}
