
package mage.cards.k;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.PowerTargetAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KillingGlare extends CardImpl {

    public KillingGlare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{B}");

        // Destroy target creature with power X or less.
        this.getSpellAbility().addEffect(new DestroyTargetEffect("destroy target creature with power X or less"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().setTargetAdjuster(new PowerTargetAdjuster(ComparisonType.OR_LESS));
    }

    private KillingGlare(final KillingGlare card) {
        super(card);
    }

    @Override
    public KillingGlare copy() {
        return new KillingGlare(this);
    }
}
