package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author nigelzor
 */
public final class ErraticExplosion extends CardImpl {

    public ErraticExplosion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Choose any target. Reveal cards from the top of your library until you reveal a nonland card. Erratic Explosion deals damage equal to that card's converted mana cost to that permanent or player. Put the revealed cards on the bottom of your library in any order.
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new ErraticExplosionEffect());
    }

    private ErraticExplosion(final ErraticExplosion card) {
        super(card);
    }

    @Override
    public ErraticExplosion copy() {
        return new ErraticExplosion(this);
    }
}

class ErraticExplosionEffect extends OneShotEffect {

    public ErraticExplosionEffect() {
        super(Outcome.Damage);
        this.staticText = "Choose any target. Reveal cards from the top of your library until you reveal a nonland card. {this} deals damage equal to that card's mana value to that permanent or player. Put the revealed cards on the bottom of your library in any order";
    }

    private ErraticExplosionEffect(final ErraticExplosionEffect effect) {
        super(effect);
    }

    @Override
    public ErraticExplosionEffect copy() {
        return new ErraticExplosionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            CardsImpl toReveal = new CardsImpl();
            Card nonLandCard = null;
            for (Card card : controller.getLibrary().getCards(game)) {
                toReveal.add(card);
                if (!card.isLand(game)) {
                    nonLandCard = card;
                    break;
                }
            }
            controller.revealCards(source, toReveal, game);
            // the nonland card
            if (nonLandCard != null) {
                Permanent targetCreature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
                if (targetCreature != null) {
                    targetCreature.damage(nonLandCard.getManaValue(), source.getSourceId(), source, game, false, true);
                } else {
                    Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
                    if (targetPlayer != null) {
                        targetPlayer.damage(nonLandCard.getManaValue(), source.getSourceId(), source, game);
                    }
                }
            }
            // put the cards on the bottom of the library in any order
            return controller.putCardsOnBottomOfLibrary(toReveal, game, source, true);
        }
        return false;
    }
}
