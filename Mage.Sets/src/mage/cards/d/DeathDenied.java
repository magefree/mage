
package mage.cards.d;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DeathDenied extends CardImpl {

    public DeathDenied(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{B}{B}");
        this.subtype.add(SubType.ARCANE);

        // Return X target creature cards from your graveyard to your hand.
        Effect effect = new ReturnFromGraveyardToHandTargetEffect();
        effect.setText("Return X target creature cards from your graveyard to your hand");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD));
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
    }

    private DeathDenied(final DeathDenied card) {
        super(card);
    }

    @Override
    public DeathDenied copy() {
        return new DeathDenied(this);
    }
}
