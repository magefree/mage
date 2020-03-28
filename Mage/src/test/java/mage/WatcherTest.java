package mage;

import static mage.constants.WatcherScope.GAME;
import static org.junit.Assert.assertEquals;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.Map;
import java.util.Set;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;
import org.junit.Test;

public class WatcherTest {

  @Test
  public void test() {
    // Given
    TestWatcher testWatcher = new TestWatcher(GAME);
    testWatcher.setStringField("stringField");
    testWatcher.setSetField(ImmutableSet.of("setField1, setField2"));
    testWatcher.setMapField(ImmutableMap.of("mapFieldKey1, mapFieldValue1", "mapFieldKey2, mapFieldValue2"));

    // When
    TestWatcher copy = testWatcher.copy();

    // Then
    assertEquals(testWatcher.getStringField(), copy.getStringField());
    assertEquals(testWatcher.getSetField(), copy.getSetField());
    assertEquals(testWatcher.getMapField(), copy.getMapField());
  }

  public static class TestWatcher extends Watcher {
    private String stringField;
    private Set<String> setField;
    private Map<String, String> mapField;

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
