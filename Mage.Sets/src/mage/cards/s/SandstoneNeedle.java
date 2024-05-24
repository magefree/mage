
package mage.cards.s;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author Plopman
 */
public final class SandstoneNeedle extends CardImpl {

    public SandstoneNeedle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Sandstone Needle enters the battlefield tapped with two depletion counters on it.
        Ability etbAbility = new EntersBattlefieldAbility(
                new TapSourceEffect(true), "tapped with two depletion counters on it"
        );
        etbAbility.addEffect(new AddCountersSourceEffect(CounterType.DEPLETION.createInstance(2)));
        this.addAbility(etbAbility);
        // {tap}, Remove a depletion counter from Sandstone Needle: Add {R}{R}. If there are no depletion counters on Sandstone Needle, sacrifice it.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.RedMana(2), new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.DEPLETION.createInstance(1)));
        ability.addEffect(new ConditionalOneShotEffect(new SacrificeSourceEffect(), new SourceHasCounterCondition(CounterType.DEPLETION, 0,0), "If there are no depletion counters on {this}, sacrifice it"));
        this.addAbility(ability);
    }

    private SandstoneNeedle(final SandstoneNeedle card) {
        super(card);
    }

    @Override
    public SandstoneNeedle copy() {
        return new SandstoneNeedle(this);
    }
}
