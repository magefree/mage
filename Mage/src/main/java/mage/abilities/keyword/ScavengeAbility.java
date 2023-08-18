

package mage.abilities.keyword;

import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.Card;
import mage.counters.CounterType;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author magenoxx_at_gmail.com
 */


//
//    702.95. Scavenge
//
//    702.95a Scavenge is an activated ability that functions only while the card
//    with scavenge is in a graveyard. "Scavenge [cost]" means "[Cost], Exile this
//    card from your graveyard: Put a number of +1/+1 counters equal to this card's
//    power on target creature. Activate this ability only any time you could cast
//    a sorcery."
//

public class ScavengeAbility extends ActivatedAbilityImpl {

    public ScavengeAbility(ManaCosts costs) {
        super(Zone.GRAVEYARD, new ScavengeEffect(), costs);
        this.timing = TimingRule.SORCERY;
        this.addCost(new ExileSourceFromGraveCost());
        this.addTarget(new TargetCreaturePermanent());
    }

    protected ScavengeAbility(final ScavengeAbility ability) {
        super(ability);
    }

    @Override
    public ScavengeAbility copy() {
        return new ScavengeAbility(this);
    }

    @Override
    public String getRule() {
        return "Scavenge " + getManaCosts().getText() + " <i>(" + getManaCosts().getText() + ", Exile this card from your graveyard: Put a number of +1/+1 counter's equal to this card's power on target creature. Scavenge only as a sorcery.)</i>";
    }
}

class ScavengeEffect extends OneShotEffect {
    ScavengeEffect() {
        super(Outcome.BoostCreature);
    }

    ScavengeEffect(final ScavengeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            int count = card.getPower().getValue();
            if (count > 0) {
                Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(count));
                effect.setTargetPointer(getTargetPointer());
                return effect.apply(game, source);
            }
        }

        return false;
    }

    @Override
    public ScavengeEffect copy() {
        return new ScavengeEffect(this);
    }
}