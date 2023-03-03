import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HippodromeTest {

    @Test
    @DisplayName("should return IllegalArgumentException if Hippodrome gets null as horses")
    public void hippodromeGetsNullHorsesList() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    @DisplayName("should return IllegalArgumentException if Hippodrome gets empty list of horses")
    public void hippodromeGetsEmptyHorsesList() {
        List<Horse> emptyList = Collections.<Horse>emptyList();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(emptyList));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    @DisplayName("should return correct list of horses")
    public void hippodromeGetHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse(i + "", i));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
        for (int i = 0; i < horses.size(); i++) {
            Horse expected = horses.get(i);
            Horse actual = hippodrome.getHorses().get(i);
            assertEquals(actual, expected);
        }
    }

    @Test
    @DisplayName("should call move() for every horse in hippodrome")
    public void hippodromeMove() {
        Horse mockHorse = Mockito.mock(Horse.class);
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(mockHorse);
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();
        Mockito.verify(mockHorse,Mockito.times(50)).move();
    }

    @Test
    @DisplayName("should return horse with the longest distance value")
    public void hippodromeGetWinner() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse(i + "", i, i));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        Horse expected = horses.get(horses.size() - 1);
        Horse actual = hippodrome.getWinner();
        assertEquals(actual, expected);
    }
}

