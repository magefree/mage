package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterPlayerOrPlaneswalker;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class Kaboom extends CardImpl {

    public Kaboom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Choose any number of target players. For each of those players, reveal cards from the top of your library until you reveal a nonland card.
        // Kaboom deals damage equal to that card's converted mana cost to that player, then you put the revealed cards on the bottom of your library in any order.
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker(0, Integer.MAX_VALUE, new FilterPlayerOrPlaneswalker(), false));
        this.getSpellAbility().addEffect(new KaboomEffect());

    }

    private Kaboom(final Kaboom card) {
        super(card);
    }

    @Override
    public Kaboom copy() {
        return new Kaboom(this);
    }
}

class KaboomEffect extends OneShotEffect {

    KaboomEffect() {
        super(Outcome.Damage);
        staticText = "Choose any number of target players or planeswalkers. " +
                "For each of them, reveal cards from the top of your library until you reveal a nonland card, " +
                "{this} deals damage equal to that card's mana value to that player or planeswalker, " +
                "then you put the revealed cards on the bottom of your library in any order.";
    }

    private KaboomEffect(final KaboomEffect effect) {
        super(effect);
    }

    @Override
    public KaboomEffect copy() {
        return new KaboomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Cards toReveal = new CardsImpl();
            Card nonland = null;
            for (Card card : controller.getLibrary().getCards(game)) {
                toReveal.add(card);
                if (!card.isLand(game)) {
                    nonland = card;
                    break;
                }
            }
            controller.revealCards(source, toReveal, game);
            if (nonland != null) {
                Permanent planeswalker = game.getPermanent(targetId);
                if (planeswalker != null) {
                    planeswalker.damage(nonland.getManaValue(), source, game);
                } else {
                    Player targetPlayer = game.getPlayer(targetId);
                    if (targetPlayer != null) {
                        targetPlayer.damage(nonland.getManaValue(), source, game);
                    }
                }
            }
            controller.putCardsOnBottomOfLibrary(toReveal, game, source, true);
        }
        return true;
    }
}
