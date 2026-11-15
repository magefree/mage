package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.TribbleToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class TribbleCluster extends CardImpl {

    public TribbleCluster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.TRIBBLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of combat on your turn, put a +1/+1 counter on this creature.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        ));

        // When this creature dies, create X 1/1 white Tribble creature tokens with "This token can't block," where X is this creature's power.
        this.addAbility(new DiesSourceTriggeredAbility(
            new CreateTokenEffect(new TribbleToken(), SourcePermanentPowerValue.NOT_NEGATIVE)
        ));
    }

    private TribbleCluster(final TribbleCluster card) {
        super(card);
    }

    @Override
    public TribbleCluster copy() {
        return new TribbleCluster(this);
    }
}
