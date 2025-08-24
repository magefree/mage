
package mage.cards.c;

import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class CircleOfFlame extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public CircleOfFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");


        // Whenever a creature without flying attacks you or a planeswalker you control, Circle of Flame deals 1 damage to that creature.
        this.addAbility(new AttacksAllTriggeredAbility(new DamageTargetEffect(1).withTargetDescription("that creature"), false,
                filter, SetTargetPointer.PERMANENT, true));
    }

    private CircleOfFlame(final CircleOfFlame card) {
        super(card);
    }

    @Override
    public CircleOfFlame copy() {
        return new CircleOfFlame(this);
    }
}
