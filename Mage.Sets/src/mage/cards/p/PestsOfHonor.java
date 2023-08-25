package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.common.CelebrationCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.watchers.common.CelebrationWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PestsOfHonor extends CardImpl {

    public PestsOfHonor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.MOUSE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Celebration - At the beginning of combat on your turn, if two or more nonland permanents entered the battlefield under your control this turn, put a +1/+1 counter on Pests of Honor.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                        TargetController.YOU, false
                ), CelebrationCondition.instance, "At the beginning of combat on your turn, if two or more " +
                "nonland permanents entered the battlefield under your control this turn, put a +1/+1 counter on {this}."
        ).addHint(CelebrationCondition.getHint()).setAbilityWord(AbilityWord.CELEBRATION), new CelebrationWatcher());
    }

    private PestsOfHonor(final PestsOfHonor card) {
        super(card);
    }

    @Override
    public PestsOfHonor copy() {
        return new PestsOfHonor(this);
    }
}
