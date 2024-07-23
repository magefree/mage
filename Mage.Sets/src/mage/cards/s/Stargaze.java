package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Stargaze extends CardImpl {

    public Stargaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}{B}");

        // Look at twice X cards from the top of your library. Put X cards from among them into your hand and the rest into your graveyard. You lose X life.
        this.getSpellAbility().addEffect(new StargazeEffect());
    }

    private Stargaze(final Stargaze card) {
        super(card);
    }

    @Override
    public Stargaze copy() {
        return new Stargaze(this);
    }
}

class StargazeEffect extends OneShotEffect {

    StargazeEffect() {
        super(Outcome.Benefit);
        staticText = "look at twice X cards from the top of your library. Put X cards " +
                "from among them into your hand and the rest into your graveyard. You lose X life";
    }

    private StargazeEffect(final StargazeEffect effect) {
        super(effect);
    }

    @Override
    public StargazeEffect copy() {
        return new StargazeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int xValue = source.getManaCostsToPay().getX();
        if (player == null || xValue < 1) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 2 * xValue));
        TargetCard target = new TargetCardInLibrary(Math.max(xValue, cards.size()), StaticFilters.FILTER_CARD);
        player.choose(outcome, cards, target, source, game);
        player.moveCards(new CardsImpl(target.getTargets()), Zone.HAND, source, game);
        cards.retainZone(Zone.LIBRARY, game);
        player.moveCards(cards, Zone.GRAVEYARD, source, game);
        player.loseLife(xValue, game, source, false);
        return true;
    }
}
