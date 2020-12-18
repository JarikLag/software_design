package stats;

import clock.ControllableClock;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RPMEventStatisticsTest {
    private ControllableClock clock;
    private RPMEventStatistics stats;

    private static final double EPS = 1e-6;

    private boolean isStatsEqual(Map<String, Double> first, Map<String, Double> second) {
        if (!first.keySet().equals(second.keySet())) {
            return false;
        }

        for (Map.Entry<String, Double> entry : first.entrySet()) {
            double firstValue = entry.getValue();
            double secondValue = second.get(entry.getKey());
            if (Math.abs(firstValue - secondValue) > EPS) {
                return false;
            }
        }

        return true;
    }

    @Before
    public void prepare() {
        Instant start = Instant.now();
        clock = new ControllableClock(start);
        stats = new RPMEventStatistics(clock);
    }

    @Test
    public void simpleTest() {
        stats.incEvent("f");
        stats.incEvent("s");
        stats.incEvent("f");

        Map<String, Double> expected = Map.of("f", 2 / 60.0, "s", 1 / 60.0);
        assertTrue(isStatsEqual(stats.getAllEventStatistic(), expected));
    }

    @Test
    public void getNonPresentEventStats() {
        double expected = 0.0;
        assertEquals(expected, stats.getEventStatisticByName("f"), EPS);
    }

    @Test
    public void expiredAllEventsTest() {
        stats.incEvent("f");
        stats.incEvent("s");
        clock.plus(Duration.ofHours(1));

        Map<String, Double> expected = Collections.emptyMap();
        assertTrue(isStatsEqual(stats.getAllEventStatistic(), expected));
    }

    @Test
    public void expiredThenAddedTest() {
        stats.incEvent("f");
        stats.incEvent("s");
        clock.plus(Duration.ofHours(1));
        stats.incEvent("f");

        Map<String, Double> expected = Map.of("f", 1 / 60.0);
        assertTrue(isStatsEqual(stats.getAllEventStatistic(), expected));
    }

    @Test
    public void expiredPartiallyTest() {
        stats.incEvent("f");
        clock.plus(Duration.ofMinutes(30));
        stats.incEvent("f");
        clock.plus(Duration.ofMinutes(30));
        stats.incEvent("f");
        stats.incEvent("s");
        clock.plus(Duration.ofMinutes(5));
        stats.incEvent("s");

        Map<String, Double> expected = Map.of("f", 2 / 60.0, "s", 2 / 60.0);
        assertTrue(isStatsEqual(stats.getAllEventStatistic(), expected));
    }
}
