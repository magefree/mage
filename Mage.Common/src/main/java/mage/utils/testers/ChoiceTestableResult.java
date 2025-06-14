package mage.utils.testers;

import java.util.List;

/**
 * Part of testable game dialogs
 *
 * @author JayDi85
 */
public class ChoiceTestableResult extends BaseTestableResult {

    String choice = null;

    public void onFinish(boolean status, List<String> info, String choice) {
        this.onFinish(status, info);
        this.choice = choice;
    }

    @Override
    public Boolean getResAssert() {
        return null; // TODO: implement
    }

    @Override
    public void onClear() {
        super.onClear();
        this.choice = null;
    }
}
