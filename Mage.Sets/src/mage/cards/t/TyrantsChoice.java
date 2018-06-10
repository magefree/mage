
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class TyrantsChoice extends CardImpl {

    public TyrantsChoice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}");

        // Will of the council - Starting with you, each player votes for death or torture. If death gets more votes, each opponent sacrifices a creature. If torture gets more votes or the vote is tied, each opponent loses 4 life.
        this.getSpellAbility().addEffect(new TyrantsChoiceEffect());
    }

    public TyrantsChoice(final TyrantsChoice card) {
        super(card);
    }

    @Override
    public TyrantsChoice copy() {
        return new TyrantsChoice(this);
    }
}

class TyrantsChoiceEffect extends OneShotEffect {

    TyrantsChoiceEffect() {
        super(Outcome.Benefit);
        this.staticText = "<i>Will of the council</i> &mdash; Starting with you, each player votes for death or torture. If death gets more votes, each opponent sacrifices a creature. If torture gets more votes or the vote is tied, each opponent loses 4 life";
    }

    TyrantsChoiceEffect(final TyrantsChoiceEffect effect) {
        super(effect);
    }

    @Override
    public TyrantsChoiceEffect copy() {
        return new TyrantsChoiceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int deathCount = 0;
            int tortureCount = 0;
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    if (player.chooseUse(Outcome.Sacrifice, "Choose death?", source, game)) {
                        deathCount++;
                        game.informPlayers(player.getLogName() + " has voted for death");
                    } else {
                        tortureCount++;
                        game.informPlayers(player.getLogName() + " has voted for torture");
                    }
                }
            }
            if (deathCount > tortureCount) {
                new SacrificeOpponentsEffect(new FilterControlledCreaturePermanent("a creature")).apply(game, source);
            } else {
                new TyrantsChoiceLoseLifeEffect().apply(game, source);
            }
            return true;
        }
        return false;
    }
}

class TyrantsChoiceLoseLifeEffect extends OneShotEffect {

    public TyrantsChoiceLoseLifeEffect() {
        super(Outcome.Damage);
        staticText = "Each opponent loses 2 life";
    }

    public TyrantsChoiceLoseLifeEffect(final TyrantsChoiceLoseLifeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            game.getPlayer(opponentId).loseLife(4, game, false);
        }
        return true;
    }

    @Override
    public TyrantsChoiceLoseLifeEffect copy() {
        return new TyrantsChoiceLoseLifeEffect(this);
    }

}
