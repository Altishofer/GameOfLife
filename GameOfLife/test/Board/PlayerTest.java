package Board;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class PlayerTest {

    @Test
    public void testSetPlayerName() {
        Player player = new Player();
        String expectedName = "John";
        player.setPlayerName(expectedName);
        String actualName = player.getPlayerName();
        assertEquals(expectedName, actualName);
    }


    @Test
    public void testGetPlayerName() {
        Player player = new Player();
        String expectedName = "John";
        player.setPlayerName(expectedName);
        String actualName = player.getPlayerName();
        assertEquals(expectedName, actualName);
    }

    @Test
    public void testGetPlayerColor() {
        Player player = new Player();
        ColorType expectedColor = ColorType.BLUE;
        player.setPlayerColor(expectedColor);
        ColorType actualColor = player.getPlayerColor();
        assertEquals(expectedColor, actualColor);
    }

    @Test
    public void testSetPlayerColor() {
        Player player = new Player();
        ColorType expectedColor = ColorType.BLUE;
        player.setPlayerColor(expectedColor);
        ColorType actualColor = player.getPlayerColor();
        assertEquals(expectedColor, actualColor);
    }



    @Test
    public void testCompareTo() {
        Player player1 = new Player();
        player1.setPlayerName("John");
        Player player2 = new Player();
        player2.setPlayerName("Jane");
        assertEquals(0, player1.compareTo(player1));
        assertEquals(-1, player2.compareTo(player1));
    }


    @Test
    public void testHashCode() {
        Player player = new Player();
        player.setPlayerName("John");
        assertEquals(4, player.hashCode());
    }








}