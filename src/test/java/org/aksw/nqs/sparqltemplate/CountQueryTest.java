package org.aksw.nqs.sparqltemplate;

import java.util.Set;
import org.aksw.asknow.Parser;
import org.aksw.asknow.Template;
import org.aksw.asknow.sparqltemplate.CountQuery;
import org.apache.jena.rdf.model.RDFNode;
import org.junit.Test;

public class CountQueryTest
{

	@Test public void test()
	{
		Template t = Parser.parse().get(43); 
		System.out.println(t);
		Set<RDFNode> result = CountQuery.INSTANCE.execute(t);
		System.out.println(result);
	}

}