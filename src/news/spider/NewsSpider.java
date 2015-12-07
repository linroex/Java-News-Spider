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
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

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
    private final List<Thread> threads;
    
    private String baseUrl = "";
    
    private String targetSelector;
    private String titleSelector;
    private String authorSelector;
    private String datetimeSelector;
    private String contentSelector;
    
    public NewsSpider(String baseUrl) {
        this.enterance = new ArrayList();
        
        this.newsList = Collections.synchronizedList(new ArrayList());
        this.threads = Collections.synchronizedList(new ArrayList());
        
        this.targetSelector = "";
        
        this.baseUrl = baseUrl;
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
            
            threads.add(channelThread);
        }
    }
    
    public int size() {
        return this.newsList.size();
    }
    
    public boolean isFinish() {
        boolean finish = false;
        
        for(Thread thread : this.threads) {
            finish |= thread.isAlive();
        }
        
        return !finish;
    }
    
    @Override
    public String toString() {
        String output = "";
        
        for(News news : this.newsList) {
            output += news.toString() + "\n";
        }
        
        return output;
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
                    
                    threads.add(parseNewsThread);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    private class ParseNewsRunnable implements Runnable {
        private final String targetUrl;
        
        public ParseNewsRunnable(String url) {
            this.targetUrl = baseUrl + url;
        }
        
        @Override
        public void run() {
            try {
                Document dom = Jsoup.connect(this.targetUrl).get();
                
                String title = dom.select(titleSelector).text();
                String author = dom.select(authorSelector).text();
                String datetime = dom.select(datetimeSelector).text();
                String content = dom.select(contentSelector).text();
                
                News news = new News(title, author, datetime, content);
                newsList.add(news);
                
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
        System.out.println("running");
        
        NewsSpider spider = new NewsSpider("http://www.bbc.com");
        
        spider.addEnterance("http://www.bbc.com/news/world/asia");
        spider.addEnterance("http://www.bbc.com/news/uk");
        spider.addEnterance("http://www.bbc.com/news/business");
        
        spider.setTargetSelector("a.title-link");
        
        spider.setTitleSelector("h1.story-body__h1");
        spider.setAuthorSelector(".story-body__mini-info-list-and-share .mini-info-list__section");
        spider.setDateTimeSelector(".story-body__mini-info-list-and-share .date.date--v2");
        spider.setContentSelector(".story-body__inner");
        
        spider.start();
        
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                if(spider.isFinish()) {
                    timer.cancel();
                    System.out.println(spider.toString());
                }
            }
        }, 0, 1000);
    }
    
}
