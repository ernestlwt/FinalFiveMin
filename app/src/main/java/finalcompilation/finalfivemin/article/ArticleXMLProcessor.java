package finalcompilation.finalfivemin.article;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import finalcompilation.finalfivemin.Constants;
import finalcompilation.finalfivemin.entity.AsiaoneFeedItem;
import finalcompilation.finalfivemin.entity.FeedItem;
import finalcompilation.finalfivemin.entity.ShapeFeedItem;

/**
 * Created by Ernest on 3/3/2017.
 */

public class ArticleXMLProcessor{
    Document data;
    ArrayList<FeedItem> listOfFeedItem;
    int source;

    public ArticleXMLProcessor(int source){
        this.source = source;
        listOfFeedItem = new ArrayList<FeedItem>();
        getDataFromURL();
        processDocument();
    }

    public ArrayList<FeedItem> getListOfFeedItem() {
        return listOfFeedItem;
    }

    private void processDocument(){
        if(data != null){
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);//channel contains title,link,etc and news items
            NodeList items = channel.getChildNodes();//get all contents in channel
            for (int i = 0; i < items.getLength(); i++) {
                Node currentChild = items.item(i);
                if (currentChild.getNodeName().equalsIgnoreCase("item")) {//if it is a news item
                    FeedItem item;
                    switch(source){
                        case Constants.ARTICLE_SOURCE_SHAPE:
                            item = new ShapeFeedItem();
                            break;
                        case Constants.ARTICLE_SOURCE_ASIAONE:
                            item = new AsiaoneFeedItem();
                            break;
                        default:
                            System.out.println("Error");
                            return;
                    }
                    NodeList itemDetails = currentChild.getChildNodes();
                    for (int j = 0; j < itemDetails.getLength(); j++) {
                        Node current = itemDetails.item(j);
                        if (current.getNodeName().equalsIgnoreCase("title")){
                            item.setTitle(current.getTextContent());
                        }else if (current.getNodeName().equalsIgnoreCase("description")){
                            item.setDescription(current.getTextContent());
                        }else if (current.getNodeName().equalsIgnoreCase("pubDate")){
                            item.setDate(current.getTextContent());
                        }else if (current.getNodeName().equalsIgnoreCase("link")){
                            item.setLink(current.getTextContent());
                        }
                    }
                    item.processDescription();
                    listOfFeedItem.add(item);
                    Log.d("Added item ", item.toString());
                }
            }
        }
    }


    private void getDataFromURL(){
        try {
            URL url;
            switch(source){
                case Constants.ARTICLE_SOURCE_SHAPE:
                    url = new URL(Constants.ARTICLE_LINK_SHAPE);
                    break;
                case Constants.ARTICLE_SOURCE_ASIAONE:
                    url = new URL(Constants.ARTICLE_LINK_ASIAONE);
                    break;
                default:
                    System.out.println("Error");
                    return;
            }
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            data = builder.parse(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
