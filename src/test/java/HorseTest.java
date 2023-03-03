import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HorseTest {

    @Test
    @DisplayName("should return IllegalArgumentException if Horse gets null as name")
    public void horseNameAsNull() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 0, 0));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", " ", "     ", "               "})
    @DisplayName("should return IllegalArgumentException if Horse gets blank name")
    public void horseNameAsBlank(String name) {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, 2));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    @DisplayName("should return IllegalArgumentException if Horse gets negative speed value")
    public void horseSpeedAsNegative() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse("someName", -0.1, 0));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    @DisplayName("should return IllegalArgumentException if Horse gets negative speed value")
    public void horseDistanceAsNegative() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse("someName", 0, -0.1));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    @DisplayName("should return Horse's name")
    public void horseGetName() {
        String expected = "horse";
        Horse horse = new Horse(expected, 2, 2);
        String actual = horse.getName();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("should return Horse's speed")
    public void horseGetSpeed() {
        Double expected = 2d;
        Horse horse = new Horse("horse", expected, 2);
        Double actual = horse.getSpeed();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("should return Horse's distance")
    public void horseGetDistance() {
        Double expected = 2d;
        Horse horse = new Horse("horse", 2, expected);
        Double actual = horse.getDistance();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("should return 0 as Horse's distance ")
    public void horseGetZeroDistance() {
        Double expected = 0d;
        Horse horse = new Horse("horse", 2);
        Double actual = horse.getDistance();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("horse.move should use getRandomDouble(0.2,0.9)")
    public void horseMoveUsesGetRandomDouble() {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("name", 1, 2);
            horse.move();
            System.out.println(horse.getDistance());
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {1d, 10d, 20d, 22d})
    @DisplayName("should return correct horse.getDistance() after horse.move()")
    public void horseCheckDistanceAfterMove(Double getRandomDoubleValue) {
        Horse horse = new Horse("name", 2, 2);
        Double expected = horse.getDistance() + horse.getSpeed() * getRandomDoubleValue;
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(getRandomDoubleValue);
            horse.move();
        }
        Double actual = horse.getDistance();
        assertEquals(expected, actual);
    }
}
