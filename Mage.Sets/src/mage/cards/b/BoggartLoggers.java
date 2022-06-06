
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class BoggartLoggers extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("Treefolk or Forest");

    static {
        filter.add(Predicates.or(
                SubType.TREEFOLK.getPredicate(),
                SubType.FOREST.getPredicate()));
    }

    public BoggartLoggers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.GOBLIN, SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Forestwalk
        this.addAbility(new ForestwalkAbility());
        
        // {2}{B}, Sacrifice Boggart Loggers: Destroy target Treefolk or Forest.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl<>("{2}{B}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private BoggartLoggers(final BoggartLoggers card) {
        super(card);
    }

    @Override
    public BoggartLoggers copy() {
        return new BoggartLoggers(this);
    }
}
