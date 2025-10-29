package mage.cards.c;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ClueArtifactToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CunningManeuver extends CardImpl {

    public CunningManeuver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Target creature gets +3/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Create a Clue token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ClueArtifactToken()).concatBy("<br>"));
    }

    private CunningManeuver(final CunningManeuver card) {
        super(card);
    }

    @Override
    public CunningManeuver copy() {
        return new CunningManeuver(this);
    }
}
