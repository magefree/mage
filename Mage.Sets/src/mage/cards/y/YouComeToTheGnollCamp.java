package mage.cards.y;

import mage.abilities.Mode;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YouComeToTheGnollCamp extends CardImpl {

    public YouComeToTheGnollCamp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Choose one —
        // • Intimidate Them — Up to two target creatures can't block this turn.
        this.getSpellAbility().addEffect(new CantBlockTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
        this.getSpellAbility().withFirstModeFlavorWord("Intimidate Them");

        // • Fend Them Off — Target creature gets +3/+1 until end of turn.
        Mode mode = new Mode(new BoostTargetEffect(3, 1));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode.withFlavorWord("Fend Them Off"));
    }

    private YouComeToTheGnollCamp(final YouComeToTheGnollCamp card) {
        super(card);
    }

    @Override
    public YouComeToTheGnollCamp copy() {
        return new YouComeToTheGnollCamp(this);
    }
}
