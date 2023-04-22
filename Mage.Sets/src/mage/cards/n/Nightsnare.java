package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Nightsnare extends CardImpl {

    public Nightsnare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Target opponent reveals their hand. You may choose a nonland card from it. If you do, that player discards that card. If you don't, that player discards two cards.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new NightsnareDiscardEffect());
    }

    private Nightsnare(final Nightsnare card) {
        super(card);
    }

    @Override
    public Nightsnare copy() {
        return new Nightsnare(this);
    }
}

class NightsnareDiscardEffect extends OneShotEffect {

    public NightsnareDiscardEffect() {
        super(Outcome.Discard);
        staticText = "Target opponent reveals their hand. You may choose a nonland card from it. If you do, that player discards that card. If you don't, that player discards two cards";
    }

    public NightsnareDiscardEffect(final NightsnareDiscardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (player == null || controller == null) {
            return false;
        }
        if (player.getHand().isEmpty()) {
            return true;
        }
        Cards revealedCards = new CardsImpl();
        revealedCards.addAll(player.getHand());
        Card sourceCard = game.getCard(source.getSourceId());
        player.revealCards(sourceCard != null ? sourceCard.getIdName() : "Discard", revealedCards, game);
        // You may choose a nonland card from it.
        if (!controller.chooseUse(outcome, "Choose a card to discard? (Otherwise " + player.getLogName() + " has to discard 2 cards).", source, game)) {
            player.discard(2, false, false, source, game);
            return true;
        }
        TargetCard target = new TargetCard(1, Zone.HAND, new FilterNonlandCard());
        if (controller.choose(Outcome.Benefit, revealedCards, target, source, game)) {
            Card card = revealedCards.get(target.getFirstTarget(), game);
            player.discard(card, false, source, game);
        }
        return true;
    }

    @Override
    public NightsnareDiscardEffect copy() {
        return new NightsnareDiscardEffect(this);
    }

}
