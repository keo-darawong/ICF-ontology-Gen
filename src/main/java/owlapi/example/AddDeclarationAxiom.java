package owlapi.example;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

public class AddDeclarationAxiom {

	public static void main(String[] args) {
		IRI IOR = IRI.create("http://owl.api.tutorial");
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology o = null;
		try {
			o = man.createOntology(IOR);
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		OWLDataFactory df = o.getOWLOntologyManager().getOWLDataFactory();
		OWLClass person = df.getOWLClass(IOR+"#Person");
		OWLDeclarationAxiom da = df.getOWLDeclarationAxiom(person);
		OWLClass woman = df.getOWLClass(IOR+"#Woman");
		OWLSubClassOfAxiom w_sub_p = df.getOWLSubClassOfAxiom(woman, person);
		o.add(da);
		o.add(w_sub_p);
		System.out.println(o);
	}
}
