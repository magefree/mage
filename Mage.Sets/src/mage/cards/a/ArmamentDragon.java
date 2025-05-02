package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetPermanentAmount;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArmamentDragon extends CardImpl {

    public ArmamentDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{B}{G}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, distribute three +1/+1 counters among one, two, or three target creatures you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DistributeCountersEffect(CounterType.P1P1));
        ability.addTarget(new TargetPermanentAmount(3, 1, StaticFilters.FILTER_CONTROLLED_CREATURES));
        this.addAbility(ability);
    }

    private ArmamentDragon(final ArmamentDragon card) {
        super(card);
    }

    @Override
    public ArmamentDragon copy() {
        return new ArmamentDragon(this);
    }
}
