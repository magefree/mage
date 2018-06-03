
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 *
 * @author LevelX2
 */
public final class Skyshooter extends CardImpl {

    private static final FilterAttackingOrBlockingCreature filter = new FilterAttackingOrBlockingCreature("attacking or blocking creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public Skyshooter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.ARCHER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Reach
        this.addAbility(ReachAbility.getInstance());
        // {tap}, Sacrifice Skyshooter: Destroy target attacking or blocking creature with flying.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new TapSourceCost()); 
        ability.addTarget(new TargetAttackingOrBlockingCreature(1,1, filter, false));
        this.addAbility(ability);

    }

    public Skyshooter(final Skyshooter card) {
        super(card);
    }

    @Override
    public Skyshooter copy() {
        return new Skyshooter(this);
    }
}
