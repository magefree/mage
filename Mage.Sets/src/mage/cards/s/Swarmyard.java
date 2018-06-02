
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class Swarmyard extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Insect, Rat, Spider, or Squirrel");

    static {
        filter.add(Predicates.or(
            new SubtypePredicate(SubType.INSECT),
            new SubtypePredicate(SubType.RAT),
            new SubtypePredicate(SubType.SPIDER),
            new SubtypePredicate(SubType.SQUIRREL)
        ));
    }

    public Swarmyard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        
        // {tap}: Regenerate target Insect, Rat, Spider, or Squirrel.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

    }

    public Swarmyard(final Swarmyard card) {
        super(card);
    }

    @Override
    public Swarmyard copy() {
        return new Swarmyard(this);
    }
}
