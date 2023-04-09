package mage;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static mage.constants.WatcherScope.GAME;
import static org.junit.Assert.*;

public class WatcherTest {

    @Test
    public void testShallowCopy() {
        // Given
        Map<String, String> mapField = new HashMap<>();
        mapField.put("mapFieldKey1", "mapFieldValue1");
        mapField.put("mapFieldKey2", "mapFieldValue2");

        TestWatcher testWatcher = new TestWatcher(GAME);
        testWatcher.setStringField("stringField");
        testWatcher.setSetField(set("setField1", "setField2"));
        testWatcher.setMapField(mapField);

        // When
        TestWatcher copy = testWatcher.copy();

        // And
        testWatcher.getSetField().add("setField3");
        mapField.put("mapFieldKey3", "mapFieldValue3");

        // Then
        assertEquals("stringField", copy.getStringField());
        assertEquals(set("setField1", "setField2"), copy.getSetField());
        assertEquals(ImmutableMap.of("mapFieldKey1", "mapFieldValue1", "mapFieldKey2", "mapFieldValue2"), copy.getMapField());
    }

    @Test
    public void testDeepCopyMapOfList() {
        // Given
        Map<String, List<String>> listInMapField = new HashMap<>();
        List<String> list1 = new ArrayList<>();
        list1.add("v1");
        list1.add("v1.1");
        List<String> list2 = new ArrayList<>();
        list2.add("v2");
        listInMapField.put("k1", list1);
        listInMapField.put("k2", list2);

        TestWatcher testWatcher = new TestWatcher(GAME);
        testWatcher.setListInMapField(listInMapField);

        // When
        TestWatcher copy = testWatcher.copy();

        // And
        testWatcher.getListInMapField().get("k1").add("v1.2");
        List<String> list3 = new ArrayList<>();
        list3.add("v5");
        testWatcher.getListInMapField().put("k5", list3);

        // Then
        Map<String, List<String>> copyListInMap = copy.getListInMapField();
        assertEquals(2, copyListInMap.size());
        assertTrue(copyListInMap.containsKey("k1"));
        assertEquals(copyListInMap.get("k1").getClass(), listInMapField.get("k1").getClass());
        assertEquals(ImmutableList.of("v1", "v1.1"), copyListInMap.get("k1"));
        assertTrue(copyListInMap.containsKey("k2"));
        assertEquals(ImmutableList.of("v2"), copyListInMap.get("k2"));
    }

    @Test
    public void testDeepCopyMapOfSet() {
        // Given
        Map<String, Set<String>> setInMapField = new HashMap<>();
        Set<String> set1 = new HashSet<>();
        set1.add("v3");
        Set<String> set2 = new HashSet<>();
        set2.add("v4");
        set2.add("v4.1");
        setInMapField.put("k3", set1);
        setInMapField.put("k4", set2);
        setInMapField.put("k", new HashSet<String>());

        TestWatcher testWatcher = new TestWatcher(GAME);
        testWatcher.setSetInMapField(setInMapField);

        // When
        TestWatcher copy = testWatcher.copy();

        // And
        testWatcher.getSetInMapField().get("k3").add("v3.1");

        // Then
        Map<String, Set<String>> copySetInMap = copy.getSetInMapField();
        assertEquals(3, copySetInMap.size());
        assertTrue(copySetInMap.containsKey("k3"));
        assertEquals(copySetInMap.get("k3").getClass(), copySetInMap.get("k3").getClass());
        assertEquals(ImmutableSet.of("v3"), copySetInMap.get("k3"));
        assertTrue(copySetInMap.containsKey("k4"));
        assertEquals(ImmutableSet.of("v4", "v4.1"), copySetInMap.get("k4"));
        assertTrue(copySetInMap.containsKey("k"));
        assertEquals(ImmutableSet.of(), copySetInMap.get("k"));
    }

    @Test
    public void testDeepCopyMapOfMap() {
        // Given
        Map<String, Map<String, String>> mapInMapField = new HashMap<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("k1.1", "v1.1");
        map1.put("k1.2", "v1.2");
        Map<String, String> map2 = new HashMap<>();
        map2.put("k2.1", "v2.1");
        mapInMapField.put("k1", map1);
        mapInMapField.put("k2", map2);

        TestWatcher testWatcher = new TestWatcher(GAME);
        testWatcher.setMapInMapField(mapInMapField);

        // When
        TestWatcher copy = testWatcher.copy();

        // And
        testWatcher.getMapInMapField().get("k2").put("k2.2", "v2.2");
        testWatcher.getMapInMapField().put("k3", new HashMap<>());

        // Then
        Map<String, Map<String, String>> copyMapInMap = copy.getMapInMapField();
        assertEquals(2, copyMapInMap.size());
        assertTrue(copyMapInMap.containsKey("k1"));
        assertEquals(copyMapInMap.get("k1").getClass(), mapInMapField.get("k1").getClass());
        assertEquals(ImmutableMap.of("k1.1", "v1.1", "k1.2", "v1.2"), copyMapInMap.get("k1"));
        assertTrue(copyMapInMap.containsKey("k2"));
        assertEquals(ImmutableMap.of("k2.1", "v2.1"), copyMapInMap.get("k2"));
        assertFalse(copyMapInMap.containsKey("k3"));
    }

    @Test
    public void testDeepCopyMapOfSortedSet() {
        // Given
        Map<String, SortedSet<String>> sortedSetInMapField = new HashMap<>();
        sortedSetInMapField.put("k1", new TreeSet<>(Arrays.asList("v1_1", "v1_2")));
        sortedSetInMapField.put("k2", new TreeSet<>(Arrays.asList("v2_1", "v2_2")));

        TestWatcher testWatcher = new TestWatcher(GAME);
        testWatcher.setSortedSetInMapField(sortedSetInMapField);

        // When
        TestWatcher copy = testWatcher.copy();

        // And
        testWatcher.getSortedSetInMapField().get("k2").add("v2_3");
        testWatcher.getSortedSetInMapField().put("k3", new TreeSet<>());

        // Then
        Map<String, SortedSet<String>> copySortedSetInMapField = copy.getSortedSetInMapField();
        assertEquals(2, copySortedSetInMapField.size());
        assertTrue(copySortedSetInMapField.containsKey("k1"));
        assertEquals(copySortedSetInMapField.get("k1").getClass(), sortedSetInMapField.get("k1").getClass());
        assertEquals(ImmutableSortedSet.of("v1_1", "v1_2"), copySortedSetInMapField.get("k1"));
        assertTrue(copySortedSetInMapField.containsKey("k2"));
        assertEquals(ImmutableSortedSet.of("v2_1", "v2_2"), copySortedSetInMapField.get("k2"));
        assertFalse(copySortedSetInMapField.containsKey("k3"));
    }

    private Set<String> set(String... values) {
        return Stream.of(values).collect(Collectors.toSet());
    }

    public static class TestWatcher extends Watcher {
        private String stringField;
        private Set<String> setField = new HashSet<>();
        private Map<String, String> mapField = new HashMap<>();
        private Map<String, List<String>> listInMapField = new HashMap<>();
        private Map<String, Set<String>> setInMapField = new HashMap<>();
        private Map<String, Map<String, String>> mapInMapField = new HashMap<>();

        private Map<String, SortedSet<String>> sortedSetInMapField = new HashMap<>();

        public TestWatcher(WatcherScope scope) {
            super(scope);
        }

        @Override
        public void watch(GameEvent event, Game game) {
            System.out.println("watch");
        }

        public String getStringField() {
            return stringField;
        }

        public void setStringField(String stringField) {
            this.stringField = stringField;
        }

        public Set<String> getSetField() {
            return setField;
        }

        public void setSetField(Set<String> setField) {
            this.setField = setField;
        }

        public Map<String, String> getMapField() {
            return mapField;
        }

        public void setMapField(Map<String, String> mapField) {
            this.mapField = mapField;
        }

        public Map<String, List<String>> getListInMapField() {
            return listInMapField;
        }

        public void setListInMapField(Map<String, List<String>> listInMapField) {
            this.listInMapField = listInMapField;
        }

        public Map<String, Set<String>> getSetInMapField() {
            return setInMapField;
        }

        public void setSetInMapField(Map<String, Set<String>> setInMapField) {
            this.setInMapField = setInMapField;
        }

        public Map<String, Map<String, String>> getMapInMapField() {
            return mapInMapField;
        }

        public void setMapInMapField(Map<String, Map<String, String>> mapInMapField) {
            this.mapInMapField = mapInMapField;
        }

        public Map<String, SortedSet<String>> getSortedSetInMapField() {
            return sortedSetInMapField;
        }

        public void setSortedSetInMapField(Map<String, SortedSet<String>> sortedSetInMapField) {
            this.sortedSetInMapField = sortedSetInMapField;
        }
    }
}
