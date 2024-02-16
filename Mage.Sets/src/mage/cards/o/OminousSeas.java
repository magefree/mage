package mage.cards.o;

import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.game.permanent.token.KrakenToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OminousSeas extends CardImpl {

    public OminousSeas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // Whenever you draw a card, put a foreshadow counter on Ominous Seas.
        this.addAbility(new DrawCardControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.FORESHADOW.createInstance()), false
        ));

        // Remove eight foreshadow counters from Ominous Seas: Create an 8/8 blue Kraken creature token.
        this.addAbility(new SimpleActivatedAbility(
                new CreateTokenEffect(new KrakenToken()).setText("create an 8/8 blue Kraken creature token"),
                new RemoveCountersSourceCost(CounterType.FORESHADOW.createInstance(8))
        ));

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private OminousSeas(final OminousSeas card) {
        super(card);
    }

    @Override
    public OminousSeas copy() {
        return new OminousSeas(this);
    }
}
