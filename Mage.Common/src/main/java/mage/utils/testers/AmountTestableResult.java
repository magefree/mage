package mage.utils.testers;

import java.util.List;

/**
 * Part of testable game dialogs
 *
 * @author JayDi85
 */
public class AmountTestableResult extends BaseTestableResult {

    Integer amount = null;

    boolean aiAssertEnabled = false;
    int aiAssertMinAmount = 0;
    int aiAssertMaxAmount = 0;

    public void onFinish(String resDebugSource, boolean status, List<String> info, int amount) {
        this.onFinish(resDebugSource, status, info);
        this.amount = amount;
    }

    @Override
    public String getResAssert() {
        if (!this.aiAssertEnabled) {
            return null;
        }

        // not finished
        if (this.amount == null) {
            return null;
        }

        if (!this.getResStatus()) {
            return String.format("Wrong status: need %s, but get %s",
                    true, // res must be true all the time
                    this.getResStatus()
            );
        }

        // wrong amount
        if (this.amount < this.aiAssertMinAmount || this.amount > this.aiAssertMaxAmount) {
            return String.format("Wrong amount: need [%d, %d], but get %d",
                    this.aiAssertMinAmount,
                    this.aiAssertMaxAmount,
                    this.amount
            );
        }

        // all fine
        return "";
    }

    @Override
    public void onClear() {
        super.onClear();
        this.amount = null;
    }
}
