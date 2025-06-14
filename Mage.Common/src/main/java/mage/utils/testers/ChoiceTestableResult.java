package mage.utils.testers;

import java.util.List;

/**
 * Part of testable game dialogs
 *
 * @author JayDi85
 */
public class ChoiceTestableResult extends BaseTestableResult {

    String choice = null;

    public void save(boolean status, List<String> info, String choice) {
        this.save(status, info);
        this.choice = choice;
    }

    @Override
    public boolean isOk() {
        return true;
    }

    @Override
    public void clear() {
        super.clear();
        this.choice = null;
    }
}
