package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mingfei.net@gmail.com
 * 3/1/17 10:45
 */
public class DumpData {
    private static final String URL_PREFIX = "http://dict.cn/";
    private static int counter;


    public static void readFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("data/CET_4.txt"))) {
            String word;
            while ((word = bufferedReader.readLine()) != null) {
                extract(word);
                System.out.println(++counter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void extract(String word) {
        try {
            URL url = new URL(URL_PREFIX.concat(word));
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()))) {
                String line;
                System.out.println(word);
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.contains("英 <bdo>")) {
                        String phoneticUk = line.substring(
                                line.indexOf('['),
                                line.indexOf(']') + 1
                        );
                        System.out.println("\t英\t" + phoneticUk);
                    }
                    if (line.contains("美 <bdo>")) {
                        String phoneticUs = line.substring(
                                line.indexOf('['),
                                line.indexOf(']') + 1
                        );
                        System.out.println("\t美\t" + phoneticUs);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        readFile();
    }
}
