package tests;

import static org.junit.Assert.*;
import model.Article;
import model.Offer;
import model.Truck;
import model.TruckState;

import org.junit.Before;
import org.junit.Test;

public class TruckTest {
	
	Truck truck;
	Article testArticle;
	Offer testOffer;
	int targetPosition;

	@Before
	public void setUp() 
	{
		truck = new Truck(0, null, 0);
		testArticle = new Article("TestArticle", 0, 1);
		testOffer = new Offer(1, 1000, testArticle, 0, 0);
		targetPosition = 1;
	}

	@Test
	public void test() {
		assertEquals(truck.getPosition(), truck.getTargetPosition());
		assertEquals(truck.getTruckState(), TruckState.IDLE);
		assertEquals(truck.getDistanceToTarget(), truck.getDistanceToTarget(0));
		truck.loadBoughtOffer(testOffer);
		assertEquals(truck.getAmount(), testOffer.getAmount());
		truck.driveTo(targetPosition);
		assertEquals(truck.getTruckState(), TruckState.DRIVING);
		assertEquals(truck.getDistanceToTarget(), 1);
		truck.update();
		assertTrue(truck.hasReachedPosition());
		assertEquals(truck.getDistanceToTarget(), 0);
		assertTrue(truck.isLoaded());
	}

}
