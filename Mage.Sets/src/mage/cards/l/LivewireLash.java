
package mage.cards.l;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
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
import mage.game.stack.Spell;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author North
 */
public final class LivewireLash extends CardImpl {

    public LivewireLash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 0)));
        // and has "Whenever this creature becomes the target of a spell, this creature deals 2 damage to any target."
        LivewireLashAbility ability = new LivewireLashAbility();
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ability, AttachmentType.EQUIPMENT)));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2), false));
    }

    private LivewireLash(final LivewireLash card) {
        super(card);
    }

    @Override
    public LivewireLash copy() {
        return new LivewireLash(this);
    }
}

class LivewireLashAbility extends TriggeredAbilityImpl {

    public LivewireLashAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(2));
    }

    public LivewireLashAbility(final LivewireLashAbility ability) {
        super(ability);
    }

    @Override
    public LivewireLashAbility copy() {
        return new LivewireLashAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(sourceId)) {
            if (game.getObject(event.getSourceId()) instanceof Spell) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever this creature becomes the target of a spell, this creature deals 2 damage to any target.";
    }
}
