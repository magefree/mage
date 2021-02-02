
package mage.cards.h;

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
public final class HickoryWoodlot extends CardImpl {

    public HickoryWoodlot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Hickory Woodlot enters the battlefield tapped with two depletion counters on it.
        this.addAbility(new EntersBattlefieldTappedAbility());
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.DEPLETION.createInstance(2))));
        // {tap}, Remove a depletion counter from Hickory Woodlot: Add {G}{G}. If there are no depletion counters on Hickory Woodlot, sacrifice it.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(2), new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.DEPLETION.createInstance(1)));
        ability.addEffect(new ConditionalOneShotEffect(new SacrificeSourceEffect(), new SourceHasCounterCondition(CounterType.DEPLETION, 0,0), "If there are no depletion counters on {this}, sacrifice it"));
        this.addAbility(ability);
    }

    private HickoryWoodlot(final HickoryWoodlot card) {
        super(card);
    }

    @Override
    public HickoryWoodlot copy() {
        return new HickoryWoodlot(this);
    }
}
