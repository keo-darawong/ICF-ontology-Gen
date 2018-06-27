package util.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonTest {
	
	public static void main(String[] args) {
		
		test1();
        
	}
	
	public static void test1(){
		JSONObject obj0 = new JSONObject();
		JSONObject obj = new JSONObject();
		JSONArray jArr = new JSONArray();
		
        obj.put("name", "Taro Tanaka");
        obj.put("age", 30);
        jArr.put(obj);    //put("member", obj);
        obj = new JSONObject();
        obj.put("name", "Junko Tanaka");
        obj.put("age", 27);
        jArr.put(obj);
        obj0.put("member", jArr);
        System.out.println(obj0);
	}

	public static void test2(){
		
        String script = "[ \"Taro\", \"Tanaka\"]";
        
        JSONArray arr = new JSONArray(script);
 
        List<String> list = new ArrayList<>();
        for(Object str : arr) {
            list.add((String) str);
        }
 
        System.out.println(list);
	}
	
	
	public static void test3(){
		
        String script = "{ \"name\":\"Taro Tanaka\", \"age\":30}";
        
        JSONObject obj = new JSONObject(script);
 
        Map<String, Object> map = new HashMap<>();
        for(Object key : obj.keySet()) {
            map.put((String) key, obj.get((String) key));
        }
 
        System.out.println(map);
	}
	
	
	public static void test4() {
		
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Taro Tanaka");
        map.put("age", 30);
 
        JSONObject obj = new JSONObject(map);
 
        System.out.println(obj);
	}
}
