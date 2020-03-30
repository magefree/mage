package mage;

import static mage.constants.WatcherScope.GAME;
import static org.junit.Assert.assertEquals;

import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;
import org.junit.Test;

public class WatcherTest {

  @Test
  public void test() {
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

  private Set<String> set(String... values) {
    return Stream.of(values).collect(Collectors.toSet());
  }

  public static class TestWatcher extends Watcher {
    private String stringField;
    private Set<String> setField = new HashSet<>();
    private Map<String, String> mapField = new HashMap<>();

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
  }
}
