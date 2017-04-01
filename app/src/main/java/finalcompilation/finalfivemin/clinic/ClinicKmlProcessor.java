package finalcompilation.finalfivemin.clinic;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.google.android.gms.maps.model.LatLng;

import finalcompilation.finalfivemin.entity.Clinic;

public class ClinicKmlProcessor {

    private static final String NAME_TAG = "<td>NAME</td><td>";
    private static final String PHONE_TAG = "<td>Telephone</td><td>";
    private static final String POSTAL_TAG = "<td>ADDRESSPOSTALCODE</td><td>";
    private static final String BLOCK_TAG = "<td>ADDRESSBLOCKHOUSENUMBER</td><td>";
    private static final String FLOOR_TAG = "<td>ADDRESSFLOORNUMBER</td><td>";
    private static final String UNIT_TAG = "<td>ADDRESSUNITNUMBER</td><td>";
    private static final String STREET_TAG = "<td>ADDRESSSTREETNAME</td><td>";
    private static final String BUILDING_TAG = "<td>ADDRESSBUILDINGNAME</td><td>";
    private static final String COORDINATES_TAG ="<coordinates>";

    public ClinicKmlProcessor (){}

    // introducing quick fix for public static ArrayList<Clinic> kmlToClinic(String fileLocation){
    public static ArrayList<Clinic> kmlToClinic(Context context, String fileLocation){
        //Iterable containers = layer.getContainers();
        ArrayList<Clinic> listOfClinic = new ArrayList<Clinic>();
        Clinic c;
        String description = null;

        //replaced with quick fix String description = fileToString(fileLocation);
        try {
            InputStream is = context.getAssets().open(fileLocation);
            description = convertStreamToString(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //end of quick fix

        description = description.replace("\n", "").replace("\r", ""); //removes linebreaks
        //additional processing
        description = description.replace("&amp;", "");
        description = description.replace("&lt;Null&gt;", "");

        //return description;

        int count = 0;

        while(true){
            c = new Clinic();

            int index = 0;

            //get name of clinic
            index = description.indexOf(NAME_TAG);
            if(index == -1)
                break; // exit condition when there are no more clinics
            description = description.substring(index + NAME_TAG.length() ,description.length());
            index = description.indexOf("<");
            c.setClinicName(description.substring(0,index));

            //get number of clinic
            index = description.indexOf(PHONE_TAG);
            description = description.substring(index + PHONE_TAG.length() ,description.length());
            index = description.indexOf("<");
            c.setContact(description.substring(0,index));

            //==================== Start of getting Address **********************
            index = description.indexOf(POSTAL_TAG);
            description = description.substring(index + POSTAL_TAG.length(),description.length());
            index = description.indexOf("<");
            String postalCode = description.substring(0,index);

            index = description.indexOf(BLOCK_TAG);
            description = description.substring(index + BLOCK_TAG.length(),description.length());
            index = description.indexOf("<");
            String block = description.substring(0,index);

            index = description.indexOf(FLOOR_TAG);
            description = description.substring(index + FLOOR_TAG.length(),description.length());
            index = description.indexOf("<");
            String floor = description.substring(0,index);

            index = description.indexOf(UNIT_TAG);
            description = description.substring(index + UNIT_TAG.length(),description.length());
            index = description.indexOf("<");
            String unit = description.substring(0,index);

            index = description.indexOf(STREET_TAG);
            description = description.substring(index + STREET_TAG.length(),description.length());
            index = description.indexOf("<");
            String street = description.substring(0,index);

            index = description.indexOf(BUILDING_TAG);
            description = description.substring(index + BUILDING_TAG.length(),description.length());
            index = description.indexOf("<");
            String building = description.substring(0,index);

            String address = "";
            if(!building.equals(""))
                address += building + " ";
            address += street +" " + block;
            if(!floor.equals("") && !unit.equals(""))
                address += " #" + floor + "-" + unit;
            address += " " + postalCode;

            c.setAddress(address);
            //******************** End of getting Address **********************

            //get coordinates
            index = description.indexOf(COORDINATES_TAG);
            description = description.substring(index + COORDINATES_TAG.length(),description.length());
            index = description.indexOf("<");
            String coordinates = description.substring(0,index);
            System.out.println(coordinates);
            String[] parts = coordinates.split(",");
            c.setLocation(new LatLng(Double.parseDouble(parts[1]),Double.parseDouble(parts[0])));

            count++;
            c.setID(count);
            listOfClinic.add(c);
        }
        return listOfClinic;

    }

    public static String fileToString(String filename) {
        try{
            String result = getStringFromFile(filename);
            return result;
        }catch (IOException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getStringFromFile (String filePath) throws Exception {
        File fl = new File(filePath);
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        fin.close();
        return ret;
    }

    public static String convertStreamToString(InputStream is) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        Boolean firstLine = true;
        while ((line = reader.readLine()) != null) {
            if(firstLine){
                sb.append(line);
                firstLine = false;
            } else {
                sb.append("\n").append(line);
            }
        }
        reader.close();
        return sb.toString();
    }


}
