package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class ExileCardFromOwnGraveyardControllerEffect extends OneShotEffect {

    private final int amount;

    public ExileCardFromOwnGraveyardControllerEffect(int amount) {
        super(Outcome.Exile);
        this.amount = amount;
        this.staticText = setText();
    }

    private ExileCardFromOwnGraveyardControllerEffect(final ExileCardFromOwnGraveyardControllerEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public ExileCardFromOwnGraveyardControllerEffect copy() {
        return new ExileCardFromOwnGraveyardControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getGraveyard().isEmpty()) {
            return false;
        }
        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(Math.min(
                amount, player.getGraveyard().size()
        ), StaticFilters.FILTER_CARD);
        target.withNotTarget(true);
        if (!player.chooseTarget(outcome, target, source, game)) {
            return true;
        }
        Cards cards = new CardsImpl();
        for (UUID targetId : target.getTargets()) {
            cards.add(player.getGraveyard().get(targetId, game));
        }
        return player.moveCards(cards, Zone.EXILED, source, game);
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("exile ");
        if (amount == 1) {
            sb.append("a card ");
        } else {
            sb.append(CardUtil.numberToText(amount)).append(" cards ");
        }
        sb.append("from your graveyard");
        return sb.toString();
    }
}
