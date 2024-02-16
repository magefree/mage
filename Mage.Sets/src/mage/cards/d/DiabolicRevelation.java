package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author North
 */
public final class DiabolicRevelation extends CardImpl {

    public DiabolicRevelation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{3}{B}{B}");

        // Search your library for up to X cards and put those cards into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new DiabolicRevelationEffect());
    }

    private DiabolicRevelation(final DiabolicRevelation card) {
        super(card);
    }

    @Override
    public DiabolicRevelation copy() {
        return new DiabolicRevelation(this);
    }
}

class DiabolicRevelationEffect extends OneShotEffect {

    DiabolicRevelationEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search your library for up to X cards, put those cards into your hand, then shuffle";
    }

    private DiabolicRevelationEffect(final DiabolicRevelationEffect effect) {
        super(effect);
    }

    @Override
    public DiabolicRevelationEffect copy() {
        return new DiabolicRevelationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int xValue = source.getManaCostsToPay().getX();
        if (xValue < 1) {
            player.shuffleLibrary(source, game);
            return true;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(
                0, xValue, StaticFilters.FILTER_CARD
        );
        player.searchLibrary(target, source, game);
        Cards cards = new CardsImpl();
        for (UUID targetId : target.getTargets()) {
            Card card = player.getLibrary().getCard(targetId, game);
            if (card != null) {
                cards.add(card);
            }
        }
        if (!cards.isEmpty()) {
            player.moveCards(cards, Zone.HAND, source, game);
        }
        player.shuffleLibrary(source, game);
        return true;
    }
}
