import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.w3c.dom.html.HTMLObjectElement;


import java.lang.reflect.Field;
import java.util.regex.Matcher;

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
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse("someName", -0.1, 0));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    @DisplayName("should return IllegalArgumentException if Horse gets negative speed value")
    public void horseDistanceAsNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse("someName", 0, -0.1));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    @DisplayName("getName() should return Horse's name")
    public void horseGetName() {
        String expected = "horse";
        Horse horse = new Horse(expected, 2, 2);
//      using refcletion to get field "name" to know if constructor or getName is broken
        String horseNameValue = null;
        try {
            Field name = Horse.class.getDeclaredField("name");
            name.setAccessible(true);
            horseNameValue = (String) name.get(horse);
            name.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
//        checking constructor
        assertEquals(expected, horseNameValue);

        String actual = horse.getName();
//        checking method
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("getSpeed() should return Horse's speed")
    public void horseGetSpeed() {
        Double expected = 2d;
        Horse horse = new Horse("horse", expected, 2);
        Double horseSpeedValue = null;
        try {
            Field speed = Horse.class.getDeclaredField("speed");
            speed.setAccessible(true);
            horseSpeedValue = (Double) speed.get(horse);
            speed.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
//        checking constructor
        assertEquals(expected, horseSpeedValue);

        Double actual = horse.getSpeed();
        //        checking method
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("etDistance() should return Horse's distance")
    public void horseGetDistance() {
        Double expected = 2d;
        Horse horse = new Horse("horse", 2, expected);
        Double horseDistanceValue = null;
        try {
            Field distance = Horse.class.getDeclaredField("distance");
            distance.setAccessible(true);
            horseDistanceValue = (Double) distance.get(horse);
            distance.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
//        checking constructor
        assertEquals(expected, horseDistanceValue);
        Double actual = horse.getDistance();
        //        checking method
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("getDistance() should return 0 as Horse's distance ")
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
            new Horse("name", 31, 32).move();
//            mockedStatic.verify(() -> Horse.getRandomDouble(ArgumentMatchers.eq(0.2d), ArgumentMatchers.anyDouble()));
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
