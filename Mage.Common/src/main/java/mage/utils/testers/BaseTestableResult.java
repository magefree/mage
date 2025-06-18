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
    String resDebugSource = ""; // source code line to find starting place to debug
    boolean resStatus = false;
    List<String> resInfo = new ArrayList<>();

    @Override
    public String getResDebugSource() {
        return this.resDebugSource;
    }

    @Override
    public boolean getResStatus() {
        return this.resStatus;
    }

    @Override
    public List<String> getResDetails() {
        return this.resInfo;
    }

    @Override
    public String getResAssert() {
        return null; // TODO: implement
    }

    @Override
    public void onFinish(String resDebugSource, boolean resStatus, List<String> resDetails) {
        this.isFinished = true;
        this.resDebugSource = resDebugSource;
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
