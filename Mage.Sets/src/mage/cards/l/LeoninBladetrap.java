
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;

/**
 *
 * @author LevelX2
 */
public final class LeoninBladetrap extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("attacking creature without flying");
    static {
        filter.add(AttackingPredicate.instance);
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public LeoninBladetrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // {2}, Sacrifice Leonin Bladetrap: Leonin Bladetrap deals 2 damage to each attacking creature without flying.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageAllEffect(2, "it", filter), new GenericManaCost(2));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

    }

    private LeoninBladetrap(final LeoninBladetrap card) {
        super(card);
    }

    @Override
    public LeoninBladetrap copy() {
        return new LeoninBladetrap(this);
    }
}
