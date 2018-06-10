
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostOpponentsEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class BiteOfTheBlackRose extends CardImpl {

    public BiteOfTheBlackRose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}");

        // Will of the council - Starting with you, each player votes for sickness or psychosis. If sickness gets more votes, creatures your opponents control get -2/-2 until end of turn. If psychosis gets more votes or the vote is tied, each opponent discards two cards.
        this.getSpellAbility().addEffect(new BiteOfTheBlackRoseEffect());
    }

    public BiteOfTheBlackRose(final BiteOfTheBlackRose card) {
        super(card);
    }

    @Override
    public BiteOfTheBlackRose copy() {
        return new BiteOfTheBlackRose(this);
    }
}

class BiteOfTheBlackRoseEffect extends OneShotEffect {

    BiteOfTheBlackRoseEffect() {
        super(Outcome.Benefit);
        this.staticText = "<i>Will of the council</i> &mdash; Starting with you, each player votes for sickness or psychosis. If sickness gets more votes, creatures your opponents control get -2/-2 until end of turn. If psychosis gets more votes or the vote is tied, each opponent discards two cards";
    }

    BiteOfTheBlackRoseEffect(final BiteOfTheBlackRoseEffect effect) {
        super(effect);
    }

    @Override
    public BiteOfTheBlackRoseEffect copy() {
        return new BiteOfTheBlackRoseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int sicknessCount = 0;
            int psychosisCount = 0;
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    if (player.chooseUse(Outcome.ExtraTurn, "Choose sickness?", source, game)) {
                        sicknessCount++;
                        game.informPlayers(player.getLogName() + " has voted for sickness");
                    } else {
                        psychosisCount++;
                        game.informPlayers(player.getLogName() + " has voted for psychosis");
                    }
                }
            }
            if (sicknessCount > psychosisCount) {
                ContinuousEffect effect = new BoostOpponentsEffect(-2, -2, Duration.EndOfTurn);
                game.addEffect(effect, source);
            } else {
                new DiscardEachPlayerEffect(new StaticValue(2), false, TargetController.OPPONENT).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
