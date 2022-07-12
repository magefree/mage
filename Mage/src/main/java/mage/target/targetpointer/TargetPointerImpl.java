package mage.target.targetpointer;

import mage.game.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author JayDi85
 */
public abstract class TargetPointerImpl implements TargetPointer {

    // Store custom data here. Use it to keep unique values for ability instances on stack (example: Gruul Ragebeast)
    Map<String, String> data = new HashMap<>();

    public TargetPointerImpl() {
        super();
    }

    public TargetPointerImpl(final TargetPointerImpl targetPointer) {
        super();
        this.data.putAll(targetPointer.data);
    }

    @Override
    public String getData(String key) {
        return this.data.getOrDefault(key, "");
    }

    @Override
    public TargetPointer withData(String key, String value) {
        this.data.put(key, value);
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return false;
        }

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        TargetPointerImpl that = (TargetPointerImpl) obj;

        if ((this.data == null ^ that.data == null) || this.data == null) {
            return false;
        }
        if (this.data.size() != that.data.size()) {
            return false;
        }

        for (String thisKey : this.data.keySet()) {
            if (!that.data.containsKey(thisKey)) {
                return false;
            }

            if (!Objects.equals(this.data.get(thisKey), that.data.get(thisKey))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean equivalent(Object obj, Game game) {
        return this.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
