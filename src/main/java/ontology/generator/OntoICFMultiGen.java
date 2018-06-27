package ontology.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import util.FileIO;

public class OntoICFMultiGen {

	//List<String> bsICF = new ArrayList<String>(); 
	//List<String> bfICF = new ArrayList<String>(); 
	List<String> adlICF = new ArrayList<String>();
	List<String> all = new ArrayList<String>();
	OWLOntologyManager man = OWLManager.createOWLOntologyManager();
	static String path = "/Users/keo_darawong/Documents/Ontology_data/ICF/test_data/used_data/";
	IRI IOR = IRI.create("http://icf-ext.ot.knowledge");
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String inputFile1 = path +"BS-BF-ADL-IADL-4ontlogy.txt";
		String inputFile2 = path + "ADL-IADL-Children.txt";
		
		List<String> replaList = replaceMixList(inputFile1,inputFile2);
		
		replaList.forEach(a -> System.out.println(a));

	}
	

	static public List<String> replaceMixList(String input1, String input2 ) {
		
		List<String> allList = FileIO.readFile(input1);
		List<String> adlList = FileIO.readFile(input2);
		List<String> replaceList = new ArrayList<String>();
		
		if(allList.size() != adlList.size()) {
			System.err.print(false);
			return null;
		}
		
		//allList.forEach(p -> p.split("\t"));
		int id = 0;
		for(String st :allList ) {
			StringBuffer stb = new StringBuffer();
			String[] aa = st.split("\t");
			String adl = adlList.get(id);
			//Arrays.asList(aa).forEach(a ->System.out.println(a));
			System.out.println("adl="+adl + " #### st=" + aa[4]);
			int count =0;
			while (count <4) {
				stb.append(aa[count]).append("\t");
				count++;
			}
			stb.append(aa[3]).append("\t");
			stb.append(adl);
			id++;
			replaceList.add(stb.toString());
		}
		return replaceList;
	}

}
