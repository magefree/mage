
package mage.abilities.effects.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class ExileCardFromOwnGraveyardControllerEffect extends OneShotEffect {
    private int amount;

    public ExileCardFromOwnGraveyardControllerEffect(int amount) {
        super(Outcome.Exile);
        this.amount = amount;
        this.staticText = setText();
    }

    public ExileCardFromOwnGraveyardControllerEffect(final ExileCardFromOwnGraveyardControllerEffect effect) {
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
        if(player != null){
            TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(Math.min(amount, player.getGraveyard().size()), new FilterCard());
            if (player.chooseTarget(outcome, target, source, game)) {
                for (UUID targetId: target.getTargets()) {
                    Card card = player.getGraveyard().get(targetId, game);
                    if (card != null) {
                        card.moveToZone(Zone.EXILED, source.getSourceId(), game, false);
                    }
                }
            }
            return true;
        }
        return false;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("exile ");
        if (amount == 1) {
            sb.append(" a card ");
        } else {
            sb.append(CardUtil.numberToText(amount)). append(" cards ");
        }
        sb.append("from your graveyard");
        return sb.toString();
    }

}
