package Model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Mark {

    private String username;
    private String content;
    private Timestamp posttime;
    private int blogid;

    public int getBlogid() {
        return blogid;
    }

    public void setBlogid(int blogid) {
        this.blogid = blogid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
