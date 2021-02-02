
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.token.TitanForgeGolemToken;

/**
 *
 * @author Loki
 */
public final class TitanForge extends CardImpl {

    public TitanForge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new TitanForgeGolemToken()), new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(3)));
        this.addAbility(ability);

    }

    private TitanForge(final TitanForge card) {
        super(card);
    }

    @Override
    public TitanForge copy() {
        return new TitanForge(this);
    }

}
