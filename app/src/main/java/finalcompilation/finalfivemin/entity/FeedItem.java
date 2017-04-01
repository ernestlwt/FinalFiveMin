package finalcompilation.finalfivemin.entity;

import org.w3c.dom.Document;

/**
 * Created by Ernest on 3/3/2017.
 */

public abstract class FeedItem {
    String title;
    protected String description;
    String date;
    String link;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description){ this.description = description; };

    public abstract void processDescription();

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return (title + link);
    }
}
