
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author cbt33
 */
public final class TouchOfInvisibility extends CardImpl {

    public TouchOfInvisibility(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}");

        // Target creature can't be blocked this turn.
        this.getSpellAbility().addEffect(new CantBeBlockedTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private TouchOfInvisibility(final TouchOfInvisibility card) {
        super(card);
    }

    @Override
    public TouchOfInvisibility copy() {
        return new TouchOfInvisibility(this);
    }
}
