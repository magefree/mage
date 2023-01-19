package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 * @author chesse20
 */
public final class BuyYourSilenceAlchemy extends CardImpl {

    public BuyYourSilenceAlchemy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{W}");

        // Exile target nonland permanent. Its controller creates a Treasure token.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addEffect(new BuyYourSilenceEffectA());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private BuyYourSilenceAlchemy(final BuyYourSilenceAlchemy card) {
        super(card);
    }

    @Override
    public BuyYourSilenceAlchemy copy() {
        return new BuyYourSilenceAlchemy(this);
    }
}

class BuyYourSilenceEffectA extends OneShotEffect {

    BuyYourSilenceEffectA() {
        super(Outcome.Benefit);
        staticText = "Its controller creates a Treasure token";
    }

    private BuyYourSilenceEffectA(final BuyYourSilenceEffectA effect) {
        super(effect);
    }

    @Override
    public BuyYourSilenceEffectA copy() {
        return new BuyYourSilenceEffectA(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent == null) {
            return false;
        }
        return new TreasureToken().putOntoBattlefield(1, game, source, permanent.getControllerId());
    }
}
