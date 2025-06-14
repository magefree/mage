package mage.utils.testers;

import java.util.List;

/**
 * Part of testable game dialogs
 *
 * @author JayDi85
 */
public class AmountTestableResult extends BaseTestableResult {

    int amount = 0;

    public void save(boolean status, List<String> info, int amount) {
        this.save(status, info);
        this.amount = amount;
    }

    @Override
    public boolean isOk() {
        return true;
    }

    @Override
    public void clear() {
        super.clear();
        this.amount = 0;
    }
}
