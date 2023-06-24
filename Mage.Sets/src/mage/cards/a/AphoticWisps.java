
package mage.cards.a;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX
 */
public final class AphoticWisps extends CardImpl {

    public AphoticWisps(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");

        // Target creature becomes black and gains fear until end of turn. (It can't be blocked except by artifact creatures and/or black creatures.)
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        Effect effect = new BecomesColorTargetEffect(ObjectColor.BLACK, Duration.EndOfTurn);
        effect.setText("Target creature becomes black");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(FearAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains fear until end of turn");
        this.getSpellAbility().addEffect(effect);
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private AphoticWisps(final AphoticWisps card) {
        super(card);
    }

    @Override
    public AphoticWisps copy() {
        return new AphoticWisps(this);
    }

}
