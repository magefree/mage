package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.replacement.ModifyCountersAddedEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MauhurUrukHaiCaptain extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("Army, Goblin, or Orc you control");

    static {
        filter.add(Predicates.or(
                SubType.ARMY.getPredicate(),
                SubType.GOBLIN.getPredicate(),
                SubType.ORC.getPredicate()
        ));
    }

    public MauhurUrukHaiCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // If one or more +1/+1 counters would be put on an Army, Goblin, or Orc you control, that many plus one +1/+1 counters are put on it instead.
        this.addAbility(new SimpleStaticAbility(new ModifyCountersAddedEffect(filter, CounterType.P1P1)));
    }

    private MauhurUrukHaiCaptain(final MauhurUrukHaiCaptain card) {
        super(card);
    }

    @Override
    public MauhurUrukHaiCaptain copy() {
        return new MauhurUrukHaiCaptain(this);
    }
}
