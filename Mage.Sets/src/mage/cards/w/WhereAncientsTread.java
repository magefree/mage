package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class WhereAncientsTread extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("a creature you control with power 5 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 4));
    }

    public WhereAncientsTread(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}");

        // Whenever a creature with power 5 or greater you control enters, you may have Where Ancients Tread deal 5 damage to any target.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(
                new DamageTargetEffect(5)
                        .setText("you may have {this} deal 5 damage to any target"),
                filter, true
        );
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
