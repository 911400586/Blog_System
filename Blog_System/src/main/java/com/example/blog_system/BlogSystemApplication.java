package com.example.blog_system;

import ch.qos.logback.core.util.TimeUtil;
import com.example.blog_system.Config.BeanFetcher;
import com.example.blog_system.Model.PubTime;
import com.example.blog_system.Service.ArticleService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class BlogSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogSystemApplication.class, args);
        //定时器 每隔3秒检查文章是否要发布
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //获取bean
                ArticleService articleService =  BeanFetcher.getBean(ArticleService.class);
                //1.获取定时发布的文章
                List<PubTime> pubTimeList = articleService.getArticleByState(3);
                if(pubTimeList.size()==0)
                {
                    return;
                }
                //2.使用优先级队列
                Queue<PubTime> p = new PriorityQueue<PubTime>();
                p.addAll(pubTimeList);
                //3.检查堆中的元素是否需要发布 pubtime默认日期2002-10-11
                while(!p.isEmpty())
                {
                    PubTime t = p.poll();
                    LocalDateTime NowTime = LocalDateTime.now();
                    LocalDateTime Time = t.getPubtime();
                   if(Time.compareTo(NowTime)<=0)
                   {
                       //进行发布
                       articleService.updataForStateById(t.getId(),1);
                   }
                   else{
                       //下次进行检查
                       break;
                   }
                }

            }
        };
        //每隔三秒检查是否发布
        scheduler.scheduleAtFixedRate(runnable,1,3, TimeUnit.SECONDS);
    }

}
