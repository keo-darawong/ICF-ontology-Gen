package owlapi.example;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

import java.io.File;

import org.semanticweb.HermiT.ReasonerFactory;;

public class InferenceReasoning {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology o;
		OWLDataFactory df = man.getOWLDataFactory();
		File file = new File("/Users/keo_darawong/Documents/Ontology_data/pizza.owl");
		//IRI pizzaontology = IRI.create("http://protege.stanford.edu/ontologies/pizza/pizza.owl");
		
		try {
			//o = man.loadOntology(pizzaontology);
			o = man.loadOntologyFromOntologyDocument(file);
			
			OWLReasonerFactory rf = new ReasonerFactory();
			OWLReasoner r = rf.createReasoner(o);
			r.precomputeInferences(InferenceType.CLASS_HIERARCHY);
			r.getSubClasses(df.getOWLClass("http://www.co-ode.org/ontologies/pizza/pizza.owl#PrinceCarlo"), false).forEach(System.out::println);;
		
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}

	}

}
