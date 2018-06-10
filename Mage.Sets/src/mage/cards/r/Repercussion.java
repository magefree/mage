
package mage.cards.r;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author cbrianhill
 */
public final class Repercussion extends CardImpl {

    public Repercussion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}{R}");

        // Whenever a creature is dealt damage, Repercussion deals that much damage to that creature's controller.
        this.addAbility(new RepercussionTriggeredAbility(new RepercussionEffect()));
    }

    public Repercussion(final Repercussion card) {
        super(card);
    }

    @Override
    public Repercussion copy() {
        return new Repercussion(this);
    }
}

class RepercussionTriggeredAbility extends TriggeredAbilityImpl {
    
    static final String PLAYER_DAMAGE_AMOUNT_KEY = "playerDamage";
    static final String TRIGGERING_CREATURE_KEY = "triggeringCreature";
    
    public RepercussionTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }
    
    public RepercussionTriggeredAbility(final RepercussionTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_CREATURE;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        for(Effect effect : getEffects()) {
            effect.setValue(PLAYER_DAMAGE_AMOUNT_KEY, event.getAmount());
            effect.setValue(TRIGGERING_CREATURE_KEY, new MageObjectReference(event.getTargetId(), game));
        }
        return true;
    }
    
    @Override
    public String getRule() {
        return "Whenever a creature is dealt damage, {this} deals that much damage to that creature's controller.";
    }

    @Override
    public TriggeredAbility copy() {
        return new RepercussionTriggeredAbility(this);
    }
}

class RepercussionEffect extends OneShotEffect {
    
    public RepercussionEffect() {
        super(Outcome.Damage);
    }
    
    public RepercussionEffect(final RepercussionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Integer playerDamage = (Integer)this.getValue(RepercussionTriggeredAbility.PLAYER_DAMAGE_AMOUNT_KEY);
        MageObjectReference mor = (MageObjectReference)this.getValue(RepercussionTriggeredAbility.TRIGGERING_CREATURE_KEY);        
        if (playerDamage != null && mor != null) {
            Permanent creature = mor.getPermanentOrLKIBattlefield(game);
            if (creature != null) {
                Player player = game.getPlayer(creature.getControllerId());
                if (player != null) {
                    player.damage(playerDamage, source.getSourceId(), game, false, true);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public Effect copy() {
        return new RepercussionEffect(this);
    }
    
}
