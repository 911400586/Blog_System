package com.example.blog_system.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PubTime implements Comparable<PubTime>{
    private int id;
    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss")
    private LocalDateTime pubtime;


    @Override
    public int compareTo(PubTime o) {
        return this.pubtime.compareTo(o.pubtime);
    }
}
