
package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

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

    public Extortion(final Extortion card) {
        super(card);
    }

    @Override
    public Extortion copy() {
        return new Extortion(this);
    }
}

class ExtortionEffect extends OneShotEffect {

    public ExtortionEffect() {
        super(Outcome.Discard);
        staticText = "Look at target player's hand and choose up to two cards from it. That player discards that card.";
    }

    public ExtortionEffect(final ExtortionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player you = game.getPlayer(source.getControllerId());
        if (targetPlayer != null && you != null) {
            you.lookAtCards("Discard", targetPlayer.getHand(), game);
            TargetCard target = new TargetCard(0, 2, Zone.HAND, new FilterCard());
            target.setNotTarget(true);
            if (you.choose(Outcome.Benefit, targetPlayer.getHand(), target, game)) {
                Card card = targetPlayer.getHand().get(target.getFirstTarget(), game);
                return targetPlayer.discard(card, source, game);

            }

        }
        return false;
    }

    @Override
    public ExtortionEffect copy() {
        return new ExtortionEffect(this);
    }
}
