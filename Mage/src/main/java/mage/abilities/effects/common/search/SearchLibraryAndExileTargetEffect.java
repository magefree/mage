package mage.abilities.effects.common.search;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public class SearchLibraryAndExileTargetEffect extends OneShotEffect {

    private final int amount;
    private final boolean upTo;

    public SearchLibraryAndExileTargetEffect(int amount, boolean upTo) {
        super(Outcome.Benefit);
        this.amount = amount;
        this.upTo = upTo;
    }

    private SearchLibraryAndExileTargetEffect(final SearchLibraryAndExileTargetEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.upTo = effect.upTo;
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
        TargetCardInLibrary target = new TargetCardInLibrary(upTo ? 0 : amount, amount, StaticFilters.FILTER_CARD);
        controller.searchLibrary(target, source, game, player.getId());
        Cards cards = new CardsImpl();
        target.getTargets()
                .stream()
                .map(uuid -> player.getLibrary().getCard(uuid, game))
                .forEach(cards::add);
        if (cards.isEmpty()) {
            return false;
        }
        controller.moveCards(cards, Zone.EXILED, source, game);
        player.shuffleLibrary(source, game);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder("search ");
        if (mode.getTargets().isEmpty()) {
            sb.append("that player");
        } else {
            sb.append("target ");
            sb.append(mode.getTargets().get(0).getTargetName());
        }
        sb.append("'s library for ");
        if (amount > 1) {
            if (upTo) {
                sb.append("up to ");
            }
            sb.append(CardUtil.numberToText(amount));
            sb.append(" cards and exile them");
        } else {
            sb.append("a card and exile it");
        }
        sb.append(". Then that player shuffles");
        return sb.toString();
    }
}
