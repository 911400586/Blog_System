package com.example.blog_system.Mapper;

import com.example.blog_system.Model.Article;
import com.example.blog_system.Model.PubTime;
import com.example.blog_system.Model.User;
import com.example.blog_system.Model.Vo.ArticleVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {
    public int add(Article article);
    public Article getArticleById(int id,int uid);
    public int updateById(Article article);
    public ArticleVo getArticle(int id);
    public int count(int id);
    public List<Article> getMyListByPage(int id,int pageSize,int offset);
    public List<Article> getArticleByUid(int uid);
    public List<Article> getListByPage(int pageSize,int offset);
    public int getcount();
    public int del(int id,int uid);
    public List<PubTime> getArticleByState(int state);
    public int updataForStateById(int id,int state);
    public int getcountById(int uid);
}
