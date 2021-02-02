
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author fireshoes
 */
public final class MagistratesScepter extends CardImpl {

    public MagistratesScepter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {4}, {tap}: Put a charge counter on Magistrate's Scepter.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        
        // {tap}, Remove three charge counters from Magistrate's Scepter: Take an extra turn after this one.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddExtraTurnControllerEffect(), new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(3)));
        this.addAbility(ability);
    }

    private MagistratesScepter(final MagistratesScepter card) {
        super(card);
    }

    @Override
    public MagistratesScepter copy() {
        return new MagistratesScepter(this);
    }
}
