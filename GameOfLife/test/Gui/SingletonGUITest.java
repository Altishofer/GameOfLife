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

//import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
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
        assertTrue(expected);
    }

    @Test
    public void testGetBoard() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException {
        // Set up test data
        int size = 25;

        // Set up SingletonGUI instance and initialize fields
        SingletonGUI instance = SingletonGUI.getInstance();
        Field sizePrivateField = SingletonGUI.class.getDeclaredField("aSize");
        sizePrivateField.setAccessible(true);

        sizePrivateField.set(instance, size);

        // Call getBoard method and verify that the returned panel has the correct number of buttons

        Method privateGetBoardMethod = SingletonGUI.class.getDeclaredMethod("getBoard");
        privateGetBoardMethod.setAccessible(true);

        JPanel board = (JPanel) privateGetBoardMethod.invoke(instance);

        Field aButtonArrayPrivateField = SingletonGUI.class.getDeclaredField("aButtonArray");
        aButtonArrayPrivateField.setAccessible(true);

        JButton[][] testButtonArray = (JButton[][]) aButtonArrayPrivateField.get(instance);

        assertEquals(size * size, testButtonArray.length * testButtonArray[0].length);

        // Verify that board size changes if getBoard is called with different size

        size = 5;
        sizePrivateField.set(instance, size);
        board = (JPanel) privateGetBoardMethod.invoke(instance);
        testButtonArray = (JButton[][]) aButtonArrayPrivateField.get(instance);

        assertEquals(size * size, testButtonArray.length * testButtonArray[0].length);
    }

    @Test
    void testPlayerNameReturn() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException {
        Player mockPlayer1 = new Player();
        Player mockPlayer2 = new Player();

        String expectedName1 = "theone";
        mockPlayer1.setPlayerName(expectedName1);

        String expectedName2 = "theother";
        mockPlayer2.setPlayerName(expectedName2);

        // Set up SingletonGUI instance and initialize fields
        SingletonGUI instance = SingletonGUI.getInstance();

        Field player1Field = SingletonGUI.class.getDeclaredField("aPlayer1");
        player1Field.setAccessible(true);
        Field player2Field = SingletonGUI.class.getDeclaredField("aPlayer2");
        player2Field.setAccessible(true);

        player1Field.set(instance, mockPlayer1);
        player2Field.set(instance, mockPlayer2);

        // Set current player to player 1
        Field currentPlayerField = SingletonGUI.class.getDeclaredField("aCurrentPlayer");
        currentPlayerField.setAccessible(true);

        Method privateGetOtherPlayerColor = SingletonGUI.class.getDeclaredMethod("getOtherPlayerColor");
        privateGetOtherPlayerColor.setAccessible(true);

        currentPlayerField.set(instance, mockPlayer1);

        // Verify that the current player was set to Player 1 and correct NAME is returned
        assertEquals(expectedName1, instance.getCurrentPlayerName());

        // Call switchCurrentPlayer method
        instance.switchCurrentPlayer();

        // Verify that the current player was switched to Player 2 and correct NAME is returned
        assertEquals(expectedName2, instance.getCurrentPlayerName());
    }

    @Test
    void testOtherPlayerColorReturn() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Player mockPlayer1 = new Player();
        Player mockPlayer2 = new Player();

        ColorType expectedColor1 = ColorType.BLUE;
        mockPlayer1.setPlayerColor(expectedColor1);

        ColorType expectedColor2 = ColorType.RED;
        mockPlayer2.setPlayerColor(expectedColor2);

        // Set up SingletonGUI instance and initialize fields
        SingletonGUI instance = SingletonGUI.getInstance();

        Field player1Field = SingletonGUI.class.getDeclaredField("aPlayer1");
        player1Field.setAccessible(true);
        Field player2Field = SingletonGUI.class.getDeclaredField("aPlayer2");
        player2Field.setAccessible(true);

        player1Field.set(instance, mockPlayer1);
        player2Field.set(instance, mockPlayer2);

        // Set current player to player 1
        Field currentPlayerField = SingletonGUI.class.getDeclaredField("aCurrentPlayer");
        currentPlayerField.setAccessible(true);

        Method privateGetOtherPlayerColor = SingletonGUI.class.getDeclaredMethod("getOtherPlayerColor");
        privateGetOtherPlayerColor.setAccessible(true);

        currentPlayerField.set(instance, mockPlayer1);

        // Verify that the current player was set to Player 1 and correct COLOR for Player 2 is returned
        assertEquals(expectedColor2, privateGetOtherPlayerColor.invoke(instance));

        // Call switchCurrentPlayer method
        instance.switchCurrentPlayer();

        // Verify that the current player was switched to Player 2 and correct COLOR for Player 1 is returned
        assertEquals(expectedColor1, privateGetOtherPlayerColor.invoke(instance));
    }

    @Test
    void checkDisableAllFinished() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        int size = 5;

        SingletonGUI instance = SingletonGUI.getInstance();

        Field sizePrivateField = SingletonGUI.class.getDeclaredField("aSize");
        sizePrivateField.setAccessible(true);

        sizePrivateField.set(instance, size);

        Method privateGetBoardMethod = SingletonGUI.class.getDeclaredMethod("getBoard");
        privateGetBoardMethod.setAccessible(true);

        JPanel board = (JPanel) privateGetBoardMethod.invoke(instance);

        Method privateDisableAllFinished = SingletonGUI.class.getDeclaredMethod("disableAllFinished");
        privateDisableAllFinished.setAccessible(true);

        privateDisableAllFinished.invoke(instance);

        Field privateButtonArray = SingletonGUI.class.getDeclaredField("aButtonArray");
        privateButtonArray.setAccessible(true);

        JButton[][] testButtonArray = (JButton[][]) privateButtonArray.get(instance);

        for(int row = 0; row < size; row++) {
            for(int col = 0; col < size; col++) {
                assertFalse(testButtonArray[row][col].isEnabled());
            }
        }
    }

    @Test
    public void testSetMessage() throws NoSuchFieldException, IllegalAccessException {
        // Set up test data
        String expected = "Test message";

        // Set up SingletonGUI instance and initialize fields
        SingletonGUI instance = SingletonGUI.getInstance();

        Field privateChartLabelMessage = SingletonGUI.class.getDeclaredField("aChartLabelMessage");
        privateChartLabelMessage.setAccessible(true);

        // Call setMessage method and verify that the message label was set correctly
        instance.setMessage(expected);
        JLabel transfer = (JLabel) privateChartLabelMessage.get(instance);

        assertEquals(expected, transfer.getText());
    }

    /*
    @Test
    public void testSwitchCurrentPlayer() throws NoSuchFieldException, IllegalAccessException {
        // Set up test data and mock objects
        Player mockPlayer1 = new Player();
        Player mockPlayer2 = new Player();

        mockPlayer1.setPlayerName("theone");
        mockPlayer1.setPlayerColor(ColorType.BLUE);
        mockPlayer2.setPlayerName("theother");
        mockPlayer2.setPlayerColor(ColorType.RED);

        // Set up SingletonGUI instance and initialize fields
        SingletonGUI instance = SingletonGUI.getInstance();

        Field player1Field = SingletonGUI.class.getDeclaredField("aPlayer1");
        player1Field.setAccessible(true);
        Field player2Field = SingletonGUI.class.getDeclaredField("aPlayer2");
        player2Field.setAccessible(true);

        player1Field.set(instance, mockPlayer1);
        player2Field.set(instance, mockPlayer2);

        // Set current player to player 1 and call switchCurrentPlayer method
        Field currentPlayerField = SingletonGUI.class.getDeclaredField("aCurrentPlayer");
        currentPlayerField.setAccessible(true);

        currentPlayerField.set(instance, mockPlayer1);
        // Verify that the current player was set to Player 1
        assertEquals(mockPlayer1, currentPlayerField.get(instance));

        instance.switchCurrentPlayer();
        // Verify that the current player was switched to Player 2
        assertEquals(mockPlayer2, currentPlayerField.get(instance));

        // Set current player to player 2 and call switchCurrentPlayer method
        currentPlayerField.set(instance, mockPlayer2);
        // Verify that the current player was set to Player 2
        assertEquals(mockPlayer2, currentPlayerField.get(instance));

        instance.switchCurrentPlayer();
        // Verify that the current player was switched back to player 1
        assertEquals(mockPlayer1, currentPlayerField.get(instance));
    }
*/
    @Test
    public void testGetButtonRowCol() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // Set up test data
        int size = 3;
        JButton[][] buttonArray = new JButton[size][size];
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                buttonArray[y][x] = new JButton();
            }
        }
        JButton targetButton = buttonArray[1][2];
        JButton falseButton = new JButton();

        // Set up SingletonGUI instance and initialize fields
        SingletonGUI instance = SingletonGUI.getInstance();

        Field privateSize = SingletonGUI.class.getDeclaredField("aSize");
        privateSize.setAccessible(true);

        privateSize.set(instance, size);

        Field privateButtonArray = SingletonGUI.class.getDeclaredField("aButtonArray");
        privateButtonArray.setAccessible(true);

        privateButtonArray.set(instance, buttonArray);

        // Call getButtonRowCol method and verify that the correct row and column indices are returned
        Method privateGetButtonRowCol = SingletonGUI.class.getDeclaredMethod("getButtonRowCol", JButton.class);
        privateGetButtonRowCol.setAccessible(true);

        int[] rowCol = (int[]) privateGetButtonRowCol.invoke(instance, targetButton);
        assertArrayEquals(new int[]{1, 2}, rowCol);

        rowCol = (int[]) privateGetButtonRowCol.invoke(instance, falseButton);
        assertArrayEquals(new int[]{}, rowCol);
    }

/*

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
    void checkIfLostTrue() throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        SingletonGUI aGUI = SingletonGUI.getInstance();
        Game tGame = new Game(null, null, new Grid(2), aGUI, 1);

        Field gameField = SingletonGUI.class.getDeclaredField("aGame");
        gameField.setAccessible(true);
        Method privateMethod = SingletonGUI.class.getDeclaredMethod("checkIfLost");
        privateMethod.setAccessible(true);
        Field buttonField = SingletonGUI.class.getDeclaredField("aButtonArray");
        buttonField.setAccessible(true);

        gameField.set(aGUI, tGame);
        privateMethod.invoke(aGUI);

        System.out.print(buttonField.get(aGUI));
    }
    */
}