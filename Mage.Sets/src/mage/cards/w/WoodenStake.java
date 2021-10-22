package mage.cards.w;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author nantuko
 */
public final class WoodenStake extends CardImpl {

    public WoodenStake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(1)));

        // Equipped creature gets +1/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(1, 0)));

        // Whenever equipped creature blocks or becomes blocked by a Vampire, destroy that creature. It can't be regenerated.
        this.addAbility(new WoodenStakeBlocksOrBecomesBlockedTriggeredAbility());
    }

    private WoodenStake(final WoodenStake card) {
        super(card);
    }

    @Override
    public WoodenStake copy() {
        return new WoodenStake(this);
    }
}

class WoodenStakeBlocksOrBecomesBlockedTriggeredAbility extends TriggeredAbilityImpl {

    public WoodenStakeBlocksOrBecomesBlockedTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(true), false);
    }

    public WoodenStakeBlocksOrBecomesBlockedTriggeredAbility(final WoodenStakeBlocksOrBecomesBlockedTriggeredAbility ability) {
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
                if (blocks != null && blocks.hasSubtype(SubType.VAMPIRE, game)) {
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getTargetId(), game));
                    }
                    return true;
                }
                return false;
            }
            if (event.getTargetId().equals(equipment.getAttachedTo())) {
                Permanent blockedBy = game.getPermanent(event.getSourceId());
                if (blockedBy != null && blockedBy.hasSubtype(SubType.VAMPIRE, game)) {
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
        return "Whenever equipped creature blocks or becomes blocked by a Vampire, destroy that creature. It can't be regenerated.";
    }

    @Override
    public WoodenStakeBlocksOrBecomesBlockedTriggeredAbility copy() {
        return new WoodenStakeBlocksOrBecomesBlockedTriggeredAbility(this);
    }
}
