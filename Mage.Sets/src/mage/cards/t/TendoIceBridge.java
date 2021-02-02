
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

/**
 *
 * @author Loki
 */
public final class TendoIceBridge extends CardImpl {

    public TendoIceBridge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        // Tendo Ice Bridge enters the battlefield with a charge counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.CHARGE.createInstance(1)), "with a charge counter on it"));
        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {tap}, Remove a charge counter from Tendo Ice Bridge: Add one mana of any color.
        Ability ability = new AnyColorManaAbility();
        ability.addCost(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(1)));
        this.addAbility(ability);
    }

    private TendoIceBridge(final TendoIceBridge card) {
        super(card);
    }

    @Override
    public TendoIceBridge copy() {
        return new TendoIceBridge(this);
    }
}
