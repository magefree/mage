
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Plopman
 */
 public final class WhereAncientsTread extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature with power 5 or greater");
    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 4));
    }

    public WhereAncientsTread(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{R}");


        // Whenever a creature with power 5 or greater enters the battlefield under your control, you may have Where Ancients Tread deal 5 damage to any target.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new DamageTargetEffect(5).setText("you may have {this} deal 5 damage to any target"), filter, true);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private WhereAncientsTread(final WhereAncientsTread card) {
        super(card);
    }

    @Override
    public WhereAncientsTread copy() {
        return new WhereAncientsTread(this);
    }
}
