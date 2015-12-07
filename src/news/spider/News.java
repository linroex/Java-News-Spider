/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package news.spider;

import com.google.gson.Gson;
import java.time.LocalDateTime;

/**
 *
 * @author linroex
 */
public class News {
    private String title;
    private String author;
    private String datetime;
    private String content;
    
    public News(String title, String author, String datetime, String content) {
        this.title = title;
        this.author = author;
        this.datetime = datetime;
        this.content = content;
    }
    
    @Override
    public String toString() {
        return this.title;
    }
    
    public String toJson() {
        return new Gson().toJson(this);
    }
}
