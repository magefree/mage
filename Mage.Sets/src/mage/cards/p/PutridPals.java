package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.watchers.common.RevoltWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PutridPals extends CardImpl {

    public PutridPals(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B/G}{B/G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.OOZE);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Disappear -- This creature enters with two +1/+1 counters on it if a permanent left the battlefield under your control this turn.
        this.addAbility(new EntersBattlefieldAbility(new ConditionalOneShotEffect(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), RevoltCondition.instance, ""
        ), "with two +1/+1 counters on it if a permanent left the battlefield under your control this turn")
                .addHint(RevoltCondition.getHint()).setAbilityWord(AbilityWord.DISAPPEAR), new RevoltWatcher());
    }

    private PutridPals(final PutridPals card) {
        super(card);
    }

    @Override
    public PutridPals copy() {
        return new PutridPals(this);
    }
}
