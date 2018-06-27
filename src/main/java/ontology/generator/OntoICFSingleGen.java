package ontology.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

import util.FileIO;

public class OntoICFSingleGen {


	List<String> bsICF = new ArrayList<String>(); 
	List<String> bfICF = new ArrayList<String>(); 
	//List<String> adlICF = new ArrayList<String>();
	List<String> all = new ArrayList<String>();
	OWLOntologyManager man = OWLManager.createOWLOntologyManager();
	String path = "/Users/keo_darawong/Documents/Ontology_data/ICF/test_data/used_data/";
	IRI IOR = IRI.create("http://icf-ext.ot.knowledge");
	IRI IOR_DUL = IRI.create("http://www.ontologydesignpatterns.org/ont/dul/DUL.owl");
	
	/**
	 * @deprecated
	 */
	public void readAllData() {
		
		
		bfICF = FileIO.readFile(path+"BF-ICF1.txt");
	}
	
	public void creatBFOnto() {
		OWLOntology o = null;
		//IRI IOR = null;
		bfICF = FileIO.readFile(path+"BF-ICF1.txt");
		
		try {
			o = man.createOntology(IOR);
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//bfICF.forEach(line -> creatClass(o, line));
		for(String line:bfICF) {
			creatClassBF(o,line);
		}
		
		 File fileout = new File(path+"BF-ICF1.owl");
	      
			try {
				man.saveOntology(o, new FileOutputStream(fileout));
			} catch (OWLOntologyStorageException | FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	public void creatBSOnto() {
		OWLOntology o = null;
		//IRI IOR = null;
		bsICF = FileIO.readFile(path+"BodyStructure-ICF-mapping.txt");
		
		try {
			o = man.createOntology(IOR);
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for(String line:bsICF) {
			creatClassBS(o,line);
		}
		
		 File fileout = new File(path+"BS-ICF1.owl");
	      
			try {
				man.saveOntology(o, new FileOutputStream(fileout));
			} catch (OWLOntologyStorageException | FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void creatADLIADLOnto() {
		OWLOntology o = null;
		String fileName = path + "ADL-IADL-ICF1";
		bsICF = FileIO.readFile(fileName+ ".txt");
		
		try {
			o = man.createOntology(IOR);
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for(String line:bsICF) {
			creatClassADLIADL(o,line);
		}
		
		 File fileout = new File(fileName+ ".owl");
	      
			try {
				man.saveOntology(o, new FileOutputStream(fileout));
			} catch (OWLOntologyStorageException | FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void creatADLIADLOntHierarchy() throws OWLOntologyCreationException {
		final OWLOntology o = man.createOntology(IOR);
		String fileName = path + "ADL-IADL-subclass.txt";
		bsICF = FileIO.readFile(fileName);
		

		for(String line:bsICF) {
			List<OWLClassAxiom> axiomList = creatClassADLIADLHierarchy(o,line);
			axiomList.forEach(a -> o.add(a));
		}
		
		 File fileout = new File(fileName+ ".owl");
	      
			try {
				man.saveOntology(o, new FileOutputStream(fileout));
			} catch (OWLOntologyStorageException | FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	/**
	 * 
	 * @param data
	 */
	public void creatClassBF(OWLOntology o,String line) {

		String[] data = line.split("\t"); 
		System.out.println(data);
	
		
		OWLDataFactory df = o.getOWLOntologyManager().getOWLDataFactory();
		OWLClass A = df.getOWLClass(IOR +"#"+ data[0].trim());
		OWLClass B = df.getOWLClass(IOR + "#"+ data[2].trim());
		
		//OWLObjectProperty R = df.getOWLObjectProperty(IOR + "#R");
		OWLObjectProperty C = df.getOWLObjectProperty(IOR + "#"+ data[1]);
		/*
		OWLSubClassOfAxiom ax = df.getOWLSubClassOfAxiom(
				df.getOWLObjectSomeValuesFrom(R, A),
				df.getOWLObjectSomeValuesFrom(S, B));*/
		OWLSubClassOfAxiom ax = df.getOWLSubClassOfAxiom(A,B);
		o.add(ax);

		o.logicalAxioms().forEach(System.out::println);
	}
	
	/**
	 * 
	 * @param data
	 */
	public void creatClassBS(OWLOntology o,String line) {

		String[] data = line.split("\t"); 
		System.out.println(data);
		
		OWLDataFactory df = o.getOWLOntologyManager().getOWLDataFactory();
		OWLClass A = df.getOWLClass(IOR +"#"+ data[0]);
		OWLClass B = df.getOWLClass(IOR + "#"+ data[2]);
		
		//OWLObjectProperty R = df.getOWLObjectProperty(IOR + "#R");
		//OWLObjectProperty C = df.getOWLObjectProperty(IOR + "#"+ data[1]);
		/*
		OWLSubClassOfAxiom ax = df.getOWLSubClassOfAxiom(
				df.getOWLObjectSomeValuesFrom(R, A),
				df.getOWLObjectSomeValuesFrom(S, B));*/
		OWLEquivalentClassesAxiom ax = df.getOWLEquivalentClassesAxiom(A, B);
		o.add(ax);

		//df.
		o.logicalAxioms().forEach(System.out::println);
	}
	
	
	public void creatClassADLIADL(OWLOntology o,String line) {

		String[] data = line.split("\t"); 
		System.out.println(data);		
		OWLDataFactory df = o.getOWLOntologyManager().getOWLDataFactory();
		OWLClass A = df.getOWLClass(IOR +"#"+ data[0].trim());
		OWLClass B = df.getOWLClass(IOR + "#"+ data[2].trim());
		
		String property = data[1].trim();
		OWLClassAxiom ax = null;
		
		if(property.startsWith("owl:equivalentClass")) {
			ax = df.getOWLEquivalentClassesAxiom(A,B);
		}
		else if(property.startsWith("rdfs:subClassOf")) {
			ax = df.getOWLSubClassOfAxiom(A,B);
		}		
		o.add(ax);
		o.logicalAxioms().forEach(System.out::println);
	}

	public List<OWLClassAxiom> creatClassADLIADLHierarchy(OWLOntology o,String line) {

		String[] data = line.split(":"); 
		//System.out.println(data);		
		OWLDataFactory df = o.getOWLOntologyManager().getOWLDataFactory();
		OWLObjectProperty hasPart = df.getOWLObjectProperty(IOR_DUL + "#hasPart");
		
		List<String> children = Arrays.asList(data[1].split(","));
		//String property = data[1].trim();
		String parent = data[2];
					
		OWLClass parentClass = df.getOWLClass(IOR +"#"+ parent);
		List<OWLClass> childOWLList = new ArrayList<OWLClass>();
		
		children.forEach(p-> childOWLList.add(df.getOWLClass(IOR +"#"+ p.trim())));
		
		List<OWLObjectSomeValuesFrom> owlOSVList = new ArrayList<OWLObjectSomeValuesFrom>();
		childOWLList.forEach(c -> owlOSVList.add(df.getOWLObjectSomeValuesFrom(hasPart, c)));
		
		//owlOSVList.forEach(p -> System.out.println(p.toString()));
		
		List<OWLClassAxiom> axiomList = new ArrayList<OWLClassAxiom>();
		
		owlOSVList.forEach(p -> axiomList.add(df.getOWLSubClassOfAxiom(parentClass,p)));
		
		//axiomList.forEach(a -> o.add(a));
		
		//o.logicalAxioms().forEach(System.out::println);
		
		//df.getOWLFunctionalObjectPropertyAxiom(property, annotations)
		
		//o.add(ax);
		//o.logicalAxioms().forEach(System.out::println);
		
		return axiomList;
	}
	
	public static void main(String[] args) {
		
		OntoICFSingleGen oICF = new OntoICFSingleGen();
		//oICF.creatBFOnto();
		//oICF.creatBSOnto();
		//oICF.creatADLIADLOnto();
		try {
			oICF.creatADLIADLOntHierarchy();
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
