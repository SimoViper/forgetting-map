package com.coding.test;

import com.coding.test.map.ForgettingMap;
import com.coding.test.model.Value;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

import java.util.concurrent.*;

public class ForgettingMapTest {

    private static final String FIRST = "First";
    private static final String SECOND = "Second";
    private static final String THIRD = "Third";
    private static final String FOURTH = "Fourth";
    private static final String FIFTH = "Fifth";
    private static final String SIXTH = "Sixth";
    private static final String SEVENTH = "Seventh";
    private static final String EIGHTH = "Eighth";
    private static final String NINTH = "Ninth";
    private static final String TENTH = "Tenth";
    public static final String THREAD_NAME_S_N = "Thread Name %s%n";


    @Test
    public void testMap() throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Future<ForgettingMap<String, Value>> futureFromCallableSix = executorService.submit(getCallableSixAssociations());
        Future<ForgettingMap<String, Value>> futureFromCallableFour = executorService.submit(getCallableFourAssociations());
        Future<ForgettingMap<String, Value>> futureFromCallableEight = executorService.submit(getCallableEightAssociations());

        ForgettingMap<String, Value> resultSix = futureFromCallableSix.get();
        ForgettingMap<String, Value> resultFour = futureFromCallableFour.get();
        ForgettingMap<String, Value> resultEight = futureFromCallableEight.get();

        executorService.shutdown();

        assertFalse(resultSix.containsKey(THIRD));
        assertFalse(resultSix.containsKey(FOURTH));

        assertFalse(resultFour.containsKey(THIRD));
        assertFalse(resultFour.containsKey(FOURTH));

        assertFalse(resultEight.containsKey(FIRST));
        assertFalse(resultEight.containsKey(SECOND));

    }

    private Callable<ForgettingMap<String, Value>> getCallableSixAssociations() {
        return () -> {
            TimeUnit.MILLISECONDS.sleep(350);
            System.out.printf(THREAD_NAME_S_N, Thread.currentThread().getName());
            ForgettingMap<String, Value> map = new ForgettingMap<>(6);

            map.add(FIRST, new Value());
            map.add(SECOND, new Value());
            map.add(THIRD, new Value());
            map.add(FOURTH, new Value());
            map.add(FIFTH, new Value());
            map.add(SIXTH, new Value());

            map.find(FIRST);
            map.find(SECOND);
            map.find(FIRST);
            map.find(FIRST);
            map.find(SECOND);
            map.find(FIRST);

            map.add(SEVENTH, new Value());
            map.add(EIGHTH, new Value());

            return map;
        };
    }

    private Callable<ForgettingMap<String, Value>> getCallableEightAssociations() {
        return () -> {
            TimeUnit.MILLISECONDS.sleep(350);
            System.out.printf(THREAD_NAME_S_N, Thread.currentThread().getName());
            ForgettingMap<String, Value> map = new ForgettingMap<>(8);

            map.add(FIRST, new Value());
            map.add(SECOND, new Value());
            map.add(THIRD, new Value());
            map.add(FOURTH, new Value());
            map.add(FIFTH, new Value());
            map.add(SIXTH, new Value());
            map.add(SEVENTH, new Value());
            map.add(EIGHTH, new Value());

            map.find(FOURTH);
            map.find(FOURTH);
            map.find(SIXTH);
            map.find(SIXTH);
            map.find(SIXTH);
            map.find(THIRD);

            map.add(NINTH, new Value());
            map.add(TENTH, new Value());

            return map;
        };
    }

    private Callable<ForgettingMap<String, Value>> getCallableFourAssociations() {
        return () -> {
            TimeUnit.MILLISECONDS.sleep(350);
            System.out.printf(THREAD_NAME_S_N, Thread.currentThread().getName());
            ForgettingMap<String, Value> map = new ForgettingMap<>(4);

            map.add(FIRST, new Value());
            map.add(SECOND, new Value());
            map.add(THIRD, new Value());
            map.add(FOURTH, new Value());

            map.find(SECOND);
            map.find(FIRST);
            map.find(SECOND);
            map.find(SECOND);
            map.find(SECOND);
            map.find(FIRST);

            map.add(FIFTH, new Value());
            map.add(SIXTH, new Value());

            return map;
        };
    }
}
