
package mage.cards.a;

import java.util.Objects;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.SoldierToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class AllianceOfArms extends CardImpl {

    public AllianceOfArms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{W}");


        // Join forces - Starting with you, each player may pay any amount of mana. Each player creates X 1/1 white Soldier creature tokens, where X is the total amount of mana paid this way.
        this.getSpellAbility().addEffect(new AllianceOfArmsEffect());
    }

    public AllianceOfArms(final AllianceOfArms card) {
        super(card);
    }

    @Override
    public AllianceOfArms copy() {
        return new AllianceOfArms(this);
    }
}

class AllianceOfArmsEffect extends OneShotEffect {

    public AllianceOfArmsEffect() {
        super(Outcome.Detriment);
        this.staticText = "<i>Join forces</i> &mdash; Starting with you, each player may pay any amount of mana. Each player creates X 1/1 white Soldier creature tokens, where X is the total amount of mana paid this way";
    }

    public AllianceOfArmsEffect(final AllianceOfArmsEffect effect) {
        super(effect);
    }

    @Override
    public AllianceOfArmsEffect copy() {
        return new AllianceOfArmsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int xSum = 0;
            xSum += playerPaysXGenericMana(controller, source, game);
            for(UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (!Objects.equals(playerId, controller.getId())) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        xSum += playerPaysXGenericMana(player, source, game);

                    }
                }
            }
            if (xSum > 0) {
                for(UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    Effect effect = new CreateTokenTargetEffect(new SoldierToken(), xSum);
                    effect.setTargetPointer(new FixedTarget(playerId));
                    effect.apply(game, source);
                }

            }
            // prevent undo
            controller.resetStoredBookmark(game);
            return true;
        }
        return false;
    }

    protected static int playerPaysXGenericMana(Player player, Ability source, Game game) {
        int xValue = 0;
        boolean payed = false;
        while (player.canRespond() && !payed) {
            xValue = player.announceXMana(0, Integer.MAX_VALUE, "How much mana will you pay?", game, source);
            if (xValue > 0) {
                Cost cost = new GenericManaCost(xValue);
                payed = cost.pay(source, game, source.getSourceId(), player.getId(), false, null);
            } else {
                payed = true;
            }
        }
        game.informPlayers(new StringBuilder(player.getLogName()).append(" pays {").append(xValue).append("}.").toString());
        return xValue;
    }
}
