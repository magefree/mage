package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.PlayerLostLifeWatcher;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class LudevicNecroAlchemist extends CardImpl {

    public LudevicNecroAlchemist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // At the beginning of each player's end step, that player may draw a card if a player other than you lost life this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new LudevicNecroAlchemistEffect(),
                TargetController.EACH_PLAYER,
                false)
                .addHint(new ConditionHint(LudevicNecroAlchemistCondition.instance, "Player other than you lost life this turn")));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private LudevicNecroAlchemist(final LudevicNecroAlchemist card) {
        super(card);
    }

    @Override
    public LudevicNecroAlchemist copy() {
        return new LudevicNecroAlchemist(this);
    }
}

enum LudevicNecroAlchemistCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && watcher != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (!playerId.equals(controller.getId()) && watcher.getLifeLost(playerId) > 0) {
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
        staticText = "that player may draw a card if a player other than you lost life this turn";
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
        // Ludevic’s triggered ability triggers at the beginning of each player’s end step, including yours,
        // even if no player has lost life that turn. Whether or not a player has lost life is checked
        // only as the triggered ability resolves. (2016-11-08)
        if (!LudevicNecroAlchemistCondition.instance.apply(game, source)) {
            return false;
        }

        Player player = game.getPlayer(game.getActivePlayerId());
        if (player != null
                && player.chooseUse(Outcome.DrawCard, "Draw a card?", source, game)) {
            player.drawCards(1, source, game);
            return true;
        }
        return false;
    }
}
