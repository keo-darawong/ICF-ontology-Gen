package util.json.jackson;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonTest2 {

    public static void main(String[] args) {

    		String file ="/Users/keo_darawong/Documents/Ontology_data/ICF/test_data/test.json";
    		readJsonTest(file);
    	
    }
    
    
    public static void readJsonTest(String file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(new File(file));
            String member_name = root.get("member").get(0).get("name").asText();
            System.out.println(member_name);
            int age = root.get("member").get(3).get("age").asInt();
            System.out.println(age);

            for (JsonNode n : root.get("album")) {
                String album_name = n.get("name").asText();
                int year = n.get("year").asInt();
                String month = n.get("month").asText();
                int day = n.get("day").asInt();
                System.out.println(album_name + ": " + day + " " + month + " " + year);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }   	
    }
    
    public static void writeJsonTest(String file) {
    
    	ObjectMapper mapper = new ObjectMapper();   
    	JsonNode node = mapper.createObjectNode();
    	
    	
    	
    }
}
