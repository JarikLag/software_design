package stats;

import clock.Clock;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class RPMEventStatistics implements EventStatistics {
    private Clock clock;
    private Deque<Event> eventQueue;
    private Map<String, Integer> eventCounts;

    private static final double minutesInHour = ChronoUnit.HOURS.getDuration().toMinutes();

    public RPMEventStatistics(Clock clock) {
        this.clock = clock;
        this.eventQueue = new ArrayDeque<>();
        this.eventCounts = new HashMap<>();
    }

    @Override
    public void incEvent(String name) {
        Instant curTime = clock.now();
        removeExpiredEvents(curTime);
        eventQueue.add(new Event(name, curTime));
        eventCounts.merge(name, 1, Integer::sum);
    }

    @Override
    public double getEventStatisticByName(String name) {
        Instant curTime = clock.now();
        removeExpiredEvents(curTime);
        return eventCounts.getOrDefault(name, 0) / minutesInHour;
    }

    @Override
    public Map<String, Double> getAllEventStatistic() {
        Instant curTime = clock.now();
        removeExpiredEvents(curTime);

        Map<String, Double> map = new HashMap<>();
        eventCounts.forEach((key, value) -> map.put(key, value / minutesInHour));
        return map;
    }

    @Override
    public void printStatistic() {
        Map<String, Double> stats = getAllEventStatistic();
        stats.forEach((key, value) -> System.out.println(String.format("event: %s, count: %f", key, value)));
    }

    private void removeExpiredEvents(Instant curTime) {
        while (!eventQueue.isEmpty() && isEventExpired(curTime)) {
            Event event = eventQueue.poll();
            assert event != null;

            int count = eventCounts.get(event.name);
            if (count >= 2) {
                eventCounts.put(event.name, count - 1);
            } else {
                eventCounts.remove(event.name);
            }
        }
    }

    private boolean isEventExpired(Instant curTime) {
        assert eventQueue.peek() != null;

        Instant lastEventTime = eventQueue.peek().time;
        return ChronoUnit.HOURS.between(lastEventTime, curTime) >= 1;
    }

    private static class Event {
        String name;
        Instant time;

        Event(String name, Instant time) {
            this.name = name;
            this.time = time;
        }
    }
}
