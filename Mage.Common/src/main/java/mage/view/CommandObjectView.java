package mage.view;

import java.util.List;
import java.util.UUID;

/**
 * @author Plopman
 */
public interface CommandObjectView extends SelectableObjectView {

    String getExpansionSetCode();

    String getName();

    UUID getId();

    int getImageNumber();

    List<String> getRules();
}
