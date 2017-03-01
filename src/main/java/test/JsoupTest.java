package test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by mingfei.net@gmail.com
 * 3/1/17 11:54
 */
public class JsoupTest {

    private static final String URL = "http://dict.cn/test";

    public static void main(String[] args) throws IOException {
        Document document = Jsoup.connect(URL).get();
        System.out.println(document.getElementsByAttributeValue("lang", "en-us").text());
    }
}
