package Gui;

import Board.ColorType;
import Board.Grid;
import Board.Player;
import GameState.Game;
import org.junit.jupiter.api.Test;

import javax.swing.*;

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
    public void testGameLogic() {
        // Set up test data and mock objects
        int y = 0;
        int x = 0;
        ColorType currentColor = ColorType.RED;
        ColorType otherColor = ColorType.BLUE;
        int[][] gridData = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        Grid mockGrid = mock(Grid.class);
        when(mockGrid.cellIsAlive(y, x)).thenReturn(false);
        when(mockGrid.cellhasColor(y, x, currentColor)).thenReturn(false);
        when(mockGrid.getData()).thenReturn(gridData);
        Player mockPlayer1 = mock(Player.class);
        when(mockPlayer1.getPlayerColor()).thenReturn(currentColor);
        Player mockPlayer2 = mock(Player.class);
        when(mockPlayer2.getPlayerColor()).thenReturn(otherColor);
        Game mockGame = mock(Game.class);
        when(mockGame.initOver()).thenReturn(false);

        // Set up SingletonGUI instance and initialize fields
        SingletonGUI instance = SingletonGUI.getInstance();
        instance.aCurrentPlayer = mockPlayer1;
        instance.aGrid = mockGrid;
        instance.aGame = mockGame;
        instance.aPlayer1 = mockPlayer1;
        instance.aPlayer2 = mockPlayer2;

        // Call gameLogic method and verify that grid and current player are updated correctly
        instance.gameLogic(new int[] {y, x});
        verify(mockGame).clickedEmptyCell(y, x, currentColor, otherColor);
        verify(mockGame).getStateRule();
        assertEquals(mockPlayer2, instance.aCurrentPlayer);
    }


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



}