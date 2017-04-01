package finalcompilation.finalfivemin.entity;

import org.jsoup.Jsoup;

/**
 * Created by Ernest on 28/3/2017.
 */

public class AsiaoneFeedItem extends FeedItem {

    @Override
    public void processDescription() {
        description = Jsoup.parse(description).text();
    }
}
