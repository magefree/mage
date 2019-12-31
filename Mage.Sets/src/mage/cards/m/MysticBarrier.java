package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.players.PlayerList;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MysticBarrier extends CardImpl {

    static final String ALLOW_ATTACKING_LEFT = "Allow attacking left";
    static final String ALLOW_ATTACKING_RIGHT = "Allow attacking right";

    public MysticBarrier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}");

        // When Mystic Barrier enters the battlefield or at the beginning of your upkeep, choose left or right.
        this.addAbility(new OrTriggeredAbility(Zone.BATTLEFIELD, new ChooseModeEffect(
                "Choose a direction to allow attacking in.",
                ALLOW_ATTACKING_LEFT, ALLOW_ATTACKING_RIGHT),
                new EntersBattlefieldTriggeredAbility(null, false),
                new BeginningOfUpkeepTriggeredAbility(null, TargetController.YOU, false)));

        // Each player may attack only the nearest opponent in the last chosen direction and planeswalkers controlled by that player.
        this.addAbility(new SimpleStaticAbility(new MysticBarrierReplacementEffect()));
    }

    private MysticBarrier(final MysticBarrier card) {
        super(card);
    }

    @Override
    public MysticBarrier copy() {
        return new MysticBarrier(this);
    }
}

class MysticBarrierReplacementEffect extends ReplacementEffectImpl {

    MysticBarrierReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Each player may attack only the nearest opponent in the " +
                "last chosen direction and planeswalkers controlled by that player.";
    }

    private MysticBarrierReplacementEffect(MysticBarrierReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARE_ATTACKER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getPlayers().size() > 2) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller == null) {
                return false;
            }
            if (!game.getState().getPlayersInRange(controller.getId(), game).contains(event.getPlayerId())) {
                return false;
            }
            String allowedDirection = (String) game.getState().getValue(source.getSourceId() + "_modeChoice");
            if (allowedDirection == null) {
                return false;
            }
            Player defender = game.getPlayer(event.getTargetId());
            if (defender == null) {
                Permanent planeswalker = game.getPermanent(event.getTargetId());
                if (planeswalker != null) {
                    defender = game.getPlayer(planeswalker.getControllerId());
                }
            }
            if (defender == null) {
                return false;
            }
            PlayerList playerList = game.getState().getPlayerList(event.getPlayerId());
            if (allowedDirection.equals(MysticBarrier.ALLOW_ATTACKING_LEFT)
                    && !playerList.getNext().equals(defender.getId())) {
                // the defender is not the player to the left
                Player attacker = game.getPlayer(event.getPlayerId());
                if (attacker != null) {
                    game.informPlayer(attacker, "You can only attack to the left!");
                }
                return true;
            }
            if (allowedDirection.equals(MysticBarrier.ALLOW_ATTACKING_RIGHT)
                    && !playerList.getPrevious().equals(defender.getId())) {
                // the defender is not the player to the right
                Player attacker = game.getPlayer(event.getPlayerId());
                if (attacker != null) {
                    game.informPlayer(attacker, "You can only attack to the right!");
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public MysticBarrierReplacementEffect copy() {
        return new MysticBarrierReplacementEffect(this);
    }

}
