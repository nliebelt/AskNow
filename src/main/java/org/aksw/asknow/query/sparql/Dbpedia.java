package org.aksw.asknow.query.sparql;

import java.util.HashSet;
import java.util.Set;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import lombok.extern.slf4j.Slf4j;

@Slf4j public class Dbpedia
{
	private Dbpedia() {}

	public static String tag(String uri)
	{
		String parts[] = uri.split("/");

		switch(parts[parts.length-2])
		{
			case "ontology": return "dbo";
			case "property": return "dbp";
			default: throw new IllegalArgumentException("uri <"+uri+"> neither ontology nor property");
		}
	}

	static final String sparqlHeader = "PREFIX dbo: <http://dbpedia.org/ontology/>" + "PREFIX yago: <http://dbpedia.org/class/yago/> "
			+ "PREFIX dbp: <http://dbpedia.org/property/> " + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
			+ "PREFIX res: <http://dbpedia.org/resource/> ";

	private static final String endpoint = "http://live.dbpedia.org/sparql";

	public static ResultSet select(String query)
	{
		log.debug("select query: "+query);
		try(QueryEngineHTTP qe = new QueryEngineHTTP(endpoint,sparqlHeader + query))
		{return ResultSetFactory.copyResults(qe.execSelect());}
	}

	public static boolean ask(String query)
	{
		log.debug("ask query: "+query);
		try(QueryEngineHTTP qe = new QueryEngineHTTP(endpoint,sparqlHeader + query))
		{return qe.execAsk();}
	}

	/** @param rs needs to be zero (one value) or one-dimensional */
	public static Set<RDFNode> nodeSet(ResultSet rs)
	{
		String var = rs.getResultVars().get(0);
		Set<RDFNode> nodes = new HashSet<>();
		while(rs.hasNext())
		{
			nodes.add(rs.next().get(var));
		}
		return nodes;
	}

}