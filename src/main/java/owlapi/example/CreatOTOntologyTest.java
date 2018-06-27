package owlapi.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
 
public class CreatOTOntologyTest {
	
	public static void main(String args[]) {
		
		CreatOTOntologyTest cT = new CreatOTOntologyTest();
	    IRI IOR = IRI.create("http://icf-ext.ot.knowledge");
		
	    OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology o = null;
		try {
			o = man.createOntology(IOR);
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    try {
	      File f = new File("/Users/keo_darawong/pro/python_pro/test/data/BS-ADL-IADL.txt");
	      BufferedReader br = new BufferedReader(new FileReader(f));
	 
	      String line;
	      // 1行ずつCSVファイルを読み込む
	      while ((line = br.readLine()) != null) {
	        String[] data = line.split("	", 0); // 行をカンマ区切りで配列に変換

	        for (String elem : data) {
	          System.out.println(elem);
	          cT.creatClassTest2(IOR, o, data);
	        }
	      }
	      br.close();

	      File fileout = new File("/Users/keo_darawong/pro/python_pro/test/data/BS-ADL-IADL2.func.owl");
	      
			try {
				man.saveOntology(o, new FunctionalSyntaxDocumentFormat(),new FileOutputStream(fileout));
			} catch (OWLOntologyStorageException | FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	    } catch (IOException e) {
	      System.out.println(e);
	    }
	  }
	
	/**
	 * 
	 * @param data
	 */
	public void creatClassTest(IRI IOR, OWLOntology o,String[] data) {

		OWLDataFactory df = o.getOWLOntologyManager().getOWLDataFactory();
		OWLClass A = df.getOWLClass(IOR +"#"+ data[0]);
		OWLClass B = df.getOWLClass(IOR + "#"+ data[2]);
		
		OWLObjectProperty R = df.getOWLObjectProperty(IOR + "#R");
		OWLObjectProperty S = df.getOWLObjectProperty(IOR + "#"+ data[1]);
		OWLSubClassOfAxiom ax = df.getOWLSubClassOfAxiom(
				df.getOWLObjectSomeValuesFrom(R, A),
				df.getOWLObjectSomeValuesFrom(S, B));
		o.add(ax);

		o.logicalAxioms().forEach(System.out::println);
	}

	/**
	 * 
	 * @param data
	 */
	public void creatClassTest2(IRI IOR, OWLOntology o,String[] data) {

		OWLDataFactory df = o.getOWLOntologyManager().getOWLDataFactory();
		OWLClass A = df.getOWLClass(IOR +"#A-"+ data[0]);
		OWLClass B = df.getOWLClass(IOR + "#B");
		
		OWLObjectProperty R = df.getOWLObjectProperty(IOR + "#"+ data[1]);
		OWLObjectProperty S = df.getOWLObjectProperty(IOR + "#"+ data[2]);
		OWLSubClassOfAxiom ax = df.getOWLSubClassOfAxiom(
				df.getOWLObjectSomeValuesFrom(R, A),
				df.getOWLObjectSomeValuesFrom(S, B));
		o.add(ax);

		o.logicalAxioms().forEach(System.out::println);
	}
	
	public List<String> separateA(String line){
		List<String>  ar = new ArrayList<String>();
		ar = Arrays.asList(line.split(","));
		
		return ar;
	}
}
