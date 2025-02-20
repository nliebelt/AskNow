package org.aksw.asknow.nqs;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;

/**Returns tokens of Query Sentence.
 * Each token has a word(s) and the tag associated with it. 
 **/

@Slf4j public class QueryTokenizer {
	private ArrayList<QueryToken> tokenList;
	PosTag tagger;
	
	public QueryTokenizer(String QuestionString){
		tokenList = new ArrayList<>();
		tagger = new PosTag();
		createTokenList(QuestionString);
	}
	
	public QueryTokenizer(){
		tokenList = new ArrayList<>();
		tagger = new PosTag();
	}
	
	/*
	 * creates token list using POStagger
	 * input: Query Sentence
	 * */
	public void createTokenList(String QuestionString){
		tokenList.clear();
		String taggedSentence = tagger.getTaggedSentence(QuestionString);
		log.debug("Tagged:"+taggedSentence);
		for(String t : taggedSentence.split(" ")){
			if(t.split("_").length==2)
				tokenList.add(new QueryToken(t.split("_")[0],t.split("_")[1]));
			else
				log.error("TAG Splitting Error", taggedSentence);
		}
		replaceWDT();
	}
	
	/*
	 * replaces WDT with WP
	 * */
	private void replaceWDT() {
		for(int i=0;i<tokenList.size();i++){
			if(tokenList.get(i).tagEquals("WDT") || tokenList.get(i).tagEquals("WRB"))
				tokenList.set(i, new QueryToken(tokenList.get(i).getString(),"WP"));
		}
	}

	public ArrayList<QueryToken> getTokenList(){
		return tokenList;
	}
	
	public ArrayList<QueryToken> createAndGetTokenList(String QuestionString){
		createTokenList(QuestionString);
		return tokenList;
	}
	
	public String getTaggedString(){
		return tokenList.toString();
	}
	
	public void reset(){
		tokenList.clear();
	}
}
