
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedAllTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class CloseQuarters extends CardImpl {

    static final private FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public CloseQuarters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}{R}");

        // Whenever a creature you control becomes blocked, Close Quarters deals 1 damage to any target.
        Ability ability = new BecomesBlockedAllTriggeredAbility(new DamageTargetEffect(1), false, filter, false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private CloseQuarters(final CloseQuarters card) {
        super(card);
    }

    @Override
    public CloseQuarters copy() {
        return new CloseQuarters(this);
    }
}
