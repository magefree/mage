

package mage.abilities;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SpecialActions extends AbilitiesImpl<SpecialAction> {

    public SpecialActions() {}

    public SpecialActions(final SpecialActions actions) {
        super(actions);
    }

    /**
     *
     * @param controllerId
     * @param manaAction true  = if mana actions should get returned
     *                   false = only non mana actions get returned
     * @return
     */
    public Map<UUID, SpecialAction> getControlledBy(UUID controllerId, boolean manaAction) {
        LinkedHashMap<UUID, SpecialAction> controlledBy = new LinkedHashMap<>();
        for (SpecialAction action: this) {
            if (action.isControlledBy(controllerId) && action.isManaAction() == manaAction) {
                controlledBy.put(action.id, action);
            }
        }
        return controlledBy;
    }

    @Override
    public SpecialActions copy() {
        return new SpecialActions(this);
    }

    public void removeManaActions() {
        this.removeIf(SpecialAction::isManaAction);
    }
}
