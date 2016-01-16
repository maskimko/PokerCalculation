/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.pp.msk.poker;

import ua.pp.msk.poker.deck.SuitSet;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author maskimko
 */
public class SuitSetTest {
    
    public SuitSetTest() {
    }
    
    

   

   

    /**
     * Test of getPostfix method, of class SuitSet.
     */
    @Test
    public void testGetNumber() {
        System.out.println("getNumber");
        assertEquals(SuitSet.ACE.getNumber(), 1);
        assertEquals(SuitSet.FIVE.getNumber(), 5);
        assertEquals(SuitSet.TEN.getNumber(), 0xa);
        assertEquals(SuitSet.JACK.getNumber(), 0xb);
        assertEquals(SuitSet.QUEEN.getNumber(), 0xd);
        assertEquals(SuitSet.KING.getNumber(), 0xe);
    }
    
}
