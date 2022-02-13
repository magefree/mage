
package mage.cards.b;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class BitterFeud extends CardImpl {

    public BitterFeud(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{R}");

        // As Bitter Feud enters the battlefield, choose two players.
        this.addAbility(new AsEntersBattlefieldAbility(new BitterFeudEntersBattlefieldEffect()));

        // If a source controlled by one of the chosen players would deal damage to the other chosen player or a permanent that player controls, that source deals double that damage to that player or permanent instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BitterFeudEffect()));
    }

    private BitterFeud(final BitterFeud card) {
        super(card);
    }

    @Override
    public BitterFeud copy() {
        return new BitterFeud(this);
    }
}

class BitterFeudEntersBattlefieldEffect extends OneShotEffect {

    public BitterFeudEntersBattlefieldEffect() {
        super(Outcome.Damage);
        staticText = "choose two players";
    }

    public BitterFeudEntersBattlefieldEffect(final BitterFeudEntersBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) { return false; }

        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (permanent == null) { return false; }

        TargetPlayer target = new TargetPlayer(2, 2, true);
        controller.chooseTarget(outcome, target, source, game);
        Player player1 = game.getPlayer(target.getFirstTarget());
        if (player1 == null) { return false; }

        if (target.getTargets().size() <= 1) { return false; }

        Player player2 = game.getPlayer(target.getTargets().get(1));
        if (player2 == null) { return false; }

        game.getState().setValue(source.getSourceId() + "_player1", player1);
        game.getState().setValue(source.getSourceId() + "_player2", player2);
        game.informPlayers(permanent.getLogName() + ": " + controller.getLogName() + " has chosen " + player1.getLogName() + " and " + player2.getLogName());
        permanent.addInfo("chosen players", "<font color = 'blue'>Chosen players: " + player1.getName() + ", " + player2.getName() + "</font>", game);

        return true;
    }

    @Override
    public BitterFeudEntersBattlefieldEffect copy() {
        return new BitterFeudEntersBattlefieldEffect(this);
    }

}

class BitterFeudEffect extends ReplacementEffectImpl {

    private Player player1;
    private Player player2;

    public BitterFeudEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "If a source controlled by one of the chosen players would deal damage to the other chosen player or a permanent that player controls, that source deals double that damage to that player or permanent instead";
    }

    public BitterFeudEffect(final BitterFeudEffect effect) {
        super(effect);
        this.player1 = effect.player1;
        this.player2 = effect.player2;
    }

    @Override
    public BitterFeudEffect copy() {
        return new BitterFeudEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT && event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        player1 = (Player) game.getState().getValue(source.getSourceId() + "_player1");
        if (player1 == null) { return false; }

        player2 = (Player) game.getState().getValue(source.getSourceId() + "_player2");
        if (player2 == null) { return false; }

        UUID targetPlayerId;
        switch (event.getType()) {
            case DAMAGE_PLAYER:
                targetPlayerId = event.getTargetId();
                break;
            case DAMAGE_PERMANENT:
                Permanent permanent = game.getPermanent(event.getTargetId());
                if (permanent == null) { return false; }

                targetPlayerId = permanent.getControllerId();
                break;
            default:
                return false;
        }

        if (!player1.getId().equals(targetPlayerId) && !player2.getId().equals(targetPlayerId)) { return false; }

        UUID sourcePlayerId;
        MageObject damageSource = game.getObject(event.getSourceId());

        if (damageSource instanceof StackObject) {
            sourcePlayerId = ((StackObject) damageSource).getControllerId();
        } else if (damageSource instanceof Permanent) {
            sourcePlayerId = ((Permanent) damageSource).getControllerId();
        } else if (damageSource instanceof Card) {
            sourcePlayerId = ((Card) damageSource).getOwnerId();
        } else {
            return false;
        }

        return sourcePlayerId != null
                && (player1.getId().equals(sourcePlayerId) || player2.getId().equals(sourcePlayerId))
                && !sourcePlayerId.equals(targetPlayerId);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }
}
