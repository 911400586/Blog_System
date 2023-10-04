package com.example.blog_system.Service;

import com.example.blog_system.Mapper.ArticleMapper;
import com.example.blog_system.Model.Article;
import com.example.blog_system.Model.PubTime;
import com.example.blog_system.Model.Vo.ArticleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {
    @Autowired
    ArticleMapper articleMapper;
    public int add(Article article){
        return articleMapper.add(article);
    }
    public Article getArticleById(int id,int uid){
        return articleMapper.getArticleById(id,uid);
    }
    public ArticleVo getArticle(int id)
    {
        return articleMapper.getArticle(id);
    }
    public int updateById(Article article)
    {
        return articleMapper.updateById(article);
    }
    public int count(int id){
        return articleMapper.count(id);
    }
    public List<Article> getArticleByUid(Integer uid){
        return articleMapper.getArticleByUid(uid);
    }
    public List<Article> getListByPage(int pageSize,int offset)
    {
        return articleMapper.getListByPage(pageSize,offset);
    }
    public List<Article> getMyListByPage(int id,int pageSize,int offset)
    {
        return articleMapper.getMyListByPage(id,pageSize,offset);
    }
    public int getCount()
    {
        return articleMapper.getcount();
    }
    public int del(int id,int uid)
    {
        return articleMapper.del(id,uid);
    }
    public List<PubTime> getArticleByState(int state)
    {
        return articleMapper.getArticleByState(state);
    }
    public int updataForStateById(int id,int state)
    {
        return articleMapper.updataForStateById(id,state);
    }
    public int getCountById(int id)
    {
        return articleMapper.getcountById(id);
    }
}
