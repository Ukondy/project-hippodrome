import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

class HorseTest {
    private final String name = "Zephyr";
    private final double speed = 10;
    private final double distance = 10;

    @Test
    void testConstructor_ShouldThrowIllegalEx_WhenArgsNameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null , speed));
    }

    @Test
    void testConstructor_ShouldThrowTextMessage_WhenArgsNameIsNull() {
        String expectedMessage = "Name cannot be null.";

        var exception = assertThrows(IllegalArgumentException.class, () -> new Horse(null, speed));
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "\n", " ", "\r", "\t", "  ", "\f"})
    void testConstructor_ShouldThrowIllegalEx_WhenNameIsBlank(String name) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(name , speed));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "\n", " ", "\r", "\t", "  ", "\f"})
    void testConstructor_ShouldThrowTextMessage_WhenArgsNameIsBlank(String name) {
        String expectedMessage = "Name cannot be blank.";

        var exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, speed));
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testConstructor_ShouldThrowIllegalEx_WhenArgsSpeedIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(name , -speed));
    }

    @Test
    void testConstructor_ShouldThrowTextMessage_WhenArgsSpeedIsNegative() {
        String expectedMessage = "Speed cannot be negative.";

        var exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, -speed));
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testConstructor_ShouldThrowIllegalEx_WhenArgsDistanceIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(name , speed, -distance));
    }

    @Test
    void testConstructor_ShouldThrowTextMessage_WhenArgsDistanceIsNegative() {
        String expectedMessage = "Distance cannot be negative.";

        var exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, speed, -distance));
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testGetName_ShouldReturnName_WhenObjectWasCreate() {
        assertEquals(name, new Horse(name, speed, distance).getName());
    }

    @Test
    void testGetSpeed_ShouldReturnSpeed_WhenObjectWasCreate() {
        assertEquals(speed, new Horse(name, speed, distance).getSpeed());
    }

    @Test
    void testGetDistance_ShouldReturnDistance_WhenObjectWasCreate() {
        assertEquals(distance, new Horse(name, speed, distance).getDistance());
    }

    @Test
    void testGetDistance_ShouldReturnDistance_WhenConstructorPassToArgs() {
        assertEquals(0, new Horse(name, speed).getDistance());
    }

    @SneakyThrows
    @Test
    void testGetName_ShouldReturnName_WhenObjectWasCreate_Reflection() {
        String expectedName = "Zephyr";

        Horse horse = new Horse(name, speed);
        Field field = horse.getClass().getDeclaredField("name");
        field.setAccessible(true);
        String actualName = (String) field.get(horse);

        assertEquals(expectedName, actualName);
    }

    @SneakyThrows
    @Test
    void testGetSpeed_ShouldReturnSpeed_WhenObjectWasCreate_Reflection() {
        double expectedSpeed = 10;

        Horse horse = new Horse(name, speed);
        Field field = horse.getClass().getDeclaredField("speed");
        field.setAccessible(true);
        double actualSpeed = (double) field.get(horse);

        assertEquals(expectedSpeed, actualSpeed);
    }

    @SneakyThrows
    @Test
    void testGetDistance_ShouldReturnDistance_WhenObjectWasCreate_Reflection() {
        double expectedDistance = 10;

        Horse horse = new Horse(name, speed, distance);
        Field field = horse.getClass().getDeclaredField("distance");
        field.setAccessible(true);
        double actualDistance = (double) field.get(horse);

        assertEquals(expectedDistance, actualDistance);
    }

    @SneakyThrows
    @Test
    void testGetDistance_ShouldReturnDistance_WhenConstructorPassToArgs_Reflection() {
        double expectedDistance = 0;

        Horse horse = new Horse(name, speed);
        Field field = horse.getClass().getDeclaredField("distance");
        field.setAccessible(true);
        double actualDistance = (double) field.get(horse);

        assertEquals(expectedDistance, actualDistance);
    }

    @Test
    void testMove_ShouldCallMethodGetRandomDouble_WhenPassTwoNumberZeroDotTwoAndZeroDotNine() {
        try(MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            new Horse(name, speed, distance).move();
            mockedStatic.verify(() -> Horse.getRandomDouble(eq(0.2), eq(0.9)));
        }
    }

    @ParameterizedTest()
    @ValueSource(doubles = {-3, 0.2, 0.3, 0.5, 0.9, 5, 99})
    void testMove_ShouldAssign_WhenPassTwoNumberZeroDotTwoAndZeroDotNine(double fakeValue) {
        Horse horse = new Horse(name, speed, distance);
        double expectedDistance = horse.getDistance() + horse.getSpeed() * fakeValue;

        try(MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(fakeValue);
            horse.move();
            double actualDistance = horse.getDistance();
            assertEquals(expectedDistance, actualDistance);
        }
    }
}


















