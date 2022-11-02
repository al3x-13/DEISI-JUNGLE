package pt.ulusofona.lp2.deisiJungle;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestMapCell {
    @Test
    public void test_01_hasPlayerID() {
        MapCell cell = new MapCell(3, "Vazio");
        assertFalse(cell.hasPlayerID(1));
        cell.addPlayer(1);
        assertTrue(cell.hasPlayerID(1));
    }

    @Test
    public void test_01_addPlayer() {
        MapCell cell = new MapCell(3, "Vazio");
        assertFalse(cell.hasPlayerID(3));
        assertTrue(cell.addPlayer(3));
        assertTrue(cell.hasPlayerID(3));
    }

    @Test
    public void test_02_addPlayer() {
        MapCell cell = new MapCell(3, "Vazio");
        assertTrue(cell.addPlayer(3));
        assertTrue(cell.hasPlayerID(3));
        assertFalse(cell.addPlayer(3));
    }

    @Test
    public void test_01_rmPlayer() {
        MapCell cell = new MapCell(3, "Vazio");
        assertFalse(cell.hasPlayerID(3));
        assertTrue(cell.addPlayer(3));
        assertTrue(cell.hasPlayerID(3));
        assertTrue(cell.rmPlayer(3));
        assertFalse(cell.hasPlayerID(3));
    }

    @Test
    public void test_02_rmPlayer() {
        MapCell cell = new MapCell(3, "Vazio");
        assertTrue(cell.addPlayer(3));
        assertTrue(cell.hasPlayerID(3));
        assertTrue(cell.rmPlayer(3));
        assertFalse(cell.rmPlayer(3));
        assertFalse(cell.hasPlayerID(3));
    }
}
