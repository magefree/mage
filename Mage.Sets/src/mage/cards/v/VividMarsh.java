
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

/**
 *
 * @author Loki
 */
public final class VividMarsh extends CardImpl {

    public VividMarsh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Vivid Marsh enters the battlefield tapped with two charge counters on it.
        Ability ability = new EntersBattlefieldAbility(new TapSourceEffect(true), false, null, "{this} enters tapped with two charge counters on it.", null);
        ability.addEffect(new AddCountersSourceEffect(CounterType.CHARGE.createInstance(2)));
        this.addAbility(ability);
        // {tap}: Add {B}.
        this.addAbility(new BlackManaAbility());
        // {tap}, Remove a charge counter from Vivid Marsh: Add one mana of any color.
        ability = new AnyColorManaAbility();
        ability.addCost(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(1)));
        this.addAbility(ability);
    }

    private VividMarsh(final VividMarsh card) {
        super(card);
    }

    @Override
    public VividMarsh copy() {
        return new VividMarsh(this);
    }
}
