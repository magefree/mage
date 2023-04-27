
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author fireshoes
 */
public final class SunhomeEnforcer extends CardImpl {

    public SunhomeEnforcer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{W}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever Sunhome Enforcer deals combat damage, you gain that much life.
        this.addAbility(new SunhomeEnforcerTriggeredAbility());
        
        // {1}{R}: Sunhome Enforcer gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{R}")));
    }

    private SunhomeEnforcer(final SunhomeEnforcer card) {
        super(card);
    }

    @Override
    public SunhomeEnforcer copy() {
        return new SunhomeEnforcer(this);
    }
}

class SunhomeEnforcerTriggeredAbility extends TriggeredAbilityImpl {

    public SunhomeEnforcerTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }
    
    public SunhomeEnforcerTriggeredAbility(final SunhomeEnforcerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SunhomeEnforcerTriggeredAbility copy() {
        return new SunhomeEnforcerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedEvent damageEvent = (DamagedEvent) event;
        if (damageEvent.isCombatDamage()) {
            if (event.getSourceId().equals(this.sourceId)) {
                this.getEffects().clear();
                this.getEffects().add(new GainLifeEffect(damageEvent.getAmount()));
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage, you gain that much life.";
    }
}
