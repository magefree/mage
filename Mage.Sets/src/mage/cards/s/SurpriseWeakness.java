package mage.cards.s;

import java.util.UUID;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.permanent.token.OtterBirdToken;
import mage.target.common.TargetCreaturePermanent;


/**
 *
 * @author ChesseTheWasp
 */
public final class SurpriseWeakness extends CardImpl {

    public SurpriseWeakness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{U}");

        

        this.getSpellAbility().addEffect(new CreateTokenEffect(new OtterBirdToken(), 1));

        this.getSpellAbility().addEffect(new BoostTargetEffect(-1, 0, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SurpriseWeakness(final SurpriseWeakness card) {
        super(card);
    }

    @Override
    public SurpriseWeakness copy() {
        return new SurpriseWeakness(this);
    }
}