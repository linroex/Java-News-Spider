/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package news.spider;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author linroex
 */
public class NewsSpider {
    
    private final ArrayList<String> enterance;
    private String targetSelector;
    
    public NewsSpider() {
        this.enterance = new ArrayList();
        this.targetSelector = "";
    }
    
    public void setTargetSelector(String selector) {
        this.targetSelector = selector;
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
                
                // parse dom as news object
                
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
