
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
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
import mage.filter.StaticFilters;
import mage.game.permanent.token.SpiritToken;

/**
 *
 * @author Loki
 */
public final class BakuAltar extends CardImpl {

    public BakuAltar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        // Whenever you cast a Spirit or Arcane spell, you may put a ki counter on Baku Altar.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.KI.createInstance(1)), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, true));
        // {2}, {tap}, Remove a ki counter from Baku Altar: Create a 1/1 colorless Spirit creature token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SpiritToken(), 1), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.KI.createInstance(1)));
        this.addAbility(ability);
    }

    private BakuAltar(final BakuAltar card) {
        super(card);
    }

    @Override
    public BakuAltar copy() {
        return new BakuAltar(this);
    }
}
