package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DoubleCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CastFromZonePredicate;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SisterhoodOfKarn extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell from anywhere other than your hand");

    static {
        filter.add(Predicates.not(new CastFromZonePredicate(Zone.HAND)));
    }

    public SisterhoodOfKarn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Sisterhood of Karn enters the battlefield with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                "with a +1/+1 counter on it"
        ));

        // Paradox -- Whenever you cast a spell from anywhere other than your hand, double the number of +1/+1 counters on Sisterhood of Karn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DoubleCountersSourceEffect(CounterType.P1P1),
                filter, false
        ).setAbilityWord(AbilityWord.PARADOX));
    }

    private SisterhoodOfKarn(final SisterhoodOfKarn card) {
        super(card);
    }

    @Override
    public SisterhoodOfKarn copy() {
        return new SisterhoodOfKarn(this);
    }
}
