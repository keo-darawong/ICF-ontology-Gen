package owlapi.example;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;


public class LoadFromIRI {

	public static void main(String[] args) {
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		IRI pizzaontology = IRI.create("http://protege.stanford.edu/ontologies/pizza/pizza.owl");
		OWLOntology o = null;
		try {
			o = man.loadOntology(pizzaontology);
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(o);
	}
}
