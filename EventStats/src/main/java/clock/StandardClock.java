package clock;

import java.time.Instant;

public class StandardClock implements Clock {
    @Override
    public Instant now() {
        return Instant.now();
    }
}
