package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileIO {
	
	public static List<String> readFile(String path) {
		List<String> lines = new ArrayList<String>();
		
		File f = new File(path);
	      BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(f));
			
		      String line;
		      // 1行ずつCSVファイルを読み込む
		      while ((line = br.readLine()) != null) {
		    	  lines.add(line);		    	  
		      }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
		      System.out.println(e);
		    }
		return lines;
	}

	public static void saveFile(List<String> lines, String path) {
		//List<String> lines = Arrays.asList("The first line", "The second line");
		Path file = Paths.get(path);
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
