package mage.abilities;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Special actions to activate at any priority time (GUI has special button to show a special commands list)
 * <p>
 * Two types of action:
 * - mana actions (auto-generated on each mana pay cycle, auto-clean)
 * - another actions (manual added, manual removed - like one short effects)
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SpecialActions extends AbilitiesImpl<SpecialAction> {

    public SpecialActions() {
    }

    protected SpecialActions(final SpecialActions actions) {
        super(actions);
    }

    /**
     * @param controllerId
     * @param manaAction   true  = if mana actions should get returned
     *                     false = only non mana actions get returned
     * @return
     */
    public Map<UUID, SpecialAction> getControlledBy(UUID controllerId, boolean manaAction) {
        LinkedHashMap<UUID, SpecialAction> controlledBy = new LinkedHashMap<>();
        for (SpecialAction action : this) {
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
