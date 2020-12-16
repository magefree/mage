package mage.target.targetpointer;

import java.util.HashMap;
import java.util.Map;

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
}
