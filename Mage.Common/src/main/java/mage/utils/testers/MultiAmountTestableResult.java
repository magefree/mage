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

    public void save(boolean status, List<String> info, List<Integer> values) {
        this.save(status, info);
        this.values = values;
    }

    @Override
    public boolean isOk() {
        return true;
    }

    @Override
    public void clear() {
        super.clear();
        this.values.clear();
    }
}
