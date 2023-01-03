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
            int i = 0;
            while ((c = reader.read()) != -1 && i<500) {
                json.append((char) c);
                i++; //download only first 500 characters, after this we know if is empty or not
            }
            return json.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //class which checks JSONs
    public static void checkJSON(String typeVideo, String dmcCode) throws MalformedURLException {


        String jsonFile; //String for JSON File

        for (int i = 0; i < countryTable.length; i++) {
            if (Objects.equals(typeVideo, "M")) {
                URL jsonAddress = new URL("https://disney.content.edge.bamgrid.com/svc/content/DmcVideoBundle/version/5.1/region/" + countryTable[i]
                        + "/audience/k-false,l-true/maturity/1850/language/pl/encodedFamilyId/" + dmcCode);
                jsonFile = downloadJSON(jsonAddress);
                 //231 is length of empty JSON about movie
                if (jsonFile.length() > 300)// long JSON = not empty = movie is available
                {
                    System.out.println(countryTable[i]);
                }
            } else {
                URL jsonAddress = new URL("https://disney.content.edge.bamgrid.com/svc/content/DmcSeriesBundle/version/5.1/region/" + countryTable[i]
                        + "/audience/k-false,l-true/maturity/1850/language/pl/encodedSeriesId/" + dmcCode);
                jsonFile = downloadJSON(jsonAddress);
                //372 is length of empty JSON about series
                if (jsonFile.length() > 400)// long JSON = not empty = series is available
                {
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
            System.out.println("What DmcCode?"); //question about video code from Disney+
            String dmcCode = scan.nextLine();

            checkJSON(typeVideo, dmcCode);
        }


    }
}