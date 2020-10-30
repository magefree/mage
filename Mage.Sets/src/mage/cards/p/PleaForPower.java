
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class PleaForPower extends CardImpl {

    public PleaForPower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}");

        // Will of the council - Starting with you, each player votes for time or knowledge. If time gets more votes, take an extra turn after this one. If knowledge gets more votes or the vote is tied, draw three cards.
        this.getSpellAbility().addEffect(new PleaForPowerEffect());
    }

    public PleaForPower(final PleaForPower card) {
        super(card);
    }

    @Override
    public PleaForPower copy() {
        return new PleaForPower(this);
    }
}

class PleaForPowerEffect extends OneShotEffect {

    PleaForPowerEffect() {
        super(Outcome.Benefit);
        this.staticText = "<i>Will of the council</i> &mdash; Starting with you, each player votes for time or knowledge. If time gets more votes, take an extra turn after this one. If knowledge gets more votes or the vote is tied, draw three cards";
    }

    PleaForPowerEffect(final PleaForPowerEffect effect) {
        super(effect);
    }

    @Override
    public PleaForPowerEffect copy() {
        return new PleaForPowerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int timeCount = 0;
            int knowledgeCount = 0;
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    if (player.chooseUse(Outcome.ExtraTurn, "Choose time?", source, game)) {
                        timeCount++;
                        game.informPlayers(player.getLogName() + " has chosen: time");
                    } else {
                        knowledgeCount++;
                        game.informPlayers(player.getLogName() + " has chosen: knowledge");
                    }
                }
            }
            if (timeCount > knowledgeCount) {
                new AddExtraTurnControllerEffect().apply(game, source);
            } else {
                controller.drawCards(3, source.getSourceId(), game);
            }
            return true;
        }
        return false;
    }
}
