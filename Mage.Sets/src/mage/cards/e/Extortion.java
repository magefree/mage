package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Extortion extends CardImpl {

    public Extortion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Look at target player's hand and choose up to two cards from it. That player discards those cards.
        this.getSpellAbility().addEffect(new ExtortionEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private Extortion(final Extortion card) {
        super(card);
    }

    @Override
    public Extortion copy() {
        return new Extortion(this);
    }
}

class ExtortionEffect extends OneShotEffect {

    ExtortionEffect() {
        super(Outcome.Discard);
        staticText = "Look at target player's hand and choose up to two cards from it. That player discards those cards.";
    }

    private ExtortionEffect(final ExtortionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player you = game.getPlayer(source.getControllerId());
        if (targetPlayer == null || you == null) {
            return false;
        }
        you.lookAtCards("Discard", targetPlayer.getHand(), game);
        TargetCard target = new TargetCardInHand(0, 2, StaticFilters.FILTER_CARD_CARDS);
        target.setNotTarget(true);
        you.choose(Outcome.Discard, targetPlayer.getHand(), target, source, game);
        targetPlayer.discard(new CardsImpl(target.getTargets()), false, source, game);
        return true;
    }

    @Override
    public ExtortionEffect copy() {
        return new ExtortionEffect(this);
    }
}
