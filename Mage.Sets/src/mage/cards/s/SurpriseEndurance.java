package mage.cards.s;

import java.util.UUID;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.permanent.token.CatDogToken;
import mage.target.common.TargetCreaturePermanent;


/**
 *
 * @author ChesseTheWasp
 */
public final class SurpriseEndurance extends CardImpl {

    public SurpriseEndurance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}{W}");

        

        this.getSpellAbility().addEffect(new CreateTokenEffect(new CatDogToken(), 1));

        this.getSpellAbility().addEffect(new BoostTargetEffect(0, 1, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SurpriseEndurance(final SurpriseEndurance card) {
        super(card);
    }

    @Override
    public SurpriseEndurance copy() {
        return new SurpriseEndurance(this);
    }
}