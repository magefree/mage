package mage.abilities.common;

import mage.abilities.SpellAbility;
import mage.abilities.costs.common.CommanderAdditionalCost;
import mage.cards.Card;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;

/**
 * @author Plopman, JayDi85
 */
public class CastCommanderAbility extends SpellAbility {

    public CastCommanderAbility(Card card) {
        super(card.getManaCost(), card.getName(), Zone.COMMAND, SpellAbilityType.BASE);
        if (card.getSpellAbility() != null) {
            this.getCosts().addAll(card.getSpellAbility().getCosts().copy());
            this.getEffects().addAll(card.getSpellAbility().getEffects().copy());
            this.getTargets().addAll(card.getSpellAbility().getTargets().copy());
            this.timing = card.getSpellAbility().getTiming();

            // extra cost
            this.addCost(new CommanderAdditionalCost());
        } else {
            throw new IllegalStateException("Cast commander ability must be used with spell ability only: " + card.getName());
        }
        this.usesStack = true;
        this.controllerId = card.getOwnerId();
        this.sourceId = card.getId();
    }

    public CastCommanderAbility(final CastCommanderAbility ability) {
        super(ability);
    }

    @Override
    public CastCommanderAbility copy() {
        return new CastCommanderAbility(this);
    }

}
