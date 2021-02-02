
package mage.cards.f;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author Plopman
 */
public final class FeedbackBolt extends CardImpl {

    public FeedbackBolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");

        // Feedback Bolt deals damage to target player equal to the number of artifacts you control.
        Effect effect = new DamageTargetEffect(new PermanentsOnBattlefieldCount(new FilterControlledArtifactPermanent()));
        effect.setText("{this} deals damage to target player or planeswalker equal to the number of artifacts you control");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
    }

    private FeedbackBolt(final FeedbackBolt card) {
        super(card);
    }

    @Override
    public FeedbackBolt copy() {
        return new FeedbackBolt(this);
    }
}
