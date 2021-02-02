
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

/**
 *
 * @author Loki
 */
public final class VividCrag extends CardImpl {

    public VividCrag(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        // Vivid Crag enters the battlefield tapped with two charge counters on it.
        Ability ability = new EntersBattlefieldAbility(new TapSourceEffect(true), false, null, "{this} enters the battlefield tapped with two charge counters on it.", null);
        ability.addEffect(new AddCountersSourceEffect(CounterType.CHARGE.createInstance(2)));
        this.addAbility(ability);
        // {tap}: Add {R}.
        this.addAbility(new RedManaAbility());
        // {tap}, Remove a charge counter from Vivid Crag: Add one mana of any color.
        ability = new AnyColorManaAbility();
        ability.addCost(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(1)));
        this.addAbility(ability);

    }

    private VividCrag(final VividCrag card) {
        super(card);
    }

    @Override
    public VividCrag copy() {
        return new VividCrag(this);
    }
}
