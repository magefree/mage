package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.SoldierToken;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class RoyalTalonFighterJet extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.P1P1);

    public RoyalTalonFighterJet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{X}{W}{W}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // This Vehicle enters with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
            new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())
        ));

        // Whenever this Vehicle enters or attacks, create a number of 1/1 white Soldier creature tokens equal to the number of +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(
            new CreateTokenEffect(new SoldierToken(), xValue)
                .setText("create a number of 1/1 white Soldier creature tokens equal to the number of +1/+1 counters on it")
        ));

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private RoyalTalonFighterJet(final RoyalTalonFighterJet card) {
        super(card);
    }

    @Override
    public RoyalTalonFighterJet copy() {
        return new RoyalTalonFighterJet(this);
    }
}
