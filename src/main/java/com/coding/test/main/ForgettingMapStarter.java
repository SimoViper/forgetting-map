package com.coding.test.main;

import com.coding.test.map.ForgettingMap;
import com.coding.test.model.Value;

import java.util.concurrent.*;

public class ForgettingMapStarter {

    private static final String FIRST = "First";
    private static final String SECOND = "Second";
    private static final String THIRD = "Third";
    private static final String FOURTH = "Fourth";
    private static final String FIFTH = "Fifth";

    public static final String THREAD_NAME_S_N = "Thread Name:  %s%n";
    public static final String RESULT_S_N = "Result: %s%n";
    public static final String MAP_SIZE = "Map Size: ";
    public static final String RESULT_ONE_S_N = "ResultOne: %s%n";

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Callable<String> callableTask = () -> {
            TimeUnit.MILLISECONDS.sleep(300);
            System.out.printf(THREAD_NAME_S_N, Thread.currentThread().getName());
            ForgettingMap<String, Value> map = new ForgettingMap<>(3);

            map.add(FIRST, new Value());
            map.add(SECOND, new Value());
            map.add(THIRD, new Value());

            map.find(FIRST);
            map.find(SECOND);
            map.find(FIRST);
            map.find(FIRST);
            map.find(SECOND);
            map.find(FIRST);
            
            map.add(FOURTH, new Value());
            map.add(FIFTH, new Value());

            return MAP_SIZE + map.size();
        };

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Future<String> futureFromCallable = executorService.submit(callableTask);
        Future<String> futureFromCallableOne = executorService.submit(callableTask);

        String result = futureFromCallable.get();
        String resultOne = futureFromCallableOne.get();

        executorService.shutdown();

        System.out.printf(RESULT_S_N, result);
        System.out.printf(RESULT_ONE_S_N, resultOne);
    }

}
