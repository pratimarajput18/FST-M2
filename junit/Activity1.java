package activities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Activity1 {
    static ArrayList list;
    @BeforeAll
    static void setup(){
        list = new ArrayList<String>();
        list.add("alpha"); // at index 0
        list.add("beta"); // at index 1
    }

    @Test
    public void insertTest(){
        assertEquals(2, list.size());
        list.add("Zeta");
        assertEquals(3, list.size());
        assertEquals("alpha", list.get(0), "Wrong element");
        assertEquals("beta", list.get(1), "Wrong element");
        assertEquals("Zeta", list.get(2), "Wrong element");
    }
    @Test
    public void replaceTest(){
        list.set(1, "beta");
        assertEquals(2, list.size(), "Wrong Size");
        assertEquals("alpha", list.get(0), "Wrong element");
        assertEquals("beta", list.get(1), "Wrong element");
    }

}
