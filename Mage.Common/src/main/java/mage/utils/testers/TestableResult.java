package mage.utils.testers;

import java.util.List;

/**
 * Part of testable game dialogs, must contain dialogs result
 *
 * @author JayDi85
 */
public interface TestableResult {

    boolean getStatus();

    List<String> getInfo();

    /**
     * Save new result after show dialog
     *
     * @param status result of choice dialog call
     * @param info   detail result to show in GUI
     */
    void save(boolean status, List<String> info);

    boolean isSaved();

    void clear();

    boolean isOk();
}
