package owlapi.example;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public interface OWLAPIFirst {
	public static void main(String[] args) {
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		System.out.println(man.getOntologies().size());
	}
}
