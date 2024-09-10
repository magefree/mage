
package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author Viserion
 */
public class PlayAdditionalLandsControllerEffect extends ContinuousEffectImpl {

    protected int additionalCards;

    public PlayAdditionalLandsControllerEffect(int additionalCards, Duration duration) {
        super(duration, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        this.additionalCards = additionalCards;
        setText();
    }

    protected PlayAdditionalLandsControllerEffect(final PlayAdditionalLandsControllerEffect effect) {
        super(effect);
        this.additionalCards = effect.additionalCards;
    }

    @Override
    public PlayAdditionalLandsControllerEffect copy() {
        return new PlayAdditionalLandsControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (player.getLandsPerTurn() == Integer.MAX_VALUE || this.additionalCards == Integer.MAX_VALUE) {
                player.setLandsPerTurn(Integer.MAX_VALUE);
            } else {
                player.setLandsPerTurn(player.getLandsPerTurn() + this.additionalCards);
            }
            return true;
        }
        return true;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append("You may play ");
        if (additionalCards == Integer.MAX_VALUE) {
            sb.append("any number of");
        } else {
            if (additionalCards > 1 && duration == Duration.EndOfTurn) {
                sb.append("up to ");
            }
            sb.append(CardUtil.numberToText(additionalCards, "an"));
        }
        sb.append(" additional land").append((additionalCards == 1 ? "" : "s"))
                .append(duration == Duration.EndOfTurn ? " this turn" : " on each of your turns");
        staticText = sb.toString();
    }

}
