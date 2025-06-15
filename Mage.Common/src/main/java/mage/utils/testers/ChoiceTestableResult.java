package mage.utils.testers;

import java.util.List;

/**
 * Part of testable game dialogs
 *
 * @author JayDi85
 */
public class ChoiceTestableResult extends BaseTestableResult {

    String choice = null;

    public void onFinish(String resDebugSource, boolean status, List<String> info, String choice) {
        this.onFinish(resDebugSource, status, info);
        this.choice = choice;
    }

    @Override
    public String getResAssert() {
        return null; // TODO: implement
    }

    @Override
    public void onClear() {
        super.onClear();
        this.choice = null;
    }
}
