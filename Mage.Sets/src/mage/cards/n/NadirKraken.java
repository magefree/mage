package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.TentacleToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NadirKraken extends CardImpl {

    public NadirKraken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        this.subtype.add(SubType.KRAKEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you draw a card, you may pay {1}. If you do, put a +1/+1 counter on Nadir Kraken and create a 1/1 blue Tentacle creature token.
        this.addAbility(new DrawCardControllerTriggeredAbility(new DoIfCostPaid(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new GenericManaCost(1)
        ).addEffect(new CreateTokenEffect(new TentacleToken()).concatBy("and")), false));
    }

    private NadirKraken(final NadirKraken card) {
        super(card);
    }

    @Override
    public NadirKraken copy() {
        return new NadirKraken(this);
    }
}
