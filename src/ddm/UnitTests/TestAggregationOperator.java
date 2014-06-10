package ddm.UnitTests;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ddm.decision.AggregationOperator;

/**
 * 
 * @author jordi Corbilla
 * Test that the OWA operator works fine
 */
public class TestAggregationOperator {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testOwa() {
		AggregationOperator ao = new AggregationOperator();
		Vector<Double> W = new Vector<Double>(3);
		Vector<Double> A = new Vector<Double>(3);
		W.addElement(0.4175);
		W.addElement(0.2216);
		W.addElement(0.3608);

		A.addElement(2.0);
		A.addElement(1.0);
		A.addElement(1.0);

		double result = ao.owa(W, A);
		System.out.println("Testing OWA " + result);
		assert (result == 1.4174);
	}

}
