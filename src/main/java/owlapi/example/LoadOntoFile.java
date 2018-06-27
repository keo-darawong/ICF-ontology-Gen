package owlapi.example;

import java.io.File;
import java.util.Set;
import java.util.stream.Stream;

import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.semanticweb.owlapi.search.Searcher;

public class LoadOntoFile {

	public static void main(String[] args) {
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		StringBuffer path = new StringBuffer("/Users/keo_darawong/Documents/Ontology_data/ICF/test_data/used_data/");
		File file = new File( path.append("BF-ICF.owl").toString());
		//File file = new File("/Users/keo_darawong/Documents/Ontology_data/pizza.owl");
		OWLOntology o = null;
		try {
			o = man.loadOntologyFromOntologyDocument(file);
			doClass(o);
			
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(o);
	}
	
	static public void doClass(OWLOntology o) {
		IRI IOR = IRI.create("http://icf-ext.ot.knowledge");
		OWLDataFactory df = o.getOWLOntologyManager().getOWLDataFactory();
		OWLClass A = df.getOWLClass(IOR + "#伸展運動制限");
		
		OWLReasonerFactory rf = new ReasonerFactory();
		OWLReasoner r = rf.createReasoner(o);
		//r.getEquivalentClasses(classExpression);
		
		//EntitySearcher.getAnnotations(e, ontologies, annotationProperty);
		Stream<OWLClassExpression> exclass = EntitySearcher.getSuperClasses(A, o);
		//OWLIndividual freshIndividual=df.getOWLAnonymousIndividual("fresh-individual");
		
		//Stream<OWLIndividual> exclass = EntitySearcher.ge(A, o);
		exclass.forEach(p->System.out.println(p.toString()));
		
	}
}
