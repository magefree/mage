

package mage.view;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Plopman
 */
public interface CommandObjectView extends Serializable {
    public String getExpansionSetCode();

    public String getName();

    public UUID getId();

    public List<String> getRules();
}
