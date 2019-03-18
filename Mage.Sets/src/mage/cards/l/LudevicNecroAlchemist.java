package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.PlayerLostLifeWatcher;

/**
 *
 * @author spjspj
 */
public final class LudevicNecroAlchemist extends CardImpl {

    public LudevicNecroAlchemist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // At the beginning of each player's end step, that player may draw a card if a player other than you lost life this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD,
                new LudevicNecroAlchemistEffect(),
                TargetController.EACH_PLAYER,
                new LudevicNecroAlchemistCondition(),
                false));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    public LudevicNecroAlchemist(final LudevicNecroAlchemist card) {
        super(card);
    }

    @Override
    public LudevicNecroAlchemist copy() {
        return new LudevicNecroAlchemist(this);
    }
}

class LudevicNecroAlchemistCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null
                && watcher != null
                && watcher.getLifeLost(controller.getId()) == 0) {
            for (UUID playerId : controller.getInRange()) {
                if (watcher.getLifeLost(playerId) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "if a player other than you lost life this turn";
    }
}

class LudevicNecroAlchemistEffect extends OneShotEffect {

    public LudevicNecroAlchemistEffect() {
        super(Outcome.DrawCard);
        staticText = "that player may draw a card";
    }

    public LudevicNecroAlchemistEffect(final LudevicNecroAlchemistEffect effect) {
        super(effect);
    }

    @Override
    public LudevicNecroAlchemistEffect copy() {
        return new LudevicNecroAlchemistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player != null
                && player.chooseUse(Outcome.DrawCard, "Draw a card?", source, game)) {
            player.drawCards(1, game);
            return true;
        }
        return false;
    }
}
