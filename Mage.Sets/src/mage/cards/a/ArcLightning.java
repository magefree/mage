package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTargetAmount;

/**
 *
 * @author Backfir3
 */
public final class ArcLightning extends CardImpl {

    public ArcLightning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");

        // Arc Lightning deals 3 damage divided as you choose among one, two, or three targets.
        this.getSpellAbility().addEffect(new DamageMultiEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTargetAmount(3));
    }

    private ArcLightning(final ArcLightning card) {
        super(card);
    }

    @Override
    public ArcLightning copy() {
        return new ArcLightning(this);
    }
}
