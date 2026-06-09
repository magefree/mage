package mage.cards.o;

import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.ModifiedPredicate;
import mage.target.common.TargetCreaturePermanentAmount;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class OnduKnotmaster extends AdventureCard {

    private static final FilterPermanent filter =
            new FilterControlledCreaturePermanent("another modified creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(ModifiedPredicate.instance);
    }

    public OnduKnotmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.KOR, SubType.ROGUE}, "{2}{W}{B}",
                "Throw a Line",
                new CardType[]{CardType.SORCERY}, "{W}{B}");

        // Ondu Knotmaster
        this.getLeftHalfCard().setPT(2, 2);

        // Lifelink
        this.getLeftHalfCard().addAbility(LifelinkAbility.getInstance());

        // Whenever another modified creature you control dies, put two +1/+1 counters on Ondu Knotmaster.
        this.getLeftHalfCard().addAbility(new DiesCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
                false, filter
        ));

        // Throw a Line
        // Distribute two +1/+1 counters among one or two target creatures.
        this.getRightHalfCard().getSpellAbility().addEffect(new DistributeCountersEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanentAmount(2));

        finalizeCard();
    }

    private OnduKnotmaster(final OnduKnotmaster card) {
        super(card);
    }

    @Override
    public OnduKnotmaster copy() {
        return new OnduKnotmaster(this);
    }
}
