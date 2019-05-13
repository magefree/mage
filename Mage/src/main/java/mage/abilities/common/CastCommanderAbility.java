package mage.abilities.common;

import mage.abilities.SpellAbility;
import mage.abilities.costs.CostsImpl;
import mage.cards.Card;
import mage.constants.SpellAbilityType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;

/**
 * @author Plopman
 */
public class CastCommanderAbility extends SpellAbility {

    public CastCommanderAbility(Card card) {
        super(card.getManaCost(), card.getName(), Zone.COMMAND, SpellAbilityType.BASE);
        if (card.getSpellAbility() != null) {
            this.getCosts().addAll(card.getSpellAbility().getCosts().copy());
            this.getEffects().addAll(card.getSpellAbility().getEffects().copy());
            this.getTargets().addAll(card.getSpellAbility().getTargets().copy());
            this.timing = card.getSpellAbility().getTiming();
        } else {
            this.costs = new CostsImpl<>();
            this.timing = TimingRule.SORCERY;
        }
        this.usesStack = true;
        this.controllerId = card.getOwnerId();
        this.sourceId = card.getId();
    }

    public CastCommanderAbility(final CastCommanderAbility ability) {
        super(ability);
    }

    @Override
    public boolean activate(Game game, boolean noMana) {
        if (super.activate(game, noMana)) {
            // save amount of times commander was cast
            Integer castCount = (Integer) game.getState().getValue(sourceId + "_castCount");
            if (castCount == null) {
                castCount = 1;
            } else {
                castCount++;
            }
            game.getState().setValue(sourceId + "_castCount", castCount);
            return true;
        }
        return false;
    }

    @Override
    public CastCommanderAbility copy() {
        return new CastCommanderAbility(this);
    }

}
