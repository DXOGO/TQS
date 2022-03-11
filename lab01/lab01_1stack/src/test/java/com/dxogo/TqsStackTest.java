package com.dxogo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.*;

public class TqsStackTest {
 
    private LinkedList<String> wordsStack;
    private int last_push;
    private int entries = 10;

	@BeforeEach // Execute before each test
	public void testBeforeEach() throws Exception {
		wordsStack = new LinkedList<>();
	}

    @DisplayName("A stack is empty on construction")
    @Test
    void isEmpty(){ 
        assertTrue(wordsStack.isEmpty()); 
        
        wordsStack.push("Test Empty");
        assertFalse(wordsStack.isEmpty());
        
        wordsStack.pop();
        assertTrue(wordsStack.isEmpty());
    }

    @DisplayName("A stack has a size 0 on construction")
    @Test
    void size(){ 
        assertEquals(0, wordsStack.size()); 

        wordsStack.push("Test Size");
        assertEquals(1, wordsStack.size());

        wordsStack.pop();
        assertEquals(0, wordsStack.size());

    }

    @DisplayName("After n pushes to an empty stack, n > 0, the stack is not empty and its size is n")
    @Test
    void pushFromEmpty(){

        assertTrue(wordsStack.isEmpty()); 

        wordsStack.push("a");
        wordsStack.push("b");
        wordsStack.push("c");

        assertEquals(wordsStack.size(), 3);
        assertFalse(wordsStack.isEmpty());


    }


    @DisplayName("If one pushes x then pops, the value popped is x")
    @Test
    void pushThenPop(){
        
    }

    @Test
    void popFromEmpty_ThenGetException(){ assertThrows(NoSuchElementException.class, () -> wordsStack.pop()); }
}
