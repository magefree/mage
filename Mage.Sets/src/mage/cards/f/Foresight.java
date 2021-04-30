package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.SearchEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
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
 * @author TheElk801
 */
public final class Foresight extends CardImpl {

    public Foresight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Search your library for three cards, exile them, then shuffle your library.
        this.getSpellAbility().addEffect(new ForesightEffect());

        // Draw a card at the beginning of the next turn's upkeep.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1)), false).concatBy("<br>"));
    }

    private Foresight(final Foresight card) {
        super(card);
    }

    @Override
    public Foresight copy() {
        return new Foresight(this);
    }
}

class ForesightEffect extends SearchEffect {

    ForesightEffect() {
        super(new TargetCardInLibrary(3, StaticFilters.FILTER_CARD), Outcome.Benefit);
        staticText = "Search your library for three cards, exile them, then shuffle";
    }

    private ForesightEffect(final ForesightEffect effect) {
        super(effect);
    }

    @Override
    public ForesightEffect copy() {
        return new ForesightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.searchLibrary(target, source, game);
        Cards cards = new CardsImpl();
        for (UUID targetId : target.getTargets()) {
            Card card = player.getLibrary().getCard(targetId, game);
            if (card != null) {
                cards.add(card);
            }
        }
        player.moveCards(cards, Zone.EXILED, source, game);
        player.shuffleLibrary(source, game);
        return true;
    }
}
