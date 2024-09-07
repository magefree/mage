package mage.target.targetpointer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JayDi85
 */
public abstract class TargetPointerImpl implements TargetPointer {

    // Store custom data here. Use it to keep unique values for ability instances on stack (example: Gruul Ragebeast)
    private Map<String, String> data;

    private boolean initialized = false;

    public TargetPointerImpl() {
        super();
    }

    protected TargetPointerImpl(final TargetPointerImpl targetPointer) {
        super();
        if (targetPointer.data != null) {
            this.data = new HashMap<>();
            this.data.putAll(targetPointer.data);
        }
        this.initialized = targetPointer.initialized;
    }

    @Override
    public boolean isInitialized() {
        return this.initialized;
    }

    @Override
    public void setInitialized() {
        this.initialized = true;
    }

    @Override
    public String getData(String key) {
        if (data == null) {
            return "";
        }
        return data.getOrDefault(key, "");
    }

    @Override
    public TargetPointer withData(String key, String value) {
        if (data == null) {
            data = new HashMap<>();
        }
        data.put(key, value);
        return this;
    }

}
