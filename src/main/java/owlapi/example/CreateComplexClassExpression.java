package owlapi.example;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

public class CreateComplexClassExpression {
	
	public static void main(String[] args) throws OWLOntologyCreationException {
		
		IRI IOR = IRI.create("http://owl.api.tutorial");
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology o = man.createOntology(IOR);
		OWLDataFactory df = o.getOWLOntologyManager().getOWLDataFactory();
		OWLClass A = df.getOWLClass(IOR + "#A");
		OWLClass B = df.getOWLClass(IOR + "#B");
		OWLClass X = df.getOWLClass(IOR + "#X");
		
		OWLObjectProperty R = df.getOWLObjectProperty(IOR + "#R");
		OWLObjectProperty S = df.getOWLObjectProperty(IOR + "#S");
		/*
		OWLSubClassOfAxiom ax = df.getOWLSubClassOfAxiom(
				df.getOWLObjectSomeValuesFrom(R, A),
				df.getOWLObjectSomeValuesFrom(S, B));
		
		o.add(ax);
		*/
		OWLSubClassOfAxiom ax = df.getOWLSubClassOfAxiom(				
		df.getOWLObjectSomeValuesFrom(R,df.getOWLObjectIntersectionOf(A,B)), df.getOWLObjectSomeValuesFrom(S, B));
		o.add(ax);
		o.logicalAxioms().forEach(System.out::println);
	}
}
