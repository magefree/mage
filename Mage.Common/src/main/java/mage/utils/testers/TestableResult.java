package mage.utils.testers;

import java.util.List;

/**
 * Part of testable game dialogs, must contain dialogs result
 *
 * @author JayDi85
 */
public interface TestableResult {

    /**
     * Get source code line with called dialog, use it as starting debug point
     */
    String getResDebugSource();

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
     */
    void onFinish(String chooseDebugSource, boolean resStatus, List<String> resDetails);

    boolean isFinished();

    void onClear();

    /**
     * Assert dialog result
     * - null - not ready (dev must setup wanted result)
     * - empty - good
     * - not empty - fail (return error message)
     */
    String getResAssert();
}
