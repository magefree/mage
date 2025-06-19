package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MemoriesReturning extends CardImpl {

    public MemoriesReturning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");

        // Reveal the top five cards of your library. Put one of them into your hand. Then choose an opponent. They put one on the bottom of your library. Then you put one into your hand. Then they put one on the bottom of your library. Put the other into your hand.
        this.getSpellAbility().addEffect(new MemoriesReturningEffect());

        // Flashback {7}{U}{U}.
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{7}{U}{U}")));
    }

    private MemoriesReturning(final MemoriesReturning card) {
        super(card);
    }

    @Override
    public MemoriesReturning copy() {
        return new MemoriesReturning(this);
    }
}

class MemoriesReturningEffect extends OneShotEffect {

    MemoriesReturningEffect() {
        super(Outcome.Benefit);
        staticText = "reveal the top five cards of your library. Put one of them into your hand. " +
                "Then choose an opponent. They put one on the bottom of your library. " +
                "Then you put one into your hand. Then they put one on the bottom of your library. " +
                "Put the other into your hand";
    }

    private MemoriesReturningEffect(final MemoriesReturningEffect effect) {
        super(effect);
    }

    @Override
    public MemoriesReturningEffect copy() {
        return new MemoriesReturningEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 5));
        controller.revealCards(source, cards, game);
        if (putCardInHand(controller, cards, source, game)) {
            return true;
        }
        TargetPlayer target = new TargetOpponent();
        target.withNotTarget(true);
        controller.choose(Outcome.Neutral, target, source, game);
        Player opponent = game.getPlayer(target.getFirstTarget());
        if (putCardOnBottom(controller, opponent, cards, source, game)) {
            return true;
        }
        if (putCardInHand(controller, cards, source, game)) {
            return true;
        }
        if (putCardOnBottom(controller, opponent, cards, source, game)) {
            return true;
        }
        putCardInHand(controller, cards, source, game);
        return true;
    }

    private static boolean putCardInHand(Player controller, Cards cards, Ability source, Game game) {
        switch (cards.size()) {
            case 0:
                return true;
            case 1:
                controller.moveCards(cards, Zone.HAND, source, game);
                return true;
        }
        TargetCard target = new TargetCardInLibrary();
        target.withChooseHint("to put in your hand");
        controller.choose(Outcome.DrawCard, cards, target, source, game);
        controller.moveCards(game.getCard(target.getFirstTarget()), Zone.HAND, source, game);
        cards.retainZone(Zone.LIBRARY, game);
        return false;
    }

    private static boolean putCardOnBottom(Player controller, Player opponent, Cards cards, Ability source, Game game) {
        switch (cards.size()) {
            case 0:
                return true;
            case 1:
                controller.putCardsOnBottomOfLibrary(cards, game, source, true);
                return true;
        }
        if (opponent == null) {
            return false;
        }
        TargetCard target = new TargetCardInLibrary();
        target.withChooseHint("to put on the bottom of " + controller.getName() + "'s library");
        opponent.choose(Outcome.Discard, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        controller.putCardsOnBottomOfLibrary(card, game, source);
        cards.remove(card);
        return false;
    }
}
