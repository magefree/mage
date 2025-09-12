package mage.cards.p;

import mage.MageInt;
import mage.abilities.condition.common.CelebrationCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.watchers.common.PermanentsEnteredBattlefieldWatcher;

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
        this.addAbility(new BeginningOfCombatTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()))
                .withInterveningIf(CelebrationCondition.instance)
                .addHint(CelebrationCondition.getHint())
                .setAbilityWord(AbilityWord.CELEBRATION), new PermanentsEnteredBattlefieldWatcher()
        );
    }

    private PestsOfHonor(final PestsOfHonor card) {
        super(card);
    }

    @Override
    public PestsOfHonor copy() {
        return new PestsOfHonor(this);
    }
}
