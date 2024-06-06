package mage.cards.o;

import mage.MageInt;
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
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{2}{W}{B}", "Throw a Line", "{W}{B}");

        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever another modified creature you control dies, put two +1/+1 counters on Ondu Knotmaster.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
                false, filter
        ));

        // Throw a Line
        // Distribute two +1/+1 counters among one or two target creatures.
        this.getSpellCard().getSpellAbility().addEffect(
                new DistributeCountersEffect(
                        CounterType.P1P1, 2, false,
                        "one or two target creatures"
                )
        );
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanentAmount(2));

        this.finalizeAdventure();
    }

    private OnduKnotmaster(final OnduKnotmaster card) {
        super(card);
    }

    @Override
    public OnduKnotmaster copy() {
        return new OnduKnotmaster(this);
    }
}
