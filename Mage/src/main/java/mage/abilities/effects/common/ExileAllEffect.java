package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author LevelX2
 */
public class ExileAllEffect extends OneShotEffect {

    private final FilterPermanent filter;
    private final boolean forSource;

    public ExileAllEffect(FilterPermanent filter) {
        this(filter, false);
    }

    public ExileAllEffect(FilterPermanent filter, boolean forSource) {
        super(Outcome.Exile);
        this.filter = filter;
        this.forSource = forSource;
        setText();
    }

    public ExileAllEffect(final ExileAllEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
        this.forSource = effect.forSource;
    }

    @Override
    public ExileAllEffect copy() {
        return new ExileAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source.getSourceId(), game
        ).stream().forEach(cards::add);
        if (forSource) {
            return controller.moveCardsToExile(cards.getCards(game), source, game, true, CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source));
        }
        return controller.moveCards(cards, Zone.EXILED, source, game);

    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append("exile all ").append(filter.getMessage());
        staticText = sb.toString();
    }
}
