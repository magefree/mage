package mage.utils.testers;

import java.util.List;

/**
 * Part of testable game dialogs, must contain dialogs result
 *
 * @author JayDi85
 */
public interface TestableResult {

    /**
     * Dialog's result
     */
    boolean getResStatus();

    /**
     * Dialog's detail result
     */
    List<String> getResDetails();

    /**
     * Save new result after show dialog
     *
     */
    void onFinish(boolean resStatus, List<String> resDetails);

    boolean isFinished();

    void onClear();

    Boolean getResAssert();
}
