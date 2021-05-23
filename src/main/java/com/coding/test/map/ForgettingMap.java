package com.coding.test.map;

import com.coding.test.model.Value;

import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import static java.util.Objects.nonNull;

public class ForgettingMap<K,V> extends ConcurrentHashMap<K,V> {

    private static final String FINDING_KEY = "Finding key: %s%n";
    private static final String ADDING_ASSOCIATION_WITH_KEY = "Adding Association With key:  %s%n";
    private static final String INITIALISING_MAP = "Initialising map";
    private static final String MAKING_SPACE_FOR_ASSOCIATION = "Making space for association";

    private final Integer maxAssociations;
    private Integer currentAssociations;


    public ForgettingMap(Integer maxAssociations) {
        System.out.println(INITIALISING_MAP);
        this.maxAssociations = maxAssociations;
        this.currentAssociations = 0;
    }

    public synchronized void add(K key, V value){
        System.out.printf(ADDING_ASSOCIATION_WITH_KEY, key);
        if (maxAssociations > currentAssociations) {
            currentAssociations++;
            put(key, value);
        } else {
           makeSpaceForNewAssociation();
           put(key, value);
        }
    }

    public synchronized Value find(K key){
        System.out.printf(FINDING_KEY, key);
        Value value = (Value) get(key);
        if(nonNull(value)) {
            value.increaseTimesRetrieved();
        }
        return value;
    }

    public synchronized void makeSpaceForNewAssociation() {
        System.out.println(MAKING_SPACE_FOR_ASSOCIATION);
        String key = (String) this.entrySet().stream()
                .sorted(getMultipleFieldsComparator())
                .collect(Collectors.toList()).get(0).getKey();

        this.remove(key);
    }

    private Comparator<Entry<K, V>> getMultipleFieldsComparator(){
        Comparator<Entry<K, V>> timesRetrievedComparator = Entry.comparingByValue(Comparator.comparingInt(o -> ((Value) o).getTimesRetrieved()));
        Comparator<Entry<K, V>> timestampComparator = Entry.comparingByValue(Comparator.comparing(o -> ((Value) o).getCreationTimestamp()));
        return timesRetrievedComparator.thenComparing(timestampComparator);
    }
}
