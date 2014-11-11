package jeune_Trie.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.framework.Test;
import junit.framework.TestSuite;

@RunWith(Suite.class)
@SuiteClasses({
	ActiveRegionTest.class,
	GameTypeTest.class,
	MoveTypeTest.class,
	PMControllerTest.class,
	PMModelTest.class,
	PositionTest.class,
	TrieNodeTest.class,
	UseTypeTest.class,
	WordDictionaryTest.class
})
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for jeune_Trie.test");
		//$JUnit-BEGIN$
		
		//$JUnit-END$
		return suite;
	}

}
