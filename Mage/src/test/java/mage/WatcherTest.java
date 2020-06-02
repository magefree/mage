package mage;

import static mage.constants.WatcherScope.GAME;
import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableSet;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;
import org.junit.Test;

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
  public void testDeepCopy() {
    // Given
    Map<String, List<String>> listInMapField = new HashMap<>();
    List<String> list1 = new ArrayList<>();
    list1.add("v1");
    list1.add("v1.1");
    List<String> list2 = new ArrayList<>();
    list2.add("v2");
    listInMapField.put("k1", list1);
    listInMapField.put("k2", list2);

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
    testWatcher.setListInMapField(listInMapField);
    testWatcher.setSetInMapField(setInMapField);

    // When
    TestWatcher copy = testWatcher.copy();

    // And
    testWatcher.getListInMapField().get("k1").add("v1.2");
    List<String> list3 = new ArrayList<>();
    list3.add("v5");
    testWatcher.getListInMapField().put("k5", list3);
    testWatcher.getSetInMapField().get("k3").add("v3.1");

    // Then
    Map<String, List<String>> copyListInMap = copy.getListInMapField();
    assertEquals(2, copyListInMap.size());
    assertTrue(copyListInMap.containsKey("k1"));
    assertEquals(ImmutableList.of("v1", "v1.1"), copyListInMap.get("k1"));
    assertTrue(copyListInMap.containsKey("k2"));
    assertEquals(ImmutableList.of("v2"), copyListInMap.get("k2"));

    Map<String, Set<String>> copySetInMap = copy.getSetInMapField();
    assertEquals(3, copySetInMap.size());
    assertTrue(copySetInMap.containsKey("k3"));
    assertEquals(ImmutableSet.of("v3"), copySetInMap.get("k3"));
    assertTrue(copySetInMap.containsKey("k4"));
    assertEquals(ImmutableSet.of("v4", "v4.1"), copySetInMap.get("k4"));
    assertTrue(copySetInMap.containsKey("k"));
    assertEquals(ImmutableSet.of(), copySetInMap.get("k"));
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
  }
}
