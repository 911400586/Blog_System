package Model;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Blog {
    private int blogid;
    private int userid;
    private String title;
    private String content;
    private Timestamp posttime;

    public int getBlogid() {
        return blogid;
    }

    public void setBlogid(int blogid) {
        this.blogid = blogid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPosttime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataStr = sdf.format(posttime);
        return dataStr;
    }

    public void setPosttime(Timestamp posttime) {
        this.posttime = posttime;
    }
}
