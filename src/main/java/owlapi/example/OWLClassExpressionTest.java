package owlapi.example;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.vocab.OWLFacet;

/**
 * Lecturer
	and (hasResearch some (relatedTo some Semantic_Web))
	and (teaches at-least 3 Course)
	and (hasTitle value "Assist.Prof.")
 * @author keo_darawong
 *
 */

public class OWLClassExpressionTest {

	static IRI IOR = IRI.create("http://owl.api.tutorial/");

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//runTest();
		try {
			//testAddSomeRestriction();
			testDatatypeRestriction();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	static public OWLOntologyManager create() {
		return OWLManager.createOWLOntologyManager();
	}
	
	static public void runTest() {
				
		OWLOntologyManager m = create();
		OWLOntology o = null;
		
		try {
			o = m.createOntology(IOR);
			
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		OWLDataFactory df = o.getOWLOntologyManager().getOWLDataFactory();
		OWLClass lecturer = df.getOWLClass(IOR+"#Lecturer");
        OWLObjectProperty hasResearch = df.getOWLObjectProperty(IOR+"#hasResearch");
        OWLObjectProperty isRelatedToResearch = df.getOWLObjectProperty(IOR+"#isRelatedToResearch");
        OWLObjectProperty teaches = df.getOWLObjectProperty(IOR+"#teaches");
        OWLNamedIndividual sWeb = df.getOWLNamedIndividual(IOR+"#Semantic_Web");
        
        OWLClass course = df.getOWLClass(IOR+"#Course");
        OWLClass researchArea = df.getOWLClass(IOR+"#ResearchArea");
        OWLDataProperty hasTitle = df.getOWLDataProperty(IOR+"#hasTitle");
        OWLDatatype dt = df.getRDFPlainLiteral();
        OWLLiteral asstprof = df.getOWLLiteral(IOR+"#Asst. Prof. Dr.",dt);

        OWLClassExpression relateToSomeResearchArea = df.getOWLObjectSomeValuesFrom(isRelatedToResearch, researchArea);
        OWLClassExpression hasResearchRelateToSomeResearchArea = df.getOWLObjectSomeValuesFrom(hasResearch,relateToSomeResearchArea);
        OWLClassExpression teachesAtLeastThreeCourse = df.getOWLObjectMinCardinality(3, teaches, course);
        OWLClassExpression hasTitleAsstProf = df.getOWLDataHasValue(hasTitle, asstprof);

        Set<OWLClassExpression> s1 = new HashSet<OWLClassExpression>();
        s1.add(lecturer);
        s1.add(hasResearchRelateToSomeResearchArea);
        s1.add(teachesAtLeastThreeCourse);
        s1.add(hasTitleAsstProf);

        OWLObjectIntersectionOf iof = df.getOWLObjectIntersectionOf(s1);

        //System.out.println(iof.getNestedClassExpressions());
        System.out.println(iof);
       // o.add((OWLAxiom) iof.asOWLClass());

		//o.logicalAxioms().forEach(System.out::println);
	}
	/**
	 *  Lecturer
	and (hasResearch some (relatedTo some Semantic_Web))
	and (teaches at-least 3 Course)
	and (hasTitle value "Assist.Prof.")
	 */

	static public void testAddSomeRestriction() throws Exception {
		IRI example_iri = IOR;
        OWLOntologyManager m = create();
        OWLOntology o = m.createOntology(example_iri);
        OWLDataFactory df = o.getOWLOntologyManager().getOWLDataFactory();
        // all Heads have parts that are noses (at least one)
        // We do this by creating an existential (some) restriction
        OWLObjectProperty hasPart = df.getOWLObjectProperty(IRI.create(example_iri
                + "#hasPart"));
        OWLClass nose = df.getOWLClass(IRI.create(example_iri + "#Nose"));
        // Now let's describe the class of individuals that have at
        // least one part that is a kind of nose
        OWLClassExpression hasPartSomeNose = df.getOWLObjectSomeValuesFrom(hasPart, nose);
        OWLClass head = df.getOWLClass(IRI.create(example_iri + "#Head"));
        // Head subclass of our restriction
        OWLSubClassOfAxiom ax = df.getOWLSubClassOfAxiom(head, hasPartSomeNose);
        // Add the axiom to our ontology
        AddAxiom addAx = new AddAxiom(o, ax);
        System.out.println(addAx);
        m.applyChange(addAx);
    }
	
    static public void testDatatypeRestriction() throws Exception {
    
    		IRI example_iri = IOR;
        OWLOntologyManager m = create();
        OWLOntology o = m.createOntology(example_iri);
        OWLDataFactory df = o.getOWLOntologyManager().getOWLDataFactory();
        // Adults have an age greater than 18.
        OWLDataProperty hasAge = df
                .getOWLDataProperty(IRI.create(example_iri + "hasAge"));
        // Create the restricted data range by applying the facet restriction
        // with a value of 18 to int
        OWLDataRange greaterThan18 = df.getOWLDatatypeRestriction(
                df.getIntegerOWLDatatype(), OWLFacet.MIN_INCLUSIVE, df.getOWLLiteral(18));
        // Now we can use this in our datatype restriction on hasAge
        OWLClassExpression adultDefinition = df.getOWLDataSomeValuesFrom(hasAge,
                greaterThan18);
        OWLClass adult = df.getOWLClass(IRI.create(example_iri + "#Adult"));
        OWLSubClassOfAxiom ax = df.getOWLSubClassOfAxiom(adult, adultDefinition);
        m.applyChange(new AddAxiom(o, ax));
        System.out.println(o);
    }
}
