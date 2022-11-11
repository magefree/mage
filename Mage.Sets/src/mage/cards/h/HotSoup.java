
package mage.cards.h;

import java.util.Objects;
import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public final class HotSoup extends CardImpl {

    public HotSoup(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature can't be blocked.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedAttachedEffect(AttachmentType.EQUIPMENT)));
        
        // Whenever equipped creature is dealt damage, destroy it.
        this.addAbility(new HotSoupTriggeredAbility());
        
        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(3)));
    }

    private HotSoup(final HotSoup card) {
        super(card);
    }

    @Override
    public HotSoup copy() {
        return new HotSoup(this);
    }
}

class HotSoupTriggeredAbility extends TriggeredAbilityImpl {
    
    HotSoupTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(), false);
        setTriggerPhrase("Whenever equipped creature is dealt damage, ");
    }
    
    HotSoupTriggeredAbility(final HotSoupTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public HotSoupTriggeredAbility copy() {
        return new HotSoupTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent equipment = game.getPermanent(this.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            if (Objects.equals(event.getTargetId(), equipment.getAttachedTo())) {
                this.getEffects().setTargetPointer(new FixedTarget(equipment.getAttachedTo(), game));
                return true;
            }
        }
        return false;
    }
}
