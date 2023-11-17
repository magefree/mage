package mage.watchers;

import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;
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

    protected Watcher(final Watcher watcher) {
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
                    field.set(watcher, CardUtil.deepCopyObject(field.get(this)));
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
