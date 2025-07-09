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

    public void onFinish(String resDebugSource, boolean status, List<String> info, Target target) {
        this.onFinish(resDebugSource, status, info);
        this.target = target;
    }

    @Override
    public String getResAssert() {
        if (!this.aiAssertEnabled) {
            return null;
        }

        // not finished
        if (this.target == null) {
            return null;
        }

        // wrong choose
        if (this.getResStatus() != this.aiAssertResStatus) {
            return String.format("Wrong status: need %s, but get %s",
                    this.aiAssertResStatus,
                    this.getResStatus()
            );
        }

        // wrong targets
        if (this.target.getTargets().size() != this.aiAssertTargetsCount) {
            return String.format("Wrong targets count: need %d, but get %d",
                    this.aiAssertTargetsCount,
                    this.target.getTargets().size()
            );
        }

        // all fine
        return "";
    }

    @Override
    public void onClear() {
        super.onClear();
        this.target = null;
    }
}
