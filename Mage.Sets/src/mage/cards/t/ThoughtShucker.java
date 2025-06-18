package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class ThoughtShucker extends CardImpl {

    public ThoughtShucker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Threshold -- {1}{U}: Put a +1/+1 counter on Thought Shucker and draw a card. Activate only if seven or more cards are in your graveyard and only once.
        this.addAbility(new ThoughtShuckerActivatedAbility());
    }

    private ThoughtShucker(final ThoughtShucker card) {
        super(card);
    }

    @Override
    public ThoughtShucker copy() {
        return new ThoughtShucker(this);
    }
}

class ThoughtShuckerActivatedAbility extends ActivateIfConditionActivatedAbility {
    ThoughtShuckerActivatedAbility() {
        super(new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                new ManaCostsImpl<>("{1}{U}"), ThresholdCondition.instance);
        addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        setAbilityWord(AbilityWord.THRESHOLD);
        maxActivationsPerGame = 1;
    }

    protected ThoughtShuckerActivatedAbility(final ThoughtShuckerActivatedAbility ability) {
        super(ability);
    }

    @Override
    public ThoughtShuckerActivatedAbility copy() {
        return new ThoughtShuckerActivatedAbility(this);
    }

    @Override
    public String getRule() {
        String rule = super.getRule();
        int len = rule.length();
        return rule.substring(0, len - 1) + " and only once.";
    }
}
