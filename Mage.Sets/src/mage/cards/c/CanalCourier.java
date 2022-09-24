
package mage.cards.c;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class CanalCourier extends CardImpl {

    public CanalCourier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // When Canal Courier enters the battlefield, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect(), false));

        // Whenever Canal Courier and another creature attack different players, Canal Courier can't be blocked this combat.
        Effect effect = new CantBeBlockedSourceEffect(Duration.EndOfCombat);
        effect.setText("{this} can't be blocked this combat");
        this.addAbility(new CanalCourierTriggeredAbility(effect));

    }

    private CanalCourier(final CanalCourier card) {
        super(card);
    }

    @Override
    public CanalCourier copy() {
        return new CanalCourier(this);
    }
}

class CanalCourierTriggeredAbility extends TriggeredAbilityImpl {

    public CanalCourierTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
        setTriggerPhrase("Whenever {this} and another creature attack different players, ");
    }

    public CanalCourierTriggeredAbility(final CanalCourierTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CanalCourierTriggeredAbility copy() {
        return new CanalCourierTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // Attacking a planeswalker isn't the same thing as attacking a player.
        // Both Canal Courier and the other creature must attack different players for the last ability to trigger.
        Permanent permanent = game.getPermanent(getSourceId());
        if (permanent != null && permanent.isAttacking()) {
            UUID sourceDefenderId = game.getCombat().getDefenderId(permanent.getId());
            Player attackedPlayer = game.getPlayer(sourceDefenderId);
            if (attackedPlayer != null) {
                for (UUID attacker : game.getCombat().getAttackers()) {
                    if (!Objects.equals(attacker, permanent.getId())) {
                        UUID defenderId = game.getCombat().getDefenderId(attacker);
                        Player attackedPlayer2 = game.getPlayer(defenderId);
                        if (attackedPlayer2 != null && attackedPlayer.getId().equals(attackedPlayer2.getId())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
