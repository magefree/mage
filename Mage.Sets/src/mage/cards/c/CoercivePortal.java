
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class CoercivePortal extends CardImpl {

    public CoercivePortal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // Will of the council - At the beginning of your upkeep, starting with you, each player votes for carnage or homage. If carnage gets more votes, sacrifice Coercive Portal and destroy all nonland permanents. If homage gets more votes or the vote is tied, draw a card.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CoercivePortalEffect(), TargetController.YOU, false));
    }

    public CoercivePortal(final CoercivePortal card) {
        super(card);
    }

    @Override
    public CoercivePortal copy() {
        return new CoercivePortal(this);
    }
}

class CoercivePortalEffect extends OneShotEffect {

    CoercivePortalEffect() {
        super(Outcome.Benefit);
        this.staticText = "<i>Will of the council</i> &mdash; At the beginning of your upkeep, starting with you, each player votes for carnage or homage. If carnage gets more votes, sacrifice Coercive Portal and destroy all nonland permanents. If homage gets more votes or the vote is tied, draw a card";
    }

    CoercivePortalEffect(final CoercivePortalEffect effect) {
        super(effect);
    }

    @Override
    public CoercivePortalEffect copy() {
        return new CoercivePortalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int carnageCount = 0;
            int homageCount = 0;
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    if (player.chooseUse(Outcome.DestroyPermanent, "Choose carnage?", source, game)) {
                        carnageCount++;
                        game.informPlayers(player.getLogName() + " has voted for carnage");
                    } else {
                        homageCount++;
                        game.informPlayers(player.getLogName() + " has voted for homage");
                    }
                }
            }
            if (carnageCount > homageCount) {
                new SacrificeSourceEffect().apply(game, source);
                new DestroyAllEffect(new FilterNonlandPermanent()).apply(game, source);
            } else {
                controller.drawCards(1, game);
            }
            return true;
        }
        return false;
    }
}
