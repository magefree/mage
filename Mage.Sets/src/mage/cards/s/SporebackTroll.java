
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.abilities.keyword.GraftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class SporebackTroll extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature with a +1/+1 counter on it");
    static {
        filter.add(CounterType.P1P1.getPredicate());
    }

    public SporebackTroll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.TROLL);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Graft 2
        this.addAbility(new GraftAbility(this, 2));
        
        // {1}{G}: Regenerate target creature with a +1/+1 counter on it.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateTargetEffect(), new ManaCostsImpl("{1}{G}"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public SporebackTroll(final SporebackTroll card) {
        super(card);
    }

    @Override
    public SporebackTroll copy() {
        return new SporebackTroll(this);
    }
}
