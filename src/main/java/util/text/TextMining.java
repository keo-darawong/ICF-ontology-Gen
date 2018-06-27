package util.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.FileIO;


public class TextMining {

	static Map<String,String> kMap = new HashMap<String,String>();
	static Map<String,String> akMap = new HashMap<String,String>();
	
	List<String> listBranket = new ArrayList<String>();
	TermExtractor tExtra = new TermExtractor();
	static StringBuffer path = new StringBuffer("/Users/keo_darawong/Documents/Ontology_data/ICF/test_data/used_data/");
	
	public static void main(String[] args) {
		
		String inFile = path.append("ADL-IADL-temp.txt").toString();
		//String outFile = path.append("ADL-IADL-term.txt").toString();
		String outFile2 = path.append("ADL-IADL-Children.txt").toString();
		List<String> listF = FileIO.readFile(inFile);
		TextMining tMining = new TextMining();
	
		//tMining.extractADLIADL(inFile,outFile);
		//tMining.replaceCount(listF);
		//tMining.creatADLIADLtext(listF, outFile2);
		tMining.creatFlatttenADLIADLtext(listF, outFile2);
		//tExtra.extractTerm(kMap, FileIO.readFile(inFile));
	}
	

	/**
	 * 
	 * @param inFile
	 * @param outFile
	 */
	public void extractADLIADL(String inFile, String outFile) {
		
		List<String> lines = FileIO.readFile(inFile);
		TermExtractor tExtra = new TermExtractor();
		tExtra.extractTerm(lines);
		tExtra.getSetAll();
		//Set<String> tSet = tExtra.getSet();
		List<String> list = new ArrayList<>(tExtra.getSet());
		
	}
	

	/**  kMAp -> key id :  , target :洗体,洗髪,体を拭く
	 * foreach -> 洗体動作(id), -> id:洗体,洗髪,体を拭く:洗体動作
	 * @param file
	 */
	public void replaceCount(List<String> listF ) {
		listF.forEach( line -> listBranket.add(rexReplaceCount(line)));
		tExtra.extractTerm(kMap, listBranket);
		//showAllMap();
	}
	
	/**  kMAp -> key id :  , target :洗体,洗髪,体を拭く
	 * foreach -> 洗体動作(id), -> id:洗体,洗髪,体を拭く:洗体動作
	 * @param file
	 */
	public void creatADLIADLtext(List<String> listF, String outFile2) {
		
		listF.forEach( line -> listBranket.add(rexReplaceCount(line)));
		tExtra.extractTerm(kMap, listBranket);
		List<String> tempOUt = tExtra.getADLList();
		tempOUt.forEach(p->System.out.println(p));
		FileIO.saveFile(tempOUt, outFile2);
		//showAllMap();
	}
	
	public void creatFlatttenADLIADLtext(List<String> listF, String outFile2) {
		
		listF.forEach( line -> listBranket.add(rexReplaceCount(line)));
		tExtra.extractFlatternTerm(kMap, listBranket);
		List<String> tempOUt = tExtra.getADLList();
		//tempOUt.forEach(p->System.out.println(p));
		FileIO.saveFile(tempOUt, outFile2);
		//showAllMap();
	}
	
	/**
	 *  机上作業 (読書, 書字, 縫製作業等) -> 机上作業(2)
	 *  kMAp -> key 2 :  , target :読書, 書字, 縫製作業等
	 * @param line
	 * @return
	 */
	public String rexReplaceCount(String line) {
						
		//System.out.println(line);
        Pattern p = Pattern.compile("\\((.+?)\\)");
        Matcher m = p.matcher(line);
        String countSt = new String(); //(new Integer(count)).toString();
        //int count = 0;
        while (m.find()) {
        		//count++;
        		String target =  m.group(1);
            //sSystem.out.println("一致した部分は : " + target);
                       
            if(kMap.containsValue(target)) {
            
            	countSt = getKeyByValue(kMap,target);
            	 
            }else {
            	  int count = kMap.size();
            	  count++;
            	  countSt = (new Integer(count)).toString();
            }

            kMap.put(countSt,target);
            String line2 = line.replaceAll(target, countSt);
            //String line2 = m.(countSt);
            //System.out.println(line2);
            line = line2;
        }  
        System.out.println("result = " + line);  
        return line;        
	}

	

	/**
	 * 
	 * @param map
	 * @param value
	 * @return
	 */
	public <K, V> K getKeyByValue(Map<K, V> map, V value) {
	    for (Map.Entry<K, V> entry : map.entrySet()) {
	            if (value.equals(entry.getValue())) {
	            return entry.getKey();
	        }
	    }
	    return null;
	}
	
	/**
	 * 
	 */
	public void showAllMap(){
        kMap.forEach((key,value) -> {
            System.out.print(key+":");
            System.out.println(value);
            });
	}
}
