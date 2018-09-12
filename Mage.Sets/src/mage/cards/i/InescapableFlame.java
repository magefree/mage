package mage.cards.i;

import java.util.UUID;
import mage.abilities.common.CantBeCounteredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author TheElk801
 */
public final class InescapableFlame extends CardImpl {

    public InescapableFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}{R}");

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredAbility());

        // Inescapable Flame deals 6 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(6));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    public InescapableFlame(final InescapableFlame card) {
        super(card);
    }

    @Override
    public InescapableFlame copy() {
        return new InescapableFlame(this);
    }
}
