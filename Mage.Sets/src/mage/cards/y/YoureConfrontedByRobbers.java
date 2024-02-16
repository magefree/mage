package mage.cards.y;

import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SoldierToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YoureConfrontedByRobbers extends CardImpl {

    public YoureConfrontedByRobbers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // Choose one —
        // • Stall for Time — Tap up to three target creatures.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 3));
        this.getSpellAbility().withFirstModeFlavorWord("Stall for Time");

        // • Call for Aid — Create three 1/1 white Soldier creature tokens.
        this.getSpellAbility().addMode(new Mode(
                new CreateTokenEffect(new SoldierToken(), 3)
        ).withFlavorWord("Call for Aid"));
    }

    private YoureConfrontedByRobbers(final YoureConfrontedByRobbers card) {
        super(card);
    }

    @Override
    public YoureConfrontedByRobbers copy() {
        return new YoureConfrontedByRobbers(this);
    }
}
