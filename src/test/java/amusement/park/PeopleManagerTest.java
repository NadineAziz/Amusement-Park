/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amusement.park;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author User
 */
public class PeopleManagerTest {
    @Test
    public void test_getNumOfThieves() {
        PeopleManagerImpl peopleManager =  new PeopleManagerImpl();
        
        assertTrue(peopleManager.getNumberOfThieves()==1);
        assertEquals(1, (int) peopleManager.getNumberOfThieves());
    }

    @Test
   public void test_getNumofsecurities() {
        PeopleManagerImpl peopleManager =  new PeopleManagerImpl();
        
        assertTrue(peopleManager.getNumofsecurities()==2);
        assertEquals(2, (int) peopleManager.getNumofsecurities());
    }
     public void test_getNumberOfQuests() {
        PeopleManager peopleManager = (PeopleManager) new PeopleManagerImpl();
        
        assertTrue(peopleManager.getNumberOfQuests()==3);
        assertEquals(3, (int) peopleManager.getNumberOfQuests());
    }
       public void test_getNumofcops() {
        PeopleManager peopleManager = (PeopleManager) new PeopleManagerImpl();
        
        assertTrue(peopleManager.getNumofcops()==4);
        assertEquals(4, (int) peopleManager.getNumofcops());
    }
}
