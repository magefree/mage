package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.OpponentsLostLifeCondition;
import mage.abilities.decorator.ConditionalReplacementEffect;
import mage.abilities.effects.common.EntersWithCountersControlledEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class VampireSocialite extends CardImpl {

    private static final FilterControlledPermanent filter =
            new FilterControlledPermanent(SubType.VAMPIRE, "other Vampire you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public VampireSocialite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // When Vampire Socialite enters the battlefield, if an opponent lost life this turn, put a +1/+1 counter on each other Vampire you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter)
        ).withInterveningIf(OpponentsLostLifeCondition.instance));

        // As long as an opponent lost life this turn, each other Vampire you control enters the battlefield with an additional +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(new ConditionalReplacementEffect(
                new EntersWithCountersControlledEffect(
                        filter, CounterType.P1P1.createInstance(), true
                ), OpponentsLostLifeCondition.instance
        ).setText("as long as an opponent lost life this turn, " +
                "each other Vampire you control enters with an additional +1/+1 counter on it")));
    }

    private VampireSocialite(final VampireSocialite card) {
        super(card);
    }

    @Override
    public VampireSocialite copy() {
        return new VampireSocialite(this);
    }
}
