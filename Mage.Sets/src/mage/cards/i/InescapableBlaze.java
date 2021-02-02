package mage.cards.i;

import java.util.UUID;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author TheElk801
 */
public final class InescapableBlaze extends CardImpl {

    public InescapableBlaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}{R}");

        // This spell can't be countered.        
        this.addAbility(new CantBeCounteredSourceAbility().setRuleAtTheTop(true));

        // Inescapable Flame deals 6 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(6));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private InescapableBlaze(final InescapableBlaze card) {
        super(card);
    }

    @Override
    public InescapableBlaze copy() {
        return new InescapableBlaze(this);
    }
}
