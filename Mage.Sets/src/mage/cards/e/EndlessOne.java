
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author fireshoes
 */
public final class EndlessOne extends CardImpl {

    public EndlessOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{X}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Endless One enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));
    }

    private EndlessOne(final EndlessOne card) {
        super(card);
    }

    @Override
    public EndlessOne copy() {
        return new EndlessOne(this);
    }
}
