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

/**
 *
 * @author linroex
 */
public class NewsSpider {
    
    private ArrayList<String> enterance;
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

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {

    }
    
}
