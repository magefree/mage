
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.OrCost;
import mage.constants.SubType;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.game.permanent.token.BirdToken;

/**
 *
 * @author TheElk801
 */
public final class JotunOwlKeeper extends CardImpl {

    public JotunOwlKeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Cumulative upkeep {W} or {U}
        this.addAbility(new CumulativeUpkeepAbility(new OrCost(
                "{W} or {U}", new ManaCostsImpl("{W}"),
                new ManaCostsImpl("{U}")
        )));

        // When J&ouml;tun Owl Keeper dies, put a 1/1 white Bird creature token with flying onto the battlefield for each age counter on it.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new BirdToken(), new CountersSourceCount(CounterType.AGE))));
    }

    private JotunOwlKeeper(final JotunOwlKeeper card) {
        super(card);
    }

    @Override
    public JotunOwlKeeper copy() {
        return new JotunOwlKeeper(this);
    }
}
