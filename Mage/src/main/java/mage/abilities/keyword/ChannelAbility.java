package mage.abilities.keyword;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.constants.AbilityWord;
import mage.constants.TimingRule;
import mage.constants.Zone;


public class ChannelAbility extends ActivatedAbilityImpl {

    public ChannelAbility(String manaString, Effect effect) {
        this(manaString, effect, TimingRule.INSTANT);
    }

    public ChannelAbility(String manaString, Effect effect, TimingRule timing) {
        super(Zone.HAND, effect, new ManaCostsImpl<>(manaString));
        this.addCost(new DiscardSourceCost());
        this.timing = timing;
        this.setAbilityWord(AbilityWord.CHANNEL);
    }

    public ChannelAbility(final ChannelAbility ability) {
        super(ability);
    }

    @Override
    public ChannelAbility copy() {
        return new ChannelAbility(this);
    }

    @Override
    public String getRule() {
        if (this.timing == TimingRule.SORCERY) {
            return super.getRule() + " Activate only as a sorcery.";
        }
        return super.getRule();
    }
}
