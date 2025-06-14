package mage.utils.testers;

import java.util.ArrayList;
import java.util.List;

/**
 * Part of testable game dialogs
 *
 * @author JayDi85
 */
public class BaseTestableResult implements TestableResult {

    boolean saved = false;
    boolean status = false;
    List<String> info = new ArrayList<>();

    @Override
    public boolean getStatus() {
        return this.status;
    }

    @Override
    public List<String> getInfo() {
        return this.info;
    }

    @Override
    public void save(boolean status, List<String> info) {
        this.saved = true;
        this.status = status;
        this.info = info;
    }

    @Override
    public boolean isOk() {
        return true;
    }

    @Override
    public boolean isSaved() {
        return this.saved;
    }

    @Override
    public void clear() {
        this.saved = false;
        this.status = false;
        this.info.clear();
    }
}
