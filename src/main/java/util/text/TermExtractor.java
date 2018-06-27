package util.text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TermExtractor {
	
	Set<String> setS = new HashSet<String>(); 
	List<String> adlList = new ArrayList<String>();
	
	/**
	 *  input =>   
	 *      kMAp -> key id :  , target :洗体,洗髪,体を拭く
	 *      list -> 洗体動作(id), 
	 *   output => id:洗体,洗髪,体を拭く:洗体動作
	 * @param map
	 * @param stList
	 */
	public void extractTerm(Map<String,String> map, List<String> stList) {
		List<String> sL = new ArrayList<String>();
		List<String> sL2 = new ArrayList<String>();
		stList.forEach(st -> Arrays.stream(st.split(",")).forEach(a -> sL.add(a)));
		
		sL.forEach(st -> assignID4Hierarchy(st,map));
		
	}
	
	/**  input =>   
	 *      kMAp -> key id :  , target :洗体,洗髪,体を拭く
	 *      st -> 洗体動作(id), 
	 *   output => id:洗体,洗髪,体を拭く:洗体動作
	 * @param st
	 * @param map
	 */
	public void assignID4Hierarchy(String st, Map<String,String> map) {
		if(st.contains("(")) {						
			String rexResult = exRexStr(st,"\\((.+?)\\)");
			//expected rexResult -> 22:起居動作
			System.out.println("rexResult ="+ rexResult.toString());
			String[] num = rexResult.split(":");
			//int id = new Integer(num);
			StringBuffer outStr = new StringBuffer(num[0]);
			//String id = num[0];
			outStr.append(":");
			outStr.append(map.get(num[0]));	
			outStr.append(":");
			outStr.append(num[1]);
			adlList.add(outStr.toString());
			System.out.println("elemts ="+ outStr.toString());
		}			
	}
	
	public void extractFlatternTerm(Map<String,String> map, List<String> stList) {
		 //List<String> sL = new ArrayList<String>();
		//stList.forEach(st ->  Arrays.stream(st.split(",")).forEach(a -> sL.add(a)));
		
		//sL.forEach(st -> assignID4Hierarchy(st,map));
		 
		 adlList = assignID4Flatten(stList, map);
		 
		 adlList.forEach(p -> System.out.println(p));
	}
	
	/**  input =>   
	 *      kMAp -> key id :  , target :洗体,洗髪,体を拭く
	 *      st -> 洗体動作(id), 
	 *   output => id:洗体,洗髪,体を拭く:洗体動作
	 * @param st
	 * @param map
	 * @deprecated
	 */
	public String assignID4Flatten(String st, Map<String,String> map) {
		//String mTerm = new String();
		if(st.contains("(")) {						
			String rexResult = exRexStr(st,"\\((.+?)\\)");
			//expected rexResult -> 22:起居動作
			String[] num = rexResult.split(":");
			//int id = new Integer(num);
			
			return map.get(num[0]);	
			//System.out.println("elemts ="+ outStr.toString());
		}else {
			
			return st;
		}
	}
	
	public List<String> assignID4Flatten(List<String> stList, Map<String,String> map) {
		//String mTerm = new String();
		List<String> allLine = new ArrayList<String>();
		for(String stLine: stList) {
			StringBuffer stb = new StringBuffer();
			List<String> stF = Arrays.asList(stLine.split(","));
			for(String st:stF) {
				if(st.contains("(")) {						
					String rexResult = exRexStr(st,"\\((.+?)\\)");
					//expected rexResult -> 22:起居動作
					String[] num = rexResult.split(":");					
					stb.append(map.get(num[0]));	
					stb.append(",");
					//System.out.println("elemts ="+ outStr.toString());
				}else {
					
					stb.append(st);
					stb.append(",");
				}
			}
			allLine.add(stb.toString());
		}
		return allLine;
	}
	
	public List<String> getADLList(){
		return adlList;
	}

	/**
	 * input [洗体動作(3)] -> output [3:洗体動作]
	 * or
	 * input [洗体動作(洗体,洗髪,体を拭く)] -> output [洗体,洗髪,体を拭く:洗体動作]
	 * @param st
	 * @param pattern
	 * @return
	 */
	public String exRexStr(String st, String pattern) {
		String term = null;
		String head = null;
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(st); 
        while (m.find()) {
        		term = m.group(1);
        		head = m.replaceAll("");
        }        
		return term+":"+ head.trim();
	}
	
	public void extractTerm(List<String> stList) {
		stList.forEach(st ->  extractTerm(st));
	}
	
	/**
	 * Delete duplicate term.
	 * @param st
	 */
	public void extractTerm(String st) {
		
		st = this.rexReplaceBracket(st);
		String[] arr = st.split(",");		
		for(int i=0; i< arr.length;i++) {
			System.out.println("分解したら　:: " + arr[i]);
		}		
		
		List<String> tList = Arrays.asList(arr);
		
		tList.forEach(term -> deleteDuplicated(term));
	}
	
	/**
	 * 
	 * @param term
	 */
	public void deleteDuplicated(String term) {
		
		String nTerm = term.trim();	
		
		if(!setS.contains(nTerm)){
  	      //System.out.println("True");
  		  setS.add(nTerm);
  	    }
	}

	public void getSetAll() {
	      Iterator<String> itr = setS.iterator();
	      while(itr.hasNext()){
	        System.out.println(itr.next());
	      }
	}
	
	public Set<String> getSet() {
		return setS;
	}
	
	/**
	 * 洗体動作(洗体,洗髪,体を拭く) -> 洗体動作
	 * @param line
	 * @return
	 */
	public String rexReplaceBracket(String line) {
		
		System.out.println(line);
        Pattern p = Pattern.compile("\\((.+?)\\)");
        Matcher m = p.matcher(line);

        while (m.find()) {

        		String target =  m.group();
            System.out.println("一致した部分は : " + target);
                       
            String line2 = line.replaceAll(target, "").replace("()", "");
            line = line2;
        }  
        System.out.println("result = " + line);  
        return line;        
	}	  
	
	public static void main(String[] args) {
		TermExtractor tmEx = new TermExtractor();
		String st = "起居動作 (起き上がり,立ち上がり時の上肢支持)";
		String rt = tmEx.exRexStr(st,"\\((.+?)\\)");
		
		System.out.println(rt);
	}
}
