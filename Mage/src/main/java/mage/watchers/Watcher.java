package mage.watchers;

import mage.cards.Cards;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.PlayerList;
import mage.util.Copyable;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.lang.reflect.*;
import java.util.*;

/**
 * watches for certain game events to occur and flags condition
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class Watcher implements Serializable {

    private static final Logger logger = Logger.getLogger(Watcher.class);

    protected UUID controllerId;
    protected UUID sourceId;
    protected boolean condition;
    protected final WatcherScope scope;

    public Watcher(WatcherScope scope) {
        this.scope = scope;
    }

    public Watcher(final Watcher watcher) {
        this.condition = watcher.condition;
        this.controllerId = watcher.controllerId;
        this.sourceId = watcher.sourceId;
        this.scope = watcher.scope;
    }

    public UUID getControllerId() {
        return controllerId;
    }

    public void setControllerId(UUID controllerId) {
        this.controllerId = controllerId;
    }

    public UUID getSourceId() {
        return sourceId;
    }

    public void setSourceId(UUID sourceId) {
        this.sourceId = sourceId;
    }

    public String getKey() {
        switch (scope) {
            case GAME:
                return getBasicKey();
            case PLAYER:
                return controllerId + getBasicKey();
            case CARD:
                return sourceId + getBasicKey();
            default:
                return getBasicKey();
        }
    }

    public boolean conditionMet() {
        return condition;
    }

    public void reset() {
        condition = false;
    }

    protected String getBasicKey() {
        return getClass().getSimpleName();
    }

    public abstract void watch(GameEvent event, Game game);

    public <T extends Watcher> T copy() {
        try {
            //use getDeclaredConstructors to allow for package-private constructors (i.e. omit public)
            List<?> constructors = Arrays.asList(this.getClass().getDeclaredConstructors());
            if (constructors.size() > 1) {
                logger.error(getClass().getSimpleName() + " has multiple constructors");
                return null;
            }

            Constructor<? extends Watcher> constructor = (Constructor<? extends Watcher>) constructors.get(0);

            // collect all fields
            constructor.setAccessible(true);
            Object[] args = new Object[constructor.getParameterCount()];
            for (int index = 0; index < constructor.getParameterTypes().length; index++) {
                Class<?> parameterType = constructor.getParameterTypes()[index];
                if (parameterType.isPrimitive()) {
                    if (parameterType.getSimpleName().equalsIgnoreCase("boolean")) {
                        args[index] = false;
                    }
                } else {
                    args[index] = null;
                }

            }
            T watcher = (T) constructor.newInstance(args);
            List<Field> allFields = new ArrayList<>();
            allFields.addAll(Arrays.asList(getClass().getDeclaredFields()));
            allFields.addAll(Arrays.asList(getClass().getSuperclass().getDeclaredFields()));

            // copy field's values
            for (Field field : allFields) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    field.setAccessible(true);

                    if (field.getType() == Set.class) {
                        // Set<UUID, xxx>
                        ((Set) field.get(watcher)).clear();
                        ((Set) field.get(watcher)).addAll((Set) field.get(this));
                    } else if (field.getType() == Map.class || field.getType() == HashMap.class) {
                        // Map<UUID, xxx>
                        ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                        Type valueType = parameterizedType.getActualTypeArguments()[1];
                        if (valueType.getTypeName().contains("SortedSet")) {
                            // Map<UUID, SortedSet<Object>>
                            Map<Object, Set<Object>> source = (Map<Object, Set<Object>>) field.get(this);
                            Map<Object, Set<Object>> target = (Map<Object, Set<Object>>) field.get(watcher);
                            target.clear();
                            for (Map.Entry<Object, Set<Object>> e : source.entrySet()) {
                                Set<Object> set = new TreeSet<>();
                                set.addAll(e.getValue());
                                target.put(e.getKey(), set);
                            }
                        } else if (valueType.getTypeName().contains("Set")) {
                            // Map<UUID, Set<Object>>
                            Map<Object, Set<Object>> source = (Map<Object, Set<Object>>) field.get(this);
                            Map<Object, Set<Object>> target = (Map<Object, Set<Object>>) field.get(watcher);
                            target.clear();
                            for (Map.Entry<Object, Set<Object>> e : source.entrySet()) {
                                Set<Object> set = new HashSet<>();
                                set.addAll(e.getValue());
                                target.put(e.getKey(), set);
                            }
                        } else if (valueType.getTypeName().contains("PlayerList")) {
                            // Map<UUID, PlayerList>
                            Map<Object, PlayerList> source = (Map<Object, PlayerList>) field.get(this);
                            Map<Object, PlayerList> target = (Map<Object, PlayerList>) field.get(watcher);
                            target.clear();
                            for (Map.Entry<Object, PlayerList> e : source.entrySet()) {
                                PlayerList list = e.getValue().copy();
                                target.put(e.getKey(), list);
                            }
                        } else if (valueType.getTypeName().endsWith("Cards")) {
                            // Map<UUID, Cards>
                            Map<Object, Cards> source = (Map<Object, Cards>) field.get(this);
                            Map<Object, Cards> target = (Map<Object, Cards>) field.get(watcher);
                            target.clear();
                            for (Map.Entry<Object, Cards> e : source.entrySet()) {
                                Cards list = e.getValue().copy();
                                target.put(e.getKey(), list);
                            }
                        } else if (valueType instanceof Class &&  Arrays.stream(((Class) valueType).getInterfaces()).anyMatch(c -> c.equals(Copyable.class))) {
                            // Map<UUID, Copyable>
                            Map<Object, Copyable> source = (Map<Object, Copyable>) field.get(this);
                            Map<Object, Copyable> target = (Map<Object, Copyable>) field.get(watcher);
                            target.clear();
                            for (Map.Entry<Object, Copyable> e : source.entrySet()) {
                                Copyable object = (Copyable) e.getValue().copy();
                                target.put(e.getKey(), object);
                            }
                        } else if (valueType.getTypeName().contains("List")) {
                            // Map<UUID, List<Object>>
                            Map<Object, List<Object>> source = (Map<Object, List<Object>>) field.get(this);
                            Map<Object, List<Object>> target = (Map<Object, List<Object>>) field.get(watcher);
                            target.clear();
                            for (Map.Entry<Object, List<Object>> e : source.entrySet()) {
                                List<Object> list = new ArrayList<>();
                                list.addAll(e.getValue());
                                target.put(e.getKey(), list);
                            }
                        } else if (valueType.getTypeName().contains("Map")) {
                            // Map<UUID, Map<UUID, Object>>
                            Map<Object, Map<Object, Object>> source = (Map<Object, Map<Object, Object>>) field.get(this);
                            Map<Object, Map<Object, Object>> target = (Map<Object, Map<Object, Object>>) field.get(watcher);
                            target.clear();
                            for (Map.Entry<Object, Map<Object, Object>> e : source.entrySet()) {
                                Map<Object, Object> map = new HashMap<>();
                                map.putAll(e.getValue());
                                target.put(e.getKey(), map);
                            }
                        } else {
                            // Map<UUID, Object>
                            // TODO: add additional tests to find unsupported watcher data

                            ((Map) field.get(watcher)).putAll((Map) field.get(this));
                        }
                    } else if (field.getType() == List.class) {
                        // List<Object>
                        ((List) field.get(watcher)).clear();
                        ((List) field.get(watcher)).addAll((List) field.get(this));
                    } else {
                        // Object
                        field.set(watcher, field.get(this));
                    }
                }
            }
            return watcher;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            logger.error("Can't copy watcher: " + e.getMessage(), e);
        }
        return null;
    }

    public WatcherScope getScope() {
        return scope;
    }
}
