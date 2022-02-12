
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Quercitron
 */
public final class EaterOfHope extends CardImpl {

    private static final FilterControlledCreaturePermanent destroyFilter = new FilterControlledCreaturePermanent("other creatures");

    static {
        destroyFilter.add(AnotherPredicate.instance);
    }

    public EaterOfHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {B}, Sacrifice another creature: Regenerate Eater of Hope.
        Ability regenAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl("{B}"));
        regenAbility.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, true)));
        this.addAbility(regenAbility);

        // {2}{B}, Sacrifice two other creatures: Destroy target creature.
        Ability destroyAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl("{2}{B}"));
        destroyAbility.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(2, 2, destroyFilter, true)));
        destroyAbility.addTarget(new TargetCreaturePermanent());
        this.addAbility(destroyAbility);
    }

    private EaterOfHope(final EaterOfHope card) {
        super(card);
    }

    @Override
    public EaterOfHope copy() {
        return new EaterOfHope(this);
    }
}
