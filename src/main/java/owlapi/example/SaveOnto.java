package owlapi.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class SaveOnto {

	public static void main(String[] args) throws OWLOntologyCreationException {
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		File fileout = new File("/Users/keo_darawong/Documents/Ontology_data/pizza.func.owl");
		IRI pizzaontology = IRI.create("http://protege.stanford.edu/ontologies/pizza/pizza.owl");
		OWLOntology o = man.loadOntology(pizzaontology);
		try {
			man.saveOntology(o, new FunctionalSyntaxDocumentFormat(),new FileOutputStream(fileout));
		} catch (OWLOntologyStorageException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
