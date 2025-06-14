package mage.utils.testers;

import java.util.ArrayList;
import java.util.List;

/**
 * Part of testable game dialogs
 *
 * @author JayDi85
 */
public class BaseTestableResult implements TestableResult {

    boolean isFinished = false;
    boolean resStatus = false;
    List<String> resInfo = new ArrayList<>();

    @Override
    public boolean getResStatus() {
        return this.resStatus;
    }

    @Override
    public List<String> getResDetails() {
        return this.resInfo;
    }

    @Override
    public Boolean getResAssert() {
        return null; // TODO: implement
    }

    @Override
    public void onFinish(boolean resStatus, List<String> resDetails) {
        this.isFinished = true;
        this.resStatus = resStatus;
        this.resInfo = resDetails;
    }

    @Override
    public boolean isFinished() {
        return this.isFinished;
    }

    @Override
    public void onClear() {
        this.isFinished = false;
        this.resStatus = false;
        this.resInfo.clear();
    }
}
