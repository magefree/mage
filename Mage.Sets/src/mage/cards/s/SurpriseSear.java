package mage.cards.s;

import java.util.UUID;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.permanent.token.LizardRhinoToken;
import mage.target.common.TargetCreaturePermanent;


/**
 *
 * @author ChesseTheWasp
 */
public final class SurpriseSear extends CardImpl {

    public SurpriseSear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{R}");

        

        this.getSpellAbility().addEffect(new CreateTokenEffect(new LizardRhinoToken(), 1));

        this.getSpellAbility().addEffect(new BoostTargetEffect(0, -1, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SurpriseSear(final SurpriseSear card) {
        super(card);
    }

    @Override
    public SurpriseSear copy() {
        return new SurpriseSear(this);
    }
}