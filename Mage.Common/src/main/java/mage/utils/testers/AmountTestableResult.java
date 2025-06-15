package mage.utils.testers;

import java.util.List;

/**
 * Part of testable game dialogs
 *
 * @author JayDi85
 */
public class AmountTestableResult extends BaseTestableResult {

    int amount = 0;

    public void onFinish(String resDebugSource, boolean status, List<String> info, int amount) {
        this.onFinish(resDebugSource, status, info);
        this.amount = amount;
    }

    @Override
    public String getResAssert() {
        return null; // TODO: implement
    }

    @Override
    public void onClear() {
        super.onClear();
        this.amount = 0;
    }
}
