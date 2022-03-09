

package mage.cards.n;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX
 */
public final class NiveousWisps extends CardImpl {

    public NiveousWisps (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        // Target creature becomes white until end of turn. Tap that creature.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BecomesColorTargetEffect(ObjectColor.WHITE, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new TapTargetEffect("tap that creature"));
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    public NiveousWisps (final NiveousWisps card) {
        super(card);
    }

    @Override
    public NiveousWisps copy() {
        return new NiveousWisps(this);
    }

}

