package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.PlotAbility;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

public class MayExileCardFromHandPlottedEffect extends OneShotEffect {

    private final FilterCard filter;

    public MayExileCardFromHandPlottedEffect(FilterCard filter) {
        super(Outcome.PutCardInPlay);
        this.filter = filter;
        this.staticText = "you may exile a " + filter.getMessage() + " from your hand. If you do, it becomes plotted";
    }

    private MayExileCardFromHandPlottedEffect(final MayExileCardFromHandPlottedEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public MayExileCardFromHandPlottedEffect copy() {
        return new MayExileCardFromHandPlottedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInHand target = new TargetCardInHand(0, 1, filter);
        if (player.chooseTarget(outcome, target, source, game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                PlotAbility.doExileAndPlotCard(card, game, source);
            }
        }
        return true;
    }
}