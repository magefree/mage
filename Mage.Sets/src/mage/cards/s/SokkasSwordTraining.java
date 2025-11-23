package mage.cards.s;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ClueArtifactToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SokkasSwordTraining extends CardImpl {

    public SokkasSwordTraining(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        this.subtype.add(SubType.LESSON);

        // Target creature gets +2/+2 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Create a Clue token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ClueArtifactToken()).concatBy("<br>"));
    }

    private SokkasSwordTraining(final SokkasSwordTraining card) {
        super(card);
    }

    @Override
    public SokkasSwordTraining copy() {
        return new SokkasSwordTraining(this);
    }
}
