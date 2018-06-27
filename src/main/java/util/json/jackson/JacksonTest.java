package util.json.jackson;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class JacksonTest {

	private static final String SAMPLE_JSON = "{\"value\":100,\"string\":\"hogehoge\",\"map\":{\"key2\":\"value2\",\"key1\":\"value1\"},\"list\":[\"item1\",\"item2\"]}";
	
	public static void main(String[] args) {
		
	      // JSON文字列⇒Object
	      JsonObject jsonObject = null;
		try {
			jsonObject = JsonConverter.toObject(SAMPLE_JSON, JsonObject.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	      // Object⇒JSON文字列
	      String jsonString = null;
		try {
			jsonString = JsonConverter.toString(jsonObject);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	      // JSON文字列出力
	      System.out.println(jsonString);
        
	}
		
}
