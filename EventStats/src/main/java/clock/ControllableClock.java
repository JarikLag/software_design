package clock;

import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.concurrent.atomic.AtomicReference;

public class ControllableClock implements Clock {
    private AtomicReference<Instant> currentInstant;

    public ControllableClock(Instant start) {
        currentInstant = new AtomicReference<>(start);
    }

    @Override
    public Instant now() {
        return currentInstant.get();
    }

    public void setNow(Instant now) {
        currentInstant.set(now);
    }

    public void plus(TemporalAmount interval) {
        currentInstant.updateAndGet(inst -> inst.plus(interval));
    }

    public void minus(TemporalAmount interval) {
        currentInstant.updateAndGet(inst -> inst.minus(interval));
    }
}
