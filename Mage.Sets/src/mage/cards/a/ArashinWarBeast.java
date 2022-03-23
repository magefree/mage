
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockingPredicate;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author fireshoes
 */
public final class ArashinWarBeast extends CardImpl {

    public ArashinWarBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever Arashin War Beast deals combat damage to one or more blockers, manifest the top card of your library.
        this.addAbility(new ArashinWarBeastTriggeredAbility(new ManifestEffect(1), false));

    }

    private ArashinWarBeast(final ArashinWarBeast card) {
        super(card);
    }

    @Override
    public ArashinWarBeast copy() {
        return new ArashinWarBeast(this);
    }
}

class ArashinWarBeastTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("one or more blockers");
    
    static {
        filter.add(BlockingPredicate.instance);
    }
    
    boolean usedForCombatDamageStep;
            
    public ArashinWarBeastTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.usedForCombatDamageStep = false;
    }

    public ArashinWarBeastTriggeredAbility(final ArashinWarBeastTriggeredAbility ability) {
        super(ability);
        this.usedForCombatDamageStep = ability.usedForCombatDamageStep;
    }

    @Override
    public ArashinWarBeastTriggeredAbility copy() {
        return new ArashinWarBeastTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT || event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_POST ;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PERMANENT &&
                event.getSourceId().equals(this.sourceId) && 
                ((DamagedEvent) event).isCombatDamage() &&
                !usedForCombatDamageStep) {
            Permanent creature = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (creature == null || !filter.match(creature, getControllerId(), this, game)) {
                return false;
            }
            // trigger only once per combat damage step
            usedForCombatDamageStep = true;
            return true;
                    
        } 
        if (event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_POST) {
            usedForCombatDamageStep = false;
        }        
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever {this} deals combat damage to one or more blockers, " ;
    }
}
