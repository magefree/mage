package mage.cards.y;

import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YouComeToARiver extends CardImpl {

    public YouComeToARiver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Choose one —
        // • Fight the Current — Return target nonland permanent top its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
        this.getSpellAbility().withFirstModeFlavorWord("Fight the Current");

        // • Find a Crossing — Target creature gets +1/+0 until end of turn and can't be blocked this turn.
        Mode mode = new Mode(new BoostTargetEffect(1, 0, Duration.EndOfTurn));
        mode.addEffect(new CantBeBlockedTargetEffect(Duration.EndOfTurn).setText("and can't be blocked this turn"));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode.withFlavorWord("Find a Crossing"));
    }

    private YouComeToARiver(final YouComeToARiver card) {
        super(card);
    }

    @Override
    public YouComeToARiver copy() {
        return new YouComeToARiver(this);
    }
}
