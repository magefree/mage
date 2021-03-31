package mage.cards.c;

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

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Curate extends CardImpl {

    public Curate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Look at the top two cards of your library. Put any number of them into your graveyard and the rest back on top of your library in any order.
        // Draw a card.
        this.getSpellAbility().addEffect(new CurateEffect());
    }

    private Curate(final Curate card) {
        super(card);
    }

    @Override
    public Curate copy() {
        return new Curate(this);
    }
}

class CurateEffect extends OneShotEffect {

    CurateEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top two cards of your library. Put any number of them into your graveyard " +
                "and the rest back on top of your library in any order.<br>Draw a card";
    }

    private CurateEffect(final CurateEffect effect) {
        super(effect);
    }

    @Override
    public CurateEffect copy() {
        return new CurateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 2));
        if (!cards.isEmpty()) {
            TargetCard target = new TargetCard(0, cards.size(), Zone.LIBRARY, StaticFilters.FILTER_CARD);
            target.withChooseHint("to graveyard");
            player.chooseTarget(Outcome.Benefit, cards, target, source, game);
            player.moveCards(new CardsImpl(target.getTargets()), Zone.GRAVEYARD, source, game);
            cards.removeAll(target.getTargets());
            player.putCardsOnTopOfLibrary(cards, game, source, true);
        }
        player.drawCards(1, source, game);
        return true;
    }
}
