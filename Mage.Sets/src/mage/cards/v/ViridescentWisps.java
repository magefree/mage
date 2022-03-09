package mage.cards.v;

import mage.ObjectColor;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX
 */
public final class ViridescentWisps extends CardImpl {

    public ViridescentWisps(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        //    Target creature becomes green and gets +1/+0 until end of turn.
        this.getSpellAbility().addEffect(new BecomesColorTargetEffect(
                ObjectColor.GREEN, Duration.EndOfTurn
        ).setText("target creature becomes green"));
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                1, 0, Duration.EndOfTurn
        ).setText("and gets +1/+0 until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    public ViridescentWisps(final ViridescentWisps card) {
        super(card);
    }

    @Override
    public ViridescentWisps copy() {
        return new ViridescentWisps(this);
    }
}
