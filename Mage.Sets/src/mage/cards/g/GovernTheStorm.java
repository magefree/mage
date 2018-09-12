package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class GovernTheStorm extends CardImpl {

    public GovernTheStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");

        // Govern the Storm deals 5 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public GovernTheStorm(final GovernTheStorm card) {
        super(card);
    }

    @Override
    public GovernTheStorm copy() {
        return new GovernTheStorm(this);
    }
}
