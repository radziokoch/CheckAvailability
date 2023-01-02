import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    //list of all countries which have Disney+
    static String[] countryTable = {"AD", "AG", "AL", "AR", "AS", "AT", "AU", "BA", "BB", "BE", "BG", "BL", "BO", "BR", "BZ", "BS", "CA", "CC", "CK", "PM", "CH", "CR", "DA", "DE", "DK", "DM", "DO", "EC", "EE", "ES", "FI", "FO", "FR", "GB", "GD", "GF", "GG", "GI", "GL", "GP", "GR", "GT", "GU", "GY", "HN", "HR", "HT", "IE", "IM", "IO", "IS", "IT", "JA", "JE", "JM", "KN", "KO", "LC", "LI", "LT", "LU", "LV", "MC", "ME", "MF", "MH", "MK", "MP", "MQ", "MT", "MU", "MX", "NC", "NF", "NI", "NL", "NO", "NU", "NZ", "PA", "PF", "PN", "PT", "PL", "PY", "RE", "SE", "SH", "SI", "SM", "SR", "SV", "SX", "TF", "TK", "TT", "UM", "US", "UY", "VA", "VC", "VE", "WF", "YT", "CO", "CL", "CX", "PE", "PM", "CZ", "SJ", "SK", "RO", "RS", "HU", "TR", "HK", "TW", "SG"};

    //class which downloads JSONs from Disney+
    public static String downloadJSON(URL url) {
        try (InputStream input = url.openStream()) {
            InputStreamReader isr = new InputStreamReader(input);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder json = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                json.append((char) c);
            }
            return json.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //class which checks JSONs
    public static void checkJSON(String typeVideo, String dmcCode) throws MalformedURLException {


        String jsonFile; //String for JSON File
        String jsonFilePart; //String for first part of JSON file which is using for check

        for (int i = 0; i < countryTable.length; i++) {
            if (Objects.equals(typeVideo, "M")) {
                URL jsonAddress = new URL("https://disney.content.edge.bamgrid.com/svc/content/DmcVideoBundle/version/5.1/region/" + countryTable[i]
                        + "/audience/k-false,l-true/maturity/1850/language/pl/encodedFamilyId/" + dmcCode);
                jsonFile = downloadJSON(jsonAddress);
                jsonFilePart = jsonFile.substring(0, 231); //231 is length of empty JSON about movie
                //check if JSON isn't empty
                if (!Objects.equals(jsonFilePart, "{\"data\":{\"DmcVideoBundle\":{\"containers\":[],\"extras\":{\"meta\":{\"hits\":0,\"offset\":0,\"page_size\":50},\"videos\":[]},\"promoLabels\":null,\"related\":{\"experimentToken\":\"\",\"items\":[],\"meta\":{\"hits\":0,\"offset\":0,\"page_size\":8}},\"video\":null}}}")) {
                    System.out.println(countryTable[i]); //if JSON doesn't empty, movie is avaiable in this country, app print code of country
                }
            } else {
                URL jsonAddress = new URL("https://disney.content.edge.bamgrid.com/svc/content/DmcSeriesBundle/version/5.1/region/" + countryTable[i]
                        + "/audience/k-false,l-true/maturity/1850/language/pl/encodedSeriesId/" + dmcCode);
                jsonFile = downloadJSON(jsonAddress);
                jsonFilePart = jsonFile.substring(0, 372); //372 is length of empty JSON about series
                if (!Objects.equals(jsonFilePart, "{\"data\":{\"DmcSeriesBundle\":{\"containers\":[],\"episodes\":{\"meta\":{\"hits\":0,\"offset\":0,\"page_size\":15},\"videos\":[]},\"extras\":{\"meta\":{\"hits\":0,\"offset\":0,\"page_size\":80},\"videos\":[]},\"promoLabels\":null,\"related\":{\"experimentToken\":\"\",\"items\":[],\"meta\":{\"hits\":0,\"offset\":0,\"page_size\":8}},\"seasons\":{\"meta\":{\"hits\":0,\"offset\":0,\"page_size\":100},\"seasons\":[]},\"series\":null}}}")) {
                    System.out.println(countryTable[i]);
                }
            }
        }
    }


    public static void main(String[] args)
            throws IOException {

        Scanner scan = new Scanner(System.in);
        System.out.println("Series (S) or Movie (M)?"); //question about type of video
        String typeVideo = scan.nextLine();
        if (Objects.equals(typeVideo, "S") || Objects.equals(typeVideo, "M")) {
            System.out.println("What DmcCode"); //question about video code from Disney+
            String dmcCode = scan.nextLine();

            checkJSON(typeVideo, dmcCode);
        }


    }
}