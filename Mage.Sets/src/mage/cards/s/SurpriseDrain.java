package mage.cards.s;

import java.util.UUID;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SpiritSlugToken;
import mage.target.common.TargetOpponent;

/**
 *
 * @author ChesseTheWasp
 */
public final class SurpriseDrain extends CardImpl {

    public SurpriseDrain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}{B}");

        

        this.getSpellAbility().addEffect(new CreateTokenEffect(new SpiritSlugToken(), 1));

        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(1));
        this.getSpellAbility().addEffect(new GainLifeEffect(1).concatBy("and"));
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private SurpriseDrain(final SurpriseDrain card) {
        super(card);
    }

    @Override
    public SurpriseDrain copy() {
        return new SurpriseDrain(this);
    }
}