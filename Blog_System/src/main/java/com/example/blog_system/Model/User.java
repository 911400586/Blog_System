package com.example.blog_system.Model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class User  implements Serializable {
    private int id;
    private String username;
    private String password;
    private String photo;
    private String name;
    private LocalDateTime createtime;
    private LocalDateTime updatetime;
    //邮箱
    private String email;
    private int state;
}
