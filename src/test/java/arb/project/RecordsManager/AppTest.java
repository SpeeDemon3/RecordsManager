package arb.project.RecordsManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */

	private static final String JAVA = "Java";
	private static final String EINSTEIN = "Einstein";
	
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
    
    /**
     * Test con un nombre largo 
     */
    @Test
    void validateLargeNameTest() {
    	try {
    		App.validateName(EINSTEIN);
        	assertTrue(true);
        } catch(PlayerNameTooShortException pntse) {
        	fail(pntse.getMessage());
        }
    	
    }
    
    /**
     * Test con un nombre corto
     */
    @Test
    void validateShortNameTest() {
    	try {
    		App.validateName(JAVA);
    		fail("Deberia haber fallado, " + JAVA + " es corto.");
    	} catch(PlayerNameTooShortException e) {
    		assertTrue(true);
    		assertTrue(e.getMessage().contains(JAVA));
    	}
    }
    
    /*
     * Test con nombre casi corto
     */
    @Test
    void validateAlmostNameTest() {
    	String name = "Venom";
    	try {
    		App.validateName(name);
    		fail("Deberia haber fallado, " + name + " es corto");
    	} catch(PlayerNameTooShortException e2) {
    		assertTrue(true);
    		assertTrue(e2.getMessage().contains(name));
    	}
    }
    
    /**
     * Test con nombre exacto
     */
    @Test
    void validateExactNameTest() {
    	String name = "Python";
    	try {
    		App.validateName(name);
    		assertTrue(true);
    	} catch(PlayerNameTooShortException e3) {
    		fail(e3.getMessage());
    	}
    }
    
    /**
     * Test con puntuacion valida
     */
    @Test
    void validateScore1000Test() {
    	try {
        	App.validateScore(EINSTEIN, 1000);
        	assertTrue(true);
    	} catch(ScoreTooLowException s) {
    		fail(s.getMessage());
    	}
    }
    
    /**
     * Test con puntuacion menor a la valida
     */
    @Test
    void validateScore999Test() {
    	try {
        	App.validateScore(JAVA, 333);
        	fail("Deberia haber fallado, 333 son pocos puntos.");
    	} catch (ScoreTooLowException s2) {
    		assertTrue(s2.getMessage().contains(JAVA));
    		assertTrue(s2.getMessage().contains("333"));
    	}
    }
    
    /**
     * Test para generar un nuevo nombre largo
     */
    @Test
    void generateNewLargeNameTest() {
    	String newName = App.generateNewName(EINSTEIN);
    	assertEquals(EINSTEIN, newName);
    }
    
    /**
     * Test para generar un nuevo nombre corto
     */
    @Test
    void genarateNewShortNameTest() {
    	String newName = App.generateNewName(JAVA);
    	assertNotEquals(JAVA, newName);
    	assertTrue(newName.contains(JAVA));
    	assertTrue(newName.startsWith(JAVA));
    	String number = newName.substring(JAVA.length());
    	assertTrue(Integer.valueOf(number) > 0);
    	assertEquals(6, newName.length());
    }
    
    /**
     * Test para generar un nuevo nombre vacio
     */
    @Test
    void generateNewEmptyNameTest() {
    	String newName = App.generateNewName("");
    	assertNotEquals("", newName);
    	assertTrue(Integer.valueOf(newName) > 0);
    	assertEquals(6, newName.length());
    }
}
