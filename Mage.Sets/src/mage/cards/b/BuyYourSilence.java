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
 */
public final class BuyYourSilence extends CardImpl {

    public BuyYourSilence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}");

        // Exile target nonland permanent. Its controller creates a Treasure token.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addEffect(new BuyYourSilenceEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private BuyYourSilence(final BuyYourSilence card) {
        super(card);
    }

    @Override
    public BuyYourSilence copy() {
        return new BuyYourSilence(this);
    }
}

class BuyYourSilenceEffect extends OneShotEffect {

    BuyYourSilenceEffect() {
        super(Outcome.Benefit);
        staticText = "Its controller creates a Treasure token";
    }

    private BuyYourSilenceEffect(final BuyYourSilenceEffect effect) {
        super(effect);
    }

    @Override
    public BuyYourSilenceEffect copy() {
        return new BuyYourSilenceEffect(this);
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
