package mage.utils.testers;

import java.util.ArrayList;
import java.util.List;

/**
 * Part of testable game dialogs
 *
 * @author JayDi85
 */
public class MultiAmountTestableResult extends BaseTestableResult {

    List<Integer> values = new ArrayList<>();

    public void onFinish(boolean status, List<String> info, List<Integer> values) {
        this.onFinish(status, info);
        this.values = values;
    }

    @Override
    public String getResAssert() {
        return null; // TODO: implement
    }

    @Override
    public void onClear() {
        super.onClear();
        this.values.clear();
    }
}
