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

    boolean aiAssert = false;
    boolean aiMustChooseStatus = false;
    int aiMustChooseTargetsCount = 0;

    public void save(boolean status, List<String> info, Target target) {
        this.save(status, info);
        this.target = target;
    }

    @Override
    public boolean isOk() {
        if (!this.aiAssert) {
            return true;
        }

        // not finish
        if (this.target == null) {
            return false;
        }

        // wrong choose
        if (this.getStatus() != this.aiMustChooseStatus) {
            return false;
        }

        // wrong targets
        if (this.target.getTargets().size() != this.aiMustChooseTargetsCount) {
            return false;
        }

        // all fine
        return true;
    }

    @Override
    public void clear() {
        super.clear();
        this.target = null;
    }
}
