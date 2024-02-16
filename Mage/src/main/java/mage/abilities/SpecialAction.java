package mage.abilities;

import mage.abilities.costs.mana.AlternateManaPaymentAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.mana.ManaOptions;
import mage.constants.AbilityType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public abstract class SpecialAction extends ActivatedAbilityImpl {

    private final AlternateManaPaymentAbility manaAbility; // mana actions generates on every pay cycle, no need to copy it

    public SpecialAction() {
        this(Zone.ALL);
    }

    public SpecialAction(Zone zone) {
        this(zone, null);
    }

    public SpecialAction(Zone zone, AlternateManaPaymentAbility manaAbility) {
        super(AbilityType.SPECIAL_ACTION, zone);
        this.usesStack = false;
        this.manaAbility = manaAbility;
    }

    protected SpecialAction(final SpecialAction action) {
        super(action);
        this.manaAbility = action.manaAbility;
    }

    public boolean isManaAction() {
        return manaAbility != null;
    }

    public ManaOptions getManaOptions(Ability source, Game game, ManaCost unpaid) {
        if (manaAbility != null) {
            return manaAbility.getManaOptions(source, game, unpaid);
        }
        return null;
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        if (isManaAction()) {
            // limit play mana abilities by steps
            int currentStepOrder = 0;
            if (!game.getStack().isEmpty()) {
                StackObject stackObject = game.getStack().getFirst();
                if (stackObject instanceof Spell) {
                    currentStepOrder = ((Spell) stackObject).getCurrentActivatingManaAbilitiesStep().getStepOrder();
                }
            }
            if (currentStepOrder > manaAbility.useOnActivationManaAbilityStep().getStepOrder()) {
                return ActivationStatus.getFalse();
            }
        }
        return super.canActivate(playerId, game);
    }
}
