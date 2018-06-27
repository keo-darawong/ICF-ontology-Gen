package owlapi.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.util.OWLObjectTransformer;

public class ChangeAxiom {

	public static void main(String[] args) throws OWLOntologyCreationException {
		
		IRI IOR = IRI.create("http://owl.api.tutorial");
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		OWLOntology o = man.createOntology(IOR);
		OWLDataFactory df = o.getOWLOntologyManager().getOWLDataFactory();
		
		final Map<OWLClassExpression, OWLClassExpression> replacements = new HashMap<>();

		OWLClass A = df.getOWLClass(IOR + "#A");
		OWLClass B = df.getOWLClass(IOR + "#B");
		OWLClass X = df.getOWLClass(IOR + "#X");
		OWLObjectProperty R = df.getOWLObjectProperty(IOR + "#R");
		OWLObjectProperty S = df.getOWLObjectProperty(IOR + "#S");
		OWLSubClassOfAxiom ax = df.getOWLSubClassOfAxiom(
		df.getOWLObjectSomeValuesFrom(R, A),
		df.getOWLObjectSomeValuesFrom(S, B));
		o.add(ax);

		o.logicalAxioms().forEach(System.out::println);
		File fileout1 = new File("/Users/keo_darawong/Documents/Ontology_data/ChangeAxiom1.func.owl");
		
		try {
			man.saveOntology(o, new FunctionalSyntaxDocumentFormat(),new FileOutputStream(fileout1));
		} catch (OWLOntologyStorageException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		replacements.put(df.getOWLObjectSomeValuesFrom(R, A), X);
		
		File fileout = new File("/Users/keo_darawong/Documents/Ontology_data/ChangeAxiom2.func.owl");
		
		OWLObjectTransformer<OWLClassExpression> replacer = 
				new OWLObjectTransformer<>((x) -> true, (input) -> { 
					OWLClassExpression l = replacements.get(input);
					if (l == null) {
							return input;
					}
					return l;
				}, df, OWLClassExpression.class);

			List<OWLOntologyChange> results = replacer.change(o);
			o.applyChanges(results);
			
		try {
			man.saveOntology(o, new FunctionalSyntaxDocumentFormat(),new FileOutputStream(fileout));
		} catch (OWLOntologyStorageException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
