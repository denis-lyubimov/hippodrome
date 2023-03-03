import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

public class MainTest {

    @Test
    @Timeout(value = 22, unit = TimeUnit.SECONDS)
    @DisplayName("should not run longer than 22 seconds")
    @Disabled
    public void mainExecutionDuration(){
        try{
            Main.main(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
