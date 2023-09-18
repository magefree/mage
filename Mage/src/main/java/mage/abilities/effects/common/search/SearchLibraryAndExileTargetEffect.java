package mage.abilities.effects.common.search;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.SearchEffect;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 * @author TheElk801
 */
public class SearchLibraryAndExileTargetEffect extends SearchEffect {

    public SearchLibraryAndExileTargetEffect(int amount, boolean upTo) {
        super(new TargetCardInLibrary(upTo ? 0 : amount, amount, amount > 1 ? StaticFilters.FILTER_CARD_CARDS : StaticFilters.FILTER_CARD), Outcome.Exile);
    }

    private SearchLibraryAndExileTargetEffect(final SearchLibraryAndExileTargetEffect effect) {
        super(effect);
    }

    @Override
    public SearchLibraryAndExileTargetEffect copy() {
        return new SearchLibraryAndExileTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || player == null) {
            return false;
        }
        controller.searchLibrary(target, source, game, player.getId());
        Cards cards = new CardsImpl();
        target.getTargets()
                .stream()
                .map(uuid -> player.getLibrary().getCard(uuid, game))
                .forEach(cards::add);
        controller.moveCards(cards, Zone.EXILED, source, game);
        player.shuffleLibrary(source, game);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "search "
                + getTargetPointer().describeTargets(mode.getTargets(), "that player")
                + "'s library for "
                + target.getDescription()
                + (target.getMaxNumberOfTargets() > 1 ? " and exile them" : " and exile it")
                + ". Then that player shuffles";
    }
}
