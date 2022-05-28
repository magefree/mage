
package mage.cards.c;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class CoralReef extends CardImpl {
    
    private static final FilterControlledPermanent islandFilter = new FilterControlledPermanent("an Island");
    private static final FilterControlledCreaturePermanent untappedBlueCreatureFilter = new FilterControlledCreaturePermanent("an untapped blue creature you control");
    
    static {
        islandFilter.add(SubType.ISLAND.getPredicate());
        untappedBlueCreatureFilter.add(TappedPredicate.UNTAPPED);
        untappedBlueCreatureFilter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public CoralReef(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{U}{U}");

        // Coral Reef enters the battlefield with four polyp counters on it.
        Effect effect = new AddCountersSourceEffect(CounterType.POLYP.createInstance(4));
        effect.setText("with four polyp counters on it");
        this.addAbility(new EntersBattlefieldAbility(effect));
        
        // Sacrifice an Island: Put two polyp counters on Coral Reef.
        effect = new AddCountersSourceEffect(CounterType.POLYP.createInstance(2), true);
        effect.setText("Put two polyp counters on {this}");
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, 
                new SacrificeTargetCost(new TargetControlledPermanent(islandFilter))));
        
        // {U}, Tap an untapped blue creature you control, Remove a polyp counter from Coral Reef: Put a +0/+1 counter on target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P0P1.createInstance()), new ManaCostsImpl<>("{U}"));
        ability.addCost(new TapTargetCost(new TargetControlledCreaturePermanent(untappedBlueCreatureFilter)));
        ability.addCost(new RemoveCountersSourceCost(CounterType.POLYP.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private CoralReef(final CoralReef card) {
        super(card);
    }

    @Override
    public CoralReef copy() {
        return new CoralReef(this);
    }
}
