package org.d2rq.csv;

import java.io.StringReader;
import java.util.Collection;
import java.util.HashSet;

import org.d2rq.D2RQTestUtil;
import org.d2rq.csv.TranslationTableParser;
import org.d2rq.lang.TranslationTable.Translation;

import junit.framework.TestCase;

/**
 * Unit tests for {@link TranslationTableParser}
 *
 * @author Richard Cyganiak (richard@cyganiak.de)
 */
public class TranslationTableParserTest extends TestCase {
	private Collection<Translation> simpleTranslations;
	
	public void setUp() {
		this.simpleTranslations = new HashSet<Translation>();
		this.simpleTranslations.add(new Translation("db1", "rdf1"));
		this.simpleTranslations.add(new Translation("db2", "rdf2"));
	}
	
	public void testEmpty() {
		Collection<Translation> translations = new TranslationTableParser(
				new StringReader("")).parseTranslations();
		assertTrue(translations.isEmpty());
	}
	
	public void testSimple() {
		String csv = "key,value";
		Collection<Translation> translations = new TranslationTableParser(
				new StringReader(csv)).parseTranslations();
		assertEquals(1, translations.size());
		Translation t = (Translation) translations.iterator().next();
		assertEquals("key", t.dbValue());
		assertEquals("value", t.rdfValue());
	}
	
	public void testTwoRows() {
		String csv = "db1,rdf1\ndb2,rdf2";
		Collection<Translation> translations = new TranslationTableParser(
				new StringReader(csv)).parseTranslations();
		assertEquals(2, translations.size());
		assertEquals(this.simpleTranslations, new HashSet<Translation>(translations));
	}
	
	public void testParseFromFile() {
		Collection<Translation> translations = new TranslationTableParser(
				D2RQTestUtil.getResourceURL("d2rq-reader/translationtable.csv")).parseTranslations();
		assertEquals(this.simpleTranslations, new HashSet<Translation>(translations));
	}
	
	public void testParseFromFileWithProtocol() {
		Collection<Translation> translations = new TranslationTableParser(
				D2RQTestUtil.getResourceURL("d2rq-reader/translationtable.csv")).parseTranslations();
		assertEquals(this.simpleTranslations, new HashSet<Translation>(translations));
	}
}
