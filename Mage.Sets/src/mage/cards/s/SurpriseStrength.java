package mage.cards.s;

import java.util.UUID;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.permanent.token.InsectFishToken;
import mage.target.common.TargetCreaturePermanent;


/**
 *
 * @author ChesseTheWasp
 */
public final class SurpriseStrength extends CardImpl {

    public SurpriseStrength(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}{G}");

        

        this.getSpellAbility().addEffect(new CreateTokenEffect(new InsectFishToken(), 1));

        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 0, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SurpriseStrength(final SurpriseStrength card) {
        super(card);
    }

    @Override
    public SurpriseStrength copy() {
        return new SurpriseStrength(this);
    }
}