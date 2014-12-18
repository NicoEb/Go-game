package go.core;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StoneChainTest extends TestCase {

    private Goban goban;
    private Player one, two;
    private StoneChain chain1,chain2;

    @Before
    public void setUp() throws Exception {
        goban = new Goban(9,9);
        one = new Player(1);
        two = new Player(2);

        chain1 = new StoneChain(goban.getIntersection(2,2),one);
            chain1.getStones().add(goban.getIntersection(2,3));
            for(Intersection stone : chain1.getStones()) stone.setStoneChain(chain1);
            chain1.getLiberties().add(goban.getIntersection(1,2));
            chain1.getLiberties().add(goban.getIntersection(1,3));
            chain1.getLiberties().add(goban.getIntersection(2,1));
            chain1.getLiberties().add(goban.getIntersection(2,4));


        chain2 = new StoneChain(goban.getIntersection(3,2),two);
            chain2.getStones().add(goban.getIntersection(3,3));
            chain2.getStones().add(goban.getIntersection(3,4));
            for(Intersection stone : chain2.getStones()) stone.setStoneChain(chain2);
            chain2.getLiberties().add(goban.getIntersection(4,2));
            chain2.getLiberties().add(goban.getIntersection(4,3));
            chain2.getLiberties().add(goban.getIntersection(4,4));
            chain2.getLiberties().add(goban.getIntersection(3,1));
            chain2.getLiberties().add(goban.getIntersection(3,5));
            chain2.getLiberties().add(goban.getIntersection(2,4));
    }

    @Test
    public void testAdd() throws Exception {
        assertFalse(chain1.getStones().contains(goban.getIntersection(2, 4)));
        assertTrue(chain1.getLiberties().contains(goban.getIntersection(2, 4)));
        chain1.add(new StoneChain(goban.getIntersection(2,4),one),goban.getIntersection(2,4));
        assertTrue(chain1.getStones().contains(goban.getIntersection(2,4)));
        assertFalse(chain1.getLiberties().contains(goban.getIntersection(2,4)));
    }

    @Test
    public void testRemoveLiberty() throws Exception {
        assertTrue(chain1.getLiberties().contains(goban.getIntersection(2,4)));
        chain1.removeLiberty(goban.getIntersection(2,4));
        assertFalse(chain1.getLiberties().contains(goban.getIntersection(2,4)));
    }

    @Test
    public void testDie() throws Exception {

        Set<Intersection> positions = chain1.getStones();

        for (Intersection stone : positions ) {
            assertFalse(chain2.getLiberties().contains(stone));
            assertTrue(stone.getStoneChain()==chain1);
        }

        chain1.die();

        for (Intersection stone : positions ) {
            assertTrue(chain2.getLiberties().contains(stone));
            assertFalse(stone.getStoneChain()==chain1);
        }
    }
}
