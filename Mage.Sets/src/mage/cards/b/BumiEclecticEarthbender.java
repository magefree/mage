package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BumiEclecticEarthbender extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("land creature you control");

    static {
        filter.add(CardType.LAND.getPredicate());
    }

    public BumiEclecticEarthbender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Bumi enters, earthbend 1.
        Ability ability = new EntersBattlefieldTriggeredAbility(new EarthbendTargetEffect(1));
        ability.addTarget(new TargetControlledLandPermanent());
        this.addAbility(ability);

        // Whenever Bumi attacks, put two +1/+1 counters on each land creature you control.
        this.addAbility(new AttacksTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(2), filter)));
    }

    private BumiEclecticEarthbender(final BumiEclecticEarthbender card) {
        super(card);
    }

    @Override
    public BumiEclecticEarthbender copy() {
        return new BumiEclecticEarthbender(this);
    }
}
