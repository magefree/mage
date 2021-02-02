
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

/**
 *
 * @author Loki
 */
public final class VividMeadow extends CardImpl {

    public VividMeadow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        // Vivid Meadow enters the battlefield tapped with two charge counters on it.
        Ability ability = new EntersBattlefieldAbility(new TapSourceEffect(true), false, null, "{this} enters the battlefield tapped with two charge counters on it.", null);
        ability.addEffect(new AddCountersSourceEffect(CounterType.CHARGE.createInstance(2)));
        this.addAbility(ability);
        // {tap}: Add {W}.
        this.addAbility(new WhiteManaAbility());
        // {tap}, Remove a charge counter from Vivid Meadow: Add one mana of any color.
        ability = new AnyColorManaAbility();
        ability.addCost(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(1)));
        this.addAbility(ability);
    }

    private VividMeadow(final VividMeadow card) {
        super(card);
    }

    @Override
    public VividMeadow copy() {
        return new VividMeadow(this);
    }
}
