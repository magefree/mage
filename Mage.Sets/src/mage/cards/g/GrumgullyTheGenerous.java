package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.EntersWithCountersControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrumgullyTheGenerous extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("non-Human creature");

    static {
        filter.add(Predicates.not(SubType.HUMAN.getPredicate()));
    }

    public GrumgullyTheGenerous(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Each other non-Human creature you control enters the battlefield with an additional +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(new EntersWithCountersControlledEffect(
                filter, CounterType.P1P1.createInstance(), true
        )));
    }

    private GrumgullyTheGenerous(final GrumgullyTheGenerous card) {
        super(card);
    }

    @Override
    public GrumgullyTheGenerous copy() {
        return new GrumgullyTheGenerous(this);
    }
}
