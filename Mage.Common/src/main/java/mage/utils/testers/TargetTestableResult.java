package mage.utils.testers;

import mage.target.Target;

import java.util.List;

/**
 * Part of testable game dialogs
 *
 * @author JayDi85
 */
public class TargetTestableResult extends BaseTestableResult {

    Target target = null;

    boolean aiAssertEnabled = false;
    boolean aiAssertResStatus = false;
    int aiAssertTargetsCount = 0;

    public void onFinish(boolean status, List<String> info, Target target) {
        this.onFinish(status, info);
        this.target = target;
    }

    @Override
    public Boolean getResAssert() {
        if (!this.aiAssertEnabled) {
            return null;
        }

        // not finished
        if (this.target == null) {
            return null;
        }

        // wrong choose
        if (this.getResStatus() != this.aiAssertResStatus) {
            return false;
        }

        // wrong targets
        if (this.target.getTargets().size() != this.aiAssertTargetsCount) {
            return false;
        }

        // all fine
        return true;
    }

    @Override
    public void onClear() {
        super.onClear();
        this.target = null;
    }
}
