package mage.utils.testers;

import java.util.ArrayList;
import java.util.List;

/**
 * Part of testable game dialogs
 *
 * @author JayDi85
 */
public class MultiAmountTestableResult extends BaseTestableResult {

    List<Integer> selectedValues;

    boolean aiAssertEnabled = false;
    List<Integer> aiAssertValues = new ArrayList<>();

    public void onFinish(String resDebugSource, boolean status, List<String> info, List<Integer> selectedValues) {
        this.onFinish(resDebugSource, status, info);
        this.selectedValues = selectedValues;
    }

    @Override
    public String getResAssert() {
        if (!this.aiAssertEnabled) {
            return null;
        }

        // not finished
        if (this.selectedValues == null) {
            return null;
        }

        // wrong selection
        String selected = this.selectedValues.toString();
        String need = this.aiAssertValues.toString();

        if (!selected.equals(need)) {
            return String.format("Wrong selection: need %s, but get %s",
                    need,
                    selected
            );
        }

        // all fine
        return "";
    }

    @Override
    public void onClear() {
        super.onClear();
        this.selectedValues = null;
    }
}
