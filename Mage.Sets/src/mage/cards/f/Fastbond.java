
package mage.cards.f;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public final class Fastbond extends CardImpl {

    public Fastbond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}");

        // You may play any number of additional lands on each of your turns.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayAdditionalLandsControllerEffect(Integer.MAX_VALUE, Duration.WhileOnBattlefield)));
        // Whenever you play a land, if it wasn't the first land you played this turn, Fastbond deals 1 damage to you.
        this.addAbility(new PlayALandTriggeredAbility());
    }

    private Fastbond(final Fastbond card) {
        super(card);
    }

    @Override
    public Fastbond copy() {
        return new Fastbond(this);
    }
}

class PlayALandTriggeredAbility extends TriggeredAbilityImpl {

    public PlayALandTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageControllerEffect(1), false);
    }

    private PlayALandTriggeredAbility(final PlayALandTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LAND_PLAYED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId());
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Player player = game.getPlayer(this.getControllerId());
        if (player != null) {
            if (player.getLandsPlayed() != 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public PlayALandTriggeredAbility copy() {
        return new PlayALandTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you play a land, if it wasn't the first land you played this turn, {this} deals 1 damage to you";
    }

}
