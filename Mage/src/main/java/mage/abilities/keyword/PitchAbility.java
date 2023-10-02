package mage.abilities.keyword;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.constants.AbilityWord;
import mage.constants.TimingRule;
import mage.constants.Zone;


public class PitchAbility extends ActivatedAbilityImpl {

    public PitchAbility(String manaString, Effect effect) {
        this(manaString, effect, TimingRule.INSTANT);
    }

    public PitchAbility(String manaString, Effect effect, TimingRule timing) {
        super(Zone.HAND, effect, new ManaCostsImpl<>(manaString));
        this.addCost(new DiscardSourceCost()); // TODO: instead it should go to the bottom of library at end of turn
        this.timing = timing;
        this.setAbilityWord(AbilityWord.CHANNEL);
    }

    protected PitchAbility(final PitchAbility ability) {
        super(ability);
    }

    @Override
    public PitchAbility copy() {
        return new PitchAbility(this);
    }

    @Override
    public String getRule() {
        if (this.timing == TimingRule.SORCERY) {
            return super.getRule() + " Activate only as a sorcery.";
        }
        return super.getRule();
    }
}
