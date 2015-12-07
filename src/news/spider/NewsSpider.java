/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package news.spider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author linroex
 */
public class NewsSpider {
    
    private final List<String> enterance;
    private final List<News> newsList;
    
    private String targetSelector;
    private String titleSelector;
    private String authorSelector;
    private String datetimeSelector;
    private String contentSelector;
    
    public NewsSpider() {
        this.enterance = new ArrayList();
        this.newsList = Collections.synchronizedList(new ArrayList());
        this.targetSelector = "";
    }
    
    public void setTargetSelector(String selector) {
        this.targetSelector = selector;
    }
    
    public void setTitleSelector(String selector) {
        this.titleSelector = selector;
    }
    
    public void setAuthorSelector(String selector) {
        this.authorSelector = selector;
    }
    
    public void setDateTimeSelector(String selector) {
        this.datetimeSelector = selector;
    }
    
    public void setContentSelector(String selector) {
        this.contentSelector = selector;
    }
    
    public int addEnterance(String url) {
        this.enterance.add(url);
        return this.enterance.size();
    }
    
    public void start() {
        for(String url : this.enterance) {
            Thread channelThread = new Thread(new ChannelSpiderRunable(url));
            channelThread.start();
        }
    }
    
    private class ChannelSpiderRunable implements Runnable {
        private final String targetUrl;
        
        public ChannelSpiderRunable(String url) {
            this.targetUrl = url;
        }
        
        @Override
        public void run(){
            try {
                Document dom = Jsoup.connect(this.targetUrl).get();
                Elements urls = dom.select(targetSelector);
                
                for(Element url : urls) {
                    Thread parseNewsThread = new Thread(new ParseNewsRunnable(url.attr("href")));
                    parseNewsThread.start();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    private class ParseNewsRunnable implements Runnable {
        private final String targetUrl;
        
        public ParseNewsRunnable(String url) {
            this.targetUrl = url;
        }
        
        @Override
        public void run() {
            try {
                Document dom = Jsoup.connect(this.targetUrl).get();
                
                String title = dom.select(titleSelector).text();
                String author = "";
                LocalDateTime datetime;
                String content = "";
                
                
                
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            
        }
    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {

    }
    
}
