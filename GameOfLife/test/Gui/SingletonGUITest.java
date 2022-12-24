package Gui;

import Board.Cell;
import Board.ColorType;
import Board.Grid;
import Board.Player;
import GameState.Game;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static org.junit.jupiter.api.Assertions.*;

class SingletonGUITest {


    @Test
    public void testGetInstance() {
        SingletonGUI instance1 = SingletonGUI.getInstance();
        SingletonGUI instance2 = SingletonGUI.getInstance();
        assertEquals(instance1, instance2);
    }


    @Test
    public void testSwitchCurrentPlayer() throws NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        ColorType currentColor = ColorType.RED;
        ColorType otherColor = ColorType.BLUE;

        Player current = new Player();
        current.setPlayerColor(currentColor);
        current.setPlayerName("current");

        Player other = new Player();
        current.setPlayerColor(otherColor);
        current.setPlayerName("other");

        // Set up SingletonGUI instance and initialize fields
        SingletonGUI instance = SingletonGUI.getInstance();
        Field privateStringField1 = SingletonGUI.class.getDeclaredField("aPlayer1");
        privateStringField1.setAccessible(true);
        privateStringField1.set(instance, current);

        Field aPlayer2 = SingletonGUI.class.getDeclaredField("aPlayer2");
        aPlayer2.setAccessible(true);
        aPlayer2.set(instance, other);

        Field aCurrentPlayer = SingletonGUI.class.getDeclaredField("aCurrentPlayer");
        aCurrentPlayer.setAccessible(true);
        aCurrentPlayer.set(instance, current);

        instance.switchCurrentPlayer();
        assertEquals(instance.getCurrentPlayerName(), other.getPlayerName());

        Method privateMethod1 = SingletonGUI.class.getDeclaredMethod("getCurrentPlayerName");
        privateMethod1.setAccessible(true);
        String returnValue1 = (String)privateMethod1.invoke(instance);
        assertEquals(returnValue1, other.getPlayerName());

        Method privateMethod2 = SingletonGUI.class.getDeclaredMethod("getOtherPlayerColor");
        privateMethod2.setAccessible(true);
        ColorType returnValue2 = (ColorType)privateMethod2.invoke(instance);
        assertEquals(returnValue2, current.getPlayerColor());

        instance.switchCurrentPlayer();

        Method privateMethod3 = SingletonGUI.class.getDeclaredMethod("getCurrentPlayerName");
        privateMethod1.setAccessible(true);
        String returnValue3 = (String)privateMethod3.invoke(instance);
        assertEquals(returnValue3, current.getPlayerName());

        Method privateMethod4 = SingletonGUI.class.getDeclaredMethod("getOtherPlayerColor");
        privateMethod4.setAccessible(true);
        ColorType returnValue4 = (ColorType)privateMethod2.invoke(instance);
        assertEquals(returnValue4, other.getPlayerColor());

    }
    @Test
    public void testSomeoneHasLost() throws NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        ColorType currentColor = ColorType.RED;
        ColorType otherColor = ColorType.BLUE;

        Player current = new Player();
        current.setPlayerColor(currentColor);
        current.setPlayerName("current");

        Player other = new Player();
        current.setPlayerColor(otherColor);
        current.setPlayerName("other");

        // Set up SingletonGUI instance and initialize fields
        SingletonGUI instance = SingletonGUI.getInstance();
        Field aPlayer1 = SingletonGUI.class.getDeclaredField("aPlayer1");
        aPlayer1.setAccessible(true);
        aPlayer1.set(instance, current);

        Field aPlayer2 = SingletonGUI.class.getDeclaredField("aPlayer2");
        aPlayer2.setAccessible(true);
        aPlayer2.set(instance, other);

        Cell[][] aNewGrid = new Cell[10][10];
        for (int i=0; i<10; i++){
            for (int j=0; j<10; j++){
                aNewGrid[i][j] = new Cell();
            }
        }

        Grid aGridObject = new Grid(10);
        Field aGridGrid = Grid.class.getDeclaredField("aGrid");
        aGridGrid.setAccessible(true);
        aGridGrid.set(aGridObject, aNewGrid);


        Field aGrid = SingletonGUI.class.getDeclaredField("aGrid");
        aGrid.setAccessible(true);
        aGrid.set(instance, aGridObject);

        Field aGame = SingletonGUI.class.getDeclaredField("aGame");
        aGame.setAccessible(true);
        aGame.set(instance, new Game(current, other, aGridObject, instance, 0));

        Method privateMethod = SingletonGUI.class.getDeclaredMethod("someoneHasLost");
        privateMethod.setAccessible(true);
        boolean expected = (boolean)privateMethod.invoke(instance);
        assertTrue(expected);

        for (int i=0; i<10; i++){
            for (int j=0; j<10; j++){
                aNewGrid[i][j].revive(currentColor);
            }
        }
        expected = (boolean)privateMethod.invoke(instance);
        assertTrue(expected);

        for (int i=0; i<10; i++){
            for (int j=0; j<10; j++){
                if (j<5){
                    aNewGrid[i][j].revive(otherColor);
                }
                else {
                    aNewGrid[i][j].revive(currentColor);
                }

            }
        }
        expected = (boolean)privateMethod.invoke(instance);
        assertFalse(expected);
    }

        /*


    @Test
    public void testGetBoard() {
        // Set up test data
        int size = 5;

        // Set up SingletonGUI instance and initialize fields
        SingletonGUI instance = SingletonGUI.getInstance();
        instance.aSize = size;

        // Call getBoard method and verify that the returned panel has the correct number of buttons
        JPanel board = instance.getBoard();
        assertEquals(size * size, board.getComponents().length);
    }

    @Test
    public void testSetMessage() {
        // Set up test data
        String message = "Test message";

        // Set up SingletonGUI instance and initialize fields
        SingletonGUI instance = SingletonGUI.getInstance();

        // Call setMessage method and verify that the message label was set correctly
        instance.setMessage(message);
        assertEquals(message, instance.aChartLabelMessage.getText());
    }

    @Test
    public void testSwitchCurrentPlayer() {
        // Set up test data and mock objects
        Player mockPlayer1 = mock(Player.class);
        Player mockPlayer2 = mock(Player.class);

        // Set up SingletonGUI instance and initialize fields
        SingletonGUI instance = SingletonGUI.getInstance();
        instance.aPlayer1 = mockPlayer1;
        instance.aPlayer2 = mockPlayer2;

        // Set current player to player 1 and call switchCurrentPlayer method
        instance.aCurrentPlayer = mockPlayer1;
        instance.switchCurrentPlayer();
        // Verify that the current player was switched to player 2
        assertEquals(mockPlayer2, instance.aCurrentPlayer);

        // Set current player to player 2 and call switchCurrentPlayer method
        instance.aCurrentPlayer = mockPlayer2;
        instance.switchCurrentPlayer();
        // Verify that the current player was switched back to player 1
        assertEquals(mockPlayer1, instance.aCurrentPlayer);
    }


    @Test
    public void testGetButtonRowCol() {
        // Set up test data
        int size = 3;
        JButton[][] buttonArray = new JButton[size][size];
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                buttonArray[y][x] = new JButton();
            }
        }
        JButton targetButton = buttonArray[1][2];

        // Set up SingletonGUI instance and initialize fields
        SingletonGUI instance = SingletonGUI.getInstance();
        instance.aButtonArray = buttonArray;
        instance.aSize = size;

        // Call getButtonRowCol method and verify that the correct row and column indices are returned
        int[] rowCol = instance.getButtonRowCol(targetButton);
        assertArrayEquals(new int[]{1, 2}, rowCol);
    }



    @Test
    public void testGetButtonRowCol() {
        // Set up test data
        int size = 3;
        JButton[][] buttonArray = new JButton[size][size];
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                buttonArray[y][x] = new JButton();
            }
        }
        JButton targetButton = buttonArray[1][2];

        // Set up SingletonGUI instance and initialize fields
        SingletonGUI instance = SingletonGUI.getInstance();
        instance.aButtonArray = buttonArray;
        instance.aSize = size;

        // Call getButtonRowCol method and verify that the correct row and column indices are returned
        int[] rowCol = instance.getButtonRowCol(targetButton);
        assertArrayEquals(new int[]{1, 2}, rowCol);
    }

     */

}