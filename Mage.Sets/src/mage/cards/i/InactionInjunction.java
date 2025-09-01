package mage.cards.i;

import mage.abilities.effects.common.DetainTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class InactionInjunction extends CardImpl {

    public InactionInjunction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Detain target creature an opponent controls.
        // (Until your next turn, that creature can't attack or block and its activated abilities can't be activated.)
        this.getSpellAbility().addEffect(new DetainTargetEffect());
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private InactionInjunction(final InactionInjunction card) {
        super(card);
    }

    @Override
    public InactionInjunction copy() {
        return new InactionInjunction(this);
    }
}
