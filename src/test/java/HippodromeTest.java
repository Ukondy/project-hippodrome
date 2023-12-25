import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class HippodromeTest {

    @Test
    void testConstructor_ShouldThrowIllegalEx_WhenHorsesIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    void testConstructor_ShouldThrowTextMessage__WhenHorsesIsNull() {
        String expectedMessage = "Horses cannot be null.";

        var exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testConstructor_ShouldThrowIllegalEx_WhenHorsesIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(List.of()));
    }

    @Test
    void testConstructor_ShouldThrowTextMessage_WhenHorsesIsEmpty() {
        String expectedMessage = "Horses cannot be empty.";

        var exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(List.of()));
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testGetHorses_ShouldReturnHorses_WhenHorsesIsExist() {
        var horses = IntStream.range(0,30).mapToObj(i -> new Horse("Zephyr#" + i, i, i)).toList();
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    void testMove_ShouldCallMoveFromEachHorse_WhenHorsesIsExist() {
        var horses = IntStream.range(0,50).mapToObj(i -> Mockito.mock(Horse.class)).toList();
        new Hippodrome(horses).move();

        horses.forEach(horse -> Mockito.verify(horse, Mockito.times(1)).move());
    }

    @Test
    void testGetWinner_ShouldReturnHorseWithBiggestDistance_WhenHorsesIsExist() {
        Horse horse1 = new Horse("name1", 1, 1);
        Horse horse2 = new Horse("name2", 2, 2);
        Horse horse3 = new Horse("name3", 3, 3);

        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3));

        assertSame(horse3, hippodrome.getWinner());
    }

}