
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class MagisterOfWorth extends CardImpl {

    public MagisterOfWorth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{B}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Will of the council - When Magister of Worth enters the battlefield, starting with you, each player votes for grace or condemnation. If grace gets more votes, each player returns each creature card from their graveyard to the battlefield. If condemnation gets more votes or the vote is tied, destroy all creatures other than Magister of Worth.
        Effect effect = new MagisterOfWorthVoteEffect();
        effect.setText("Will of the council - When Magister of Worth enters the battlefield, starting with you, each player votes for grace or condemnation. If grace gets more votes, each player returns each creature card from their graveyard to the battlefield. If condemnation gets more votes or the vote is tied, destroy all creatures other than Magister of Worth");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect, false, true));
    }

    public MagisterOfWorth(final MagisterOfWorth card) {
        super(card);
    }

    @Override
    public MagisterOfWorth copy() {
        return new MagisterOfWorth(this);
    }
}

class MagisterOfWorthVoteEffect extends OneShotEffect {

    MagisterOfWorthVoteEffect() {
        super(Outcome.Benefit);
        this.staticText = "<i>Will of the council</i> &mdash; When {this} enters the battlefield, starting with you, each player votes for grace or condemnation. If grace gets more votes, each player returns each creature card from their graveyard to the battlefield. If condemnation gets more votes or the vote is tied, destroy all creatures other than {this}.";
    }

    MagisterOfWorthVoteEffect(final MagisterOfWorthVoteEffect effect) {
        super(effect);
    }

    @Override
    public MagisterOfWorthVoteEffect copy() {
        return new MagisterOfWorthVoteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int graceCount = 0;
            int condemnationCount = 0;
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    if (player.chooseUse(Outcome.DestroyPermanent, "Choose grace?", source, game)) {
                        graceCount++;
                        game.informPlayers(player.getLogName() + " has chosen: grace");
                    } else {
                        condemnationCount++;
                        game.informPlayers(player.getLogName() + " has chosen: condemnation");
                    }
                }
            }
            if (graceCount > condemnationCount) {
                new MagisterOfWorthReturnFromGraveyardEffect().apply(game, source);
            } else {
                FilterPermanent filter = new FilterCreaturePermanent("creatures other than {this}");
                filter.add(AnotherPredicate.instance);
                new DestroyAllEffect(filter).apply(game, source);
            }
            return true;
        }
        return false;
    }
}

class MagisterOfWorthReturnFromGraveyardEffect extends OneShotEffect {

    public MagisterOfWorthReturnFromGraveyardEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "each player returns each creature card from their graveyard to the battlefield";
    }

    public MagisterOfWorthReturnFromGraveyardEffect(final MagisterOfWorthReturnFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    player.moveCards(player.getGraveyard().getCards(new FilterCreatureCard(), game), Zone.BATTLEFIELD, source, game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public MagisterOfWorthReturnFromGraveyardEffect copy() {
        return new MagisterOfWorthReturnFromGraveyardEffect(this);
    }

}
