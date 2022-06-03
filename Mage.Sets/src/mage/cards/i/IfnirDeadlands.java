
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class IfnirDeadlands extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Desert");

    static {
        filter.add(SubType.DESERT.getPredicate());
    }

    public IfnirDeadlands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        
        this.subtype.add(SubType.DESERT);

        // {t}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        
        // {t}, Pay 1 life: Add {B}.
        Ability manaAbility = new BlackManaAbility();
        manaAbility.addCost(new PayLifeCost(1));
        this.addAbility(manaAbility);
        
        // {2}{B}{B}, {t}, Sacrifice a Desert: Put two -1/-1 counters on target creature an opponent controls. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.M1M1.createInstance(2)), new ManaCostsImpl<>("{2}{B}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(1, 1, filter, true)));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
        
    }

    private IfnirDeadlands(final IfnirDeadlands card) {
        super(card);
    }

    @Override
    public IfnirDeadlands copy() {
        return new IfnirDeadlands(this);
    }
}