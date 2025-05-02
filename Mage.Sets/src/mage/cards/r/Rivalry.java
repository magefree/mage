
package mage.cards.r;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Plopman
 */
public final class Rivalry extends CardImpl {

    public Rivalry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");


        // At the beginning of each player's upkeep, if that player controls more lands than each other player, Rivalry deals 2 damage to them.
        this.addAbility(new RivalryTriggeredAbility());
        
    }

    private Rivalry(final Rivalry card) {
        super(card);
    }

    @Override
    public Rivalry copy() {
        return new Rivalry(this);
    }
}

class RivalryTriggeredAbility extends TriggeredAbilityImpl {

    public RivalryTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(2), false);
    }

    private RivalryTriggeredAbility(final RivalryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RivalryTriggeredAbility copy() {
        return new RivalryTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (getTargets().isEmpty()) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
            }
        }
        return true;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        UUID player = game.getActivePlayerId();
        int land = game.getBattlefield().countAll(StaticFilters.FILTER_LAND, player, game);
        if(player != null){
            for(UUID opponent : game.getOpponents(player)){
                if(land <= game.getBattlefield().countAll(StaticFilters.FILTER_LAND, opponent, game)){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of each player's upkeep, if that player controls more lands than each other player, Rivalry deals 2 damage to them.";
    }
}