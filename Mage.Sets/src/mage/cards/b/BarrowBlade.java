package mage.cards.b;

import java.util.UUID;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.LoseAllAbilitiesTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TiagoMDG
 */
public final class BarrowBlade extends CardImpl {

    public BarrowBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(1, 1)));

        // Whenever equipped creature blocks or becomes blocked by a creature, that creature loses all abilities until end of turn.
        this.addAbility(new BarrowBladeBlocksOrBecomesBlockedTriggeredAbility());


        // Equip {1}
        this.addAbility(new EquipAbility(1, true));
    }

    private BarrowBlade(final BarrowBlade card) {
        super(card);
    }

    @Override
    public BarrowBlade copy() {
        return new BarrowBlade(this);
    }
}

class BarrowBladeBlocksOrBecomesBlockedTriggeredAbility extends TriggeredAbilityImpl {

    public BarrowBladeBlocksOrBecomesBlockedTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseAllAbilitiesTargetEffect(Duration.EndOfTurn));
    }

    public BarrowBladeBlocksOrBecomesBlockedTriggeredAbility(final BarrowBladeBlocksOrBecomesBlockedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent equipment = game.getPermanent(sourceId);
        if (equipment != null && equipment.getAttachedTo() != null) {
            if (event.getSourceId().equals(equipment.getAttachedTo())) {
                Permanent blocks = game.getPermanent(event.getTargetId());
                if (blocks != null) {
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getTargetId(), game));
                    }
                    return true;
                }
                return false;
            }
            if (event.getTargetId().equals(equipment.getAttachedTo())) {
                Permanent blockedBy = game.getPermanent(event.getSourceId());
                if (blockedBy != null) {
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getSourceId(), game));
                    }
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature blocks or becomes blocked by a creature, that creature loses all abilities until end of turn.";
    }

    @Override
    public BarrowBladeBlocksOrBecomesBlockedTriggeredAbility copy() {
        return new BarrowBladeBlocksOrBecomesBlockedTriggeredAbility(this);
    }
}
