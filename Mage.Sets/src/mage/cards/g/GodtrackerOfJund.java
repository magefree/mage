package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class GodtrackerOfJund extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("a creature you control with power 5 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 4));
    }

    public GodtrackerOfJund(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a creature with power 5 or greater you control enters, you may put a +1/+1 counter on Godtracker of Jund.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter, true
        ));
    }

    private GodtrackerOfJund(final GodtrackerOfJund card) {
        super(card);
    }

    @Override
    public GodtrackerOfJund copy() {
        return new GodtrackerOfJund(this);
    }
}
