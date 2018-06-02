
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class AcrobaticManeuver extends CardImpl {

    public AcrobaticManeuver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Exile target creature you control, then return that card to the battlefield under its owner's control.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        Effect effect = new ExileTargetForSourceEffect();
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new ReturnToBattlefieldUnderOwnerControlTargetEffect());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    public AcrobaticManeuver(final AcrobaticManeuver card) {
        super(card);
    }

    @Override
    public AcrobaticManeuver copy() {
        return new AcrobaticManeuver(this);
    }
}
