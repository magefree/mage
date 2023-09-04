package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.ManaUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SharedTrauma extends CardImpl {

    public SharedTrauma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");


        // Join forces - Starting with you, each player may pay any amount of mana. Each player puts the top X cards of their library into their graveyard, where X is the total amount of mana paid this way.
        this.getSpellAbility().addEffect(new SharedTraumaEffect());
    }

    private SharedTrauma(final SharedTrauma card) {
        super(card);
    }

    @Override
    public SharedTrauma copy() {
        return new SharedTrauma(this);
    }
}

class SharedTraumaEffect extends OneShotEffect {

    public SharedTraumaEffect() {
        super(Outcome.Detriment);
        this.staticText = "<i>Join forces</i> &mdash; Starting with you, each player may pay any amount of mana. Each player mills X cards, where X is the total amount of mana paid this way";
    }

    private SharedTraumaEffect(final SharedTraumaEffect effect) {
        super(effect);
    }

    @Override
    public SharedTraumaEffect copy() {
        return new SharedTraumaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int xSum = 0;
            xSum += ManaUtil.playerPaysXGenericMana(false, "Shared Trauma", controller, source, game);
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (!Objects.equals(playerId, controller.getId())) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        xSum += ManaUtil.playerPaysXGenericMana(false, "Shared Trauma", player, source, game);
                    }
                }
            }

            if (xSum > 0) {
                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    Effect effect = new MillCardsTargetEffect(xSum);
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
}

