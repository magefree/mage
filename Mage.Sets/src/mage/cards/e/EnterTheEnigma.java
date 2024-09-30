package mage.cards.e;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EnterTheEnigma extends CardImpl {

    public EnterTheEnigma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");

        // Target creature can't be blocked this turn.
        this.getSpellAbility().addEffect(new CantBeBlockedTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private EnterTheEnigma(final EnterTheEnigma card) {
        super(card);
    }

    @Override
    public EnterTheEnigma copy() {
        return new EnterTheEnigma(this);
    }
}
